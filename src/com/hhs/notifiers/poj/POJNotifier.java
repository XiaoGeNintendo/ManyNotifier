package com.hhs.notifiers.poj;

import java.util.*;

import com.hhs.notifiers.common.DialogQueue;
import com.hhs.notifiers.common.Starter;
import com.hhs.notifiers.common.WebPageSource;

/**
 * Peking University Online Judge notifier main thread.<br>
 * Grab status information from POJ and create {@link POJWindow}.
 * 
 * @author XGN,Zzzyt
 *
 */
public class POJNotifier extends Thread {
	String reg;

	/**
	 * Constructor.
	 * 
	 * @param regex
	 *            Regex
	 */
	public POJNotifier(String regex) {
		reg = regex;
		System.out.println("[POJ]start with regex=" + regex);
	}

	Map<Integer, POJSubmission> mp = new HashMap<Integer, POJSubmission>();
	Set<Integer> load = new HashSet<Integer>();

	/**
	 * Run the thread.
	 */
	public void run() {
		while (true) {
			try {
				Thread.sleep(Starter.interval);

				load.clear();

				System.out.println("[POJ]Checking POJ Status Page");

				// Grab web source
				String s = WebPageSource.get("http://poj.org/status");
				int index = s
						.indexOf("<TABLE cellSpacing=0 cellPadding=0 width=100% border=1 class=a bordercolor=#FFFFFF>");
				if (index == -1) {
					throw new Exception("[POJ]Cannot find status table beginning");
				}

				String tableContext = "";
				for (int i = index; i < s.length(); i++) {
					if (s.substring(i, i + "</table>".length()).equals("</table>")) {
						tableContext = s.substring(index, i + "</table>".length());
						break;
					}
				}

				if (tableContext.equals("")) {
					throw new Exception("[POJ]Cannot find status table content");
				}

				// Add
				String[] trs = tableContext.split("\n");
				for (int i = 1; i < trs.length - 1; i++) {
					POJSubmission ps = new POJSubmission(trs[i]);

					if (ps.User.matches(reg)) {
						if (!ps.equals(mp.get(ps.RunID))) {
							System.out.println("[POJ]Changed:" + ps.RunID);
							mp.put(ps.RunID, ps);
							DialogQueue.add(new POJWindow(ps.RunID, this));

						}
						load.add(ps.RunID);
					}

				}

				// Clear unused status result
				for (Integer t : mp.keySet()) {
					if (!load.contains(t)) {
						System.out.println("[POJ]Cleared:" + t);
						mp.put(t, null);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
