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

import static dev.bronzylobster.starrpchat.utils.InternalPlaceholders.PlayerPlaceholders;

public class DimensionChatRenderer implements ChatRenderer {
    FileConfiguration config = StarRPChat.getInstance().getConfig();
    @Override
    public @NotNull Component render(@NotNull Player player, @NotNull Component playerDisplayName, @NotNull Component rawMessage, @NotNull Audience audience) {
        String format = config.getString("DimensionMode.DimensionChatFormat");
        String dimensionChatSymbol = config.getString("DimensionMode.DimensionChatSymbol");
        String msg = LegacyComponentSerializer.legacyAmpersand().serialize(rawMessage);

        assert dimensionChatSymbol != null : "DimensionChatSymbol is not exists";
        assert format != null: "DimensionChatFormat is not exists";
        format = PlayerPlaceholders(format, player);
        format = format
                .replace("&", "§")
                .replace("%message%", msg)
                .replaceFirst(dimensionChatSymbol, "");

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            format = PlaceholderAPI.setPlaceholders(player, format);
        }

        return LegacyComponentSerializer.legacyAmpersand().deserialize(format);
    }
}
