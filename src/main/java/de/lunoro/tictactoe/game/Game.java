package de.lunoro.tictactoe.game;

import de.lunoro.tictactoe.game.tictactoe.TicTacToe;
import de.lunoro.tictactoe.game.gameinventory.GameInventory;
import lombok.Getter;
import lombok.Setter;
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
    private GamePhase gamePhase;

    public Game(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.gamePhase = GamePhase.PENDING_INVITE;
        this.gameInventory = new GameInventory(new TicTacToe(3), this);
        this.isTurnOfPlayerOne = true;
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

    public void stopGame() {
        playerOne.closeInventory();
        playerTwo.closeInventory();
        playerOne.sendMessage("The Game was won.");
        playerTwo.sendMessage("The Game was won.");
    }

    public void acceptInvite(Player playerTwo) {
        if (this.playerTwo == playerTwo) {
            startGame();
        }
    }

    public boolean isInvitedPlayer(Player player) {
        return player.equals(playerTwo);
    }

    public boolean isInactive() {
        return gamePhase == GamePhase.PENDING_INVITE;
    }
}