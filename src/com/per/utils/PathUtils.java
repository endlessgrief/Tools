package com.per.utils;

import java.io.File;

import com.per.tool.Tool;

public class PathUtils {

	public static String initPath()
	{
		//file:D:/*/*/*!/*/*/*
		String localPath = PathUtils.class.getResource ("").getFile ();
		//D:/*/*/*
		localPath = localPath.substring(localPath.indexOf(":")+2, localPath.lastIndexOf("!"));
		//上一级目录
		String path = handlePath(localPath+"/../"+CommomUtils.confXML);
		
		return path;
	}
	
	public static String handlePath(String path)
	{
		if(path.contains(".."))
		{
			String [] paths = path.split("/");
			String [] result = new String[paths.length-2];
			StringBuffer sb = new StringBuffer();
			int index = 0;
			for(int i=0;i<paths.length;i++)
			{
				if("..".equals(paths[i]))
				{
					index = i;
					break;
				}
				
			}
			System.arraycopy(paths, 0, result, 0, index-1);
			System.arraycopy(paths, index+1, result, index-1, paths.length-1-index);
			for(String s : result)
			{
				sb.append(s).append("/");
			}
			return handlePath(sb.toString().substring(0, sb.toString().length()-1));
		}
		return path;
	}
	
}
