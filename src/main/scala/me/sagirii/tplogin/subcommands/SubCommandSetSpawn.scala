package me.sagirii.tplogin.subcommands

import me.sagirii.tplogin.TpLoginPlugin
import me.sagirii.tplogin.config.ConfigManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object SubCommandSetSpawn extends SubCommand {

    override val name = "setspawn"

    override val permission = Some("command.setspawn")

    override val paramNums = Some(List(1, 2))

    override val helpText = "Set spawn point of a world"

    override def execute(sender: CommandSender, params: List[String]): Boolean = {
        if !sender.isInstanceOf[Player] then {
            sender.sendMessage("Only players can use this command.")
            return false
        }

        val locName = params.head
        // World can be manually set
        val worldName =
            if params.length == 2
            then params(1)
            else sender.asInstanceOf[Player].getWorld.getName

        val loc = sender.asInstanceOf[Player].getLocation

        if !TpLoginPlugin.config.locations.contains(locName) then {
            sender.sendMessage(s"Can not find location with name $locName.")
            return false
        }

        if TpLoginPlugin.plugin.getServer.getWorld(worldName) == null then {
            sender.sendMessage(s"World with name $worldName does not exist.")
            return false
        }

        ConfigManager.setSpawn(TpLoginPlugin.plugin, worldName, locName)

        sender.sendMessage(s"Set spawn point of world $worldName to location $locName.")
        true

    }

}
