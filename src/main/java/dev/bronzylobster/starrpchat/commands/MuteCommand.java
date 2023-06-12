package dev.bronzylobster.starrpchat.commands;

import dev.bronzylobster.starrpchat.StarRPChat;
import dev.bronzylobster.starrpchat.commands.Completers.MuteCompleter;
import dev.bronzylobster.starrpchat.utils.Database;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

import static dev.bronzylobster.starrpchat.StarRPChat.debug;

public class MuteCommand extends AbstractCommand {

    public MuteCommand() {
        super("mute", new MuteCompleter());
    }
    Database db;
    {
        try {
            db = new Database();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void intDebug(String s) {
        debug("[MuteCommand] " + s);
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        intDebug("[execute] command hooked");
        int length = args.length;
        long s_time = System.currentTimeMillis();
        intDebug("[execute] s_time = " + s_time);
        intDebug("[execute] args.length = " + length);
        final long[] time = {0L};
        List<String> arrReason = new ArrayList<>();
        List<String> arrTime = new ArrayList<>();
        final String[] reason = new String[1];

        if (length == 0) {
            sender.sendMessage("/mute <player> [reason] [time: d/h/m/s]");
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                intDebug("[execute] started reason parse");
                for (String s : args) {
                    intDebug("[execute] s = " + s);
                    if (s.equals("time:")) {
                        intDebug("[execute] time: detected, reason parse ends");
                        break;
                    }
                    arrReason.add(s);
                    intDebug("[execute] " + s + " added to arrReason");
                }

                intDebug("[execute] started time parse");
                for (int x = arrReason.size(); x < length; x++) {
                    String s = args[x];
                    intDebug("[execute] s = " + s);
                    arrTime.add(args[x]);
                    long t;
                    if (s.endsWith("d")) {
                        intDebug("[execute] days detected");
                        s = s.replace("d", "");
                        intDebug("[execute] s = " + s);
                        t = Long.parseLong(s);
                        time[0] += t * 1000 * 60 * 60 * 24;
                        intDebug("[execute] time[0] = " + time[0]);
                    } else if (s.endsWith("h")) {
                        intDebug("[execute] hours detected");
                        s = s.replace("h", "");
                        intDebug("[execute] s = " + s);
                        t = Long.parseLong(s);
                        time[0] += t * 1000 * 60 * 60;
                        intDebug("[execute] time[0] = " + time[0]);
                    } else if (s.endsWith("m")) {
                        intDebug("[execute] minutes detected");
                        s = s.replace("m", "");
                        intDebug("[execute] s = " + s);
                        t = Long.parseLong(s);
                        time[0] += t * 1000 * 60;
                        intDebug("[execute] time[0] = " + time[0]);
                    } else if (s.endsWith("s")) {
                        intDebug("[execute] seconds detected");
                        s = s.replace("s", "");
                        intDebug("[execute] s = " + s);
                        t = Long.parseLong(s);
                        time[0] += t * 1000;
                        intDebug("[execute] time[0] = " + time[0]);
                    }
                }
                intDebug("[execute] time parse ends");

                String sTime;
                if (time[0] > 0) {
                    sTime = String.join(" ", arrTime);
                    intDebug("[execute] time[0] > 0");
                } else {
                    sTime = "Infinity";
                    intDebug("[execute] time[0] > 0");
                }
                intDebug("[execute] sTime = " + sTime);

                arrReason.remove(0);
                if (arrReason.size() == 0) {
                    reason[0] = "Muted for " + sTime + " by " + sender.getName();
                    intDebug("[execute] arrReason = 0");
                } else {
                    reason[0] = String.join(" ", arrReason);
                    intDebug("[execute] arrReason != 0");
                }
                intDebug("[execute] reason = " + reason[0]);

                if (db.isMuted(args[0])) {
                    intDebug("[execute] " + args[0] + " is muted");
                    time[0] = time[0] > 0 ? time[0] : 9223372036854775807L;
                    intDebug("[execute] time[0] = " + time[0]);
                    db.setMuteTime(args[0], time[0]);
                } else {
                    db.addMuted(args[0], time[0], reason[0], s_time);
                    intDebug("[execute] " + args[0] + " is not muted");
                }
                sender.sendMessage(args[0] + " has been muted for " + sTime + " with reason: " + reason[0]);
            }
        }.runTaskAsynchronously(StarRPChat.getInstance());
    }
}
