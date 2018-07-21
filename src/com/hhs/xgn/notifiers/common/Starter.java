package com.hhs.xgn.notifiers.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.hhs.xgn.notifiers.codeforces.CodeforcesNotifier;
import com.hhs.xgn.notifiers.fzu.FZUNotifier;
import com.hhs.xgn.notifiers.hrbust.HRBUSTNotifier;
import com.hhs.xgn.notifiers.mysbz.MYSBZNotifier;
import com.hhs.xgn.notifiers.atcoder.AtcoderNotifier;
import com.hhs.xgn.notifiers.poj.POJNotifier;

public class Starter {

	
	public static void main(String[] args) {
		start s=new start();
		s.solve();
	}

}

class start{
	
	public Map<String,String> l=new HashMap<String,String>();
	
	/**
	 * Load a single config file
	 * @param path
	 * @throws  
	 */
	private void loadLangFile(){
		try{
			File f=new File("user.txt");
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			String s;
			while((s=br.readLine())!=null){
				String[] s2=s.split("=");
				l.put(s2[0], s2[1]);
			}
			
			String fg=f.getName();
			
			System.out.println("Read config file:"+fg);
			
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void solve(){
		
		System.out.println("Reading config files");
		loadLangFile();
		
		if(l.containsKey("Codeforces")){
			Thread t=new CodeforcesNotifier(l.get("Codeforces"));
			t.start();
		}
		if(l.containsKey("POJ")){
			Thread t=new POJNotifier(l.get("POJ"));
			t.start();
		}
		if(l.containsKey("Atcoder") && l.containsKey("AtcoderContest")){
			Thread t=new AtcoderNotifier(l.get("Atcoder"),l.get("AtcoderContest"));
			t.start();
		}
		if(l.containsKey("FZU")){
			Thread t=new FZUNotifier(l.get("FZU"));
			t.start();
		}
		if(l.containsKey("MYSBZ")){
			Thread t=new MYSBZNotifier(l.get("MYSBZ"));
			t.start();
		}
		if(l.containsKey("HRBUST")){
			Thread t=new HRBUSTNotifier(l.get("HRBUST"));
			t.start();
		}
	}
}