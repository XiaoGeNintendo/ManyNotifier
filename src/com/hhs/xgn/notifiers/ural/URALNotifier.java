package com.hhs.xgn.notifiers.ural;

import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.hhs.xgn.notifiers.common.DialogQueue;
import com.hhs.xgn.notifiers.common.WebPageSource;
import com.hhs.xgn.notifiers.poj.POJWindow;

/**
 * Timus Online Judge Submission Notifier Main 
 * @author XGN
 *
 */
public class URALNotifier extends Thread {
	String regex;
	public URALNotifier(String string) {
		regex=string;
		System.out.println("[URAL]start with regex="+string);
	}
	
	Map<Integer,URALSubmission> mp=new HashMap<Integer,URALSubmission>();
	Set<Integer> load=new HashSet<Integer>();
	
	public void run(){
		while(true){
			try{
				Thread.sleep(1000);
				
				load.clear();
				
				System.out.println("[URAL]Checking Timus Status Page");
				
				String s=WebPageSource.get("http://acm.timus.ru/status.aspx");
				int index=s.indexOf("<TABLE cellspacing=\"0\" cellpadding=\"0\" ALIGN=\"center\" class=\"status status_nofilter\">");
				int end=s.indexOf("<TABLE class=\"status_footer\" cellspacing=\"0\" cellpadding=\"0\">",index);
				if(index==-1 || end==-1){
					throw new Exception("Cannot find status table beginning");
				}
				
				String tableContext=s.substring(index,end)+"</tr></table>";
				
				if(tableContext.equals("")){
					throw new Exception("Cannot find status table content");
				}
				
				//System.out.println(tableContext);
				
				Document d=Jsoup.parseBodyFragment(tableContext);
				Element tbody=d.body().getElementsByTag("table").get(0).getElementsByTag("tbody").get(0);
				//System.out.println(tbody);
				
				boolean first=true;
				for(Element tr:tbody.children()){
					if(first){
						first=false;
						continue;
					}
					URALSubmission ps=new URALSubmission(tr);
					//System.out.println(ps);
					
					if(ps.good==false){
						continue;
					}
					
					if(ps.User.matches(regex)){
						if(!ps.equals(mp.get(ps.RunID))){
							System.out.println("Change "+ps.RunID);
							mp.put(ps.RunID,ps);
							DialogQueue.add(new URALWindow(ps.RunID,this));
							
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
