package team.kun.fnaf.command

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import team.kun.fnaf.db.InitDatabase

class DatabaseCommand(private val plugin: JavaPlugin) : BaseCommand(plugin) {
    override fun onCommandByPlayer(player: Player, command: Command, label: String, args: Array<String>): Boolean {
        return onCommand(args)
    }

    override fun onCommandByOther(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<String>
    ): Boolean {
        return onCommand(args)
    }

    fun onCommand(args: Array<String>): Boolean {
        return when (Action.find(args[0])) {
            Action.INIT -> {
                InitDatabase(plugin).init()
                true
            }
            else -> false
        }
    }

    private enum class Action(val value: String) {
        INIT("init");

        companion object {
            fun find(value: String?): Action? {
                return values().firstOrNull { it.value == value }
            }
        }
    }
}