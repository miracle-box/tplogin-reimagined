package me.sagirii.tplogin.config

import pureconfig.*
import pureconfig.generic.semiauto.*

case class LocationsConfig(locations: LocationsSection)

type LocationsSection = Map[String, Location]

case class Location(
    world: String,
    x: Double,
    y: Double,
    z: Double,
    yaw: Float,
    pitch: Float
)

given ConfigConvert[LocationsConfig] = deriveConvert
given ConfigConvert[Location]        = deriveConvert
