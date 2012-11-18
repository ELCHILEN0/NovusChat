package com.TeamNovus.NovusChat.Formats;

import org.bukkit.ChatColor;

import com.TeamNovus.NovusChat.NovusChat;
import com.TeamNovus.NovusChat.Listeners.PlayerChatEvent;
import com.TeamNovus.NovusChat.Models.Format;
import com.TeamNovus.NovusChat.Models.Formatter;
import com.TeamNovus.SupernaturalRaces.SupernaturalRaces;
import com.TeamNovus.SupernaturalRaces.Models.Race;
import com.TeamNovus.SupernaturalRaces.Models.SNPlayer;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;

public class DefaultFormats extends Formatter {
	@Format(replacement = "{M}")
	public String message(PlayerChatEvent e) {
		if(e.getSender().hasPermission("novuschat.color")) {
			return ChatColor.translateAlternateColorCodes("&".charAt(0), e.getMessage());
		}
		return e.getMessage();
	}
	
	@Format(replacement = "{N}")
	public String name(PlayerChatEvent e) {
		return e.getSender().getName();
	}
	
	@Format(replacement = "{DN}")
	public String displayName(PlayerChatEvent e) {
		return e.getSender().getDisplayName();
	}
	
	@Format(replacement = "{S}")
	public String suffix(PlayerChatEvent e) {
		return ChatColor.translateAlternateColorCodes("&".charAt(0), NovusChat.chat.getGroupSuffix(e.getPlayer().getWorld(), NovusChat.permission.getPrimaryGroup(e.getSender())));
	}
	
	@Format(replacement = "{P}")
	public String prefix(PlayerChatEvent e) {
		return ChatColor.translateAlternateColorCodes("&".charAt(0), NovusChat.chat.getGroupPrefix(e.getPlayer().getWorld(), NovusChat.permission.getPrimaryGroup(e.getSender())));
	}
	
	@Format(replacement = "{G}")
	public String group(PlayerChatEvent e) {
		return NovusChat.permission.getPrimaryGroup(e.getSender());
	}
	
	@Format(replacement = "{W}")
	public String world(PlayerChatEvent e) {
		return e.getSender().getWorld().getName();
	}
	
	@Format(replacement = "{F}")
	public String faction(PlayerChatEvent e) {
		FPlayer sender = FPlayers.i.get(e.getSender());
		FPlayer player = FPlayers.i.get(e.getPlayer());

		if (!(sender.hasFaction())) {
			return "";
		}
				
		return sender.getChatTag(player) + " ";
	}
	
	@Format(replacement = "{R}")
	public String race(PlayerChatEvent e) {
		SNPlayer player = SupernaturalRaces.getPlayerManager().getPlayer(e.getSender());
		Race race = SupernaturalRaces.getRaceManager().getRace(player);
		return race.color() + race.name();
	}
}
