package de.lunoro.tictactoe.listeners;

import de.lunoro.tictactoe.game.GameContainer;
import de.lunoro.tictactoe.game.gameevents.GameEndEvent;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@AllArgsConstructor
public class GameEndListener implements Listener {

    private final GameContainer gameContainer;

    @EventHandler
    public void onGameEnd(GameEndEvent event) {
        gameContainer.removeGame(event.getGame());
    }
}
