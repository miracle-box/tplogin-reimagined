package me.sagirii.tplogin

import me.sagirii.tplogin.subcommands.SubCommand
import me.sagirii.tplogin.subcommands.SubCommandCreate
import me.sagirii.tplogin.subcommands.SubCommandHelp
import me.sagirii.tplogin.subcommands.SubCommandReload
import me.sagirii.tplogin.subcommands.SubCommandSetSpawn
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/** Command handler copied from WorldBorder.
  * @todo
  *   Consider move these common modules into a common library.
  */
object TpLoginCommand extends CommandExecutor {

    private val subCommandsList = List(
      SubCommandHelp,
      SubCommandReload,
      SubCommandCreate,
      SubCommandSetSpawn
    )

    val subCommands: Map[String, SubCommand] =
        subCommandsList.map(cmd => cmd.name -> cmd).toMap

    override def onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        params: Array[String]
    ): Boolean = {
        val cmd = {
            params.headOption match {
            case Some(cmdString) if subCommands.contains(cmdString) => subCommands(cmdString)
            // Fallback empty subcommands to "help"
            case None => subCommands("help")
            // Do not accept commands that are not exist
            case Some(cmdString) =>
                sender.sendMessage(s"Subcommand $cmdString not found, use /tplogin help for help.")
                null
            }
        }

        if cmd == null then return true

        val hasPermission = sender match {
        // Ignore commands that does not require a permission
        case player: Player if cmd.permission.isDefined =>
            cmd.permission.exists(TpLoginPermission.hasPermission(player, _))
        // Console always have permissions
        case _ => true
        }

        if !hasPermission then return true

        // Checking params count
        val cmdParams = params.toList.drop(1)

        val paramsCorrect = cmd.paramNums match {
        case None                                                    => true
        case Some(paramNums) if paramNums.contains(cmdParams.length) => true
        case _                                                       => false
        }

        if paramsCorrect then cmd.execute(sender, cmdParams)
        else {
            sender.sendMessage(s"You have provided invalid number of params.")
            cmd.showHelp(sender)
        }

        true

    }

}
