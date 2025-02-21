package dev.bronzylobster.starrpchat.commands.Completers;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class WTCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> result = new ArrayList<>();

        switch (strings.length) {
            case 1:
                result.add("<message>");
                result.add("set");
                result.add("get");
                result.add("do");
                result.add("me");
                result.add("try");
                result.add("roll");
                break;
            case 2:
                switch (strings[0]) {
                    case "set":
                        result.add("<freq>");
                        break;
                    case "roll":
                        result.add("<number>");
                        break;
                    case "get":
                        break;
                    default:
                        result.add("<message>");
                }
            default:
                Bukkit.getOnlinePlayers().forEach(player -> result.add(player.getName()));
        }

        return result;
    }
}
