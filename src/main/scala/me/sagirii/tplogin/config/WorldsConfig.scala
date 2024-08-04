package me.sagirii.tplogin.config

import pureconfig.*
import pureconfig.generic.semiauto.*

case class WorldsConfig(worlds: WorldsSection)

type WorldsSection = Map[String, World]

case class World(
    spawnLocation: String
)

given ConfigConvert[WorldsConfig] = deriveConvert
given ConfigConvert[World]        = deriveConvert
