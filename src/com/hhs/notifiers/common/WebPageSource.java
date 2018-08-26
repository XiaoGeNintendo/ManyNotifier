package com.hhs.notifiers.common;

import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.net.HttpURLConnection;

import java.net.URL;

/**
 * Util to get web page source.<br>
 * <br>
 * <i>Seems copied from Internet...</i>
 * 
 * @author XGN
 *
 */
public class WebPageSource {
	/**
	 * Get source of a web page.
	 * 
	 * @param addr
	 *            URL
	 * @return Web page source
	 * @throws Exception
	 *             When get bad response code
	 */
	public static String get(String addr) throws Exception {
		URL url;
		int responsecode;
		HttpURLConnection urlConnection;
		BufferedReader reader;
		String line;

		url = new URL(addr);
		urlConnection = (HttpURLConnection) url.openConnection();
		responsecode = urlConnection.getResponseCode();

		if (responsecode == 200) {
			reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
			String ss = "";
			while ((line = reader.readLine()) != null) {
				ss += line + "\n";
			}
			ss=htmlReplace(ss);
			return ss;
		} else {
			throw new Exception("Bad response Code:" + responsecode);
		}

	}

	/**
	 * Replace special expressions in HTML into normal characters.
	 * 
	 * @param str
	 *            String to replace
	 * @return Replaced string
	 */
	public static String htmlReplace(String str) {
		str = str.replace("&quot;", "\"");
		str = str.replace("&amp;", "&");
		str = str.replace("&#39;", "'");
		str = str.replace("&lt;", "<");
		str = str.replace("&gt;", ">");

		return str;
	}

}