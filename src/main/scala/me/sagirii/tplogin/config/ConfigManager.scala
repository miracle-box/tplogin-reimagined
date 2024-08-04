package me.sagirii.tplogin.config

import java.nio.file.Files
import java.nio.file.Paths
import me.sagirii.tplogin.TpLoginPlugin
import me.sagirii.tplogin.config.*
import pureconfig.*

class ConfigManager(plugin: TpLoginPlugin) {

    private val dataFolder    = plugin.getDataFolder
    private val configPath    = Paths.get(dataFolder.getPath, "config.conf")
    private val locationsPath = Paths.get(dataFolder.getPath, "locations.conf")
    private val worldsPath    = Paths.get(dataFolder.getPath, "worlds.conf")

    val config: Config = load()

    def saveDefault(): Unit = {
        if Files.notExists(configPath) then plugin.saveResource("config.conf", false)
        if Files.notExists(locationsPath) then plugin.saveResource("locations.conf", false)
        if Files.notExists(worldsPath) then plugin.saveResource("worlds.conf", false)
    }

    def load(): Config = {
        val config    = ConfigSource.file(configPath).loadOrThrow[PluginConfig]
        val locations = ConfigSource.file(locationsPath).loadOrThrow[LocationsConfig].locations
        val worlds    = ConfigSource.file(worldsPath).loadOrThrow[WorldsConfig].worlds

        Config(
          config = config,
          locations = locations,
          worlds = worlds
        )
    }

    def save(newConfig: Config): Unit = {
        val config =
            ConfigWriter[PluginConfig].to(PluginConfig()).render()
        val locations =
            ConfigWriter[LocationsConfig].to(LocationsConfig(newConfig.locations)).render()
        val worlds =
            ConfigWriter[WorldsConfig].to(WorldsConfig(newConfig.worlds)).render()

        plugin.getLogger.info(config)
        plugin.getLogger.info(locations)
        plugin.getLogger.info(worlds)
    }

}

object ConfigManager {
    
}