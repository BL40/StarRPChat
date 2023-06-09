package dev.bronzylobster.starrpchat.commands;

import dev.bronzylobster.starrpchat.StarRPChat;
import dev.bronzylobster.starrpchat.utils.Database;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MuteCommand extends AbstractCommand implements TabCompleter{

    public MuteCommand(){
        super("MuteCommand");
    }

    Database db;
    {
        try {
            db = new Database();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        int length = args.length;
        long s_time = System.currentTimeMillis();
        final long[] time = {0L};
        List<String> arrReason = new ArrayList<>();
        List<String> arrTime = new ArrayList<>();
        String reason;
        new BukkitRunnable() {
            @Override
            public void run(){
                for (String s : args) {
                    if (s == null || s.equals("time:")) {
                        break;
                    }
                    arrReason.add(s);
                }
            }
        }.runTaskAsynchronously(StarRPChat.getInstance());
        arrReason.remove(0);

        new BukkitRunnable() {
            @Override
            public void run() {
                for (int x = arrReason.size(); x < length; x++) {
                    String s = args[x];
                    arrTime.add(args[x]);
                    long t;
                    if (s.endsWith("d")){
                        s = s.replace("d", "");
                        t = Long.parseLong(s);
                        time[0] += t * 1000 * 60 * 60 * 24;
                    } else if (s.endsWith("h")) {
                        s = s.replace("h", "");
                        t = Long.parseLong(s);
                        time[0] += t * 1000 * 60 * 60;
                    } else if (s.endsWith("m")) {
                        s = s.replace("m", "");
                        t = Long.parseLong(s);
                        time[0] += t * 1000 * 60;
                    } else if (s.endsWith("s")) {
                        s = s.replace("s", "");
                        t = Long.parseLong(s);
                        time[0] += t * 1000;
                    }
                }
            }
        }.runTaskAsynchronously(StarRPChat.getInstance());
        String sTime;
        if (arrTime.size() > 0 ){
            sTime = String.join(" ", arrTime);
        } else {
            sTime = "Infinity";
        }

        if (arrReason.size() == 0) {
            reason = "Muted for" + sTime + " by " + sender.getName();
        } else {
            reason = String.join(" ", arrReason);
        }

        if (length == 0) {
            sender.sendMessage("/mute <player> [reason] [time: d/h/m/s]");
        } else {
            if (db.isMuted(args[0])) {
                time[0] = time[0] > 0 ? time[0] : 9223372036854775807L;
                db.setMuteTime(args[0], time[0]);
            } else {
                db.addMuted(args[0], time[0], reason, s_time);
            }
            sender.sendMessage(args[0] + "has been muted for " + sTime + "with reason: " + reason);
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        int length = strings.length;
        List<String> out = new ArrayList<>();

        switch (length) {
            case 0:
                for (Player p : Bukkit.getOnlinePlayers()) {
                    out.add(p.getName());
                }
                break;
            case 1:
                out.add("reason");
                out.add("time:");
                break;
            case 2:
                out.add("time:");
            default:
                out.add("1d");
                out.add("1h");
                out.add("1m");
                out.add("1s");
        }
        return out;
    }
}
