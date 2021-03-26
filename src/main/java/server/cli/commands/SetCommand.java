package server.cli.commands;

import server.database.Database;

public class SetCommand implements Command {

    private final String key;
    private final String value;

    public SetCommand(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public void execute() {
        Database.INSTANCE.set(key, value);
    }
}
