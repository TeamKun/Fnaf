package team.kun.fnaf.command

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import team.kun.fnaf.Fnaf

class ShutDownCommand(private val plugin: Fnaf) : BaseCommand(plugin) {
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
        plugin.isShutDown = args[0] == "true"
        return true
    }
}