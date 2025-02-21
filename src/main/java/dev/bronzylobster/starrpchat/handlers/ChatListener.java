package dev.bronzylobster.starrpchat.handlers;

import dev.bronzylobster.starrpchat.StarRPChat;
import dev.bronzylobster.starrpchat.utils.Config;
import dev.bronzylobster.starrpchat.utils.Database;
import dev.bronzylobster.starrpchat.utils.InternalPlaceholders;
import dev.bronzylobster.starrpchat.utils.Utils;
import dev.bronzylobster.starrpchat.utils.renderers.DimensionChatRenderer;
import dev.bronzylobster.starrpchat.utils.renderers.GlobalChatRenderer;
import dev.bronzylobster.starrpchat.utils.renderers.LocalChatRenderer;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
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
            if (!(0 >= db.getMuteTime(name))) {
                String muted = config.getString(Config.MUTED.getPath());
                assert muted != null : "Messages.muted is not exists";
                muted = muted.replace("%time%", Utils.longTimeConverter(db.getMuteTime(name)))
                        .replace("%reason%", db.getReason(name));
                p.sendMessage(Utils.toComponent(InternalPlaceholders.PlayerPlaceholders(muted, p)));
                e.setCancelled(true);
                return;
            } else {
                db.removeMuted(name);
            }
        }


        ChatRenderer global = new GlobalChatRenderer();
        ChatRenderer local = new LocalChatRenderer();
        ChatRenderer dim = new DimensionChatRenderer();
        String rangeOverrideSymbol = config.getString(Config.RANGE_OVERRIDE_SYMBOL.getPath());
        String dimensionChatSymbol = config.getString(Config.DIM_CHAT_SYMBOL.getPath());

        if (config.getBoolean(Config.RANGEMODE_ENABLED.getPath())) {
            String stringMessage = PlainTextComponentSerializer.plainText().serialize(e.message());

            assert rangeOverrideSymbol != null : "RangeOverrideSymbol is not exists";
            if (stringMessage.startsWith(rangeOverrideSymbol)) {
                e.renderer(global);
            } else {
                assert dimensionChatSymbol != null : "DimensionOverrideSymbol is not exists";
                if (config.getBoolean(Config.DIMMODE_ENABLED.getPath()) && stringMessage.startsWith(dimensionChatSymbol)) {
                    e.viewers().clear();
                    e.viewers().addAll(getDimensionViewers(e.getPlayer()));
                    e.viewers().add(Bukkit.getConsoleSender());

                    e.renderer(dim);
                } else {
                    e.viewers().clear();
                    e.viewers().addAll(getLocalViewers(e.getPlayer()));
                    e.viewers().add(Bukkit.getConsoleSender());

                    e.renderer(local);
                }
            }
        } else {
            e.renderer(local);
        }
    }
}
