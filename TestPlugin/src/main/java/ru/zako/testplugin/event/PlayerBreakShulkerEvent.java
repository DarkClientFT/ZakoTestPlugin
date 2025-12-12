package ru.zako.testplugin.event;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerBreakShulkerEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();


    private boolean cancel = false;

    private final Block block;

    public PlayerBreakShulkerEvent(Player who, Block block) {
        super(who);
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
