package com.tjplaysnow.discord.object;

public abstract class ProgramThread implements Runnable {
	
	private Thread thread;
	private boolean running;
	
	protected ProgramThread(boolean thread) {
		if (thread) {
			running = true;
			this.thread = new Thread(this, "ProgramThread");
			this.thread.start();
		}
	}
	
	/**
	 * A new action to run later.
	 * @param action The action to be run.
	 * @param seconds The time in seconds to wait before running the action.
	 */
	public abstract void addAction(Runnable action, int seconds);
	
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
	public boolean isRunning() {
		return running;
	}
}