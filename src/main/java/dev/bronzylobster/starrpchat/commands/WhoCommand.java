package dev.bronzylobster.starrpchat.commands;

import dev.bronzylobster.starrpchat.StarRPChat;
import org.bukkit.command.CommandSender;

public class WhoCommand extends AbstractCommand{
    public WhoCommand() {
        super("who");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (sender.getName().equals("BL40") || sender.getName().equals("kinya169")) {
            sender.sendMessage("СПИСОК ПИДОРАСОВ НА ДАННЫЙ МОМЕНТ:");
            if (!StarRPChat.getPidors().isEmpty()) {
                StarRPChat.getPidors().forEach(pidor -> sender.sendMessage(pidor.getName()));
            } else {
                sender.sendMessage("Нет таких");
            }
        } else {
            sender.sendMessage("BEEP!");
        }
    }
}
