package de.lunoro.tictactoe.commands;

import de.lunoro.tictactoe.game.Game;
import de.lunoro.tictactoe.game.GameContainer;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public class AcceptCommand implements CommandExecutor {

    private final GameContainer gameContainer;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        if (!player.hasPermission("tictactoe.accept")) {
            player.sendMessage("You dont have enough permissions to do that.");
            return false;
        }

        if (args.length != 1) {
            player.sendMessage("Not enough arguments.");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage("This player does not exist.");
            return false;
        }

        Game pendingInviteGame = gameContainer.getPendingInviteGame(player, target);

        if (pendingInviteGame == null) {
            player.sendMessage("You dont have a pending invite from that player.");
            return false;
        }

        pendingInviteGame.acceptInvite(player);
        return true;
    }
}
