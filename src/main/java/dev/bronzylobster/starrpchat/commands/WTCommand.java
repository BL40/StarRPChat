package dev.bronzylobster.starrpchat.commands;

import dev.bronzylobster.starrpchat.StarRPChat;
import dev.bronzylobster.starrpchat.commands.Completers.WTCompleter;
import dev.bronzylobster.starrpchat.utils.Config;
import dev.bronzylobster.starrpchat.utils.Database;
import dev.bronzylobster.starrpchat.utils.InternalPlaceholders;
import dev.bronzylobster.starrpchat.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class WTCommand extends AbstractCommand {
    public WTCommand() {
        super("wt", new WTCompleter());
    }

    FileConfiguration config = StarRPChat.getInstance().getConfig();

    Database db;
    {
        try {
            db = new Database();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Component msg;
    ArrayList<Player> viewers = new ArrayList<>();
    String result;

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission("srpc.wt")) {
            return;
        }

        if (args.length == 0) {
            sender.sendMessage("Usage:");
            sender.sendMessage("/wt <msg>");
            sender.sendMessage("/wt set <freq>");
            sender.sendMessage("/wt get");
            sender.sendMessage("/wt do <msg>");
            sender.sendMessage("/wt me <msg>");
            sender.sendMessage("/wt try <msg>");
            sender.sendMessage("/wt roll <dice>");
            return;
        }

        String nick = sender.getName();
        Player p = (Player) sender;

        String hasnotfreq = config.getString(Config.HAS_NOT_FREQ.getPath());
        assert hasnotfreq != null: Config.HAS_NOT_FREQ.getPath() + " is not exists";

        if (!(args[0].equals("set")) && (db.getWTFreq(nick) == null)) {
            sender.sendMessage(Utils.toComponent(InternalPlaceholders.PlayerPlaceholders(hasnotfreq, p)));
        }

        String freq = db.getWTFreq(nick);

        switch (args[0]) {
            case "set":
                String set = config.getString(Config.WT_SET.getPath());
                assert set != null: Config.WT_SET.getPath() + " is not exists";

                db.setWTFreq(nick, args[1]);
                set = set.replace("%freq%", args[1]);
                sender.sendMessage(Utils.toComponent(InternalPlaceholders.PlayerPlaceholders(set, p)));

                break;
            case "get":
                String get = config.getString(Config.WT_GET.getPath());
                assert get != null: Config.WT_GET.getPath() + " is not exists";

                get = get.replace("%freq%", freq);
                sender.sendMessage(Utils.toComponent(InternalPlaceholders.PlayerPlaceholders(get, p)));

                break;
            case "do":
                String do1 = config.getString(Config.WT_DO.getPath());
                assert do1 != null: Config.WT_DO.getPath() + " is not exists";

                do1 = do1.replace("%message%", Utils.parseToString(true, args));
                msg = Utils.toComponent(InternalPlaceholders.PlayerPlaceholders(do1, p));
                viewers = db.getWTPlayers(freq);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Player p: viewers) {
                            p.sendMessage(msg);
                        }
                    }
                }.runTaskAsynchronously(StarRPChat.getInstance());

                break;
            case "me":
                String me = config.getString(Config.WT_ME.getPath());
                assert me != null: Config.WT_ME.getPath() + " is not exists";

                me = me.replace("%message%", Utils.parseToString(true, args));
                msg = Utils.toComponent(InternalPlaceholders.PlayerPlaceholders(me, p));
                viewers = db.getWTPlayers(freq);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Player p: viewers) {
                            p.sendMessage(msg);
                        }
                    }
                }.runTaskAsynchronously(StarRPChat.getInstance());

                break;
            case "try":
                String try1 = config.getString(Config.WT_TRY.getPath());
                assert try1 != null: Config.WT_TRY.getPath() + " is not exists";
                String tryWin = config.getString(Config.WT_TRY_WIN.getPath());
                assert tryWin != null: Config.WT_TRY_WIN.getPath() + " is not exists";
                String tryLose = config.getString(Config.WT_TRY_LOSE.getPath());
                assert tryLose != null: Config.WT_TRY_LOSE.getPath() + " is not exists";

                result = Math.random() > 0.5 ? tryWin : tryLose;

                try1 = try1.replace("%message%", Utils.parseToString(true, args))
                        .replace("%result%", result);
                msg = Utils.toComponent(InternalPlaceholders.PlayerPlaceholders(try1, p));
                viewers = db.getWTPlayers(freq);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Player p: viewers) {
                            p.sendMessage(msg);
                        }
                    }
                }.runTaskAsynchronously(StarRPChat.getInstance());

                break;
            case "roll":
                String roll = config.getString(Config.WT_ROLL.getPath());
                assert roll != null: Config.WT_ROLL.getPath() + " is not exists";
                String srollmax = config.getString(Config.WT_ROLL_MAX.getPath());
                assert srollmax != null: Config.WT_ROLL_MAX.getPath() + " is not exists";
                String srollmin = config.getString(Config.WT_ROLL_MIN.getPath());
                assert srollmin != null: Config.WT_ROLL_MIN.getPath() + " is not exists";
                int rollmax = Utils.getNumber(srollmax, sender);
                int rollmin = Utils.getNumber(srollmin, sender);

                int max = args.length > 1 ? Utils.getNumber(args[1], sender) : rollmin;
                if (max > rollmax) {
                    max = rollmax;
                } else if (max < rollmin) {
                    max = rollmin;
                }
                int res = (int) ((Math.random() * max) + 1);

                roll = roll.replace("%result%", String.valueOf(res))
                        .replace("%max%", String.valueOf(max));
                msg = Utils.toComponent(InternalPlaceholders.PlayerPlaceholders(roll, p));
                viewers = db.getWTPlayers(freq);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Player p: viewers) {
                            p.sendMessage(msg);
                        }
                    }
                }.runTaskAsynchronously(StarRPChat.getInstance());

                break;
            default:
                String format = config.getString(Config.WT.getPath());
                assert format != null: Config.WT.getPath() + " is not exists";

                format = format.replace("%message%", Utils.parseToString(false, args));
                msg = Utils.toComponent(InternalPlaceholders.PlayerPlaceholders(format, p));
                viewers = db.getWTPlayers(freq);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Player p: viewers) {
                            p.sendMessage(msg);
                        }
                    }
                }.runTaskAsynchronously(StarRPChat.getInstance());

                break;
        }
    }
}
