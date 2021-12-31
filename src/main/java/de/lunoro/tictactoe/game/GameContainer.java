package de.lunoro.tictactoe.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class GameContainer {

    private final List<Game> gameList;
    private final Plugin plugin;

    public GameContainer(Plugin plugin) {
        gameList = new ArrayList<>();
        this.plugin = plugin;
    }

    public void createGame(Player playerOne, Player playerTwo) {
        final Game game = new Game(playerOne, playerTwo);
        addGame(game);
        deleteGameIfInvitationTimerIsExpired(game);
    }

    private void deleteGameIfInvitationTimerIsExpired(Game game) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (game.getGamePhase().equals(GamePhase.PENDING_INVITE)) {
                removeGame(game);
            }
        }, 30 * 20);
    }

    public void addGame(Game game) {
        gameList.add(game);
    }

    public void removeGame(Game game) {
        gameList.remove(game);
    }

    public Game getGame(Player player) {
        for (Game game : gameList) {
            if (game.getPlayerOne().equals(player) || game.getPlayerTwo().equals(player)) {
                return game;
            }
        }
        return null;
    }

    public Game getPendingInviteGame(Player player, Player playerWhoSentInvite) {
        for (Game game : gameList) {
            if (game.isInvitedPlayer(player) && game.isOwner(playerWhoSentInvite)) {
                return game;
            }
        }
        return null;
    }
}
