package me.sagirii.tplogin.config

import me.sagirii.tplogin.TpLoginPlugin
import org.bukkit.Location
import pureconfig.*
import pureconfig.generic.semiauto.*

case class LocationsConfig(locations: LocationsSection)

type LocationsSection = Map[String, LocationOptions]

case class LocationOptions(
    world: String,
    x: Double,
    y: Double,
    z: Double,
    yaw: Float,
    pitch: Float
)

object LocationOptions {

    // Implicitly convert our location config to a bukkit Location
    given Conversion[LocationOptions, Location] with {

        def apply(loc: LocationOptions): Location = {
            val world = TpLoginPlugin.plugin.getServer.getWorld(loc.world)
            if world == null then throw new IllegalArgumentException(
              s"World ${loc.world} not found!"
            )

            new Location(
              world,
              loc.x,
              loc.y,
              loc.z,
              loc.yaw,
              loc.pitch
            )
        }

    }

    // And convert back
    given Conversion[Location, LocationOptions] with {

        def apply(loc: Location): LocationOptions =
            LocationOptions(
              loc.getWorld.getName,
              loc.getX,
              loc.getY,
              loc.getZ,
              loc.getYaw,
              loc.getPitch
            )

    }

}

given ConfigConvert[LocationsConfig] = deriveConvert
given ConfigConvert[LocationOptions] = deriveConvert
