package ru.zako.testplugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.zako.testplugin.event.PlayerBreakShulkerEvent;

public class BukkitListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    private void on(PlayerJoinEvent event) {
        if (!Config.testMessagesEnable) return;
        final Player player = event.getPlayer();
        for (int i = 0; i < Config.testMessagesAmount; i++) {
            player.sendMessage(Config.Messages.test);
        }
    }

    @EventHandler
    private void on(BlockBreakEvent event) {
        final Block block = event.getBlock();
        if (block.getType() == Material.SHULKER_BOX) {
            final PlayerBreakShulkerEvent e = new PlayerBreakShulkerEvent(event.getPlayer(), block);
            Bukkit.getPluginManager().callEvent(e);
            if (e.isCancelled()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void on(PlayerBreakShulkerEvent event) {
        event.getPlayer().sendMessage("Ты сломал шалкер!!!");
        if (Math.random() >= 0.5) {
            event.setCancelled(true);
        }
    }
}
