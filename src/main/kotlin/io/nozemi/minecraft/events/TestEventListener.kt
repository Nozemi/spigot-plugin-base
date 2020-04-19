package io.nozemi.minecraft.events

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.springframework.stereotype.Component
import java.util.logging.Level

@Component
class TestEventListener : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        Bukkit.getLogger().log(Level.WARNING, event.player.displayName + " joined the server...")
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        event.player.sendMessage("You broke a block...")
    }
}