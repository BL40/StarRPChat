package dev.bronzylobster.starrpchat.commands;

import dev.bronzylobster.starrpchat.StarRPChat;
import dev.bronzylobster.starrpchat.utils.Config;
import dev.bronzylobster.starrpchat.utils.InternalPlaceholders;
import dev.bronzylobster.starrpchat.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SpyCommand extends AbstractCommand{
    public SpyCommand() {
        super("spy");
    }

    FileConfiguration config = StarRPChat.getInstance().getConfig();

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission("srpc.spy")) {
            sender.sendMessage("Я УЗНАЮ ГАДЫ КТО ЛЕЗЕТ И ПО ЖОПЕ ДАМ");
            return;
        }

        Player p = (Player) sender;
        if (!StarRPChat.getPidors().contains(p)) {
            StarRPChat.addPidor(p);
            String spyon = config.getString(Config.MSG_SPY_ON.getPath());
            assert spyon != null : Config.MSG_SPY_ON.getPath() + " is not exists";

            p.sendMessage(Utils.toComponent(InternalPlaceholders.PlayerPlaceholders(spyon, p)));
            p.sendMessage("Я разачарован...");
        } else {
            StarRPChat.removePidor(p);
            String spyoff = config.getString(Config.MSG_SPY_OFF.getPath());
            assert spyoff != null : Config.MSG_SPY_OFF.getPath() + " is not exists";

            p.sendMessage(Utils.toComponent(InternalPlaceholders.PlayerPlaceholders(spyoff, p)));
            p.sendMessage("Я всё равно разачарован...");
        }
    }
}
