package me.sagirii.tplogin.listener

import me.sagirii.tplogin.utility.TpUtility
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent

object PlayerRespawnListener extends Listener {

    @EventHandler(EventPriority.NORMAL, ignoreCancelled = false)
    def onPlayerRespawn(event: PlayerRespawnEvent): Unit = {
        val player = event.getPlayer

        // Do teleport
        val loc = TpUtility.findLocation(player)
        loc.foreach(location => event.setRespawnLocation(location))
    }

    def unregister(): Unit =
        HandlerList.unregisterAll(this)

}
