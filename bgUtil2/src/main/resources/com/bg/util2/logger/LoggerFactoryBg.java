package com.bg.util2.logger;

import java.io.File;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Logger;

public class LoggerFactoryBg {
	
	public static File dirLog = new File("logs");
	static {
		try {
			dirLog.mkdirs();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static HashMap<Logger, String> hLoggers = new HashMap<Logger, String>();

	public static Logger getLogger(String name) {
		System.out.println("------------------getLogger  name:" + name);

		Logger logger = Logger.getLogger(name);
		if (hLoggers.get(logger) == null) {
			try {
				System.out.println("------------------getLogger  name:" + name);
				logger.setUseParentHandlers(false);
				FileHandler fileHandler = new FileHandler(dirLog.getName() + File.separatorChar + name + "%g.log", 20000, 3);
				Formatter newFormatter = new FormatterBg();
				fileHandler.setFormatter(newFormatter);
				logger.addHandler(fileHandler);
				hLoggers.put(logger, name);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return logger;
	}

	public static File getFile(String name) {
		return getFile(name, 0);
	}

	public static File getFile(String name, int i) {
		File f = new File(dirLog, name + i + ".log");
		return f;
	}

	public static void removAllFiles() {
		System.out.println("LoggerFactoryBg removeAllFile");
		removeFile(dirLog);
		dirLog.mkdirs();
	}

	private static void removeFile(File dir) {
		if (dir.isDirectory()) {
			File[] f = dir.listFiles();
			for (int i = 0; i < f.length; i++) {
				File ff = f[i];
				removeFile(ff);
			}
		} else if (dir.exists()) {
			dir.delete();
		}
	}

	public static HashMap<Logger, String> getHLoggers() {
		return hLoggers;
	}

}
