package de.lunoro.tictactoe.listeners;

import de.lunoro.tictactoe.game.Game;
import de.lunoro.tictactoe.game.gameevents.MarkEvent;
import de.lunoro.tictactoe.game.tictactoe.TicTacToeGame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MarkListener implements Listener {

    @EventHandler
    public void onMark(MarkEvent event) {
        Game game = event.getGame();
        TicTacToeGame ticTacToe = game.getTicTacToe();

        if (ticTacToe.isDraw() || ticTacToe.isCompleted()) {
            game.stopGame();
        }
    }
}
