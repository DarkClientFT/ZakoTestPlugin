package ru.zako.testplugin.command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import ru.zako.testplugin.util.NBTEditor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GiveItemCommand implements CommandExecutor, Listener {
    private final NamespacedKey nsk;
    private final JavaPlugin plugin;
    public GiveItemCommand(JavaPlugin plugin) {
        this.plugin = plugin;

        nsk = new NamespacedKey(plugin, "magic-helmet");

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void on(PlayerInteractEvent event) {
        final ItemStack stack = event.getItem();
        if (stack != null && !stack.getType().isAir() && isMagicHelmet(stack)) {
            final Player player = event.getPlayer();
            player.setFoodLevel(20);

            ItemMeta meta = stack.getItemMeta();
            Damageable damageable = (Damageable) meta;

            damageable.setDamage(Math.max(1,damageable.getDamage()*2));

            if (damageable.getDamage() >= stack.getType().getMaxDurability()) {
                stack.setAmount(0);
            } else {
                stack.setItemMeta(meta);
            }
        }
    }

    private boolean isMagicHelmet(ItemStack item) {
        if (item.getType() != Material.IRON_HELMET) return false;

        return NBTEditor.contains(item, "magic-helmet");
    }




    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player p)) return true;

        ItemStack item = new ItemStack(Material.IRON_HELMET);

        ItemMeta meta = item.getItemMeta();

        final List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GREEN+"Железный шлем");
        meta.setLore(lore);

        meta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
        meta.addEnchant(Enchantment.DAMAGE_ALL, 10, true);

        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH,
                new AttributeModifier(UUID.randomUUID(),"generic.max_health", 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
        item.setItemMeta(meta);

        final ItemStack newStack = NBTEditor.set(item, (byte) 1, "magic-helmet");


        PlayerInventory inventory = p.getInventory();
        inventory.addItem(newStack)
                .forEach((i, is) -> p.getWorld().dropItem(p.getLocation(), is));

        return true;
    }
}
