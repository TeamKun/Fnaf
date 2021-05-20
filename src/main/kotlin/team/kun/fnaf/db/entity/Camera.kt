package team.kun.fnaf.db.entity

import org.bukkit.Location
import org.bukkit.entity.LivingEntity
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.and
import team.kun.fnaf.db.table.Cameras

class Camera(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Camera>(Cameras) {
        fun create(cameraEntity: LivingEntity): Camera? {
            val location = cameraEntity.location
            val eyeLocation = cameraEntity.eyeLocation
            val currentWorld = location.world ?: return null
            return new {
                x = location.x
                y = location.y
                z = location.z
                yaw = location.yaw
                pitch = location.pitch
                eyeYaw = eyeLocation.yaw
                eyePitch = eyeLocation.pitch
                world = currentWorld.name
            }
        }

        fun findByLocation(location: Location): Camera? {
            return find {
                ((Cameras.x eq location.x) and
                        (Cameras.y eq location.y) and
                        (Cameras.z eq location.z)
                        )
            }.firstOrNull()
        }
    }

    var x by Cameras.x
    var y by Cameras.y
    var z by Cameras.z
    var yaw by Cameras.yaw
    var pitch by Cameras.pitch
    var eyeYaw by Cameras.eyeYaw
    var eyePitch by Cameras.eyePitch
    var world by Cameras.world
}