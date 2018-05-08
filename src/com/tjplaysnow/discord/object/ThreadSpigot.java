package com.tjplaysnow.discord.object;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class ThreadSpigot extends ProgramThread {
	
	private Plugin plugin;
	
	public ThreadSpigot(Plugin plugin) {
		super(false);
		this.plugin = plugin;
	}
	
	@Override
	public void addAction(Runnable action, int seconds) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, action, (seconds * 20));
	}
	
	@Override
	public void stop() {}
	
	@Override
	public void run() {}
}