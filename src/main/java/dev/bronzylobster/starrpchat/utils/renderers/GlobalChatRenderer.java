package dev.bronzylobster.starrpchat.utils.renderers;

import dev.bronzylobster.starrpchat.StarRPChat;
import io.papermc.paper.chat.ChatRenderer;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import dev.bronzylobster.starrpchat.utils.Config;

import static dev.bronzylobster.starrpchat.utils.InternalPlaceholders.*;

public class GlobalChatRenderer implements ChatRenderer {

    FileConfiguration config = StarRPChat.getInstance().getConfig();

    @Override
    public @NotNull Component render(@NotNull Player player, @NotNull Component playerDisplayName, @NotNull Component message, @NotNull Audience audience) {
        String sformat = config.getString(Config.GLOBAL_CHAT_FORMAT.getPath());
        assert sformat != null : "RangeMode.GlobalChatFormat not exists";
        String rangeOverrideSymbol = config.getString(Config.RANGE_OVERRIDE_SYMBOL.getPath());
        assert rangeOverrideSymbol != null : "RangeMode.RangeOverrideSymbol not exists";
        Component cFormat = LegacyComponentSerializer.legacyAmpersand().deserialize(sformat);

        MiniMessage minimessage = MiniMessage.builder()
                .tags(TagResolver.builder()
                        .resolver(StandardTags.color())
                        .resolver(StandardTags.decorations())
                        .build()
                )
                .build();
        String format = minimessage.serialize(cFormat);
        format = PlayerPlaceholders(format, player);
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            format = PlaceholderAPI.setPlaceholders(player, format);
        }
        String sMessage = minimessage.serialize(message);
        sMessage = format
                .replace("%message%", sMessage)
                .replaceFirst(rangeOverrideSymbol, "");

        if (player.hasPermission("srpc.chat.placeholders")) {
            sMessage = PlayerPlaceholders(sMessage, player);
            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                sMessage = PlaceholderAPI.setPlaceholders(player, sMessage);
            }
        }
        message = minimessage.deserialize(sMessage);

        return message;
    }
}
