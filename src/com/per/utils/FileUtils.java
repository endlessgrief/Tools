package com.per.utils;

import java.io.File;

public class FileUtils {
	
	public static boolean deleteFile(File f)
	{
		boolean flag = false;
		if(!f.exists())
		{
			return flag;
		}
		if(!f.isDirectory())
		{
			f.delete();
		}
		else
		{
			File[] fs = f.listFiles();
			for(File file:fs)
			{
				deleteFile(file);
				file.delete();
			}
			f.delete();
		}
		flag = true;
		return flag;
	}
	
	public static boolean moveFile(String sourceFile,String targetPath)
	{
		boolean flag = false;
		File file = new File(sourceFile); 
		if(!file.exists())
		{
			return flag;
		}
		File dir = new File(targetPath); 
		flag = file.renameTo(new File(dir, file.getName())); 
		return flag;
	}
}
