package com.TeamNovus.NovusChat.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import com.TeamNovus.NovusChat.NovusChat;

public class PlayerListener implements Listener {

	@EventHandler
	public void onPlayerLoginEvent(PlayerLoginEvent event) {
		final Player p = event.getPlayer();
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(NovusChat.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				p.setPlayerListName(ChatColor.translateAlternateColorCodes("&".charAt(0), p.getDisplayName()));
			}
		}, 20);		
	}
	
}
