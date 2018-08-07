package com.hhs.xgn.notifiers.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import com.hhs.xgn.notifiers.codeforces.CodeforcesNotifier;
import com.hhs.xgn.notifiers.fzu.FZUNotifier;
import com.hhs.xgn.notifiers.hrbust.HRBUSTNotifier;
import com.hhs.xgn.notifiers.luogu.LuoguNotifier;
import com.hhs.xgn.notifiers.mysbz.MYSBZNotifier;
import com.hhs.xgn.notifiers.atcoder.AtcoderNotifier;
import com.hhs.xgn.notifiers.poj.POJNotifier;
import com.hhs.xgn.notifiers.ural.URALNotifier;
import com.hhs.xgn.notifiers.zoj.ZOJNotifier;

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

	public static Map<String,String> config=new HashMap<String,String>();
	public static int interval=1000;
	
	/**
	 * Load a single config file.<br>
	 * 
	 * @param path Path of the file
	 */
	private static void loadConfigFile(){
		try{
			File f=new File("user.txt");
			
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			String s;
			
			String fg=f.getName();
			System.out.println("Read config file:"+fg);
			
			for(int i=0;(s=br.readLine())!=null;i++){
				String[] s2=s.split("=");
				if(s2.length==2) {
					config.put(s2[0], s2[1]);
				}
				else {
					//Wrong config format
					System.out.println("[STARTER][WARNING]Wrong config format at line "+i);
					System.out.println("[STARTER]Ignored this line of config");
				}
			}
			
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Entrance function of the whole program.<br>
	 * Starts all the notifier threads.<br>
	 * 
	 * @param args System parameter
	 */
	public static void main(String[] args) {
		System.out.println("[STARTER]Good luck in the life of OI! =) ---from Zzzyt");
		System.out.println("===============Program Started===============");
		
		System.out.println("[STARTER]Reading config files");
		loadConfigFile();
		
		if(config.containsKey("Interval")) {
			interval=Integer.parseInt(config.get("Interval"));
			if(interval<0) {
				System.out.println("[STARTER][WARNING]Wrong interval "+interval+":too small");
				System.out.println("[STARTER]Interval is set to default(1000)");
				interval=1000;
			}
		}
		
		if(config.containsKey("Codeforces")){
			Thread t=new CodeforcesNotifier(config.get("Codeforces"));
			t.start();
		}
		/*
		if(config.containsKey("POJ")){
			Thread t=new POJNotifier(config.get("POJ"));
			t.start();
		}
		if(config.containsKey("Atcoder") && config.containsKey("AtcoderContest")){
			Thread t=new AtcoderNotifier(config.get("Atcoder"),config.get("AtcoderContest"));
			t.start();
		}
		if(config.containsKey("FZU")){
			Thread t=new FZUNotifier(config.get("FZU"));
			t.start();
		}
		if(config.containsKey("MYSBZ")){
			Thread t=new MYSBZNotifier(config.get("MYSBZ"));
			t.start();
		}
		if(config.containsKey("HRBUST")){
			Thread t=new HRBUSTNotifier(config.get("HRBUST"));
			t.start();
		}
		if(config.containsKey("ZOJ")){
			Thread t=new ZOJNotifier(config.get("ZOJ"));
			t.start();
		}
		if(config.containsKey("URAL")){
			Thread t=new URALNotifier(config.get("URAL"));
			t.start();
		}
		if(config.containsKey("LUOGU")){
			JOptionPane.showMessageDialog(null, "Luogu Supporting hasn't finished!");
		}
		*/
		
		DialogQueue.run();
	}

}