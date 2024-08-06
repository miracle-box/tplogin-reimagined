package me.sagirii.tplogin

import org.bukkit.command.CommandExecutor

/**
 * Command `/spawn`
 *
 * Alias for `/tplogin spawn`
 */
object SpawnCommand extends CommandExecutor {

    override def onCommand(
        sender: org.bukkit.command.CommandSender,
        command: org.bukkit.command.Command,
        label: String,
        params: Array[String]
    ): Boolean = TpLoginCommand.onCommand(sender, command, label, "spawn" +: params)

}
