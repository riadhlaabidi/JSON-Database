package server.cli;

import com.beust.jcommander.Parameter;

public class CommandLineArgs {
    @Parameter(
            names = {"-t", "--type"},
            description = "Type of the request",
            order = 0)
    public String type;

    @Parameter(
            names = {"-k", "--key"},
            description = "Record key",
            order = 1)
    public String key;

    @Parameter(
            names = {"-v", "--value"},
            description = "Text value to add",
            order = 2)
    public String value;

    @Parameter(
            names = {"-in", "--input-file"},
            description = "File containing the request as json string",
            order = 3)
    public String filename;
}
