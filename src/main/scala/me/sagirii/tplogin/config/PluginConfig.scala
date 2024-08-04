package me.sagirii.tplogin.config

import pureconfig.*
import pureconfig.generic.semiauto.*

case class PluginConfig()

given ConfigConvert[PluginConfig] = deriveConvert
