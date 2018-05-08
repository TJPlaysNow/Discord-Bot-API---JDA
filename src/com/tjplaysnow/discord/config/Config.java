package com.tjplaysnow.discord.config;

import java.util.List;

/**
 * Created for use when not using Spigot's version of a config.
 * @version 1.0
 * @author TJPlaysNow
 */
public class Config {
	
	private String seperator = " =- ";
	
	private File conf;
	
	/**
	 * Create a new config.
	 * @param file to format.
	 */
	public Config(File file) {
		this.conf = file;
	}
	
	/**
	 * Set an object to a path.
	 * @param path Place to put the object.
	 * @param object Object to place.
	 */
	public void set(String path, String object) {
		List<String> a = conf.readSmallTextFile();
		int lineNum = a.size();
		boolean set = false;
		for (String b : a) {
			if (b.contains(path + seperator)) {
				set = true;
				lineNum = a.indexOf(b);
			}
		}
		if (set) {
			a.set(lineNum, path + seperator + object);
		} else {
			a.add(path + seperator + object);
		}
		conf.writeSmallTextFile(a);
	}
	
	/**
	 * Set an object to a path, if nothing else is there.
	 * @param path Place to put the object.
	 * @param def Object to place.
	 */
	public void setDefault(String path, String def) {
		List<String> a = conf.readSmallTextFile();
		String ret = "nil";
		for (String b : a) {
			if (b.contains(path + seperator)) {
				ret = b.replace(path + seperator, "");
			}
		}
		if (ret.equals("nil")) {
			set(path, def);
		}
	}
	
	/**
	 * Get an object from a path.
	 * @param path Place to get the object from.
	 * @return The object in String form.
	 */
	public String getObject(String path) {
		List<String> a = conf.readSmallTextFile();
		String ret = "";
		for (String b : a) {
			if (b.contains(path + seperator)) {
				ret = b.replace(path + seperator, "");
			}
		}
		return ret;
	}
	
	/**
	 * Get an object from a path.
	 * @param path Place to get the object from.
	 * @param def The object if it is null (Sets this in the config)
	 * @return The object in String form.
	 */
	public String getObject(String path, String def) {
		List<String> a = conf.readSmallTextFile();
		String ret = "nil";
		for (String b : a) {
			if (b.contains(path + seperator)) {
				ret = b.replace(path + seperator, "");
			}
		}
		if (ret.equals("nil")) {
			set(path, def);
			ret = def;
		}
		return ret;
	}
}