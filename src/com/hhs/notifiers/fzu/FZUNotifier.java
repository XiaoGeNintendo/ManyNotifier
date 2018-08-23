package com.hhs.notifiers.fzu;

import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.hhs.notifiers.common.DialogQueue;
import com.hhs.notifiers.common.Starter;
import com.hhs.notifiers.common.WebPageSource;

/**
 * Fuzhou University Online Judge Submission Notifier main thread.<br>
 * Grab status information from FZU and create {@link FZUWindow}.
 * 
 * @author XGN,Zzzyt
 *
 */
public class FZUNotifier extends Thread {

	String reg;

	/**
	 * Constructor.
	 * 
	 * @param regex
	 *            Regex
	 */
	public FZUNotifier(String regex) {
		reg = regex;
		System.out.println("[FZU]start with regex=" + regex);
	}

	Map<Integer, FZUSubmission> mp = new HashMap<Integer, FZUSubmission>();
	Set<Integer> load = new HashSet<Integer>();

	/**
	 * Run the thread.
	 */
	public void run() {
		while (true) {
			try {
				Thread.sleep(Starter.interval);

				load.clear();

				System.out.println("[FZU]Checking FZU Status Page");

				// Grab web source
				String s = WebPageSource.get("http://acm.fzu.edu.cn/log.php");
				int index = s.indexOf("<table>");
				int end = s.indexOf("</table>", index);
				if (index == -1 || end == -1) {
					throw new Exception("[FZU]Cannot find status table beginning");
				}

				String tableContext = s.substring(index, end + "</table>".length());

				if (tableContext.equals("")) {
					throw new Exception("[FZU]Cannot find status table content");
				}

				Document d = Jsoup.parseBodyFragment(tableContext);
				Element tbody = d.body().getElementsByTag("table").get(0).getElementsByTag("tbody").get(0);
				
				//Add
				boolean first = true;
				for (Element tr : tbody.children()) {
					if (first) {
						first = false;
						continue;
					}
					FZUSubmission ps = new FZUSubmission(tr);

					if (ps.User.matches(reg)) {
						if (!ps.equals(mp.get(ps.RunID))) {
							System.out.println("[FZU]Changed:" + ps.RunID);
							mp.put(ps.RunID, ps);
							DialogQueue.add(new FZUWindow(ps.RunID, this));

						}
						load.add(ps.RunID);
					}
				}

				// Clear unused status result
				for (Integer t : mp.keySet()) {
					if (!load.contains(t)) {
						System.out.println("[FZU]Cleared:" + t);
						mp.put(t, null);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
