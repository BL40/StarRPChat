package dev.bronzylobster.starrpchat.utils.renderers;

import dev.bronzylobster.starrpchat.StarRPChat;
import io.papermc.paper.chat.ChatRenderer;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static dev.bronzylobster.starrpchat.utils.InternalPlaceholders.*;

public class GlobalChatRenderer implements ChatRenderer {

    FileConfiguration config = StarRPChat.getInstance().getConfig();

    @Override
    public @NotNull Component render(@NotNull Player player, @NotNull Component playerDisplayName, @NotNull Component rawMessage, @NotNull Audience audience) {
        String format = config.getString("RangeMode.GlobalChatFormat");
        String rangeOverrideSymbol = config.getString("RangeMode.RangeOverrideSymbol");
        String msg = LegacyComponentSerializer.legacyAmpersand().serialize(rawMessage);

        assert format != null: "GlobalChatFormat is not exists";
        assert rangeOverrideSymbol != null : "RangeOverrideSymbol is not exists";
        format = PlayerPlaceholders(format, player);
        format = format
                .replace("&", "§")
                .replace("%message%", msg)
                .replaceFirst(rangeOverrideSymbol, "");

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            format = PlaceholderAPI.setPlaceholders(player, format);
        }

        return LegacyComponentSerializer.legacyAmpersand().deserialize(format);
    }
}
