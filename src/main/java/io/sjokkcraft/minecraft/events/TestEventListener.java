package io.sjokkcraft.minecraft.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.springframework.stereotype.Component;

import java.util.logging.Level;

@Component
public class TestEventListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Bukkit.getLogger().log(Level.WARNING, event.getPlayer().getDisplayName() + " joined the server...");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        event.getPlayer().sendMessage("You broke a block...");
    }
}
