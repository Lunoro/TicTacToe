package de.lunoro.tictactoe;

import de.lunoro.tictactoe.commands.AcceptCommand;
import de.lunoro.tictactoe.commands.InviteCommand;
import de.lunoro.tictactoe.game.GameContainer;
import de.lunoro.tictactoe.listeners.InventoryClickListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class TicTacToe extends JavaPlugin {

    private GameContainer gameContainer;

    @Override
    public void onLoad() {
        init();
    }

    private void init() {
        saveResource("config.yml", false);
        gameContainer = new GameContainer(this);
    }

    @Override
    public void onEnable() {
        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {

    }

    private void registerCommands() {
        Bukkit.getPluginCommand("invite").setExecutor(new InviteCommand(gameContainer));
        Bukkit.getPluginCommand("accept").setExecutor(new AcceptCommand(gameContainer));
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(gameContainer), this);
    }

}
