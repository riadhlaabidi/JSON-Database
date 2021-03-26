package server.cli.commands;

import server.database.Database;

public class DeleteCommand implements Command {

    private final String key;

    public DeleteCommand(String key) {
        this.key = key;
    }

    @Override
    public void execute() {
        Database.INSTANCE.delete(key);
    }
}
