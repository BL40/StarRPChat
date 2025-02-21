package dev.bronzylobster.starrpchat.commands;

import dev.bronzylobster.starrpchat.StarRPChat;
import dev.bronzylobster.starrpchat.utils.Config;
import dev.bronzylobster.starrpchat.utils.Database;
import dev.bronzylobster.starrpchat.utils.InternalPlaceholders;
import dev.bronzylobster.starrpchat.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class UnmuteCommand extends AbstractCommand{
    FileConfiguration config = StarRPChat.getInstance().getConfig();

    Database db;
    {
        try {
            db = new Database();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UnmuteCommand() {
        super("unmute");
    }
    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        int length = args.length;

        switch (length) {
            case 0:
                sender.sendMessage("Usage: /unmute <player>");
                break;
            case 1:
                Player mutedP = (Player) Bukkit.getOfflinePlayer(args[0]);

                String unmute = config.getString(Config.UNMUTE.getPath());
                assert unmute != null : Config.UNMUTE.getPath() + " is not exists";
                String isnotmuted = config.getString(Config.IS_NOT_MUTED.getPath());
                assert isnotmuted != null : Config.IS_NOT_MUTED.getPath() + " is not exists";
                if(db.isMuted(args[0])) {
                    db.removeMuted(args[0]);
                    sender.sendMessage(Utils.toComponent(InternalPlaceholders.PlayerPlaceholders(unmute, mutedP)));
                } else {
                    sender.sendMessage(Utils.toComponent(InternalPlaceholders.PlayerPlaceholders(isnotmuted, mutedP)));
                }
                break;
        }
    }
}
