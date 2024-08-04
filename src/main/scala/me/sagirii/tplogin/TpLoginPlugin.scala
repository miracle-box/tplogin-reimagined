package me.sagirii.tplogin

import me.sagirii.tplogin.TpLoginPlugin.config
import me.sagirii.tplogin.TpLoginPlugin.config_=
import me.sagirii.tplogin.TpLoginPlugin.plugin
import me.sagirii.tplogin.TpLoginPlugin.plugin_=
import me.sagirii.tplogin.config.Config
import me.sagirii.tplogin.config.ConfigManager
import me.sagirii.tplogin.listener.BlockPlaceListener
import org.bukkit.plugin.java.JavaPlugin

object TpLoginPlugin {

    @volatile
    private var _plugin: TpLoginPlugin = _

    @volatile
    private var _config: Config = _

    def plugin: TpLoginPlugin = _plugin

    def config: Config = _config

    private def plugin_=(plugin: TpLoginPlugin): Unit = _plugin = plugin

    private def config_=(config: Config): Unit = _config = config

}

class TpLoginPlugin extends JavaPlugin {

    override def onEnable(): Unit = {
        if plugin == null then plugin = this

        // Load configuration and register tasks and listeners
        ConfigManager.saveDefault(plugin)
        updateConfig(ConfigManager.load(plugin))

        this.getCommand("border").setExecutor(TpLoginCommand)
    }

    def updateConfig(newConfig: Config): Unit = {
        config = newConfig

        // Save config to disk
        ConfigManager.save(plugin, newConfig)

        // Register events
        BlockPlaceListener.unregister()
        this.getServer.getPluginManager.registerEvents(
          BlockPlaceListener,
          this
        )

        getLogger.info("Configuration updated.")
    }

}
