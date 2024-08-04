package me.sagirii.tplogin.config

import me.sagirii.tplogin.TpLoginPlugin
import me.sagirii.tplogin.config.*
import scala.jdk.CollectionConverters.*

object ConfigManager {

    def saveDefault(plugin: TpLoginPlugin): Unit = {
        plugin.saveResource("config.conf", false)
        plugin.saveResource("locations.conf", false)
        plugin.saveResource("worlds.conf", false)
    }

    def load(plugin: TpLoginPlugin): Config =
        Config()

    def save(plugin: TpLoginPlugin, config: Config): Unit = {}

}
