package dev.bronzylobster.starrpchat.handlers;

import dev.bronzylobster.starrpchat.StarRPChat;
import dev.bronzylobster.starrpchat.utils.renderers.DimensionChatRenderer;
import dev.bronzylobster.starrpchat.utils.renderers.GlobalChatRenderer;
import dev.bronzylobster.starrpchat.utils.renderers.LocalChatRenderer;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static dev.bronzylobster.starrpchat.utils.Utils.*;

public class ChatListener implements Listener {

    FileConfiguration config = StarRPChat.getInstance().getConfig();

    @EventHandler()
    public void chatEvent(AsyncChatEvent e) {
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
            e.renderer(global);
        }
    }
}
