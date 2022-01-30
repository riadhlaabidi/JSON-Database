package server.cli.commands;

import com.google.gson.JsonElement;
import server.Database;

public class SetCommand implements Command {

    private final JsonElement key;
    private final JsonElement value;

    public SetCommand(JsonElement key, JsonElement value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public void execute() {
        Database.INSTANCE.set(key, value);
    }
}
