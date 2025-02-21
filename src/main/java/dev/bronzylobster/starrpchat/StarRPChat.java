package dev.bronzylobster.starrpchat;

import dev.bronzylobster.starrpchat.commands.*;
import dev.bronzylobster.starrpchat.handlers.ChatListener;
import dev.bronzylobster.starrpchat.handlers.JoinListener;
import dev.bronzylobster.starrpchat.utils.Config;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class StarRPChat extends JavaPlugin {

    @Getter
    private static StarRPChat instance;
    @Getter
    private static Map<Player, Player> reply = new HashMap<>();
    @Getter
    private static List<Player> pidors = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);

        new MuteCommand();
        new UnmuteCommand();
        new WTCommand();
        new MsgCommand();
        new ReplyCommand();
        new SpyCommand();
        new WhoCommand();

        this.getLogger().info("StarRPChat enabled!");
        this.getLogger().info("Nice playing!");

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            this.getLogger().info("PlaceholderAPI installed!");

        } else {
            this.getLogger().warning("PlaceholderAPI not installed!");
        }

    }

    @Override
    public void onDisable() {
    }

    public static void debug(String s) {
        if (getInstance().getConfig().getBoolean(Config.DEBUG_ENABLED.getPath())) {
            Bukkit.broadcast(Component.text("[DEBUG] " + s).color(TextColor.color(0xFF0000)));
        }
    }

    public static void addReply(Player sender, Player receiver) {
        reply.put(receiver, sender);
    }

    public static void addPidor(Player p) {
        pidors.add(p);
    }

    public static void removePidor(Player p) {
        pidors.remove(p);
    }
}
