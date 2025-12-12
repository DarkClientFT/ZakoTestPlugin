package ru.zako.testplugin.command;

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import ru.zako.testplugin.User;
import ru.zako.testplugin.storage.Storage;

@AllArgsConstructor
public class SetUserAge implements CommandExecutor {
    private final Storage storage;
    private final JavaPlugin plugin;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 2) return false;
        final String name = args[0];
        int age = getInt(args[1]);

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            User user = storage.getUser(name);
            if (user == null) {
                sender.sendMessage("Пользователя нет в таблице");
                return;
            }

            storage.setUserAge(name, age);

            sender.sendMessage("Возраст изменен");
        });

        return true;
    }

    private static int getInt(String arg) {
        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
