package dev.bronzylobster.starrpchat.utils;

import dev.bronzylobster.starrpchat.StarRPChat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Utils {
    public static Collection<Player> getLocalViewers(Player p) {
        FileConfiguration config = StarRPChat.getInstance().getConfig();

        Collection<Player> viewers = p.getWorld().getPlayers();
        viewers.clear();

        Location location = p.getLocation();
        double distance = config.getDouble(Config.RANGEMODE_DISTANCE.getPath());

        for (Player viewer : p.getWorld().getPlayers()) {
            if (location.distanceSquared(viewer.getLocation()) > distance) {
                continue;
            }
            viewers.add(viewer);
        }
        return viewers;
    }

    public static Collection<Player> getDimensionViewers(Player p) {
        Collection<Player> viewers = p.getWorld().getPlayers();
        viewers.clear();

        World.Environment dim = p.getWorld().getEnvironment();

        for (Player viewer : p.getWorld().getPlayers()) {
            if (!viewer.getWorld().getEnvironment().equals(dim)) {
                continue;
            }
            viewers.add(viewer);
        }
        return viewers;
    }

    public static String parseToString(boolean ignoreFirst, String[] msg) {

        ArrayList<String> arrMsg = new ArrayList<>(Arrays.asList(msg));
        if (ignoreFirst) {
            arrMsg.remove(0);
        }

        return String.join(" ", arrMsg);
    }

    public static String longTimeConverter(long time) {
        long d = time/1000/60/60/24;
        long h = time/1000/60/60 - d*24;
        long m = time/1000/60 - (h + d*24)*60;
        long s = time/1000 - (m + (h + d*24)*60)*60;

        return d + "d " + h + "h " + m + "m " + s + "s";
    }

    public static Component toComponent (String message) {
        MiniMessage minimessage = MiniMessage.builder()
                .tags(TagResolver.builder()
                        .resolver(StandardTags.color())
                        .resolver(StandardTags.decorations())
                        .build()
                )
                .build();

        Component out = LegacyComponentSerializer.legacyAmpersand().deserialize(message);
        String string = minimessage.serialize(out);
        return minimessage.deserialize(string);
    }

    public static Component toComponent (Component message) {
        MiniMessage minimessage = MiniMessage.builder()
                .tags(TagResolver.builder()
                        .resolver(StandardTags.color())
                        .resolver(StandardTags.decorations())
                        .build()
                )
                .build();

        String string = minimessage.serialize(message);
        return minimessage.deserialize(string);
    }

    public static int getNumber (String sNum, CommandSender p) throws NumberFormatException{
        try {
            return NumberUtils.createInteger(sNum);
        } catch (NumberFormatException e) {
            p.sendMessage(Component.text("Value is not number").color(TextColor.color(0xFF0000)).clickEvent(ClickEvent.openUrl("https://www.youtube.com/watch?v=L1YkydtyTt8")));
            return 0;
        }
    }
}
