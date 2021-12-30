package de.lunoro.tictactoe.listeners;

import de.lunoro.tictactoe.game.Game;
import de.lunoro.tictactoe.game.GameContainer;
import de.lunoro.tictactoe.game.gameinventory.GameInventory;
import de.lunoro.tictactoe.game.tictactoe.mark.Mark;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

@AllArgsConstructor
public class InventoryClickListener implements Listener {

    private final GameContainer gameContainer;

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        Game game = gameContainer.getGame(player);
        GameInventory gameInventory = game.getGameInventory();

        if (!event.getInventory().equals(gameInventory.getInventory())) {
            return;
        }

        if (!game.isTurnOfPlayerOne() && player.equals(game.getPlayerTwo())) {
            System.out.println("Player two clicked");
            gameInventory.clickBlock(event, Mark.Y);
        }

        if (game.isTurnOfPlayerOne() && player.equals(game.getPlayerOne())) {
            gameInventory.clickBlock(event, Mark.X);
        }

        event.setCancelled(true);
    }
}
