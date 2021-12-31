package de.lunoro.tictactoe.game;

import de.lunoro.tictactoe.game.gameevents.GameEndEvent;
import de.lunoro.tictactoe.game.tictactoe.TicTacToeGame;
import de.lunoro.tictactoe.game.gameinventory.GameInventory;
import de.lunoro.tictactoe.game.tictactoe.mark.Mark;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Game {

    @Getter
    private final Player playerOne;
    @Getter
    private final Player playerTwo;
    @Getter
    private final GameInventory gameInventory;
    @Getter
    @Setter
    private boolean isTurnOfPlayerOne;
    @Getter
    private GamePhase gamePhase;
    @Getter
    private final TicTacToeGame ticTacToe;

    public Game(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.gamePhase = GamePhase.PENDING_INVITE;
        this.isTurnOfPlayerOne = true;
        this.ticTacToe = new TicTacToeGame(3);
        this.gameInventory = new GameInventory(ticTacToe, this);
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
        playerOne.closeInventory();
        playerTwo.closeInventory();
        sendMessage("Game was interrupted.");
    }

    public void stopGame() {
        fireEndGameEvent();
        gamePhase = GamePhase.END;
        playerOne.closeInventory();
        playerTwo.closeInventory();
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