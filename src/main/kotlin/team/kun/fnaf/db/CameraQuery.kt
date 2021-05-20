package team.kun.fnaf.db

import org.bukkit.entity.LivingEntity
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.transactions.transaction
import team.kun.fnaf.db.entity.Camera

class CameraQuery(private val plugin: JavaPlugin) {
    fun create(cameraEntity: LivingEntity) {
        transaction {
            Camera.create(cameraEntity)
        }
    }

    fun get(): List<Camera> {
        var result = emptyList<Camera>()
        transaction {
            result = Camera.all().toList()
        }
        return result
    }

    fun delete(id: Int) {
        transaction {
            Camera.findById(id)?.delete()
        }
    }
}