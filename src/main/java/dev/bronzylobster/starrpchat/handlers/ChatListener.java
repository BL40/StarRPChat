package dev.bronzylobster.starrpchat.handlers;

import dev.bronzylobster.starrpchat.StarRPChat;
import dev.bronzylobster.starrpchat.utils.Database;
import dev.bronzylobster.starrpchat.utils.renderers.DimensionChatRenderer;
import dev.bronzylobster.starrpchat.utils.renderers.GlobalChatRenderer;
import dev.bronzylobster.starrpchat.utils.renderers.LocalChatRenderer;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static dev.bronzylobster.starrpchat.utils.Utils.*;

public class ChatListener implements Listener {

    FileConfiguration config = StarRPChat.getInstance().getConfig();

    Database db;
    {
        try {
            db = new Database();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @EventHandler()
    public void chatEvent(AsyncChatEvent e) {
        Player p = e.getPlayer();
        String name = p.getName();

        if (db.isMuted(name)) {
            if (db.getMuteTime(name) > 0) {
                p.sendMessage(Component.text()
                        .color(TextColor.color(0xFF0000))
                        .content("You has been muted for " + longTimeConverter(db.getMuteTime(name)) + " with reason: " + db.getReason(name)));
                e.setCancelled(true);
                return;
            } else {
                db.removeMuted(name);
            }
        }


        ChatRenderer global = new GlobalChatRenderer();
        ChatRenderer local = new LocalChatRenderer();
        ChatRenderer dim = new DimensionChatRenderer();
        String rangeOverrideSymbol = config.getString("RangeMode.RangeOverrideSymbol");
        String dimensionChatSymbol = config.getString("DimensionMode.DimensionChatSymbol");

        if (config.getBoolean("RangeMode.Enabled")) {
            String stringMessage = PlainTextComponentSerializer.plainText().serialize(e.message());

            assert rangeOverrideSymbol != null : "RangeOverrideSymbol is not exists";
            if (stringMessage.startsWith(rangeOverrideSymbol)) {
                e.renderer(global);
            } else {
                assert dimensionChatSymbol != null : "DimensionOverrideSymbol is not exists";
                if (config.getBoolean("DimensionMode.Enabled") && stringMessage.startsWith(dimensionChatSymbol)) {
                    e.viewers().clear();
                    e.viewers().addAll(getDimensionViewers(e.getPlayer()));

                    e.renderer(dim);
                } else {
                    e.viewers().clear();
                    e.viewers().addAll(getLocalViewers(e.getPlayer()));

                    e.renderer(local);
                }
            }
        } else {
            e.renderer(local);
        }
    }
}
