package team.kun.fnaf.db.table

import org.jetbrains.exposed.dao.id.IntIdTable

object Cameras : IntIdTable("cameras") {
    val x = double("x")
    val y = double("y")
    val z = double("z")
    val yaw = float("yaw")
    val pitch = float("pitch")
    val eyeYaw = float("eye_yaw")
    val eyePitch = float("eye_pitch")
    val world = varchar("world", 255)

    init {
        uniqueIndex(x, y, z)
    }
}