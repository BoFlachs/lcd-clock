package nl.sogyo.javaopdrachten;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Clock {
    public enum Format {FORMAT12, FORMAT24};
    private int size;
    private Format format;

    //  The HORIZONTAL state produces a single horizontal line. There
    //  are two types:
    // 
    //  0 - skip, no line necessary, just space fill
    //  1 - line required of given size
    // 
    //  The VERTICAL state produces a either a single right side line,
    //  a single left side line or a both lines.
    // 
    //  The idea of this coding comes from http://rubyquiz.com/quiz14.html
    //  0 - skip, no line necessary, just space fill
    //  1 - single right side line
    //  2 - single left side line
    //  3 - both lines
    private final int[][] numberFormats = {{1, 3, 0, 3, 1},
                                         {0, 1, 0, 1, 0},
                                         {1, 1, 1, 2, 1},
                                         {1, 1, 1, 1, 1},
                                         {0, 3, 1, 1, 0},
                                         {1, 2, 1, 1, 1},
                                         {1, 2, 1, 3, 1},
                                         {1, 1, 0, 1, 0},
                                         {1, 3, 1, 3, 1},
                                         {1, 3, 1, 1, 1}};
    private final int[][] letterFormats = {{1, 3, 1, 3, 1}, // A
                                           {1, 3, 1, 2, 0}, // P
                                           }; // M is nog te moeilijk

    public Clock(int size, Format format){
        this.size = size;
        this.format = format;
    }

    public void showTime(){
        ArrayList<String> lines = buildClock();

        for(String line: lines){
            System.out.println(line);
        } 
    }

    private ArrayList<String> buildClock(){
        String hourString;
        if(format == Format.FORMAT12){
            hourString = String.format("%tI", LocalTime.now()); 
        }else{
            hourString = String.format("%tH", LocalTime.now()); 
        }
        String minuteString = String.format("%tM", LocalTime.now()); 
        String secondString = String.format("%tS", LocalTime.now()); 
        String timeString = hourString + minuteString + secondString;
        int[] allNumbers = charArrayToIntArray(timeString.toCharArray());

        ArrayList<String> lines = new ArrayList<>();
        lines.add(addHorizontal(0, allNumbers));
        for(int i = 0; i < size; i++){
            lines.add(addVertical(1, allNumbers));
        }
        lines.add(addHorizontal(2, allNumbers));
        for(int i = 0; i < size; i++){
            lines.add(addVertical(3, allNumbers));
        }
        lines.add(addHorizontal(4, allNumbers));

        return lines;
    }

    // Deze functie komt van https://examples.javacodegeeks.com/convert-char-array-to-int-array-in-java/
    private int[] charArrayToIntArray(char[] charArray) {
        int[] intArray = new int[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            intArray[i] = Integer.parseInt(String.valueOf(charArray[i]));
        }
        return intArray;
    }

    private String addHorizontal(int lineNumber, int[] allNumbers){
        String line = "";

        if(format == Format.FORMAT12){
            String amOrPm = String.format("%tp", LocalTime.now());
            int index = (amOrPm.equals("am")) ? 0 : 1;            

            switch (letterFormats[index][lineNumber]) {
                case 0:
                    line += "  " + " ".repeat(size) + "  ";
                    break;
                case 1:
                    line += "  " + "-".repeat(size) + "  ";
                    break;
            }
        }

        for(int i = 0; i<allNumbers.length; i++){
            switch (numberFormats[allNumbers[i]][lineNumber]) {
                case 0:
                    line += "  " + " ".repeat(size) + "  ";
                    break;
                case 1:
                    line += "  " + "-".repeat(size) + "  ";
                    break;
            }
            if(i % 2 == 1 && i != allNumbers.length - 1){
                line += "   ";
            }
        } 
        return line;
    }
    
    private String addVertical(int lineNumber, int[] allNumbers){
        String line = "";
        
        if(format == Format.FORMAT12){
            String amOrPm = String.format("%tp", LocalTime.now());
            int index = (amOrPm.equals("am")) ? 0 : 1;            

            switch (letterFormats[index][lineNumber]) {
                case 0:
                    line += "  " + " ".repeat(size) + "  ";
                    break;
                case 1:
                    line += "  " + " ".repeat(size) + "| ";
                    break;
                case 2:
                    line += " |" + " ".repeat(size) + "  ";
                    break;
                case 3:
                    line += " |" + " ".repeat(size) + "| ";
                    break;
            }
        }

        for(int i = 0; i<allNumbers.length; i++){
            switch (numberFormats[allNumbers[i]][lineNumber]) {
                case 0:
                    line += "  " + " ".repeat(size) + "  ";
                    break;
                case 1:
                    line += "  " + " ".repeat(size) + "| ";
                    break;
                case 2:
                    line += " |" + " ".repeat(size) + "  ";
                    break;
                case 3:
                    line += " |" + " ".repeat(size) + "| ";
                    break;
            }
            if(i % 2 == 1 && i != allNumbers.length - 1){
                line += " --";
            }
        } 
        return line;
    }
   
    public void runClockTillCrash(){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                showTime();
                System.out.println("\n\n");
            }
        };

        timer.scheduleAtFixedRate(task, 0, 1000);
    }
}
