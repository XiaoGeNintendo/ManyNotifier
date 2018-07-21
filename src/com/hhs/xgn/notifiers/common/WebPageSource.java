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
		
			// ����һ��URL����Ҫ��ȡԴ�������ҳ��ַΪ��http://www.sina.com.cn

			url = new URL(addr);

			// ��URL

			urlConnection = (HttpURLConnection) url.openConnection();

			// ��ȡ��������Ӧ����

			responsecode = urlConnection.getResponseCode();

			if (responsecode == 200) {

				// �õ������������������ҳ������

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