package ru.zako.testplugin.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GameModeCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) {
            return false;
        }

        try {
            final GameMode mode = getByValue(Integer.parseInt(args[0]));
            if (mode == null) {
                return false;
            }

            Player target = getTargetPlayer(sender, args);
            if (target == null) {
                return true;
            }

            target.setGameMode(mode);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private Player getTargetPlayer(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length > 1) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Player offline");
                return null;
            }
            return target;
        } else {
            if (sender instanceof Player player) {
                return player;
            } else {
                sender.sendMessage("Players only");
                return null;
            }
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return List.of("0","1","2","3");
        }
        return null;
    }

    // Используем свой метод, т.к. аналогичный метод в GameMode
    // Является deprecated и может быть удален
    @Nullable
    private static GameMode getByValue(int value) {
        return switch (value) {
            case 0 -> GameMode.SURVIVAL;
            case 1 -> GameMode.CREATIVE;
            case 2 -> GameMode.ADVENTURE;
            case 3 -> GameMode.SPECTATOR;
            default -> null;
        };
    }
}
