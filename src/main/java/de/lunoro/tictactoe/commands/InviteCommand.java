package de.lunoro.tictactoe.commands;

import de.lunoro.tictactoe.config.Config;
import de.lunoro.tictactoe.game.GameContainer;
import de.lunoro.tictactoe.messages.DefaultConfigRegistry;
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
    private final DefaultConfigRegistry defaultConfigRegistry;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        if (!player.hasPermission("tictactoe.invite")) {
            player.sendMessage(defaultConfigRegistry.get("permissionError"));
            return false;
        }

        if (args.length != 1) {
            player.sendMessage(defaultConfigRegistry.get("argumentError"));
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage(defaultConfigRegistry.get("playerNotFoundError"));
            return false;
        }

        if (target.equals(player)) {
            player.sendMessage(defaultConfigRegistry.get("selfInviteError"));
            return false;
        }

        if (gameContainer.getGame(player) != null) {
            player.sendMessage(defaultConfigRegistry.get("createGameWhileOneIsRunningError"));
            return false;
        }

        String invitationSentMessage = defaultConfigRegistry.get("invitationSentMessage").replace("%t", target.getName());
        player.sendMessage(invitationSentMessage);
        String invitationReceivedMessage = defaultConfigRegistry.get("invitationReceivedMessage")
                .replace("%t", target.getName())
                .replace("%p", player.getName());
        target.sendMessage(invitationReceivedMessage);
        gameContainer.createGame(player, target);
        return true;
    }
}
