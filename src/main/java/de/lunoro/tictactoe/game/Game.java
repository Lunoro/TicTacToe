package de.lunoro.tictactoe.game;

import de.lunoro.tictactoe.game.gameevents.GameEndEvent;
import de.lunoro.tictactoe.game.tictactoe.TicTacToeGame;
import de.lunoro.tictactoe.game.gameinventory.GameInventory;
import de.lunoro.tictactoe.game.tictactoe.mark.Mark;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Random;

@Getter
public class Game {

    private final Player playerOne;
    private final Player playerTwo;
    private final GameInventory gameInventory;
    @Setter
    private boolean isTurnOfPlayerOne;
    private GamePhase gamePhase;
    private final TicTacToeGame ticTacToe;

    public Game(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.gamePhase = GamePhase.PENDING_INVITE;
        this.isTurnOfPlayerOne = randomizeStart();
        this.ticTacToe = new TicTacToeGame(3);
        this.gameInventory = new GameInventory(ticTacToe, this);
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
        sendMessage("Game was interrupted.");
    }

    public void stopGame() {
        fireEndGameEvent();
        gamePhase = GamePhase.END;
        if (ticTacToe.isDraw()) {
            sendMessage("The game is a draw. GG");
        } else {
            sendMessage(getWinner().getName() + " has won.");
        }
    }

    private void sendMessage(String message) {
        playerOne.sendMessage(message);
        playerTwo.sendMessage(message);
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

    public boolean isInvitedPlayer(Player player) {
        return player.equals(playerTwo);
    }

    public boolean isOwner(Player player) {
        return player.equals(playerOne);
    }
}