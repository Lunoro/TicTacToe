package de.lunoro.tictactoe.game.gameinventory;

import de.lunoro.tictactoe.game.Game;
import de.lunoro.tictactoe.game.gameevents.MarkEvent;
import de.lunoro.tictactoe.game.tictactoe.TicTacToeGame;
import de.lunoro.tictactoe.game.tictactoe.mark.Mark;
import de.lunoro.tictactoe.itembuilder.ItemBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GameInventory {

    @Getter
    private final Inventory inventory;
    private final TicTacToeGame ticTacToe;
    private final Game game;

    public GameInventory(TicTacToeGame ticTacToe, Game game) {
        this.ticTacToe = ticTacToe;
        this.game = game;
        this.inventory = Bukkit.createInventory(null, InventoryType.WORKBENCH);
        setupInventory();
    }

    private void setupInventory() {
        int i = 1;
        for (Mark[] marks : ticTacToe.getGameBoard()) {
            for (Mark mark : marks) {
                inventory.setItem(i, new ItemBuilder(Material.WHITE_WOOL).setName("Click me").toItemStack());
                i++;
            }
        }
        updateTurnItem();
    }

    public void clickBlock(InventoryClickEvent event, Player player) {
        Mark mark = Mark.X;
        if (game.getPlayerTwo().equals(player)) {
            mark = Mark.Y;
        }
        final int slot = event.getSlot();

        if (!ticTacToe.positionIsValid(slot)) {
            return;
        }

        if (!game.getPlayerOnTurn().equals(player)) {
            return;
        }

        ticTacToe.markPos(slot, mark);
        fireMarkEvent(slot, player, mark);
        game.playSound(Sound.BLOCK_NOTE_BLOCK_BASS);
        updateWool(event.getInventory(), slot, mark);
        updateTurnItem();
    }

    private void updateWool(Inventory inventory, int slot, Mark mark) {
        if (mark.equals(Mark.X)) {
            inventory.setItem(slot,
                    new ItemBuilder(Material.RED_WOOL)
                            .setName(ChatColor.RED + "Im red :I")
                            .toItemStack());
            game.setTurnOfPlayerOne(false);
        } else {
            inventory.setItem(slot,
                    new ItemBuilder(Material.BLUE_WOOL)
                            .setName(ChatColor.BLUE + "Im blue :I")
                            .toItemStack());
            game.setTurnOfPlayerOne(true);
        }
    }

    private void updateTurnItem() {
        ItemStack turnItem;
        if (game.isTurnOfPlayerOne()) {
            turnItem = new ItemBuilder(Material.NETHER_STAR).setName(ChatColor.RED + game.getPlayerOnTurn().getName() + "'s turn").toItemStack();
        } else {
            turnItem = new ItemBuilder(Material.NETHER_STAR).setName(ChatColor.BLUE + game.getPlayerOnTurn().getName() + "'s turn").toItemStack();
        }
        inventory.setItem(0, turnItem);
    }

    private void fireMarkEvent(int position, Player player, Mark mark) {
        MarkEvent markEvent = new MarkEvent(position, player, mark, game);
        Bukkit.getServer().getPluginManager().callEvent(markEvent);
    }
}