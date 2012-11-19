package com.TeamNovus.NovusChat.Managers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.TeamNovus.NovusChat.NovusChat;
import com.TeamNovus.NovusChat.Models.Format;
import com.TeamNovus.NovusChat.Models.Formatter;
import com.TeamNovus.NovusChat.Models.MessageFormat;

public class ChatManager {
	private HashMap<String, Method> defaultFormatting = new HashMap<String, Method>();
	private HashMap<String, Method> chatFormatting = new HashMap<String, Method>();

	public void registerClass(Class<? extends Formatter> c) {
		for(Method method : c.getMethods()) {
			if(method.isAnnotationPresent(Format.class)) {
				if(method.getParameterAnnotations().length == 2 && method.getParameterAnnotations()[0].equals(Player.class) && method.getParameterTypes()[2].equals(Player.class)) {
					try {
						defaultFormatting.put(method.getAnnotation(Format.class).replacement(), method);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
				}
			}
			
			if(method.isAnnotationPresent(MessageFormat.class)) {
				if(method.getParameterAnnotations().length == 3 && method.getParameterAnnotations()[0].equals(Player.class) && method.getParameterTypes()[2].equals(Player.class) && method.getParameterTypes()[2].equals(String.class)) {
					try {
						chatFormatting.put(method.getAnnotation(Format.class).replacement(), method);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public String formatMessage(Player sender, Player reciever, String message) {
		String format = NovusChat.getPlugin().getConfig().getString("format.chat");
		format = defaultFormat(sender, format);
		format = messageFormat(sender, reciever, message, format);
		return format;
	}
	
	public String formatList(Player sender, String message) {
		String format = NovusChat.getPlugin().getConfig().getString("format.list");
		format = defaultFormat(sender, format);
		return format;
	}
	
	public String defaultFormat(Player target, String format) {
		format = ChatColor.translateAlternateColorCodes("&".charAt(0), format);
		
		for(String f : defaultFormatting.keySet()) {
			try {
				format = format.replace(f, (String) defaultFormatting.get(f).invoke(defaultFormatting.get(f).getDeclaringClass().newInstance(), target));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			}
		}
		
		return format;
	}
	
	public String messageFormat(Player sender, Player reciever, String message, String format) {
		format = ChatColor.translateAlternateColorCodes("&".charAt(0), format);
		
		for(String f : chatFormatting.keySet()) {
			try {
				format = format.replace(f, (String) chatFormatting.get(f).invoke(chatFormatting.get(f).getDeclaringClass().newInstance(), sender, reciever, message));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			}
		}
		
		return format;
	}
	
}