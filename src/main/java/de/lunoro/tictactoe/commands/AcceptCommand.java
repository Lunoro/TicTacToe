package de.lunoro.tictactoe.commands;

import de.lunoro.tictactoe.game.Game;
import de.lunoro.tictactoe.game.GameContainer;
import lombok.AllArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public class AcceptCommand implements CommandExecutor {

    private final GameContainer gameContainer;
    // TODO: 30.12.2021 add player arg

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        if (!player.hasPermission("tictactoe.accept")) {
            player.sendMessage("You dont have enough permissions to do that.");
            return false;
        }

        Game pendingInviteGame = gameContainer.getPendingInviteGame(player);

        if (pendingInviteGame == null) {
            player.sendMessage("You dont have a pending invite to a game.");
            return false;
        }

        pendingInviteGame.acceptInvite(player);
        return true;
    }
}
