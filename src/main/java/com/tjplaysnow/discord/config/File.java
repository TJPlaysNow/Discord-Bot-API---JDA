package com.tjplaysnow.discord.config;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created for use with the Config not from Spigot.
 * @version 1.0
 * @author TJPlaysNow
 */
public class File {
	
	private Path file;
	
	/**
	 * Create / load a new File for a Config to read.
	 * @param path Path to file
	 */
	public File(String path) {
		String loc;
		try {
			String[] p = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath().split("/");
			loc = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + path;
			loc = loc.replace(p[p.length - 1], "").replace("\\", "/").replace("file:", "");
			loc = loc.replace("/C:", "C:");
			loc = loc.replace("/D:", "D:");
			loc = loc.replace("/G:", "G:");
			loc = loc.replace("/F:", "F:");
			loc = loc.replace("/E:", "E:");
			loc = loc.replace("/./", "/");
			System.out.println(loc);
			file = Paths.get(loc);
			Files.createFile(file);
		} catch (FileAlreadyExistsException ignored) {
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	private final static Charset ENCODING = StandardCharsets.UTF_8;
	
	/**
	 * Reads the file.
	 * @return File in lines
	 */
	public List<String> readSmallTextFile() {
		try {
			return Files.readAllLines(file, ENCODING);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Writes to the file.
	 * @param lines to write to file.
	 */
	public void writeSmallTextFile(List<String> lines) {
		try {
			Files.write(file, lines, ENCODING);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public java.io.File getFile() {
		return file.toFile();
	}
}