package dev.bronzylobster.starrpchat;

import dev.bronzylobster.starrpchat.commands.MuteCommand;
import dev.bronzylobster.starrpchat.commands.UnmuteCommand;
import dev.bronzylobster.starrpchat.handlers.ChatListener;
import dev.bronzylobster.starrpchat.utils.Config;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class StarRPChat extends JavaPlugin {

    @Getter
    private static StarRPChat instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);

        new MuteCommand();
        new UnmuteCommand();

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
}
