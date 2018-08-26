package com.hhs.notifiers.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.hhs.notifiers.atcoder.AtcoderNotifier;
import com.hhs.notifiers.codeforces.CodeforcesNotifier;
import com.hhs.notifiers.fzu.FZUNotifier;
import com.hhs.notifiers.hrbust.HRBUSTNotifier;
import com.hhs.notifiers.hysbz.HYSBZNotifier;
import com.hhs.notifiers.luogu.LuoguNotifier;
import com.hhs.notifiers.poj.POJNotifier;
import com.hhs.notifiers.ural.URALNotifier;
import com.hhs.notifiers.zoj.ZOJNotifier;

/**
 * 
 * Starter of the whole program.<br>
 * <br>
 * <i>Should we stop the threads more safely?</i>
 * 
 * @author XGN,Zzzyt
 *
 */
public class Starter {

	public static Map<String, String> config = new HashMap<String, String>();
	public static int interval = 1000;
	public static boolean reduceDebug = false;
	public static boolean doNotGetFocus = false;

	/**
	 * Load a single config file.<br>
	 * 
	 * @param path
	 *            Path of the file
	 */
	private static void loadConfigFile() {

		try {
			File f = new File("config.txt");

			if (!f.exists()) {
				System.out.println("[STARTER][ERROR]config file not found");
				System.exit(0);
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			String s;

			String fname = f.getName();
			System.out.println("[Starter]Read config file:" + fname);

			for (int i = 0; (s = br.readLine()) != null; i++) {
				String[] s2 = s.split("=");
				if (s2.length == 2) {
					config.put(s2[0], s2[1]);
				} else {
					// Wrong config format
					System.out.println("[STARTER][WARNING]Wrong config format at line " + i);
					System.out.println("[STARTER]Ignored this line of config");
				}
			}

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Entrance function of the whole program.<br>
	 * Starts all the notifier threads.<br>
	 * 
	 * @param args
	 *            System parameter
	 */
	public static void main(String[] args) {
		System.out.println("[STARTER]Good luck in the life of OI! =) ---from Zzzyt");
		System.out.println("===============Program Started===============");

		System.out.println("[STARTER]Reading config file");
		loadConfigFile();

		if (config.containsKey("Interval")) {
			interval = Integer.parseInt(config.get("Interval"));
			if (interval < 0) {
				System.out.println("[STARTER][WARNING]Wrong interval " + interval + ":too small");
				System.out.println("[STARTER]Interval is set to default(1000)");
				interval = 1000;
			} else {
				System.out.println("[STARTER]Interval is set to " + interval);
			}
		} else {
			System.out.println("[STARTER]Interval is set to default(1000)");
			interval = 1000;
		}

		reduceDebug = setBoolConfig("ReduceDebug", false);
		doNotGetFocus = setBoolConfig("DoNotGetFocus", false);

		if (config.containsKey("Codeforces")) {
			Thread t = new CodeforcesNotifier(config.get("Codeforces"));
			t.start();
		}
		if (config.containsKey("POJ")) {
			Thread t = new POJNotifier(config.get("POJ"));
			t.start();
		}
		if (config.containsKey("Atcoder") && config.containsKey("AtcoderContest")) {
			Thread t = new AtcoderNotifier(config.get("Atcoder"), config.get("AtcoderContest"));
			t.start();
		}
		if (config.containsKey("FZU")) {
			Thread t = new FZUNotifier(config.get("FZU"));
			t.start();
		}
		if (config.containsKey("HYSBZ")) {
			Thread t = new HYSBZNotifier(config.get("HYSBZ"));
			t.start();
		}
		if (config.containsKey("HRBUST")) {
			Thread t = new HRBUSTNotifier(config.get("HRBUST"));
			t.start();
		}
		if (config.containsKey("ZOJ")) {
			Thread t = new ZOJNotifier(config.get("ZOJ"));
			t.start();
		}
		if (config.containsKey("URAL")) {
			Thread t = new URALNotifier(config.get("URAL"));
			t.start();
		}
		 if(config.containsKey("LUOGU")){
			 Thread t = new LuoguNotifier(config.get("LUOGU"));
			 t.start();
		 }

		DialogQueue.run();
	}

	/**
	 * Little help function to deal with config reading.<br>
	 * Can set a boolean config.
	 * 
	 * @param name
	 *            name of config
	 * @param def
	 *            default value
	 * @return config value
	 */
	private static boolean setBoolConfig(String name, boolean def) {
		if (config.containsKey(name)) {
			String tmp = config.get(name);
			if (tmp.toLowerCase().equals("true")) {
				System.out.println("[STARTER]" + name + ":true");
				return true;
			} else if (tmp.toLowerCase().equals("false")) {
				System.out.println("[STARTER]" + name + ":false");
				return false;
			} else {
				System.out.println("[STARTER][WARNING]" + name + " config is wrong:" + tmp);
				System.out.println("[STARTER]" + name + " is set to dafault:" + def);
				return def;
			}
		} else {
			System.out.println("[STARTER]" + name + " is set to dafault:" + def);
			return def;
		}
	}
}