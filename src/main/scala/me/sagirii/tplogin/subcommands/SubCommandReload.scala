package me.sagirii.tplogin.subcommands

import me.sagirii.tplogin.TpLoginPlugin
import me.sagirii.tplogin.TpLoginPlugin.plugin
import me.sagirii.tplogin.config.ConfigManager
import org.bukkit.command.CommandSender

object SubCommandReload extends SubCommand {

    override val name = "reload"

    override val permission = Some("command.reload")

    override val paramNums = None

    override val helpText = "Reloads configuration from disk."

    override def execute(sender: CommandSender, params: List[String]): Boolean = {
        val plugin = TpLoginPlugin.plugin
        plugin.updateConfig(ConfigManager(plugin).load())
        sender.sendMessage("Config reloaded from disk.")
        true
    }

}
