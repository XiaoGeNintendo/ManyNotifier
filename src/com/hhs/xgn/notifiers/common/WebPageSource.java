package com.hhs.xgn.notifiers.common;

import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.net.HttpURLConnection;

import java.net.URL;

public class WebPageSource {

	public static String get(String addr) throws Exception {

		URL url;

		int responsecode;

		HttpURLConnection urlConnection;

		BufferedReader reader;

		String line;
		
			// 生成一个URL对象，要获取源代码的网页地址为：http://www.sina.com.cn

			url = new URL(addr);

			// 打开URL

			urlConnection = (HttpURLConnection) url.openConnection();

			// 获取服务器响应代码

			responsecode = urlConnection.getResponseCode();

			if (responsecode == 200) {

				// 得到输入流，即获得了网页的内容

				reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
				String ss = "";

				while ((line = reader.readLine()) != null) {
					ss += line + "\n";
				}

				return ss;
			}

			else {

				throw new Exception("Bad response Code:"+responsecode);

			}

		}

	public static String htmlReplace(String str){
        str = str.replace("&quot;", "\"");
        str = str.replace("&amp;", "&");
        str = str.replace("&#39;", "'");
        str = str.replace("&lt;", "<");
        str = str.replace("&gt;", ">");
        
        return str;
    }
	
}