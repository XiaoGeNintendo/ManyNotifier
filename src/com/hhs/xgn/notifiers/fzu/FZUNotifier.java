package com.hhs.xgn.notifiers.fzu;

import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.hhs.xgn.notifiers.common.DialogQueue;
import com.hhs.xgn.notifiers.common.WebPageSource;
import com.hhs.xgn.notifiers.poj.POJWindow;

/**
 * Fuzhou University Online Judge Submission Notifier Main 
 * @author XGN
 *
 */
public class FZUNotifier extends Thread {
	String regex;
	public FZUNotifier(String string) {
		regex=string;
		System.out.println("[FZU]start with regex="+string);
	}
	
	Map<Integer,FZUSubmission> mp=new HashMap<Integer,FZUSubmission>();
	Set<Integer> load=new HashSet<Integer>();
	
	public void run(){
		while(true){
			try{
				Thread.sleep(1000);
				
				load.clear();
				
				System.out.println("[FZU]Checking FZU Status Page");
				
				String s=WebPageSource.get("http://acm.fzu.edu.cn/log.php");
				int index=s.indexOf("<table>");
				int end=s.indexOf("</table>",index);
				if(index==-1 || end==-1){
					throw new Exception("Cannot find status table beginning");
				}
				
				String tableContext=s.substring(index,end+"</table>".length());
				
				if(tableContext.equals("")){
					throw new Exception("Cannot find status table content");
				}
				
				
				Document d=Jsoup.parseBodyFragment(tableContext);
				Element tbody=d.body().getElementsByTag("table").get(0).getElementsByTag("tbody").get(0);
				boolean first=true;
				for(Element tr:tbody.children()){
					if(first){
						first=false;
						continue;
					}
					FZUSubmission ps=new FZUSubmission(tr);
					//System.out.println(ps);
					

					if(ps.User.matches(regex)){
						if(!ps.equals(mp.get(ps.RunID))){
							System.out.println("Change "+ps.RunID);
							mp.put(ps.RunID,ps);
							DialogQueue.add(new FZUWindow(ps.RunID,this));
							
						}
						load.add(ps.RunID);
					}
				}
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
