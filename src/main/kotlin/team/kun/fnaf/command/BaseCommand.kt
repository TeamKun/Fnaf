package team.kun.fnaf.command

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

abstract class BaseCommand(private val plugin: JavaPlugin) : CommandExecutor, TabCompleter {

    abstract fun onCommandByPlayer(player: Player, command: Command, label: String, args: Array<String>): Boolean

    abstract fun onCommandByOther(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player) {
            return onCommandByPlayer(sender, command, label, args)
        }
        return onCommandByOther(sender, command, label, args)
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): List<String> {
        return emptyList()
    }
}