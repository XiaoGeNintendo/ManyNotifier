package com.hhs.xgn.notifiers.atcoder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.hhs.xgn.notifiers.common.DialogQueue;
import com.hhs.xgn.notifiers.common.WebPageSource;


public class AtcoderNotifier extends Thread {
	String regex,contest;
	public AtcoderNotifier(String user,String con){
		regex=user;
		contest=con;
		System.out.println("[ATCODER]Atcoder Running with userReg="+user+" contestId="+con);
	}
	
	Map<String,AtcoderSubmission> mp=new HashMap<String,AtcoderSubmission>();
	Set<String> load=new HashSet<String>();
	
	public void run(){
		while(true){
			try{
				Thread.sleep(1000);
				
				String s=WebPageSource.get("https://beta.atcoder.jp/contests/"+contest+"/submissions");
				
				int index=s.indexOf("<table class=\"table table-bordered table-striped small th-center\">");
				
				int end=s.indexOf("</table>", index);
				
				if(index==-1 || end==-1){
					throw new Exception("Grab error:table not found");
				}
				
				String tableContent=s.substring(index,end+"</table>".length());
				//System.out.println(tableContent);
				
				Document d=Jsoup.parseBodyFragment(tableContent);
				Element tbody=d.body().getElementsByTag("table").get(0).getElementsByTag("tbody").get(0);
				for(Element tr:tbody.children()){
					AtcoderSubmission ps=new AtcoderSubmission(tr);
					//System.out.println(ps);
					
					if(ps.User.matches(regex)){
						if(!ps.equals(mp.get(ps.RunID))){
							if(mp.get(ps.RunID)==null){
								mp.put(ps.RunID,ps);
								DialogQueue.add(new AtcoderWindow(ps.RunID,contest,this));
							}
							System.out.println("Change "+ps.RunID);
							mp.put(ps.RunID,ps);
						}
						load.add(ps.RunID);
					}
				}
				
				//Clear mp
				for(String t:mp.keySet()){
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
