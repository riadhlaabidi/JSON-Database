package server.cli;

import server.cli.commands.Command;

public class CommandExecutor {
    public void executeCommand(Command command) {
        command.execute();
    }
}
