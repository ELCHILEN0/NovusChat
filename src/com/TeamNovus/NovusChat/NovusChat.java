package com.TeamNovus.NovusChat;

import java.io.File;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.TeamNovus.NovusChat.Formats.DefaultFormats;
import com.TeamNovus.NovusChat.Listeners.ChatListener;
import com.TeamNovus.NovusChat.Listeners.PlayerListener;
import com.TeamNovus.NovusChat.Managers.ChatManager;

public class NovusChat extends JavaPlugin {
	private static NovusChat plugin;
	private static ChatManager chatManager;
    public static Permission permission;
    public static Chat chat;

	@Override
	public void onEnable() {
		plugin = this;
		
		if(!(new File(getDataFolder() + File.separator + "config.yml").exists())) {
			saveDefaultConfig();
		}
		reloadConfig();
		
		getServer().getPluginManager().registerEvents(new ChatListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(Chat.class);
        permission = permissionProvider.getProvider();
        chat = chatProvider.getProvider();
		
		chatManager = new ChatManager();
		chatManager.registerClass(DefaultFormats.class);
	}
	
	@Override
	public void onDisable() {
		plugin = null;
		chatManager = null;
		permission = null;
		chat = null;
	}
	
	public static NovusChat getPlugin() {
		return plugin;
	}
	
	public static ChatManager getChatManager() {
		return chatManager;
	}
}
