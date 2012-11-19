package com.TeamNovus.NovusChat.Managers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.TeamNovus.NovusChat.NovusChat;
import com.TeamNovus.SupernaturalRaces.SupernaturalRaces;
import com.TeamNovus.SupernaturalRaces.Models.Race;
import com.TeamNovus.SupernaturalRaces.Models.SNPlayer;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;

public class ChatManager {
	
	/**
	 * Parse a given message with the given parameters.
	 * 
	 * @param input - The input to parse
	 * @param player - The player sending the message
	 * @param receiver - The player receiving the message
	 * @param message - The actual message
	 * @return - The formatted message
	 */
	public String parseMessage(String input, Player player, Player receiver, String message) {
		String output = input;
		output = ChatColor.translateAlternateColorCodes("&".charAt(0), output);
		output = parseName(output, player);
		output = parseDisplayName(output, player);
		output = parseWorld(output, player);
		output = parseGroup(output, player);
		output = parsePrefix(output, player);
		output = parseSuffix(output, player);
		output = parseFaction(output, player, receiver);
		output = parseRace(output, player);
		output = parseMessage(output, player, message);
		return output;
	}
	
	public String parseTabListName(String input, Player player) {
		String output = input;
		output = parseName(output, player);
		output = parseDisplayName(output, player);
		if(output.length() > 16)
			output = output.substring(0, 16);
		return output;
	}
	
	// Advanced
	private String parseMessage(String input, Player player, String message) {
		if(player.hasPermission("novuschat.color")) {
			message = ChatColor.translateAlternateColorCodes("&".charAt(0), message);
		}
		
		if(!(player.hasPermission("novuschat.caps"))) {
			message = parseCaps(player, message);
		}
		
		return input.replace("{M}", parseCaps(player, message));
	}

	private String parseCaps(Player player, String message) {
		String output = "";
		
		int totalCaps = 0;
		int maxCaps = NovusChat.getPlugin().getConfig().getInt("options.max-caps");
		int maxCapsPerWord = NovusChat.getPlugin().getConfig().getInt("options.max-caps-per-word");
		for(String s : message.split(" ")) {
			int capsInWord = 0;
			for(char ch : s.toCharArray()) {
				if(Character.isUpperCase(ch)) {
					totalCaps++;
					capsInWord++;
					if(totalCaps > maxCaps || capsInWord > maxCapsPerWord) {
						output += Character.toString(Character.toLowerCase(ch));
					} else {
						output += Character.toString(ch);
					}
				} else {
					output += Character.toString(ch);
				}
			}
			output += " ";
		}

		return output.trim();
	}
	
	// Bukkit:
	private String parseName(String input, Player player) {
		return input.replace("{N}", player.getName());
	}
	
	private String parseDisplayName(String input, Player player) {
		return input.replace("{DN}", player.getDisplayName());
	}
	
	private String parseWorld(String input, Player player) {
		return input.replace("{W}", player.getWorld().getName());
	}
	
	// Permissions:
	private String parseGroup(String input, Player player) {
		return input.replace("{G}", ChatColor.translateAlternateColorCodes("&".charAt(0), NovusChat.permission.getPrimaryGroup(player)));
	}
	
	private String parsePrefix(String input, Player player) {
		return input.replace("{P}", ChatColor.translateAlternateColorCodes("&".charAt(0), NovusChat.chat.getGroupPrefix(player.getWorld(), NovusChat.permission.getPrimaryGroup(player))));
	}
	
	private String parseSuffix(String input, Player player) {
		return input.replace("{S}", ChatColor.translateAlternateColorCodes("&".charAt(0), NovusChat.chat.getGroupSuffix(player.getWorld(), NovusChat.permission.getPrimaryGroup(player))));
	}
	
	// Custom:
	private String parseFaction(String input, Player player, Player receiver) {
		FPlayer sender = FPlayers.i.get(player);
		FPlayer target = FPlayers.i.get(receiver);

		if (!(sender.hasFaction())) {
			return input.replace("{F}", "");
		}
				
		return input.replace("{F}", sender.getChatTag(target) + " ");
	}
	
	private String parseRace(String input, Player player) {
		SNPlayer p = SupernaturalRaces.getPlayerManager().getPlayer(player);
		Race r = SupernaturalRaces.getRaceManager().getRace(p);
		
		return input.replace("{R}", r.color() + r.name());
	}
	
}