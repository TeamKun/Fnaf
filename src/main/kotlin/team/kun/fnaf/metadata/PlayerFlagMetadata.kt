package team.kun.fnaf.metadata

import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import team.kun.fnaf.ext.getMeta
import team.kun.fnaf.ext.setMeta

class PlayerFlagMetadata(private val plugin: JavaPlugin) {
    fun avoidTwice(player: Player) {
        setFlag(player, true)
        object : BukkitRunnable() {
            override fun run() {
                setFlag(player, false)
            }
        }.runTaskLater(plugin, 10)
    }

    fun setFlag(player: Player, flag: Boolean) {
        player.setMeta(plugin, MetadataKey.IsPlayerInteract, flag)
    }

    fun getFlag(player: Player): Boolean {
        return player.getMeta(MetadataKey.IsPlayerInteract, false)
    }
}