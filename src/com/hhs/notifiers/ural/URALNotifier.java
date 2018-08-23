package com.hhs.notifiers.ural;

import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.hhs.notifiers.common.DialogQueue;
import com.hhs.notifiers.common.Starter;
import com.hhs.notifiers.common.WebPageSource;

/**
 * Timus Online Judge (URAL) Submission Notifier mian thread.<br>
 * Grab status information from URAL and create {@link URALWindow}.
 * 
 * @author XGN,Zzzyt
 *
 */
public class URALNotifier extends Thread {

	String reg;

	/**
	 * Constructor.
	 * 
	 * @param regex
	 *            Regex
	 */
	public URALNotifier(String regex) {
		reg = regex;
		System.out.println("[URAL]start with regex=" + reg);
	}

	Map<Integer, URALSubmission> mp = new HashMap<Integer, URALSubmission>();
	Set<Integer> load = new HashSet<Integer>();

	/**
	 * Run the thread.
	 */
	public void run() {
		while (true) {
			try {
				Thread.sleep(Starter.interval);

				load.clear();

				System.out.println("[URAL]Checking Timus Status Page");

				// Grab web source
				String s = WebPageSource.get("http://acm.timus.ru/status.aspx");
				int index = s.indexOf(
						"<TABLE cellspacing=\"0\" cellpadding=\"0\" ALIGN=\"center\" class=\"status status_nofilter\">");
				int end = s.indexOf("<TABLE class=\"status_footer\" cellspacing=\"0\" cellpadding=\"0\">", index);
				if (index == -1 || end == -1) {
					throw new Exception("[URAL]Cannot find status table beginning");
				}

				String tableContext = s.substring(index, end) + "</tr></table>";

				if (tableContext.equals("")) {
					throw new Exception("[URAL]Cannot find status table content");
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
					URALSubmission ps = new URALSubmission(tr);

					if (ps.good == false) {
						continue;
					}

					if (ps.User.matches(reg)) {
						if (!ps.equals(mp.get(ps.RunID))) {
							System.out.println("[URAL]Changed:" + ps.RunID);
							mp.put(ps.RunID, ps);
							DialogQueue.add(new URALWindow(ps.RunID, this));

						}
						load.add(ps.RunID);
					}
				}

				// Clear unused status result
				for (Integer t : mp.keySet()) {
					if (!load.contains(t)) {
						System.out.println("[URAL]Cleared:" + t);
						mp.put(t, null);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
