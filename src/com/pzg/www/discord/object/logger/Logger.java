package com.pzg.www.discord.object.logger;

/**
 * <b>The easy logger lib</b>
 * <p>Used to log messages to the console.</p>
 * 
 * @author TJPlaysNow
 *
 */
public class Logger {
	
	private LogLevel printLevel;
	
	/**
	 * <b>Create a new Logger</b>
	 * <p>Create a new logger with
	 * a log level.</p>
	 * 
	 * @param printLevel The <b>LogLevel</b> it will print at.
	 */
	public Logger(LogLevel printLevel) {
		this.printLevel = printLevel;
	}
	
	/**
	 * <b>Log something to the conosle.</b>
	 * <p>Used to log a message to the console.</p>
	 * 
	 * @param level The level you are logging at.
	 * @param message The message you are logging.
	 */
	public void log(LogLevel level, String message) {
		if (printLevel.getLevel() >= level.getLevel()) {
			if (printLevel.getLevel() <= 0) if (level == LogLevel.SPAM)  System.out.println(Colors.WHITE +  "[Bot] [SPAM]: "    + Colors.RESET + message);
			if (printLevel.getLevel() <= 1) if (level == LogLevel.INFO)  System.out.println(Colors.WHITE +  "[Bot] [INFO]: "    + Colors.RESET + message);
			if (printLevel.getLevel() <= 2) if (level == LogLevel.WARN)  System.out.println(Colors.YELLOW + "[Bot] [WARNING]: " + Colors.RESET + message);
			if (printLevel.getLevel() <= 3) if (level == LogLevel.ERROR) System.out.println(Colors.RED +    "[Bot] [ERROR]: "   + Colors.RESET + message);
		}
	}
	
	/**
	 * <b>Spam messages</b>
	 * <p>Used when you are printing
	 * unneeded data to the console</p>
	 * 
	 * @param message The spam message.
	 */
	public void spam(String message) {
		if (printLevel.getLevel() <= 0) {
			System.out.println(Colors.WHITE + "[Bot] [SPAM]: " + Colors.RESET + message);
		}
	}

	/**
	 * <b>Info messages</b>
	 * <p>Used when you are printing
	 * informational data to the console</p>
	 * 
	 * @param message The info message.
	 */
	public void info(String message) {
		if (printLevel.getLevel() <= 1) {
			System.out.println(Colors.WHITE + "[Bot] [INFO]: " + Colors.RESET + message);
		}
	}

	/**
	 * <b>Warning messages</b>
	 * <p>Used when you are printing
	 * important data to the console</p>
	 * 
	 * @param message The warn message.
	 */
	public void warn(String message) {
		if (printLevel.getLevel() <= 2) {
			System.out.println(Colors.YELLOW + "[Bot] [WARNING]: " + Colors.RESET + message);
		}
	}

	/**
	 * <b>Error messages</b>
	 * <p>Used when you are printing
	 * extreme data to the console</p>
	 * 
	 * @param message The error message.
	 */
	public void error(String message) {
		if (printLevel.getLevel() <= 3) {
			System.out.println(Colors.RED + "[Bot] [ERROR]: " + Colors.RESET + message);
		}
	}
}