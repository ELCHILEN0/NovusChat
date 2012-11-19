package com.TeamNovus.NovusChat.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import com.TeamNovus.NovusChat.NovusChat;

public class ChatListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerChatEvent(AsyncPlayerChatEvent event) {		
		for(Player recipient : event.getRecipients()) {
			if(event.isCancelled()) break;
			
			// Build the message
			String messageFormat = NovusChat.getPlugin().getConfig().getString("format.chat");
			String message = NovusChat.getChatManager().parseMessage(
										messageFormat, 
										event.getPlayer(), 
										recipient, 
										event.getMessage());
			
			
			// Console Override to display messages in the console
			if(event.getPlayer().getName().equals(recipient.getName())) {
				Bukkit.getServer().getConsoleSender().sendMessage(message);
			}
				
			// Send the message
			event.getPlayer().sendMessage(message);
			
			// Update the player list tag
			String listFormat = NovusChat.getPlugin().getConfig().getString("format.tab-list");
			String list = NovusChat.getChatManager().parseTabListName(
										ChatColor.translateAlternateColorCodes("&".charAt(0), listFormat), 
										event.getPlayer());
			event.getPlayer().setPlayerListName(list);
		}
		
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerLoginEvent(PlayerLoginEvent event) {
		
		final Player p = event.getPlayer();
		Bukkit.getScheduler().scheduleAsyncDelayedTask(
				NovusChat.getPlugin(), 
				new Runnable() {
					@Override
					public void run() {
						String listFormat = NovusChat.getPlugin().getConfig().getString("format.tab-list");
						String list = NovusChat.getChatManager().parseTabListName(
												ChatColor.translateAlternateColorCodes("&".charAt(0), listFormat), 
												p);
						p.setPlayerListName(list);
					}
				}, 2);
	}
}
