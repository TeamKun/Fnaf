package team.kun.fnaf.db

import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import team.kun.fnaf.db.table.Cameras

class InitDatabase (val plugin: JavaPlugin) {
    fun init() {
        transaction {
            SchemaUtils.create(Cameras)
        }
    }
}