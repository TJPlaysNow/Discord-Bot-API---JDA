package com.tjplaysnow.discord.object;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadHandle extends ProgramThread {
	
	private ScheduledExecutorService scheduledExecutorService;
	
	public ThreadHandle() {
		super(false);
		scheduledExecutorService = Executors.newScheduledThreadPool(5);
	}
	
	@Override
	public void addAction(Runnable action, int seconds) {
		scheduledExecutorService.schedule(action, seconds, TimeUnit.SECONDS);
	}
	
	@Override
	public void stop() {
		scheduledExecutorService.shutdown();
	}
	
	@Override
	public void run() {}
}