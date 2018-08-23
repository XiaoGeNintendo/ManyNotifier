package com.hhs.notifiers.zoj;

import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.hhs.notifiers.common.DialogQueue;
import com.hhs.notifiers.common.Starter;
import com.hhs.notifiers.common.WebPageSource;
import com.hhs.notifiers.zoj.ZOJWindow;

/**
 * Zhejiang University Online Judge Submission Notifier main thread Grab status
 * information from ZOJ and create {@link ZOJWindow}.
 * 
 * @author XGN,Zzzyt
 *
 */
public class ZOJNotifier extends Thread {
	String reg;

	/**
	 * Constructor.
	 * 
	 * @param regex
	 *            Regex
	 */
	public ZOJNotifier(String regex) {
		reg = regex;
		System.out.println("[ZOJ]start with regex=" + regex);
	}

	Map<Integer, ZOJSubmission> mp = new HashMap<Integer, ZOJSubmission>();
	Set<Integer> load = new HashSet<Integer>();

	/**
	 * Run the thread.
	 */
	public void run() {
		while (true) {
			try {
				Thread.sleep(Starter.interval);

				load.clear();

				System.out.println("[ZOJ]Checking ZOJ Status Page");

				// Grab web source
				String s = WebPageSource.get("http://acm.zju.edu.cn/onlinejudge/showRuns.do?contestId=1");
				int index = s.indexOf("<table class=\"list\">");
				int end = s.indexOf("</table>", index);
				if (index == -1 || end == -1) {
					throw new Exception("Cannot find status table beginning");
				}

				String tableContext = s.substring(index, end + "</table>".length());

				if (tableContext.equals("")) {
					throw new Exception("Cannot find status table content");
				}

				Document d = Jsoup.parseBodyFragment(tableContext);
				Element tbody = d.body().getElementsByTag("table").get(0).getElementsByTag("tbody").get(0);

				// Add
				boolean first = true;
				for (Element tr : tbody.children()) {
					if (first) {
						first = false;
						continue;
					}
					ZOJSubmission ps = new ZOJSubmission(tr);

					if (ps.User.matches(reg)) {
						if (!ps.equals(mp.get(ps.RunID))) {
							System.out.println("Change " + ps.RunID);
							mp.put(ps.RunID, ps);
							DialogQueue.add(new ZOJWindow(ps.RunID, this));

						}
						load.add(ps.RunID);
					}
				}

				// Clear unused status result
				for (Integer t : mp.keySet()) {
					if (!load.contains(t)) {
						System.out.println("Clears:" + t);
						mp.put(t, null);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
