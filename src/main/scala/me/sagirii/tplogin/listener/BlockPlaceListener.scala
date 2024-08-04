package me.sagirii.tplogin.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

object BlockPlaceListener extends Listener {

    @EventHandler(EventPriority.LOWEST, ignoreCancelled = true)
    def onBlockPlace(event: BlockPlaceEvent): Unit = {}

    def unregister(): Unit =
        HandlerList.unregisterAll(this)

}
