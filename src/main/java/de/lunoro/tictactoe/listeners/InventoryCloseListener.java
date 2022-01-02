package de.lunoro.tictactoe.listeners;

import de.lunoro.tictactoe.game.Game;
import de.lunoro.tictactoe.game.GameContainer;
import de.lunoro.tictactoe.game.GamePhase;
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
        if (!(event.getPlayer() instanceof Player player)) {
            return;
        }

        if (event.getReason().equals(InventoryCloseEvent.Reason.PLUGIN)) {
            return;
        }

        Game game = gameContainer.getGame(player);

        if (game == null) {
            return;
        }

        if (game.isSpectator(player)) {
            game.removeSpectator(player);
            return;
        }

        if (game.getGamePhase().equals(GamePhase.END)) {
            return;
        }

        game.stopGameWithoutWinner();
    }
}
