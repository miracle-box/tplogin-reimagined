package me.sagirii.tplogin.subcommands

import me.sagirii.tplogin.TpLoginPlugin
import me.sagirii.tplogin.utility.TpUtility
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object SubCommandTeleport extends SubCommand {

    override val name = "teleport"

    override val permission = Some("command.teleport")

    override val paramNums = Some(List(1))

    override val helpText = "Teleport to a location."

    override def execute(sender: CommandSender, params: List[String]): Boolean = {
        if !sender.isInstanceOf[Player] then {
            sender.sendMessage("Only players can use this command.")
            return false
        }

        val locName    = params.head
        val locOptions = TpLoginPlugin.config.locations.get(locName)

        locOptions match {
        case Some(opts) =>
            // TODO)) Will probably add permission check to locations
            TpUtility.teleport(sender.asInstanceOf[Player], locOptions.get)
            true
        case None =>
            sender.sendMessage(s"Location with name $locName does not exist.")
            false
        }

    }

}
