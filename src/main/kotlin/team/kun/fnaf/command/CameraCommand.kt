package team.kun.fnaf.command

import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Creeper
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import team.kun.fnaf.db.CameraQuery
import team.kun.fnaf.ext.setNbt
import team.kun.fnaf.ext.spawn
import team.kun.fnaf.metadata.BasicNbtKey
import team.kun.fnaf.item.SpyCamera

class CameraCommand(private val plugin: JavaPlugin) : BaseCommand(plugin) {
    override fun onCommandByPlayer(player: Player, command: Command, label: String, args: Array<String>): Boolean {
        val action = Action.find(args[0]) ?: return false
        return when (action) {
            Action.LIST -> {
                val cameras = CameraQuery(plugin).get()
                cameras.forEach {
                    player.sendMessage(it.toString())
                }
                true
            }
            Action.SET -> {
                CameraQuery(plugin).create(player)
                true
            }
            Action.DELETE -> {
                val id = args[1].toIntOrNull() ?: return false
                CameraQuery(plugin).delete(id)
                true
            }
            Action.SPAWN -> {
                val cameras = CameraQuery(plugin).get()
                cameras.forEach {
                    val world = plugin.server.getWorld(it.world) ?: return@forEach
                    val location = Location(world, it.x, it.y, it.z, it.yaw, it.pitch)
                    location.spawn<Creeper> { creeper ->
                        creeper.setAI(false)
                        creeper.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 10000, 2))
                        creeper.isCollidable = false
                        creeper.isInvulnerable = true
                        creeper.eyeLocation.yaw = it.eyeYaw
                        creeper.eyeLocation.pitch = it.eyePitch
                        creeper.persistentDataContainer.setNbt(plugin, BasicNbtKey.Name, this::class.simpleName)
                        creeper.persistentDataContainer.setNbt(plugin, BasicNbtKey.ID, it.id.value)
                        creeper.persistentDataContainer.setNbt(plugin, BasicNbtKey.IsCamera, 1.toByte())
                    }
                }
                true
            }
            Action.ITEM -> {
                player.inventory.addItem(SpyCamera().toItemStack(plugin))
                true
            }
        }
    }

    override fun onCommandByOther(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<String>
    ): Boolean {
        sender.sendMessage("You must be a player!")
        return false
    }

    private enum class Action(val value: String) {
        LIST("list"),
        SET("set"),
        DELETE("delete"),
        SPAWN("spawn"),
        ITEM("item");

        companion object {
            fun find(value: String?): Action? {
                return values().firstOrNull { it.value == value }
            }
        }
    }
}