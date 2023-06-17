package dev.bronzylobster.starrpchat.commands;

import dev.bronzylobster.starrpchat.commands.Completers.UnmuteCompleter;
import dev.bronzylobster.starrpchat.utils.Database;
import org.bukkit.command.CommandSender;

public class UnmuteCommand extends AbstractCommand{

    Database db;
    {
        try {
            db = new Database();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UnmuteCommand() {
        super("unmute", new UnmuteCompleter());
    }
    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        int length = args.length;

        switch (length) {
            case 0:
                sender.sendMessage("Usage: /unmute <player>");
                break;
            case 1:
                if(db.isMuted(args[0])) {
                    db.removeMuted(args[0]);
                    sender.sendMessage(args[0] + " has been unmuted");
                } else {
                    sender.sendMessage(args[0] + " is not muted");
                }
                break;
        }
    }
}
