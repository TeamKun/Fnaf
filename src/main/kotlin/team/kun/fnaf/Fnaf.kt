package team.kun.fnaf

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.annotation.command.Command
import org.bukkit.plugin.java.annotation.command.Commands
import org.bukkit.plugin.java.annotation.plugin.ApiVersion
import org.bukkit.plugin.java.annotation.plugin.Plugin
import org.bukkit.plugin.java.annotation.plugin.author.Author
import org.jetbrains.exposed.sql.Database
import team.kun.fnaf.command.CameraCommand
import team.kun.fnaf.command.DatabaseCommand
import team.kun.fnaf.command.ShutDownCommand
import team.kun.fnaf.ext.initCommand
import team.kun.fnaf.ext.registerListener
import team.kun.fnaf.listener.ItemListener
import team.kun.fnaf.listener.PlayerListener
import java.io.File
import java.io.IOException
import java.sql.SQLException

@Plugin(name = "Fnaf", version = "1.0-SNAPSHOT")
@Author("ReyADayer")
@ApiVersion(ApiVersion.Target.v1_15)
@Commands(
    Command(
        name = PluginCommands.DATABASE,
        desc = "database command",
        usage = "/<command>",
        permission = PluginPermissions.ADMIN,
        permissionMessage = "You don't have <permission>"
    ),
    Command(
        name = PluginCommands.CAMERA,
        desc = "camera command",
        usage = "/<command>",
        permission = PluginPermissions.ADMIN,
        permissionMessage = "You don't have <permission>"
    ),
    Command(
        name = PluginCommands.SHUT_DOWN,
        desc = "shut down command",
        usage = "/<command>",
    )
)
class Fnaf : JavaPlugin() {
    private lateinit var database: Database

    var isShutDown = false

    override fun onEnable() {
        saveDefaultConfig()
        setupDatabase()

        initCommand(PluginCommands.DATABASE, DatabaseCommand(this))
        initCommand(PluginCommands.CAMERA, CameraCommand(this))
        initCommand(PluginCommands.SHUT_DOWN, ShutDownCommand(this))

        registerListener(ItemListener(this))
        registerListener(PlayerListener(this))
    }

    override fun onDisable() {
    }

    private fun setupDatabase() {
        val dataFolder = File(dataFolder, "fnaf.db")
        if (!dataFolder.exists()) {
            try {
                dataFolder.createNewFile()
            } catch (e: IOException) {
            }
        }
        try {
            database = Database.connect(
                "jdbc:sqlite:${dataFolder}",
                "org.sqlite.JDBC"
            )
        } catch (ex: SQLException) {
        } catch (ex: ClassNotFoundException) {
        }
    }
}