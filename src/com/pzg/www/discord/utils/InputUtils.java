package com.pzg.www.discord.utils;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.function.Consumer;

public interface InputUtils {
	ScheduledExecutorService EXECUTOR = new ScheduledThreadPoolExecutor(1);
	
	static Closeable scanLines(InputStream stream, Consumer<String> lines) {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		final ScheduledFuture<?> task = EXECUTOR.scheduleAtFixedRate(() -> {
			try {
				lines.accept(reader.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}, 0, 1, MILLISECONDS);
		
		return () -> {
			task.cancel(true);
			reader.close();
		};
	}
}