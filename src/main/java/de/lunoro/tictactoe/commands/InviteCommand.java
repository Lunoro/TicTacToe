package de.lunoro.tictactoe.commands;

import de.lunoro.tictactoe.game.GameContainer;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public class InviteCommand implements CommandExecutor {

    private final GameContainer gameContainer;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        if (!player.hasPermission("tictactoe.invite")) {
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

        if (target.equals(player)) {
            player.sendMessage("You cannot invite yourself.");
            return false;
        }

        if (gameContainer.getGame(player) != null) {
            player.sendMessage("You cannot create a game while one is running.");
            return false;
        }

        player.sendMessage("You've sent an invitation to " + target.getName() + ".");
        target.sendMessage("You were invited from " + player.getName() + " to a TicTacToe match. You have 30 seconds to accept the invite with /accept [" + player.getName() + "].");
        gameContainer.createGame(player, target);
        return true;
    }
}
