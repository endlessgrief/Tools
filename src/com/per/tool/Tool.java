package com.per.tool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

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
	
	
	private static void sendGet(String url,String param)
	{
		String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
	}
	
	
	private static void downloadFile(String remoteFilePath, String localFilePath)
    {
        URL urlfile = null;
        HttpURLConnection httpUrl = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        File f = new File(localFilePath);
        try
        {
            urlfile = new URL(remoteFilePath);
            httpUrl = (HttpURLConnection)urlfile.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());
            bos = new BufferedOutputStream(new FileOutputStream(f));
            int len = 2048;
            byte[] b = new byte[len];
            while ((len = bis.read(b)) != -1)
            {
                bos.write(b, 0, len);
            }
            bos.flush();
            bis.close();
            httpUrl.disconnect();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                bis.close();
                bos.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
