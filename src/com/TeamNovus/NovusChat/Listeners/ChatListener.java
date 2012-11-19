package com.TeamNovus.NovusChat.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.TeamNovus.NovusChat.NovusChat;

public class ChatListener implements Listener {

	@EventHandler
	public void onPlayerChatEvent(AsyncPlayerChatEvent event) {		
		for(Player recipient : event.getRecipients()) {
			PlayerChatEvent e = new PlayerChatEvent(recipient,
													event.getPlayer(),
													event.getFormat(),
													event.getMessage());
			Bukkit.getServer().getPluginManager().callEvent(e);
			
			if(!(e.isCancelled()) && !(event.isCancelled())) {
				if(event.getPlayer().getName().equals(recipient.getName())) {
					Bukkit.getServer().getConsoleSender().sendMessage((NovusChat.getChatManager().parse(e)));
				}
				
				e.getPlayer().sendMessage(NovusChat.getChatManager().parse(e));
			}
		}
		
		event.setCancelled(true);
	}
}
