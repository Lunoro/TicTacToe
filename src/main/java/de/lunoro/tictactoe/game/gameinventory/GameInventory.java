package de.lunoro.tictactoe.game.gameinventory;

import de.lunoro.tictactoe.game.Game;
import de.lunoro.tictactoe.game.gameevents.MarkEvent;
import de.lunoro.tictactoe.game.tictactoe.TicTacToeGame;
import de.lunoro.tictactoe.game.tictactoe.mark.Mark;
import de.lunoro.tictactoe.itembuilder.ItemBuilder;
import de.lunoro.tictactoe.defaultconfig.DefaultConfigRegistry;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
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
    private final DefaultConfigRegistry defaultConfigRegistry;


    public GameInventory(TicTacToeGame ticTacToe, Game game) {
        this.ticTacToe = ticTacToe;
        this.game = game;
        this.defaultConfigRegistry = game.getDefaultConfigRegistry();
        this.inventory = Bukkit.createInventory(null, InventoryType.WORKBENCH, Component.text(defaultConfigRegistry.get("inventoryName")));
        setupInventory();
    }

    private void setupInventory() {
        int i = 1;
        String material = defaultConfigRegistry.get("normalItem");
        String itemName = defaultConfigRegistry.get("normalItemName");
        for (Mark[] marks : ticTacToe.getGameBoard()) {
            for (Mark mark : marks) {
                inventory.setItem(i, new ItemBuilder(Material.valueOf(material)).setName(itemName).toItemStack());
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
        String playerOneMaterial = defaultConfigRegistry.get("playerOneItem");
        String playerOneItemName = defaultConfigRegistry.get("playerOneItemName");

        String playerTwoMaterial = defaultConfigRegistry.get("playerTwoItem");
        String playerTwoItemName = defaultConfigRegistry.get("playerTwoItemName");

        if (mark.equals(Mark.X)) {
            inventory.setItem(slot,
                    new ItemBuilder(Material.valueOf(playerOneMaterial))
                            .setName(playerOneItemName)
                            .toItemStack());
            game.setTurnOfPlayerOne(false);
        } else {
            inventory.setItem(slot,
                    new ItemBuilder(Material.valueOf(playerTwoMaterial))
                            .setName(playerTwoItemName)
                            .toItemStack());
            game.setTurnOfPlayerOne(true);
        }
    }

    private void updateTurnItem() {
        ItemStack turnItem;
        String turnMaterial = defaultConfigRegistry.get("turnItem");
        String turnItemNamePlayerOne = defaultConfigRegistry.get("turnItemNamePlayerOne").replace("%p1", game.getPlayerOne().getName());
        String turnItemNamePlayerTwo = defaultConfigRegistry.get("turnItemNamePlayerTwo").replace("%p2", game.getPlayerTwo().getName());

        if (game.isTurnOfPlayerOne()) {
            turnItem = new ItemBuilder(Material.valueOf(turnMaterial)).setName(turnItemNamePlayerOne).toItemStack();
        } else {
            turnItem = new ItemBuilder(Material.valueOf(turnMaterial)).setName(turnItemNamePlayerTwo).toItemStack();
        }
        inventory.setItem(0, turnItem);
    }

    private void fireMarkEvent(int position, Player player, Mark mark) {
        MarkEvent markEvent = new MarkEvent(position, player, mark, game);
        Bukkit.getServer().getPluginManager().callEvent(markEvent);
    }
}