package me.sagirii.tplogin.subcommands

import me.sagirii.tplogin.TpLoginPlugin
import org.bukkit.command.CommandSender

object SubCommandHelp extends SubCommand {

    override val name = "help"

    override val permission = None

    override val paramNums = None

    override val helpText = "Shows the help message of World Border."

    override def execute(sender: CommandSender, params: List[String]): Boolean = {
        sender.sendMessage(this.helpText)
        true
    }

}
