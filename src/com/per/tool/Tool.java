package com.per.tool;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.per.utils.BatUtils;
import com.per.utils.CommomUtils;
import com.per.utils.FileUtils;
import com.per.utils.PathUtils;

public class Tool {

	public static void main(String[] args) throws JDOMException, IOException{
		String path = PathUtils.initPath();
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(new File(path));
		Element conf = doc.getRootElement();
		List<Element> source= conf.getChildren("source");
		List<Element> target= conf.getChildren("target");
		String sourceFile= null;
		String sourcePath= null;
		String targetFile= null;
		String targetPath= null;
		for(Element e:source)
		{
			sourceFile = e.getChildTextTrim("file");
			sourcePath = e.getChildTextTrim("path");
		}
		for(Element e:target)
		{
			targetFile = e.getChildTextTrim("file");
			targetPath = e.getChildTextTrim("path");
		}
		//删除target文件
		if(targetFile.contains(","))
		{
			String [] strs = targetFile.split(",");
			for(String s :strs)
			{
				File tfile = new File(targetPath+File.separator+s);
				FileUtils.deleteFile(tfile);
			}
		}
		else
		{
			File tfile = new File(targetPath+File.separator+targetFile);
			FileUtils.deleteFile(tfile);
		}
		//移动source文件到target
		FileUtils.moveFile(sourcePath+File.separator+sourceFile, targetPath);
		//CATALINA_HOME必须配置
		String runBat = conf.getChildTextTrim("run");
		BatUtils.runBat(runBat);
	}
	
}
