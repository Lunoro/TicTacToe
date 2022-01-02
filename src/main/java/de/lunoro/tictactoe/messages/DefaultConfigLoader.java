package de.lunoro.tictactoe.messages;

import de.lunoro.tictactoe.config.Config;
import lombok.AllArgsConstructor;

import java.util.HashMap;

@AllArgsConstructor
public class DefaultConfigLoader {

    private final Config config;

    public HashMap<String, String> loadMessages() {
        HashMap<String, String> returningMap = new HashMap<>();
        for (String key : config.getFileConfiguration().getKeys(false)) {
            returningMap.put(key, config.getFileConfiguration().getString(key));
        }
        return returningMap;
    }
}
