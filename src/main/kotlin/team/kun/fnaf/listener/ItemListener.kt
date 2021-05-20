package team.kun.fnaf.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin
import team.kun.fnaf.Fnaf
import team.kun.fnaf.ext.getMeta
import team.kun.fnaf.ext.getNbt
import team.kun.fnaf.metadata.BasicNbtKey
import team.kun.fnaf.metadata.MetadataKey
import team.kun.fnaf.metadata.PlayerFlagMetadata
import team.kun.fnaf.item.SpyCamera

class ItemListener(private val plugin: Fnaf) : Listener {
    private val playerFlagMetadata = PlayerFlagMetadata(plugin)

    @EventHandler
    fun onClick(event: PlayerInteractEvent) {
        val player = event.player
        if (playerFlagMetadata.getFlag(player)) {
            return
        }
        if (player.getMeta(MetadataKey.UsingCamera, false)) {
            playerFlagMetadata.avoidTwice(player)
            val camera = player.getMeta(MetadataKey.Camera)
            camera?.let {
                SpyCamera().end(player, it, plugin)
            }
            event.isCancelled = true
            return
        }
        if (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) {
            if (SpyCamera().equal(event.player.inventory.itemInMainHand, plugin)) {
                playerFlagMetadata.avoidTwice(player)
                SpyCamera().execute(player, event.player.inventory.itemInMainHand, plugin)
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onClick(event: PlayerInteractAtEntityEvent) {
        val player = event.player
        val entity = event.rightClicked
        if (playerFlagMetadata.getFlag(player)) {
            return
        }
        if (entity.persistentDataContainer.getNbt(plugin, BasicNbtKey.IsCamera, 0) == 1.toByte()) {
            if (SpyCamera().equal(event.player.inventory.itemInMainHand, plugin)) {
                playerFlagMetadata.avoidTwice(player)
                SpyCamera().set(player, event.player.inventory.itemInMainHand, entity, plugin)
                event.isCancelled = true
            }
        }
    }
}