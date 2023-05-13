package dev.josantonius.minecraft.whitelist

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.util.*
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class WhitelistManager(private val plugin: Main) {
    private var whitelist: MutableSet<String> = HashSet()
    private var whitelistFile: File

    init {
        whitelistFile = File(plugin.dataFolder, "whitelist.txt")
        loadWhitelist()
    }

    fun enableWhitelist(sender: CommandSender) {
        plugin.configuration.enableWhitelist()
        plugin.sendMessage(sender, "whitelist.enable")

        Bukkit.getOnlinePlayers().forEach { player ->
            if (plugin.configuration.excludeOps() && player.isOp()) {
                return@forEach
            }
            if (!isPlayerInWhitelist(player.name)) {
                kickPlayer(player)
            }
        }
    }

    fun disableWhitelist(sender: CommandSender) {
        plugin.configuration.disableWhitelist()
        plugin.sendMessage(sender, "whitelist.disable")
    }

    fun infoPlayer(sender: CommandSender, player: String) {
        if (isPlayerInWhitelist(player)) {
            plugin.sendMessage(sender, "player.exists", player)
        } else {
            plugin.sendMessage(sender, "player.not_exists", player)
        }
    }

    fun addPlayer(sender: CommandSender, player: String) {
        if (!isPlayerInWhitelist(player)) {
            whitelist.add(player)
            plugin.sendMessage(sender, "player.added", player)
            saveWhitelist()
        } else {
            plugin.sendMessage(sender, "error.player.already_on", player)
        }
    }

    fun removePlayer(sender: CommandSender, player: String) {
        if (isPlayerInWhitelist(player)) {
            plugin.sendMessage(sender, "player.removed", player)
            val removedPlayer = Bukkit.getPlayer(player)
            if (removedPlayer != null) {
                kickPlayer(removedPlayer)
            }
            whitelist.remove(player)
            saveWhitelist()
        } else {
            plugin.sendMessage(sender, "error.player.not_on", player)
        }
    }

    fun isPlayerInWhitelist(player: String): Boolean {
        return whitelist.contains(player)
    }

    fun getWhitelist(): Set<String> {
        return whitelist
    }

    fun saveWhitelist() {
        try {
            BufferedWriter(FileWriter(whitelistFile)).use { writer ->
                for (username in whitelist) {
                    writer.write(username)
                    writer.newLine()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun showHelp(player: CommandSender) {
        plugin.sendMessage(player, "help.header")
        plugin.sendMessage(player, "help.add", "/wl add [player]")
        plugin.sendMessage(player, "help.remove", "/wl remove [player]")
        plugin.sendMessage(player, "help.enable", "/wl on")
        plugin.sendMessage(player, "help.disable", "/wl off")
        plugin.sendMessage(player, "help.info", "/wl info")
        plugin.sendMessage(player, "help.reload", "/wl reload")
    }

    private fun kickPlayer(player: Player) {
        if (plugin.configuration.kickPlayers() &&
                        (plugin.configuration.excludeOps() && !player.isOp)
        ) {
            player.kick(Component.text(plugin.message.getString("kick.online_message")))
        }
    }

    private fun loadWhitelist() {
        if (!whitelistFile.exists()) {
            try {
                whitelistFile.parentFile.mkdirs()
                whitelistFile.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        whitelist.clear()

        try {
            BufferedReader(FileReader(whitelistFile)).use { reader ->
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    whitelist.add(line!!.trim())
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
