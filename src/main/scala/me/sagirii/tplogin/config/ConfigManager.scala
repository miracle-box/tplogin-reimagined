package me.sagirii.tplogin.config

import com.typesafe.config.ConfigRenderOptions
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
        val renderOptions = ConfigRenderOptions
            .defaults()
            // Use HOCON
            .setJson(false)
            // Disable commands that shows the origin of the value
            .setOriginComments(false)
            // Preserve original comments (though there's no way to add comments in PureConfig)
            .setComments(true)
            // Pretty print
            .setFormatted(true)

        val config =
            ConfigWriter[PluginConfig].to(PluginConfig()).render(renderOptions)
        val locations =
            ConfigWriter[LocationsConfig].to(LocationsConfig(newConfig.locations)).render(renderOptions)
        val worlds =
            ConfigWriter[WorldsConfig].to(WorldsConfig(newConfig.worlds)).render(renderOptions)

        Files.write(configPath, config.getBytes)
        Files.write(locationsPath, locations.getBytes)
        Files.write(worldsPath, worlds.getBytes)
    }

}

object ConfigManager {

    def addLocation(plugin: TpLoginPlugin, name: String, location: Location): Unit = {
        val config = TpLoginPlugin.config.copy(
          locations = TpLoginPlugin.config.locations + (name -> location)
        )

        plugin.getLogger.info(s"Added location $location with name $name")
        plugin.updateConfig(config)
    }

    def setSpawn(plugin: TpLoginPlugin, world: String, location: String): Unit = {
        val worldOpts =
            TpLoginPlugin.config.worlds.getOrElse(world, World(spawnLocation = location))

        val config = TpLoginPlugin.config.copy(
          worlds = TpLoginPlugin.config.worlds + (world -> worldOpts.copy(spawnLocation = location))
        )

        plugin.getLogger.info(s"Set spawn point for world $world to $location")
        plugin.updateConfig(config)
    }

}
