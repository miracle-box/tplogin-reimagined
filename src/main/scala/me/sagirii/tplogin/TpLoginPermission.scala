package me.sagirii.tplogin

import org.bukkit.entity.Player

object TpLoginPermission {

    def hasPermission(player: Player, perm: String, notify: Boolean = true) = {
        val has = player.hasPermission(s"tplogin.$perm")
        if notify && !has then player.sendMessage("You do not have sufficient permissions.")
        has
    }

}
