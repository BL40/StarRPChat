package dev.bronzylobster.starrpchat.commands.Completers;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MuteCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        int length = strings.length;
        List<String> out = new ArrayList<>();

        switch (length) {
            case 1:
                for (Player p : Bukkit.getOnlinePlayers()) {
                    out.add(p.getName());
                }
                break;
            case 2:
                out.add("<reason>");
                break;
            default:
                out.add("time:");
                out.add("1d");
                out.add("1h");
                out.add("1m");
                out.add("1s");
        }
        return out;
    }
}

