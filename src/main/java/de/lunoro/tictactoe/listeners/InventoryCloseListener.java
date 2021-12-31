package de.lunoro.tictactoe.listeners;

import de.lunoro.tictactoe.game.Game;
import de.lunoro.tictactoe.game.GameContainer;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

@AllArgsConstructor
public class InventoryCloseListener implements Listener {

    private final GameContainer gameContainer;

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) {
            return;
        }

        if (event.getReason().equals(InventoryCloseEvent.Reason.PLUGIN)) {
            return;
        }

        Player player = (Player) event.getPlayer();
        Game game = gameContainer.getGame(player);

        if (game == null) {
            return;
        }

        game.stopGameWithoutWinner();
    }
}
