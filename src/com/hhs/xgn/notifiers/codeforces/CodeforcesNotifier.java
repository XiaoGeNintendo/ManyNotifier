package com.hhs.xgn.notifiers.codeforces;


import java.util.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hhs.xgn.notifiers.common.DialogQueue;
import com.hhs.xgn.notifiers.common.WebPageSource;

public class CodeforcesNotifier extends Thread {
	String regex;
	public CodeforcesNotifier(String reg){
		System.out.println("[CF] start with regex="+reg);
		this.regex=reg;
	}
	
	public Map<Integer,FullSubmission> mp=new HashMap<Integer,FullSubmission>();
	
	public Set<Integer> load=new HashSet<Integer>();
	
	final int td=1000;
	final int cnt=10;
	
	public void run(){
		while(true){
			try{
				Thread.sleep(td);
				
				load.clear();
				System.out.println("[CF]Codeforces API Checking");
				//Get 
				String api=WebPageSource.get("http://codeforces.com/api/problemset.recentStatus?count="+cnt);
				
				//Get json Parser
				JsonParser jp=new JsonParser();
				JsonObject jo=jp.parse(api).getAsJsonObject();
				
				//Check status
				if(jo.get("status").getAsString().equals("OK")){
					
					//Get result and permute
					JsonArray ja=jo.get("result").getAsJsonArray();
					for(int i=0;i<ja.size();i++){
						FullSubmission fs=new FullSubmission(ja.get(i).getAsJsonObject());
						load.add(fs.id);
						JsonArray mem=fs.author.get("members").getAsJsonArray();
						
						//Find if author exists
						boolean ok=false;
						String name="";
						for(int j=0;j<mem.size();j++){
							if(mem.get(j).getAsJsonObject().get("handle").getAsString().matches(regex)){
								ok=true;
								name=mem.get(j).getAsJsonObject().get("handle").getAsString();
								break;
							}
						}
						
						//Author exists, put in map
						if(ok){
							if(!fs.equals(mp.get(fs.id))){
								mp.put(fs.id, fs);
								
								DialogQueue.add(new CodeforcesWindow(name,fs.id,this));
							}
						}
						
					}	
					
					//All ends clear unused result
					for(Integer key:mp.keySet()){
						if(load.contains(key)==false){
							System.out.println("Clears:"+key);
							mp.put(key, null);
						}
					}
				}else{
					throw new Exception("Codeforces API Status Error");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
