package de.lunoro.tictactoe.listeners;

import de.lunoro.tictactoe.game.Game;
import de.lunoro.tictactoe.game.GameContainer;
import de.lunoro.tictactoe.game.gameinventory.GameInventory;
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

        gameInventory.clickBlock(event, player);
        event.setCancelled(true);
    }
}
