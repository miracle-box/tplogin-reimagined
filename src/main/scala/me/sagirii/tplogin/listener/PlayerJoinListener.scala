package me.sagirii.tplogin.listener

import me.sagirii.tplogin.utility.TpUtility
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object PlayerJoinListener extends Listener {

    @EventHandler(EventPriority.NORMAL, ignoreCancelled = false)
    def onPlayerJoin(event: PlayerJoinEvent): Unit = {
        val player = event.getPlayer

        // Do teleport
        val loc = TpUtility.findLocation(player)
        loc.foreach(location => TpUtility.teleport(player, location))
    }

    def unregister(): Unit =
        HandlerList.unregisterAll(this)

}
