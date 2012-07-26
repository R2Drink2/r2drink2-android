package com.vimal.r2drink2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Logger {
	public static void appendLog(String text) {
		File logFile = new File("/mnt/extSdCard/r2drink2-log.txt");
		try {
			if (!logFile.exists()) {
				logFile.createNewFile();
			}
			BufferedWriter buf = new BufferedWriter(new FileWriter(logFile,true));
			buf.append(text);
			buf.newLine();
			buf.flush();
			buf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getTimestamp()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ", Locale.US);
		return sdf.format(new Date());
	}
}
