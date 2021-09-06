package com.tjplaysnow.discord.object;

import com.tjplaysnow.discord.object.logger.LogLevel;
import com.tjplaysnow.discord.object.logger.Logger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;

public abstract class ProgramBot implements EventListener {
	
	private final String token;
	private final Logger logger;
	private boolean isOnline;
	protected JDA jda;
	
	protected ProgramBot(String token) {
		this.token = token;
		logger = new Logger(LogLevel.INFO);
	}
	
	protected ProgramBot(String token, LogLevel logLevel) {
		this.token = token;
		logger = new Logger(logLevel);
	}
	
	protected void build() {
		try {
			jda = JDABuilder.createDefault(token, EnumSet.allOf(GatewayIntent.class)).addEventListeners(this).build();
			jda.updateCommands().queue();
			isOnline = true;
		} catch (LoginException | IllegalArgumentException e) {
			e.printStackTrace();
			isOnline = false;
		}
	}
	
	/**
	 * This is used for indepth manipulation with the bot.
	 * @return JDA instance of the bot.
	 */
	public JDA getBot() {
		return jda;
	}
	
	/**
	 * Checks to see if the bot is online.
	 * @return Boolean of online.
	 */
	public boolean isOnline() {
		return isOnline;
	}
	
	/**
	 * Stops the bot and goes offline.
	 */
	public void logout() {
		jda.shutdown();
		isOnline = false;
	}
	
	/**
	 * Set the bot's playing game text.
	 * @param game Playing text.
	 */
	public void setGame(String game) {
		jda.getPresence().setPresence(Activity.playing(game), true);
	}
	
	/**
	 * Set the bot's activity with the text.
	 *
	 * @param action The bot's action (Listening, Playing, Streaming, Watching)
	 * @param text The text to display.
	 *
	 */
	public void setActivity(String action, String text) {
		if (action.equalsIgnoreCase("Listening")) {
			jda.getPresence().setPresence(Activity.listening(text), true);
		} else if (action.equals("Playing")) {
			jda.getPresence().setPresence(Activity.playing(text), true);
		} else if (action.equals("Streaming")) {
			jda.getPresence().setPresence(Activity.streaming(text.split(" : ")[0], text.split(" : ")[1]), true);
		} else if (action.equals("Watching")) {
			jda.getPresence().setPresence(Activity.watching(text), true);
		}
	}
	
	/**
	 * Get the logger for logging purposes.
	 * @return <b>Logger</b>
	 */
	public Logger logger() {
		return logger;
	}
}