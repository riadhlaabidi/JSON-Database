package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import server.cli.CommandLineArgs;


public class Main {
    public static void main(String[] args) {
        CommandLineArgs cla = new CommandLineArgs();
        JCommander jCommander = new JCommander(cla);
        jCommander.setProgramName("JSON Database");
        try {
            jCommander.parse(args);
            Client.start(cla);
        } catch (ParameterException e) {
            System.out.println("Wrong parameter: " + e.getMessage());
            e.usage();
        }
    }
}
