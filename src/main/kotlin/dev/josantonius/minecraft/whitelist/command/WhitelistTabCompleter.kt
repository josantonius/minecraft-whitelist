package dev.josantonius.minecraft.whitelist.command

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class WhitelistTabCompleter : TabCompleter {

    override fun onTabComplete(
            sender: CommandSender,
            cmd: Command,
            label: String,
            args: Array<String>
    ): List<String>? {
        if (cmd.name.equals("wl", ignoreCase = true)) {
            if (args.size == 1) {
                val suggestions = mutableListOf<String>()
                suggestions.add("add")
                suggestions.add("remove")
                suggestions.add("info")
                suggestions.add("on")
                suggestions.add("off")
                suggestions.add("reload")
                suggestions.add("help")

                return suggestions
            } else if (args.size == 2) {
                if (args[0].equals("add", ignoreCase = true) ||
                                args[0].equals(
                                        "remove",
                                        ignoreCase =
                                                true || args[0].equals("info", ignoreCase = true)
                                )
                ) {
                    val playerNames =
                            Bukkit.getOnlinePlayers().stream().map(Player::getName).toList()
                    return playerNames
                }
            }
        }

        return null
    }
}
