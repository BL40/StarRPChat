package dev.bronzylobster.starrpchat.utils;

import dev.bronzylobster.starrpchat.StarRPChat;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Collection;

public class Utils {
    public static Collection<Player> getLocalViewers(Player p) {
        FileConfiguration config = StarRPChat.getInstance().getConfig();

        Collection<Player> viewers = p.getWorld().getPlayers();
        viewers.clear();

        Location location = p.getLocation();
        double distance = config.getDouble("RangeMode.RangeDistance");

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

    public static String longTimeConverter(long time) {
        long d = time/1000/60/60/24;
        long h = time/1000/60/60 - d*24;
        long m = time/1000/60 - (h + d*24)*60;
        long s = time/1000 - (m + (h + d*24)*60)*60;

        return d + "d " + h + "h " + m + "m " + s + "s";
    }
}
