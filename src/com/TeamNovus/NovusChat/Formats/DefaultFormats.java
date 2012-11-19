package com.TeamNovus.NovusChat.Formats;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.TeamNovus.NovusChat.NovusChat;
import com.TeamNovus.NovusChat.Models.Format;
import com.TeamNovus.NovusChat.Models.Formatter;
import com.TeamNovus.NovusChat.Models.MessageFormat;
import com.TeamNovus.SupernaturalRaces.SupernaturalRaces;
import com.TeamNovus.SupernaturalRaces.Models.Race;
import com.TeamNovus.SupernaturalRaces.Models.SNPlayer;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;

public class DefaultFormats extends Formatter {
	
	// Message Formats:
	@MessageFormat(replacement = "{MessageFormat}")
	public String message(Player sender, Player reciever, String message) {
		if(sender.hasPermission("novuschat.color")) {
			return ChatColor.translateAlternateColorCodes("&".charAt(0), message);
		}
		return message;
	}
	
	// Default Formats:
	@Format(replacement = "{N}")
	public String name(Player target, Player reciever) {
		return target.getName();
	}
	
	@Format(replacement = "{DN}")
	public String displayName(Player target, Player reciever) {
		return target.getDisplayName();
	}
	
	@Format(replacement = "{S}")
	public String suffix(Player target, Player reciever) {
		return ChatColor.translateAlternateColorCodes("&".charAt(0), NovusChat.chat.getGroupSuffix(target.getWorld(), NovusChat.permission.getPrimaryGroup(target)));
	}
	
	@Format(replacement = "{P}")
	public String prefix(Player target, Player reciever) {
		return ChatColor.translateAlternateColorCodes("&".charAt(0), NovusChat.chat.getGroupPrefix(target.getWorld(), NovusChat.permission.getPrimaryGroup(target)));
	}
	
	@Format(replacement = "{G}")
	public String group(Player target, Player reciever) {
		return NovusChat.permission.getPrimaryGroup(target);
	}
	
	@Format(replacement = "{W}")
	public String world(Player target, Player reciever) {
		return target.getWorld().getName();
	}
	
	@Format(replacement = "{F}")
	public String faction(Player target, Player reciever) {
		FPlayer sender = FPlayers.i.get(target);
		FPlayer player = FPlayers.i.get(reciever);

		if (!(sender.hasFaction())) {
			return "";
		}
				
		return sender.getChatTag(player) + " ";
	}
	
	@Format(replacement = "{R}")
	public String race(Player target, Player reciever) {
		SNPlayer player = SupernaturalRaces.getPlayerManager().getPlayer(target);
		Race race = SupernaturalRaces.getRaceManager().getRace(player);
		return race.color() + race.name();
	}
}
