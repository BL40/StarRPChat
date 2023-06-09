package dev.bronzylobster.starrpchat.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class InternalPlaceholders {
    public static String PlayerPlaceholders(String input, Player p) {
        String result;
        Location loc = p.getLocation();

        result = input
                .replace("%name%", p.getName())
                .replace("%display_name%", LegacyComponentSerializer.legacyAmpersand().serialize(p.displayName()))
                .replace("%pos_x%", String.valueOf(loc.blockX()))
                .replace("%pos_y%", String.valueOf(loc.blockY()))
                .replace("%pos_z%", String.valueOf(loc.blockZ()))
                .replace("%level%", String.valueOf(p.getLevel()))
                .replace("%ping%", String.valueOf(p.getPing()))
                .replace("%dim%", p.getWorld().getEnvironment().name());

        return result;
    }

    public static Component PlayerPlaceholders(Component input, Player p) {
        Location loc = p.getLocation();

        input = input.replaceText(TextReplacementConfig.builder()
                .replacement(p.getName())
                .match("%name%").build());
        input = input.replaceText(TextReplacementConfig.builder()
                .replacement(p.displayName())
                .match("%display_name%").build());
        input = input.replaceText(TextReplacementConfig.builder()
                .replacement(String.valueOf(loc.blockX()))
                .match("%pos_x%").build());
        input = input.replaceText(TextReplacementConfig.builder()
                .replacement(String.valueOf(loc.blockY()))
                .match("%pos_y%").build());
        input = input.replaceText(TextReplacementConfig.builder()
                .replacement(String.valueOf(loc.blockZ()))
                .match("%pos_z%").build());
        input = input.replaceText(TextReplacementConfig.builder()
                .replacement(String.valueOf(p.getLevel()))
                .match("%level%").build());
        input = input.replaceText(TextReplacementConfig.builder()
                .replacement(String.valueOf(p.getPing()))
                .match("%ping%").build());
        input = input.replaceText(TextReplacementConfig.builder()
                .replacement(p.getWorld().getEnvironment().name())
                .match("%dim%").build());

        return input;
    }
}
