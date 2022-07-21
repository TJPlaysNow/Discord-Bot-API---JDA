package com.tjplaysnow.discord.object;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.concurrent.TimeUnit;

public class ThreadBungee extends ProgramThread {
	
	private Plugin plugin;
	
	protected ThreadBungee(boolean thread) {
		super(thread);
		this.plugin = plugin;
	}
	
	@Override
	public void addAction(Runnable action, int seconds) {
		ProxyServer.getInstance().getScheduler().schedule(plugin, action, seconds, TimeUnit.SECONDS);
	}
	
	@Override
	public void stop() {
	}
	
	@Override
	public void run() {
	}
}
