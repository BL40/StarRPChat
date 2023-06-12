package dev.bronzylobster.starrpchat.commands;

import dev.bronzylobster.starrpchat.StarRPChat;
import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractCommand implements CommandExecutor {
    public AbstractCommand(String command, TabCompleter completer) {
        PluginCommand pluginCommand = StarRPChat.getInstance().getCommand(command);
        if (pluginCommand != null) {
            pluginCommand.setExecutor(this);
            pluginCommand.setTabCompleter(completer);
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        execute(commandSender, s, strings);
        return true;
    }

    public abstract void execute(CommandSender sender, String label, String[] args);
}
