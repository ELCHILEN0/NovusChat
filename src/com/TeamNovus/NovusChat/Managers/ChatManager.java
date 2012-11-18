package com.TeamNovus.NovusChat.Managers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;

import org.bukkit.ChatColor;

import com.TeamNovus.NovusChat.NovusChat;
import com.TeamNovus.NovusChat.Listeners.PlayerChatEvent;
import com.TeamNovus.NovusChat.Models.Format;
import com.TeamNovus.NovusChat.Models.Formatter;


public class ChatManager {
	private HashMap<String, Method> formats = new HashMap<String, Method>();

	public void registerClass(Class<? extends Formatter> c) {
		for(Method method : c.getMethods()) {
			if(method.isAnnotationPresent(Format.class)) {
				for(Type type : method.getParameterTypes()) {
					if(type.equals(PlayerChatEvent.class)) {
						try {
							formats.put(method.getAnnotation(Format.class).replacement(), method);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	public String parse(PlayerChatEvent event) {
		return parseFormat(event, NovusChat.getPlugin().getConfig().getString("format.message"));
	}
	
	public String parseFormat(PlayerChatEvent event, String format) {
		format = ChatColor.translateAlternateColorCodes("&".charAt(0), format);
		for(String f : formats.keySet()) {
			try {
				format = format.replace(f, (String) formats.get(f).invoke(formats.get(f).getDeclaringClass().newInstance(), event));
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