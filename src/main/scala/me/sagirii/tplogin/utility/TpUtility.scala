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
        worldOpts match {
        case Some(options) =>
            val locOpts = TpLoginPlugin.config.locations.get(options.spawnLocation)

            // Implicit conversion happens here
            locOpts match {
            case Some(loc) => Some(loc)
            case None      => None
            }

        case None =>
            None
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
