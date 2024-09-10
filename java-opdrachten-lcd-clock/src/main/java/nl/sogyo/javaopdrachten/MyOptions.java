package nl.sogyo.javaopdrachten;

import com.beust.jcommander.Parameter;

public class MyOptions {
    @Parameter(names = {"--size", "-s"}, description="Size")
    private int size = 2;

    @Parameter(names = {"--12"}, description="Format12")
    private boolean format12;
    
    @Parameter(names = {"--24"}, description="Format24")
    private boolean format24;

    public int getSize() {
        return size;
    }

    public boolean getFormat12() {
        return format12;
    }

    public boolean getFormat24() {
        return format24;
    }
}
