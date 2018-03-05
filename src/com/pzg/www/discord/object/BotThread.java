package com.pzg.www.discord.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * <b>Action Manager Class</b><br>
 * <i>Used dirctly by the <b>Bot</b>.</i>
 * @version 1.0
 * @author TJPlaysNow
 */
public class BotThread implements Runnable {
	
	private Bot bot;
	private HashMap<Runnable, Long> actions;
	private Thread thread;
	private Plugin plugin;
	
	/**
	 * Create a new Action Manager that the bot uses.
	 * @param bot The bot that is creating the manager.
	 */
	public BotThread(Bot bot) {
		this.bot = bot;
		actions = new HashMap<Runnable, Long>();
		thread = new Thread(this, "BotThread");
		thread.start();
	}
	
	/**
	 * Create a new Action Manager that the bot uses.
	 * @param bot The bot that is creating the manager.
	 * @param plugin The plugin that the bot is running from.
	 */
	public BotThread(Bot bot, Plugin plugin) {
		this.bot = bot;
		this.plugin = plugin;
		actions = new HashMap<Runnable, Long>();
	}
	
	/**
	 * A new action to run later.
	 * @param action The action to be run.
	 * @param seconds The time in seconds to wait before running the action.
	 */
	public void addAction(Runnable action, int seconds) {
		if (plugin != null) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, action, (seconds * 20));
		} else {
			long time = TimeUnit.SECONDS.toMillis(seconds) + System.currentTimeMillis();
			actions.put(action, time);
		}
	}
	
	/**
	 * <b>DO NOT CALL THIS METHOD</b><br><br>
	 * <i>This method is constantly being looped<br>
	 * and will run an <b>Action</b> when time has come.</i>
	 */
	@Override
	public void run() {
		while (bot.isOnline()) {
//			Check to see if there is an action needed to run.
			List<Runnable> delete = new ArrayList<Runnable>();
			for (Runnable action : actions.keySet()) {
				if (System.currentTimeMillis() >= actions.get(action)) {
					action.run();
					delete.add(action);
				}
			}
			for (Runnable action : delete) {
				actions.remove(action);
			}
//			Check to see if a command was typed into the console
//			bot.consoleCheck();
		}
	}
}