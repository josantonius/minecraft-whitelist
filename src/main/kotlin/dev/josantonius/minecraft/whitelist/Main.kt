package dev.josantonius.minecraft.whitelist

import dev.josantonius.minecraft.messaging.Message
import dev.josantonius.minecraft.whitelist.command.WhitelistCommandExecutor
import dev.josantonius.minecraft.whitelist.command.WhitelistTabCompleter
import java.io.*
import java.io.File
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLoginEvent
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin(), Listener {
    lateinit var configuration: WhitelistConfig
    lateinit var message: Message
    lateinit var whitelistManager: WhitelistManager

    override fun onEnable() {
        load()
    }

    @EventHandler
    fun onPlayerLogin(event: PlayerLoginEvent) {
        val player = event.player
        if (!configuration.whitelistIsEnabled() || (configuration.excludeOps() && player.isOp())) {
            return
        }
        if (!whitelistManager.isPlayerInWhitelist(player.name)) {
            val kickMessage: Component = message.getComponent("kick.message")
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, kickMessage)
            message.sendToConsole("console.player.outside", player.name)
            if (configuration.notifyOps()) {
                Bukkit.getOnlinePlayers().forEach { onlinePlayer ->
                    if (onlinePlayer.isOp) {
                        message.sendToPlayer(
                                onlinePlayer,
                                "advise.player.attempt_join",
                                player.name
                        )
                    }
                }
            }
        } else {
            message.sendToConsole("console.player.inside", player.name)
        }
    }

    fun load() {
        val messagesFile = File(dataFolder, "messages.yml")
        if (!messagesFile.exists()) {
            saveResource("messages.yml", false)
        }

        message = Message(messagesFile, this)
        message.setConsolePrefix("<gold>[<dark_aqua>Whitelist<gold>] <white>")
        message.setChatPrefix("<gold>[<dark_aqua>Whitelist<gold>] <white>")
        configuration = WhitelistConfig(this)
        whitelistManager = WhitelistManager(this)

        HandlerList.getHandlerLists().forEach { handlerList ->
            handlerList.unregister(this as Listener)
        }
        server.pluginManager.registerEvents(this, this)

        val whitelistTabCompleter = WhitelistTabCompleter()
        val whitelistCommandExecutor = WhitelistCommandExecutor(this, whitelistManager)

        getCommand("wl")?.setTabCompleter(whitelistTabCompleter)
        getCommand("wl")?.setExecutor(whitelistCommandExecutor)
    }

    fun reload(sender: CommandSender) {
        load()
        sendMessage(sender, "plugin.reloaded")
    }

    fun sendMessage(sender: CommandSender, key: String, vararg params: String) {
        if (sender is Player) {
            message.sendToPlayer(sender, key, *params)
        } else {
            message.sendToConsole(key, *params)
        }
    }
}
