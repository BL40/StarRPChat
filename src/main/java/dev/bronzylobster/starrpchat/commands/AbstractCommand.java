package dev.bronzylobster.starrpchat.commands;

import dev.bronzylobster.starrpchat.StarRPChat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractCommand implements CommandExecutor {
    public AbstractCommand(String c) {
        PluginCommand pluginCommand = StarRPChat.getInstance().getCommand(c);
        if (pluginCommand != null) {
            pluginCommand.setExecutor(this);
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        execute(commandSender, s, strings);
        return true;
    }

    public abstract void execute(CommandSender sender, String label, String[] args);
}
