package com.tjplaysnow.discord.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ThreadHandle extends ProgramThread {
	
	private HashMap<Runnable, Long> actions;
	
	public ThreadHandle() {
		super(true);
		actions = new HashMap<>();
	}
	
	@Override
	public void addAction(Runnable action, int seconds) {
		long time = TimeUnit.SECONDS.toMillis(seconds) + System.currentTimeMillis();
		actions.put(action, time);
	}
	
	@Override
	public void stop() {
		stopThread();
	}
	
	@Override
	public void run() {
		while (isRunning()) {
//			Check to see if there is an action needed to run.
			List<Runnable> delete = new ArrayList<>();
			for (Runnable action : actions.keySet()) {
				if (System.currentTimeMillis() >= actions.get(action)) {
					action.run();
					delete.add(action);
				}
			}
			for (Runnable action : delete) {
				actions.remove(action);
			}
		}
	}
}