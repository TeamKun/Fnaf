package team.kun.fnaf.item

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import team.kun.fnaf.Fnaf
import team.kun.fnaf.ext.*
import team.kun.fnaf.metadata.BasicNbtKey
import team.kun.fnaf.metadata.MetadataKey
import team.kun.fnaf.packet.CameraPacket

open class SpyCamera : Item() {
    override val name = "カメラ"
    override val description = listOf(
        "右クリックでカメラを使う"
    )
    override val itemStack = ItemStack(Material.STONE_PICKAXE)

    fun execute(player: Player, itemStack: ItemStack, plugin: Fnaf) {
        if (plugin.isShutDown) {
            player.sendMessage("停電中です")
            return
        }

        val id = itemStack.itemMeta?.persistentDataContainer?.getNbt(plugin, BasicNbtKey.ID) ?: return

        val camera =
            player.world.entities.firstOrNull { it.persistentDataContainer.getNbt(plugin, BasicNbtKey.ID) == id }

        if (camera == null || camera.isDead) {
            val itemMeta = itemStack.itemMeta ?: return
            itemMeta.persistentDataContainer.removeNbt(plugin, BasicNbtKey.EntityUUID)
            itemStack.itemMeta = itemMeta
            player.sendMessage("カメラが存在しません")
        } else {
            val players = camera.getMeta(MetadataKey.CameraPlayers, listOf()).toMutableList()
            players.add(player)
            players.distinct()
            camera.setMeta(plugin, MetadataKey.CameraPlayers, players)
            player.playSound(player.location, Sound.BLOCK_SHULKER_BOX_OPEN, 1.0f, 1.0f)
            CameraPacket(camera).send(player)
            player.setMeta(plugin, MetadataKey.Camera, camera)
            player.setMeta(plugin, MetadataKey.UsingCamera, true)
        }
    }

    fun set(player: Player, itemStack: ItemStack, camera: Entity, plugin: JavaPlugin) {
        val itemMeta = itemStack.itemMeta ?: return
        val id = camera.persistentDataContainer.getNbt(plugin, BasicNbtKey.ID) ?: return
        itemMeta.persistentDataContainer.setNbt(plugin, BasicNbtKey.ID, id)
        itemStack.itemMeta = itemMeta
    }

    fun end(player: Player, camera: Entity, plugin: JavaPlugin) {
        val players = camera.getMeta(MetadataKey.CameraPlayers, listOf()).toMutableList()
        players.remove(player)
        camera.setMeta(plugin, MetadataKey.CameraPlayers, players)
        CameraPacket(player).send(player)
        player.removeMeta(plugin, MetadataKey.Camera)
        player.setMeta(plugin, MetadataKey.UsingCamera, false)
    }
}