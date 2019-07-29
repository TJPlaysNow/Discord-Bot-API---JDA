package com.tjplaysnow.discord.object;

import com.tjplaysnow.discord.object.logger.LogLevel;
import com.tjplaysnow.discord.object.logger.Logger;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.hooks.EventListener;
import okhttp3.OkHttpClient;

import javax.security.auth.login.LoginException;

public abstract class ProgramBot implements EventListener {
	
	private String token;
	
	private JDA jda;
	private boolean isOnline;
	
	private Logger logger;
	
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
			jda = new JDABuilder(AccountType.BOT).setToken(token).addEventListener(this).setHttpClient(new OkHttpClient.Builder().build()).build();
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
		jda.getPresence().setPresence(Game.playing(game), true);
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
			jda.getPresence().setPresence(Game.listening(text), true);
		} else if (action.equals("Playing")) {
			jda.getPresence().setPresence(Game.playing(text), true);
		} else if (action.equals("Streaming")) {
			jda.getPresence().setPresence(Game.streaming(text.split(" : ")[0], text.split(" : ")[1]), true);
		} else if (action.equals("Watching")) {
			jda.getPresence().setPresence(Game.watching(text), true);
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