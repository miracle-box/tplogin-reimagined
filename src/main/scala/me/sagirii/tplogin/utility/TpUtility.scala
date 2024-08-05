package me.sagirii.tplogin.utility

import me.sagirii.tplogin.TpLoginPlugin
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause
import scala.jdk.CollectionConverters.*

object TpUtility {

    def findLocation(player: Player): Option[Location] = {
        val world     = player.getWorld.getName
        val worldOpts = TpLoginPlugin.config.worlds.get(world)

        // TODO)) Support more teleport types
        val targetLocOpts = worldOpts match {
        case Some(options) =>
            TpLoginPlugin.config.locations.get(options.spawnLocation)
        case None =>
            None
        }

        targetLocOpts.flatMap { opts =>
            Option(TpLoginPlugin.plugin.getServer.getWorld(opts.world)).map { targetWorld =>
                new Location(targetWorld, opts.x, opts.y, opts.z, opts.yaw, opts.pitch)
            } orElse {
                // Log when no target world is found
                TpLoginPlugin.plugin.getLogger.warning(s"World ${opts.world} not found!")
                None
            }
        }

    }

    def teleport(player: Player, location: Location): Boolean = {
        // If the player is in a vehicle
        if player.isInsideVehicle then {
            val ride = player.getVehicle
            player.leaveVehicle()
        }

        // If the player have something ride on them
        val passengers = player.getPassengers.asScala.toList
        if passengers.nonEmpty then player.eject()
        for passenger <- passengers do passenger.teleport(location, TeleportCause.PLUGIN)

        player.teleport(location, TeleportCause.PLUGIN)

    }

}
