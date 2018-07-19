package com.hhs.xgn.notifiers.poj;

import java.util.*;

import com.hhs.xgn.notifiers.common.DialogQueue;
import com.hhs.xgn.notifiers.common.WebPageSource;

/**
 * Peking University Online Judge Submission Notifier Main 
 * @author XGN
 *
 */
public class POJNotifier extends Thread {
	String regex;
	public POJNotifier(String string) {
		regex=string;
		System.out.println("[POJ]start with regex="+string);
	}
	
	Map<Integer,POJSubmission> mp=new HashMap<Integer,POJSubmission>();
	Set<Integer> load=new HashSet<Integer>();
	
	public void run(){
		while(true){
			try{
				Thread.sleep(1000);
				
				load.clear();
				
				System.out.println("[POJ]Checking POJ Status Page");
				
				String s=WebPageSource.get("http://poj.org/status");
				int index=s.indexOf("<TABLE cellSpacing=0 cellPadding=0 width=100% border=1 class=a bordercolor=#FFFFFF>");
				if(index==-1){
					throw new Exception("Cannot find status table beginning");
				}
				
				String tableContext="";
				for(int i=index;i<s.length();i++){
					if(s.substring(i,i+"</table>".length()).equals("</table>")){
						tableContext=s.substring(index,i+"</table>".length());
						break;
					}
				}
				
				if(tableContext.equals("")){
					throw new Exception("Cannot find status table content");
				}
				
				//Add 
				String[] trs=tableContext.split("\n");
				for(int i=1;i<trs.length-1;i++){
					POJSubmission ps=new POJSubmission(trs[i]);
					//System.out.println(ps);
					
					if(ps.User.matches(regex)){
						if(!ps.equals(mp.get(ps.RunID))){
							System.out.println("Change "+ps.RunID);
							mp.put(ps.RunID,ps);
							DialogQueue.add(new POJWindow(ps.RunID,this));
							load.add(ps.RunID);
						}
					}
					
				}
				
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
