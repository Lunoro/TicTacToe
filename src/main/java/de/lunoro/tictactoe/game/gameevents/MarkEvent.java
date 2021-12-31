package de.lunoro.tictactoe.game.gameevents;

import de.lunoro.tictactoe.game.Game;
import de.lunoro.tictactoe.game.tictactoe.mark.Mark;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class MarkEvent extends Event {

    @Getter
    private final int position;
    @Getter
    private final Player player;
    @Getter
    private final Mark mark;
    @Getter
    private final Game game;
    private static final HandlerList handlerList = new HandlerList();

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
