package de.lunoro.tictactoe;

import de.lunoro.tictactoe.commands.AcceptCommand;
import de.lunoro.tictactoe.commands.InviteCommand;
import de.lunoro.tictactoe.commands.SpectateCommand;
import de.lunoro.tictactoe.config.Config;
import de.lunoro.tictactoe.config.ConfigContainer;
import de.lunoro.tictactoe.game.GameContainer;
import de.lunoro.tictactoe.listeners.GameEndListener;
import de.lunoro.tictactoe.listeners.InventoryClickListener;
import de.lunoro.tictactoe.listeners.InventoryCloseListener;
import de.lunoro.tictactoe.listeners.MarkListener;
import de.lunoro.tictactoe.messages.DefaultConfigRegistry;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class TicTacToe extends JavaPlugin {

    private ConfigContainer configContainer;
    private DefaultConfigRegistry defaultConfigRegistry;
    private GameContainer gameContainer;
    private Config config;

    @Override
    public void onLoad() {
        init();
    }

    private void init() {
        saveResource("config.yml", false);
        configContainer = new ConfigContainer(this);
        config = configContainer.getFile("config");
        defaultConfigRegistry = new DefaultConfigRegistry(config);
        gameContainer = new GameContainer(this, config);
    }

    @Override
    public void onEnable() {
        registerCommands();
        registerListeners();
    }

    private void registerCommands() {
        Bukkit.getPluginCommand("invite").setExecutor(new InviteCommand(gameContainer, defaultConfigRegistry));
        Bukkit.getPluginCommand("accept").setExecutor(new AcceptCommand(gameContainer, defaultConfigRegistry));
        Bukkit.getPluginCommand("spectate").setExecutor(new SpectateCommand(gameContainer, defaultConfigRegistry));
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(gameContainer), this);
        Bukkit.getPluginManager().registerEvents(new InventoryCloseListener(gameContainer), this);
        Bukkit.getPluginManager().registerEvents(new GameEndListener(gameContainer, this), this);
        Bukkit.getPluginManager().registerEvents(new MarkListener(), this);
    }

}
