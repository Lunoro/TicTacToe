package de.lunoro.tictactoe.game.gameevents;

import de.lunoro.tictactoe.game.Game;
import de.lunoro.tictactoe.game.tictactoe.mark.Mark;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@Getter
public class MarkEvent extends Event {

    private final int position;
    private final Player player;
    private final Mark mark;
    private final Game game;
    @Getter(value = AccessLevel.NONE)
    private static final HandlerList handlerList = new HandlerList();

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
