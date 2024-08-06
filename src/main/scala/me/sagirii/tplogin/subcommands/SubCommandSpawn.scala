package me.sagirii.tplogin.subcommands

import me.sagirii.tplogin.utility.TpUtility
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object SubCommandSpawn extends SubCommand {

    override val name = "spawn"

    override val permission = Some("command.spawn")

    override val paramNums = None

    override val helpText = "Teleport to a spawn point."

    override def execute(sender: CommandSender, params: List[String]): Boolean = {
        if !sender.isInstanceOf[Player] then {
            sender.sendMessage("Only players can use this command.")
            return false
        }

        // Do teleport
        val loc = TpUtility.findLocation(sender.asInstanceOf[Player])
        loc match {
        case Some(location) => TpUtility.teleport(sender.asInstanceOf[Player], location)
        case None           => sender.sendMessage("Can not find spawn point for this world.")
        }

        true

    }

}
