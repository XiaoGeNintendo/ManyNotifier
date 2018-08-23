package com.hhs.notifiers.hysbz;

import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.hhs.notifiers.common.DialogQueue;
import com.hhs.notifiers.common.Starter;
import com.hhs.notifiers.common.WebPageSource;

/**
 * Dashiye Online Judge(HYSBZ) Submission Notifier main thread.<br>
 * Grab status information from POJ and create {@link HYSBZWindow}.
 * 
 * @author XGN,Zzzyt
 *
 */
public class HYSBZNotifier extends Thread {
	String reg;

	/**
	 * Constructor.
	 * 
	 * @param regex
	 *            Regex
	 */
	public HYSBZNotifier(String regex) {
		reg = regex;
		System.out.println("[HYSBZ]start with regex=" + regex);
	}

	Map<Integer, HYSBZSubmission> mp = new HashMap<Integer, HYSBZSubmission>();
	Set<Integer> load = new HashSet<Integer>();

	/**
	 * Run the thread.
	 */
	public void run() {
		while (true) {
			try {
				Thread.sleep(Starter.interval);

				load.clear();

				System.out.println("[HYSBZ]Checking MYSBZ Status Page");

				// Grab web source
				String s = WebPageSource.get("https://www.lydsy.com/JudgeOnline/status.php");
				int index = s.indexOf("<table align=center>");
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
					HYSBZSubmission ps = new HYSBZSubmission(tr);

					if (ps.User.matches(reg)) {
						if (!ps.equals(mp.get(ps.RunID))) {
							System.out.println("Change " + ps.RunID);
							mp.put(ps.RunID, ps);
							DialogQueue.add(new HYSBZWindow(ps.RunID, this));

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
