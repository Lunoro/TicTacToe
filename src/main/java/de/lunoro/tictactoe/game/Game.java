package de.lunoro.tictactoe.game;

import de.lunoro.tictactoe.game.gameevents.GameEndEvent;
import de.lunoro.tictactoe.game.tictactoe.TicTacToeGame;
import de.lunoro.tictactoe.game.gameinventory.GameInventory;
import de.lunoro.tictactoe.game.tictactoe.mark.Mark;
import de.lunoro.tictactoe.defaultconfig.DefaultConfigRegistry;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
public class Game {

    private final Player playerOne;
    private final Player playerTwo;
    @Setter
    private boolean isTurnOfPlayerOne;
    private GamePhase gamePhase;
    private final TicTacToeGame ticTacToe;
    private final DefaultConfigRegistry defaultConfigRegistry;
    private final GameInventory gameInventory;
    @Getter(value = AccessLevel.NONE)
    private final List<Player> spectatorList;

    public Game(Player playerOne, Player playerTwo, DefaultConfigRegistry defaultConfigRegistry) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.gamePhase = GamePhase.PENDING_INVITE;
        this.isTurnOfPlayerOne = randomizeStart();
        this.ticTacToe = new TicTacToeGame(3);
        this.defaultConfigRegistry = defaultConfigRegistry;
        this.gameInventory = new GameInventory(ticTacToe, this);
        this.spectatorList = new ArrayList<>();
    }

    private boolean randomizeStart() {
        Random r = new Random();
        int i = r.nextInt(0, 2);
        return i == 0;
    }

    public void startGame() {
        if (playerTwo != null) {
            gamePhase = GamePhase.RUNNING;
            openInventory();
        }
    }

    private void openInventory() {
        playerOne.openInventory(gameInventory.getInventory());
        playerTwo.openInventory(gameInventory.getInventory());
    }

    public void stopGameWithoutWinner() {
        fireEndGameEvent();
        gamePhase = GamePhase.END;
        sendMessage(defaultConfigRegistry.get("gameInterruptedError"));
    }

    public void stopGame() {
        fireEndGameEvent();
        gamePhase = GamePhase.END;
        if (ticTacToe.isDraw()) {
            sendMessage(defaultConfigRegistry.get("gameEndDraw"));
        } else {
            sendMessage(defaultConfigRegistry.get("gameEnd").replace("%w", getWinner().getName()));
        }
    }

    private void sendMessage(String message) {
        playerOne.sendMessage(message);
        playerTwo.sendMessage(message);

        for (Player player : spectatorList) {
            player.sendMessage(message);
        }
    }

    private void fireEndGameEvent() {
        GameEndEvent gameEndEvent = new GameEndEvent(this);
        Bukkit.getServer().getPluginManager().callEvent(gameEndEvent);
    }

    public void acceptInvite(Player playerTwo) {
        if (this.playerTwo == playerTwo) {
            startGame();
        }
    }

    public void playSound(Sound sound) {
        playerOne.playSound(playerOne.getLocation(), sound, 1f, 1f);
        playerTwo.playSound(playerTwo.getLocation(), sound, 1f, 1f);
    }

    public Player getPlayerOnTurn() {
        if (isTurnOfPlayerOne) {
            return playerOne;
        }
        return playerTwo;
    }

    public Player getWinner() {
        Mark winner = ticTacToe.getWinner();
        if (ticTacToe.getWinner() == null) {
            return null;
        }
        if (winner.equals(Mark.Y)) {
            return playerTwo;
        }
        return playerOne;
    }

    public void addSpectator(Player player) {
        spectatorList.add(player);
    }

    public void removeSpectator(Player player) {
        spectatorList.remove(player);
    }

    public void closeSpectatorInventory(Inventory inventory) {
        for (Player player : spectatorList) {
            if (inventory.equals(gameInventory.getInventory())) {
                player.closeInventory();
            }
        }
    }

    public boolean isSpectator(Player player) {
        return spectatorList.contains(player);
    }

    public boolean isInvitedPlayer(Player player) {
        return player.equals(playerTwo);
    }

    public boolean isOwner(Player player) {
        return player.equals(playerOne);
    }
}