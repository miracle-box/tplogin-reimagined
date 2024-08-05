package me.sagirii.tplogin.config

import pureconfig.*
import pureconfig.generic.semiauto.*

case class WorldsConfig(worlds: WorldsSection)

type WorldsSection = Map[String, WorldOptions]

case class WorldOptions(
    spawnLocation: String
)

given ConfigConvert[WorldsConfig] = deriveConvert
given ConfigConvert[WorldOptions]        = deriveConvert
