package com.TeamNovus.NovusChat.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerChatEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	private Player sender;
	private String format;
	private String message;
    
    public PlayerChatEvent(Player player, Player sender, String format, String message) {
    	super(player);
    	this.format = format;
    	this.sender = sender;
    	this.message = message;
    }
    
    public Player getSender() {
    	return sender;
    }

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
    
    public String getMessage() {
    	return message;
    }
    
    public void setMessage(String message) {
    	this.message = message;
    }
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
