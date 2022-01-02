package de.lunoro.tictactoe.commands;

import de.lunoro.tictactoe.config.Config;
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
public class SpectateCommand implements CommandExecutor {

    private final GameContainer gameContainer;
    private final Config messagesConfig;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        if (!player.hasPermission("tictactoe.spectate")) {
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


        Game game = gameContainer.getGame(target);

        if (game == null) {
            player.sendMessage(messagesConfig.getString("gameNotFoundError"));
            return false;
        }

        String message = messagesConfig.getString("spectatingStartedMessage")
                .replace("%p1", game.getPlayerOne().getName())
                .replace("%p2", game.getPlayerTwo().getName());
        player.sendMessage(message);
        player.openInventory(game.getGameInventory().getInventory());
        game.addSpectator(player);
        return true;
    }
}
