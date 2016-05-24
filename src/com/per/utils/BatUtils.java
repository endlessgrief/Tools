package com.per.utils;

import java.io.IOException;

public class BatUtils {
	
	public static void runBat(String batPath)
	{
		 String cmd = "cmd /c start ";
		 cmd = cmd + batPath;
		 try {
			Process ps = Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
