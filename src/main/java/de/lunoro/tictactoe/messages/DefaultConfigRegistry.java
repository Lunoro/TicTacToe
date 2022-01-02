package de.lunoro.tictactoe.messages;

import de.lunoro.tictactoe.config.Config;

import java.util.Map;

public class DefaultConfigRegistry {

    private final Map<String, String> configMap;

    public DefaultConfigRegistry(Config defaultConfig) {
        DefaultConfigLoader defaultConfigLoader = new DefaultConfigLoader(defaultConfig);
        this.configMap = defaultConfigLoader.loadMessages();
    }

    public String get(String target) {
        return configMap.get(target);
    }
}
