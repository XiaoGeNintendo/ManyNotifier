package com.hhs.notifiers.hrbust;

import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.hhs.notifiers.common.DialogQueue;
import com.hhs.notifiers.common.Starter;
import com.hhs.notifiers.common.WebPageSource;

/**
 * Hrbust Online Judge Submission Notifier main thread.<br>
 * Grab status information from Hrbust and create {@link HRBUSTWindow}.
 * 
 * @author XGN,Zzzyt
 *
 */
public class HRBUSTNotifier extends Thread {
	String reg;

	/**
	 * Constructor.
	 * 
	 * @param regex
	 *            Regex
	 */
	public HRBUSTNotifier(String regex) {
		reg = regex;
		System.out.println("[HRBUST]start with regex=" + reg);
	}

	Map<Integer, HRBUSTSubmission> mp = new HashMap<Integer, HRBUSTSubmission>();
	Set<Integer> load = new HashSet<Integer>();

	/**
	 * Run the thread.
	 */
	public void run() {
		while (true) {
			try {
				Thread.sleep(Starter.interval);

				load.clear();

				System.out.println("[HRBUST]Checking HRBUST Status Page");

				// Grab web source
				String s = WebPageSource.get("http://acm.hrbust.edu.cn/index.php?m=Status&a=showStatus");
				int index = s.indexOf("<table class=\"ojlist\" width=\"95%\">");
				int end = s.indexOf("</table>", index);
				if (index == -1 || end == -1) {
					throw new Exception("[HRBUST]Cannot find status table beginning");
				}

				String tableContext = s.substring(index, end + "</table>".length());

				if (tableContext.equals("")) {
					throw new Exception("[HRBUST]Cannot find status table content");
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
					HRBUSTSubmission ps = new HRBUSTSubmission(tr);

					if (ps.User.matches(reg)) {
						if (!ps.equals(mp.get(ps.RunID))) {
							System.out.println("[HRBUST]Changed:" + ps.RunID);
							mp.put(ps.RunID, ps);
							DialogQueue.add(new HRBUSTWindow(ps.RunID, this));

						}
						load.add(ps.RunID);
					}
				}

				// Clear unused status result
				for (Integer t : mp.keySet()) {
					if (!load.contains(t)) {
						System.out.println("[HRBUST]Cleared:" + t);
						mp.put(t, null);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
