package de.lunoro.tictactoe.game.gameevents;

import de.lunoro.tictactoe.game.Game;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class GameEndEvent extends Event {

    @Getter
    private final Game game;
    private static final HandlerList handlerList = new HandlerList();

    public GameEndEvent(Game game) {
        this.game = game;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
