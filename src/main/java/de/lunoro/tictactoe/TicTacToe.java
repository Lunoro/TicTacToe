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
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class TicTacToe extends JavaPlugin {

    private ConfigContainer configContainer;
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
        gameContainer = new GameContainer(this, config);
    }

    @Override
    public void onEnable() {
        registerCommands();
        registerListeners();
    }

    private void registerCommands() {
        Bukkit.getPluginCommand("invite").setExecutor(new InviteCommand(gameContainer, config));
        Bukkit.getPluginCommand("accept").setExecutor(new AcceptCommand(gameContainer, config));
        Bukkit.getPluginCommand("spectate").setExecutor(new SpectateCommand(gameContainer, config));
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(gameContainer), this);
        Bukkit.getPluginManager().registerEvents(new InventoryCloseListener(gameContainer), this);
        Bukkit.getPluginManager().registerEvents(new GameEndListener(gameContainer, this), this);
        Bukkit.getPluginManager().registerEvents(new MarkListener(), this);
    }

}
