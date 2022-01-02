package de.lunoro.tictactoe.commands;

import de.lunoro.tictactoe.config.Config;
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
    private final Config messagesConfig;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        if (!player.hasPermission("tictactoe.invite")) {
            player.sendMessage(messagesConfig.getString("permissionError"));
            return false;
        }

        if (args.length != 1) {
            player.sendMessage(messagesConfig.getString("argumentError"));
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage(messagesConfig.getString("playerNotFoundError"));
            return false;
        }

        if (target.equals(player)) {
            player.sendMessage(messagesConfig.getString("selfInviteError"));
            return false;
        }

        if (gameContainer.getGame(player) != null) {
            player.sendMessage(messagesConfig.getString("createGameWhileOneIsRunningError"));
            return false;
        }

        String invitationSentMessage = messagesConfig.getString("invitationSentMessage").replace("%t", target.getName());
        player.sendMessage(invitationSentMessage);
        String invitationReceivedMessage = messagesConfig.getString("invitationReceivedMessage")
                .replace("%t", target.getName())
                .replace("%p", player.getName());
        target.sendMessage(invitationReceivedMessage);
        gameContainer.createGame(player, target);
        return true;
    }
}
