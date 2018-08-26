package com.hhs.notifiers.luogu;

import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hhs.notifiers.common.DialogQueue;
import com.hhs.notifiers.common.Starter;
import com.hhs.notifiers.common.WebPageSource;
import com.hhs.notifiers.luogu.LuoguWindow;

/**
 * Luogu Submission Notifier main thread. Grab status information from URAL and
 * create {@link LuoguWindow}.
 * 
 * @author XGN,Zzzyt
 *
 */
public class LuoguNotifier extends Thread {
	String reg;

	/**
	 * Constructor.
	 * 
	 * @param regex
	 *            Regex
	 */
	public LuoguNotifier(String regex) {
		reg = regex;
		System.out.println("[LUOGU]start with regex=" + regex);
	}

	Map<Integer, LuoguSubmission> mp = new HashMap<Integer, LuoguSubmission>();
	Set<Integer> load = new HashSet<Integer>();

	/**
	 * Run the thread.
	 */
	public void run() {
		while (true) {
			try {
				Thread.sleep(Starter.interval);

				load.clear();

				System.out.println("[LUOGU]Checking Luogu Status Page");

				// Grab web source
				String s = WebPageSource.get("https://www.luogu.org/recordnew/lists?uid=&pid=&status=&sort=undefined");

				Document d = Jsoup.parse(s);
				Elements sub = d.body().getElementsByClass("lg-content-table-left").get(0)
						.getElementsByClass("am-g lg-table-bg0 lg-table-row");

				// Add
				for (Element e : sub) {
					LuoguSubmission ps = new LuoguSubmission(e);

					if (ps.author.matches(reg)) {
						if (!ps.equals(mp.get(ps.runID))) {
							System.out.println("[LUOGU]Changed:" + ps.runID);
							mp.put(ps.runID, ps);
							DialogQueue.add(new LuoguWindow(ps.runID, this));

						}
						load.add(ps.runID);
					}
				}

				// Clear unused status result
				for (Integer t : mp.keySet()) {
					if (!load.contains(t)) {
						System.out.println("[LUOGU]Cleared:" + t);
						mp.put(t, null);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
