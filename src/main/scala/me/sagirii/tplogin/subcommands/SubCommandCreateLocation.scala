package me.sagirii.tplogin.subcommands

import me.sagirii.tplogin.TpLoginPlugin
import me.sagirii.tplogin.config.ConfigManager
import me.sagirii.tplogin.config.Location
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object SubCommandCreateLocation extends SubCommand {

    override val name = "create"

    override val permission = Some("location.create")

    override val paramNums = Some(List(1))

    override val helpText = "Create a location."

    override def execute(sender: CommandSender, params: List[String]): Boolean = {
        if !sender.isInstanceOf[Player] then {
            sender.sendMessage("Only players can use this command.")
            return false
        }

        val name = params.head
        val loc  = sender.asInstanceOf[Player].getLocation

        if TpLoginPlugin.config.locations.contains(name) then {
            sender.sendMessage(s"Location $name already exists.")
            return false
        }

        ConfigManager.addLocation(
          TpLoginPlugin.plugin,
          name,
          Location(loc.getWorld.getName, loc.getX, loc.getY, loc.getZ, loc.getYaw, loc.getPitch)
        )

        sender.sendMessage(s"Created location $name at your current location.")
        true

    }

}
