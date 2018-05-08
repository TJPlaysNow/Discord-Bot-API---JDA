package com.tjplaysnow.discord.object;

import java.util.List;

public abstract class ProgramConsoleCommandManager implements Runnable {
	
	private Thread thread;
	private boolean running;
	
	protected ProgramConsoleCommandManager(boolean thread) {
		if (thread) {
			running = true;
			this.thread = new Thread(this, "ProgramThread");
			this.thread.start();
		}
	}
	
	/**
	 * Add a command to the manager.
	 * @param command A new command.
	 */
	public abstract void addCommand(ProgramCommandConsole command);
	
	public abstract List<ProgramCommandConsole> getCommands();
	
	/**
	 * Stops the ProgramBot Thread thread.
	 */
	public abstract void stop();
	
	/**
	 * The run loop.
	 */
	@Override
	public abstract void run();
	
	/**
	 * Stops the Thread.
	 */
	protected void stopThread() {
		thread.interrupt();
		running = false;
	}
	
	/**
	 * Get whether the thread is still running.
	 * @return
	 */
	protected boolean isRunning() {
		return running;
	}
}