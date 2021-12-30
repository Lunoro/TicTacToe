package de.lunoro.tictactoe.game;

import de.lunoro.tictactoe.game.gameevents.GameEndEvent;
import de.lunoro.tictactoe.game.tictactoe.TicTacToeGame;
import de.lunoro.tictactoe.game.gameinventory.GameInventory;
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

    public Game(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.gamePhase = GamePhase.PENDING_INVITE;
        this.isTurnOfPlayerOne = true;
        this.gameInventory = new GameInventory(new TicTacToeGame(3), this);
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
    }

    public void stopGame() {
        fireEndGameEvent();
        gamePhase = GamePhase.END;
        playerOne.closeInventory();
        playerTwo.closeInventory();
        playerOne.sendMessage("The Game was won.");
        playerTwo.sendMessage("The Game was won.");
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

    public boolean isInvitedPlayer(Player player) {
        return player.equals(playerTwo);
    }
}