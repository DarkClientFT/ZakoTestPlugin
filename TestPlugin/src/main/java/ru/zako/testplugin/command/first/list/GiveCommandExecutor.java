package ru.zako.testplugin.command.first.list;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.zako.testplugin.command.SubCommand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GiveCommandExecutor implements SubCommand {
    public void onExecute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage(ChatColor.RED+"Только для игроков");
            return;
        }
        if (args.length < 3) {
            return ;
        }
        Material material = Material.getMaterial(args[1]);
        if (material == null) {
            p.sendMessage("Я не знаю, шо это такое");
            return;
        }
        try {
            final int amount = Integer.parseInt(args[2]);

            if (amount <= 0 || amount > 64) {
                p.sendMessage("1-64");
                return;
            }

            p.getInventory().addItem(new ItemStack(material, amount));
        } catch (NumberFormatException e) {
            return;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length == 2) {
            final List<String> result = new ArrayList<>();
            final String arg = args[1].toUpperCase();
            for (Material m: Material.values()) {
                final String name = m.name();
                if (name.startsWith(arg)) {
                    result.add(name);
                }
            }
            return result;
        }
        else if (args.length == 3) {
            return List.of("1", "5", "32", "64");
        }

        return Collections.emptyList();
    }
}
