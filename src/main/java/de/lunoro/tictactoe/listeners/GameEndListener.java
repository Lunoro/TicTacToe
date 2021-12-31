package de.lunoro.tictactoe.listeners;

import de.lunoro.tictactoe.game.GameContainer;
import de.lunoro.tictactoe.game.gameevents.GameEndEvent;
import lombok.AllArgsConstructor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

@AllArgsConstructor
public class GameEndListener implements Listener {

    private final GameContainer gameContainer;
    private final Plugin plugin;

    @EventHandler
    public void onGameEnd(GameEndEvent event) {
        Player playerOne = event.getGame().getPlayerOne();
        Player playerTwo = event.getGame().getPlayerTwo();
        closeInventory(playerOne);
        closeInventory(playerTwo);
        playerOne.playSound(playerOne.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
        playerTwo.playSound(playerTwo.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
        gameContainer.removeGame(event.getGame());
    }

    public int closeInventory(Player player) {
        BukkitRunnable bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                player.closeInventory();
            }
        };
        bukkitTask.runTaskLater(plugin, 3 * 20);
        return bukkitTask.getTaskId();
    }
}
