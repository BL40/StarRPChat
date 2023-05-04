package dev.bronzylobster.starrpchat;

import dev.bronzylobster.starrpchat.handlers.ChatListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
public final class StarRPChat extends JavaPlugin {

    private static StarRPChat instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);

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

    public static StarRPChat getInstance() {
        return instance;
    }
}
