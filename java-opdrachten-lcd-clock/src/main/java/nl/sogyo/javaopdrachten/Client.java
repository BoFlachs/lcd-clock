package nl.sogyo.javaopdrachten;

import com.beust.jcommander.JCommander;

/*  Om de verschillende commando's te testen dient de 
launch.json in de .vscode directory aangepast te worden.
 */

public class Client {
    public static void main(String[] args) {
        MyOptions options = new MyOptions();
        JCommander helloCmd = JCommander.newBuilder()
            .addObject(options)
            .build();
        helloCmd.parse(args);

        int size = options.getSize();

        Clock clock;
        if(options.getFormat12()){
            clock = new Clock(size, Clock.Format.FORMAT12);
        } else {
            clock = new Clock(size, Clock.Format.FORMAT24);
        }

        // clock.showTime();
        clock.runClockTillCrash();
    }
}
