package dev.bronzylobster.starrpchat.commands;

import dev.bronzylobster.starrpchat.StarRPChat;
import dev.bronzylobster.starrpchat.utils.Config;
import dev.bronzylobster.starrpchat.utils.InternalPlaceholders;
import dev.bronzylobster.starrpchat.utils.Utils;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ReplyCommand extends AbstractCommand {
    public ReplyCommand() {
        super("reply");
    }

    FileConfiguration config = StarRPChat.getInstance().getConfig();

    @Override
    public void execute(CommandSender sender, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage("Usage: /r <message>");
        }

        String sMSG = Utils.parseToString(false, args);

        if (!StarRPChat.getReply().containsKey((Player) sender)) {
            String noreply = config.getString(Config.MSG_NO_REPLY.getPath());
            assert noreply != null: Config.MSG_NO_REPLY.getPath() + " is not exists";

            noreply = noreply.replace("%sender%", sender.getName())
                    .replace("%message%", sMSG);

            sender.sendMessage(Utils.toComponent(InternalPlaceholders.PlayerPlaceholders(noreply, (Player) sender)));
            return;
        }

        Player p = (Player) sender;
        Player receiver = StarRPChat.getReply().get(p);

        if (!receiver.isOnline()) {
            String playerisoffline = config.getString(Config.MSG_OFFLINE.getPath());
            assert playerisoffline != null : Config.MSG_OFFLINE.getPath() + " is not exists";

            playerisoffline = playerisoffline.replace("%receiver%", args[0]);
            p.sendMessage(Utils.toComponent(InternalPlaceholders.PlayerPlaceholders(playerisoffline, p)));

            return;
        }

        if (!StarRPChat.getPidors().isEmpty()) {
            String spy = config.getString(Config.MSG_SPY.getPath());
            assert spy != null : Config.MSG_SPY.getPath() + " is not exists";

            spy = spy.replace("%sender%", p.getName())
                    .replace("%receiver%", args[0])
                    .replace("%message%", sMSG);
            Component msg = Utils.toComponent(InternalPlaceholders.PlayerPlaceholders(spy, p));

            new BukkitRunnable() {
                @Override
                public void run() {
                    for(Player pidor : StarRPChat.getPidors()) {
                        pidor.sendMessage(msg);
                    }
                }
            }.runTaskAsynchronously(StarRPChat.getInstance());
        }

        final String[] send = {config.getString(Config.MSG_SEND.getPath())};
        assert send[0] != null : Config.MSG_SEND.getPath() + " is not exists";
        final String[] receive = {config.getString(Config.MSG_RECEIVE.getPath())};
        assert receive[0] != null : Config.MSG_RECEIVE.getPath() + " is not exists";

        new BukkitRunnable() {
            @Override
            public void run() {
                send[0] = send[0].replace("%sender%", p.getName())
                        .replace("%receiver%", args[0])
                        .replace("%message%", sMSG);
                p.sendMessage(Utils.toComponent(InternalPlaceholders.PlayerPlaceholders(send[0], receiver)));

                receive[0] = receive[0].replace("%sender%", p.getName())
                        .replace("%receiver%", args[0])
                        .replace("%message%", sMSG);
                receiver.sendMessage(Utils.toComponent(InternalPlaceholders.PlayerPlaceholders(receive[0], p)));

                StarRPChat.addReply(p, receiver);
            }
        }.runTaskLater(StarRPChat.getInstance(), (long) config.getInt(Config.MSG_DELAY.getPath()) * 20);

        String soundkey = config.getString(Config.SOUND_REPLY.getPath());
        if (soundkey != null) {
            receiver.playSound(Sound.sound(Key.key(soundkey), Sound.Source.AMBIENT, 1f, 1f));
        }
    }
}
