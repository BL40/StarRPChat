package dev.bronzylobster.starrpchat.handlers;

import dev.bronzylobster.starrpchat.StarRPChat;
import dev.bronzylobster.starrpchat.utils.Config;
import dev.bronzylobster.starrpchat.utils.InternalPlaceholders;
import dev.bronzylobster.starrpchat.utils.Utils;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class JoinListener implements Listener {

    FileConfiguration config = StarRPChat.getInstance().getConfig();

    @EventHandler
    public void JoinEvent (PlayerJoinEvent e) {
        Player p = e.getPlayer();

        String join = config.getString(Config.JOIN.getPath());
        assert join != null : Config.JOIN.getPath() + " is not exists";

        e.joinMessage(Utils.toComponent(InternalPlaceholders.PlayerPlaceholders(join, p)));

        String soundkey = config.getString(Config.SOUND_JOIN.getPath());
        if (soundkey != null) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.getOnlinePlayers().forEach(viewer -> viewer.playSound(Sound.sound(Key.key(soundkey), Sound.Source.AMBIENT, 1f, 1f)));
                }
            }.runTaskAsynchronously(StarRPChat.getInstance());
        }
    }

    @EventHandler
    public void QuitEvent (PlayerQuitEvent e) {
        Player p = e.getPlayer();

        String quit = config.getString(Config.QUIT.getPath());
        assert quit != null : Config.QUIT.getPath() + " is not exists";

        e.quitMessage(Utils.toComponent(InternalPlaceholders.PlayerPlaceholders(quit, p)));

        String soundkey = config.getString(Config.SOUND_QUIT.getPath());
        if (soundkey != null) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.getOnlinePlayers().forEach(viewer -> viewer.playSound(Sound.sound(Key.key(soundkey), Sound.Source.AMBIENT, 1f, 1f)));
                }
            }.runTaskAsynchronously(StarRPChat.getInstance());
        }
    }
}
