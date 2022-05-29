package com.pb.commandlog;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommandLog extends JavaPlugin implements Listener {
FileConfiguration config = this.getConfig();
	
	@Override
    public void onEnable() {
		config.options().copyDefaults(true);
		this.saveConfig();
		
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
    public void onCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		if(player == null) return;
		String msg = event.getMessage().replaceFirst("[/]", "");
		if(msg.startsWith("say ") || msg.startsWith("t ") || msg.startsWith("tell ") || msg.startsWith("tellraw ") || msg.startsWith("w ") || msg.startsWith("me ") || msg.startsWith("msg ")) {
			return;
		}
		
		Date currentDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("kk:mm:ss");
		String time = formatter.format(currentDate);
		
		String format = config.getString("format");
		format = format.replaceAll("[$]TIME", time);
		format = format.replaceAll("[$]WORLD", player.getLocation().getWorld().getName());
		format = format.replaceAll("[$]USERNAME", player.getName());
		format = format.replaceAll("[$]COMMAND", msg);
		
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', format));
	}

    @Override
    public void onDisable() {
		
    }
}
