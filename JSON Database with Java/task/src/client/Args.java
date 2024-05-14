package client;

import com.beust.jcommander.Parameter;

import java.io.File;

public class Args {
    @Parameter(names = {"--requestType","-t"})
    private String requestType;
    @Parameter(names = {"--index","-k"})
    private String index;
    @Parameter(names = {"--value","-v"})
    private String value;

    public String getJson() {
        return json;
    }

    @Parameter(names = {"--json","-in"})
    private String json;
    public String getRequestType() {
        return requestType;
    }

    public String getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }
}
