package com.hhs.notifiers.atcoder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.hhs.notifiers.common.DialogQueue;
import com.hhs.notifiers.common.WebPageSource;

/**
 * Main thread to grab status information from AtCoder and create
 * {@link AtcoderWindow}.
 * 
 * @author XGN,Zzzyt
 */
public class AtcoderNotifier extends Thread {
	String regex, contest;

	/**
	 * Constructor.
	 * 
	 * @param user
	 *            user handle
	 * @param con
	 *            contest number
	 */
	public AtcoderNotifier(String user, String con) {
		regex = user;
		contest = con;
		System.out.println("[ATCODER]Atcoder Running with userReg=" + user + " contestId=" + con);
	}

	Map<String, AtcoderSubmission> mp = new HashMap<String, AtcoderSubmission>();
	Set<String> load = new HashSet<String>();

	/**
	 * Run the thread.
	 */
	public void run() {
		while (true) {
			try {
				Thread.sleep(Starter.interval);

				System.out.println("[ATCODER]Atcoder Notifier grabbing");

				String s = WebPageSource.get("https://beta.atcoder.jp/contests/" + contest + "/submissions");

				// Analyze web source
				int index = s.indexOf("<table class=\"table table-bordered table-striped small th-center\">");
				int end = s.indexOf("</table>", index);

				if (index == -1 || end == -1) {
					throw new Exception("[ATCODER]Grab error:table not found");
				}

				String tableContent = s.substring(index, end + "</table>".length());

				Document d = Jsoup.parseBodyFragment(tableContent);
				Element tbody = d.body().getElementsByTag("table").get(0).getElementsByTag("tbody").get(0);
				for (Element tr : tbody.children()) {
					AtcoderSubmission ps = new AtcoderSubmission(tr);

					if (ps.User.matches(regex)) {
						if (!ps.equals(mp.get(ps.RunID))) {
							if (mp.get(ps.RunID) == null) {
								mp.put(ps.RunID, ps);
								DialogQueue.add(new AtcoderWindow(ps.RunID, contest, this));
							}
							System.out.println("[ATCODER]Changed:" + ps.RunID);
							mp.put(ps.RunID, ps);
						}
						load.add(ps.RunID);
					}
				}

				// Clear unused status result
				for (String t : mp.keySet()) {
					if (!load.contains(t)) {
						System.out.println("[ATCODER]Cleared:" + t);
						mp.put(t, null);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
