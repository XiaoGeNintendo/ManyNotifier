package com.hhs.notifiers.luogu;

import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hhs.notifiers.common.DialogQueue;
import com.hhs.notifiers.common.WebPageSource;
import com.hhs.notifiers.poj.POJWindow;

/**
 * Luogu Submission Notifier Main Hasn't finished!
 * @author XGN
 *
 */
public class LuoguNotifier extends Thread {
	String regex;
	public LuoguNotifier(String string) {
		regex=string;
		System.out.println("[LUOGU]start with regex="+string);
	}
	
	Map<Integer,LuoguSubmission> mp=new HashMap<Integer,LuoguSubmission>();
	Set<Integer> load=new HashSet<Integer>();
	
	public void run(){
		while(true){
			try{
				Thread.sleep(1000);
				
				load.clear();
				
				System.out.println("[LUOGU]Checking Luogu Status Page");
				
				String s=WebPageSource.get("https://www.luogu.org/recordnew/lists?uid=&pid=&status=&sort=undefined");
				
				
				Document d=Jsoup.parse(s);
				Elements sub=d.body().getElementsByClass("lg-content-table-left").get(0).getElementsByClass("am-g lg-table-bg0 lg-table-row");
				
				//System.out.println(tbody);
				//System.out.println(tbody);
				
				
				for(Element e:sub){
					LuoguSubmission ps=new LuoguSubmission(e);
					System.out.println(ps.verdict+" "+ps.score);
				}	
//					if(ps.User.matches(regex)){
//						if(!ps.equals(mp.get(ps.RunID))){
//							System.out.println("Change "+ps.RunID);
//							mp.put(ps.RunID,ps);
//							DialogQueue.add(new LuoguWindow(ps.RunID,this));
//							
//						}
//						load.add(ps.RunID);
//					}
//				
//				System.out.println(mp.keySet());
//				System.out.println(load);
				
				//Clear mp
				for(Integer t:mp.keySet()){
					if(!load.contains(t)){
						System.out.println("Clears:"+t);
						mp.put(t,null);	
					}
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
