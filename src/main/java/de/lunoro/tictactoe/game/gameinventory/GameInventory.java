package de.lunoro.tictactoe.game.gameinventory;

import de.lunoro.tictactoe.game.Game;
import de.lunoro.tictactoe.game.tictactoe.TicTacToe;
import de.lunoro.tictactoe.game.tictactoe.mark.Mark;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GameInventory {

    @Getter
    private final Inventory inventory;
    @Getter
    private final TicTacToe ticTacToe;
    private final Game game;

    public GameInventory(TicTacToe ticTacToe, Game game) {
        this.ticTacToe = ticTacToe;
        this.game = game;
        this.inventory = Bukkit.createInventory(null, InventoryType.WORKBENCH);
        setupInventory();
    }

    private void setupInventory() {
        int i = 1;
        for (Mark[] marks : ticTacToe.getGameBoard()) {
            for (Mark mark : marks) {
                inventory.setItem(i, new ItemStack(Material.WHITE_WOOL));
                i++;
            }
        }
        // Shows the turn and which player can mark.
        inventory.setItem(0, new ItemStack(Material.NETHER_STAR));
    }

    public void clickBlock(InventoryClickEvent event, Mark mark) {
        final int slot = event.getSlot();
        System.out.println(ticTacToe.positionIsValid(slot));
        if (ticTacToe.positionIsValid(slot)) {
            System.out.println("Block was valid");
            ticTacToe.markPos(slot, mark);
            if (mark.equals(Mark.X)) {
                event.getInventory().setItem(slot, new ItemStack(Material.RED_WOOL));
                game.setTurnOfPlayerOne(false);
            } else {
                event.getInventory().setItem(slot, new ItemStack(Material.BLUE_WOOL));
                game.setTurnOfPlayerOne(true);
            }
            stopGameIfCompleted();
        }
    }

    private void stopGameIfCompleted() {
        if (ticTacToe.isCompleted()) {
            game.stopGame();
        }
    }
}
