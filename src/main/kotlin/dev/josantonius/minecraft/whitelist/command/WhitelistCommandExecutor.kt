package dev.josantonius.minecraft.whitelist.command

import dev.josantonius.minecraft.whitelist.Main
import dev.josantonius.minecraft.whitelist.WhitelistManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class WhitelistCommandExecutor(
        private val plugin: Main,
        private val whitelistManager: WhitelistManager
) : CommandExecutor {
    override fun onCommand(
            sender: CommandSender,
            command: Command,
            label: String,
            args: Array<String>
    ): Boolean {
        if (label.equals("wl", ignoreCase = true)) {
            if (!sender.hasPermission("wl.admin")) {
                plugin.sendMessage(sender, "error.command.permission")
                return true
            }

            if (args.isEmpty()) {
                plugin.sendMessage(sender, "error.command.invalid_argument")
                return true
            }

            when (args[0].lowercase()) {
                "on" -> whitelistManager.enableWhitelist(sender)
                "off" -> whitelistManager.disableWhitelist(sender)
                "reload" -> plugin.reload(sender)
                "help" -> {
                    whitelistManager.showHelp(sender)
                }
                "add" -> {
                    if (args.size != 2) {
                        plugin.sendMessage(sender, "error.command.invalid_argument")
                        return true
                    }
                    whitelistManager.addPlayer(sender, args[1])
                }
                "remove" -> {
                    if (args.size != 2) {
                        plugin.sendMessage(sender, "error.command.invalid_argument")
                        return true
                    }
                    whitelistManager.removePlayer(sender, args[1])
                }
                "info" -> {
                    if (args.size != 2) {
                        plugin.sendMessage(sender, "error.command.invalid_argument")
                        return true
                    }
                    whitelistManager.infoPlayer(sender, args[1])
                }
                else -> plugin.sendMessage(sender, "error.command.invalid_argument")
            }
            return true
        }
        return false
    }
}
