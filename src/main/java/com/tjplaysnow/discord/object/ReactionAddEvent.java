package com.tjplaysnow.discord.object;

import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

import java.util.function.Consumer;

public class ReactionAddEvent {
	
	private final Long messageID;
	private final Consumer<MessageReactionAddEvent> event;
	
	public ReactionAddEvent(Long messageID, Consumer<MessageReactionAddEvent> event) {
		this.messageID = messageID;
		this.event = event;
	}
	
	public Long getMessageID() {
		return messageID;
	}
	
	public Consumer<MessageReactionAddEvent> getEvent() {
		return event;
	}
}