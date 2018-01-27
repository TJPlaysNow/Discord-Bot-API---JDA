package com.pzg.www.discord.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <b>Action Manager Class</b><br>
 * <i>Used dirctly by the <b>Bot</b>.</i>
 * @version 1.0
 * @author TJPlaysNow
 */
public class BotThread implements Runnable {
	
	private Bot bot;
	private HashMap<Runnable, Long> actions;
	
	/**
	 * Create a new Action Manager that the bot uses.
	 * @param bot The bot that is creating the manager.
	 */
	public BotThread(Bot bot) {
		this.bot = bot;
		actions = new HashMap<Runnable, Long>();
		new Thread(this).start();
		Thread.currentThread().setName("Bot Action Thread");
	}
	
	/**
	 * A new action to run later.
	 * @param action The action to be run.
	 * @param seconds The time in seconds to wait before running the action.
	 */
	public void addAction(Runnable action, int seconds) {
		long time = TimeUnit.SECONDS.toMillis(seconds) + System.currentTimeMillis();
		actions.put(action, time);
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