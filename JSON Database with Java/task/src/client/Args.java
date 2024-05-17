package client;

import com.beust.jcommander.Parameter;

public class Args {
    @Parameter(names = {"--requestType", "-t"})
    private String requestType;
    @Parameter(names = {"--index", "-k"})
    private String index;
    @Parameter(names = {"--value", "-v"})
    private String value;
    @Parameter(names = {"--file", "-in"})
    private String file;

    public String getFile() {
        return file;
    }

    public String getRequestType() {
        return requestType;
    }

    public String getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }


    public Args() {

    }
}
