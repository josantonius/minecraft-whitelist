package dev.josantonius.minecraft.whitelist

import org.bukkit.configuration.file.FileConfiguration

class WhitelistConfig(private val plugin: Main) {
    private val config: FileConfiguration
    private val notifyOps: Boolean
    private val excludeOps: Boolean
    private val kickPlayers: Boolean
    private var enabled: Boolean

    init {
        plugin.saveDefaultConfig()
        plugin.reloadConfig()
        config = plugin.config
        enabled = config.getBoolean("enabled", false)
        notifyOps = config.getBoolean("notifyOps", false)
        excludeOps = config.getBoolean("excludeOps", false)
        kickPlayers = config.getBoolean("kickPlayers", false)
    }

    fun enableWhitelist() {
        config.set("enabled", true)
        enabled = true
        plugin.saveConfig()
    }

    fun disableWhitelist() {
        config.set("enabled", false)
        enabled = false
        plugin.saveConfig()
    }

    fun whitelistIsEnabled(): Boolean {
        return enabled
    }

    fun notifyOps(): Boolean {
        return notifyOps
    }

    fun excludeOps(): Boolean {
        return excludeOps
    }

    fun kickPlayers(): Boolean {
        return kickPlayers
    }
}
