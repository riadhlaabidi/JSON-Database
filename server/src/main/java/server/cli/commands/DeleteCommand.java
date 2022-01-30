package server.cli.commands;

import com.google.gson.JsonElement;
import server.Database;

public class DeleteCommand implements Command {

    private final JsonElement key;

    public DeleteCommand(JsonElement key) {
        this.key = key;
    }

    @Override
    public void execute() {
        Database.INSTANCE.delete(key);
    }
}
