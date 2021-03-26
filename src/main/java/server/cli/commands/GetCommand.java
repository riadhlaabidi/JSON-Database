package server.cli.commands;

import server.database.Database;

public class GetCommand implements Command {

    private final String key;
    private String result;

    public GetCommand(String key) {
        this.key = key;
    }

    public String getResult() {
        return result;
    }

    @Override
    public void execute() {
        result = Database.INSTANCE.get(key);
    }
}
