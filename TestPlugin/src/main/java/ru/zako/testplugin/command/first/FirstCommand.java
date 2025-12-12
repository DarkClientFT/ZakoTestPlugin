package ru.zako.testplugin.command.first;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.zako.testplugin.command.LongCommandExecutor;
import ru.zako.testplugin.command.first.list.GiveCommandExecutor;

import java.util.List;

public class FirstCommand extends LongCommandExecutor {

    public FirstCommand() {
        addSubCommand(new GiveCommandExecutor(), new String[] {"give"}, new Permission("testplugin.fc.give"));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) return false;
        final SubCommandWrapper wrapper = getWrapperFromLabel(args[0]);
        if (wrapper == null) return false;

        if (!sender.hasPermission(wrapper.getPermission())) {
            sender.sendMessage(command.getPermissionMessage());
            return true;
        }

        wrapper.getCommand().onExecute(sender, args);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return getFirstAliases();
        }
        final SubCommandWrapper wrapper = getWrapperFromLabel(args[0]);
        if (wrapper == null) return null;

        return wrapper.getCommand().onTabComplete(sender, args);
    }
}
