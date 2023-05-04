package dev.bronzylobster.starrpchat.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
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
}
