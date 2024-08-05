package me.sagirii.tplogin

import me.sagirii.tplogin.TpLoginPlugin.config
import me.sagirii.tplogin.TpLoginPlugin.config_=
import me.sagirii.tplogin.TpLoginPlugin.plugin
import me.sagirii.tplogin.TpLoginPlugin.plugin_=
import me.sagirii.tplogin.config.Config
import me.sagirii.tplogin.config.ConfigManager
import me.sagirii.tplogin.config.Location
import me.sagirii.tplogin.config.World
import me.sagirii.tplogin.listener.PlayerJoinListener
import me.sagirii.tplogin.listener.PlayerRespawnListener
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

        val cm = ConfigManager(this)
        // Load configuration and register tasks and listeners
        cm.saveDefault()
        updateConfig(cm.load())

        this.getCommand("tplogin").setExecutor(TpLoginCommand)
    }

    def updateConfig(newConfig: Config): Unit = {
        config = newConfig

        // Save config to disk
        val cm = ConfigManager(this)
        cm.save(newConfig)

        // Register events
        PlayerJoinListener.unregister()
        this.getServer.getPluginManager.registerEvents(PlayerJoinListener, this)

        PlayerRespawnListener.unregister()
        this.getServer.getPluginManager.registerEvents(PlayerRespawnListener, this)
        getLogger.info("Configuration updated.")
    }

}
