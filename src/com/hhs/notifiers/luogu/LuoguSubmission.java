package com.hhs.notifiers.luogu;

import java.io.ByteArrayInputStream;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;


public class LuoguSubmission {
	public String verdict;
	public String probId;
	/**Time and Memory*/
	public String timeMem;
	public String author;
	public String submitTime;
	/**Code Length and Language*/
	public String lenLang;
	public String score;
	public LuoguSubmission(){

	}
	
	public LuoguSubmission(Element div){
		Element div1=div.children().get(0);
		System.out.println(div1+"\n\n\n");
		Element lg_inline_up=div1.children().get(2);
		System.out.println(lg_inline_up+"\n\n\n");
		Element data_pjax=lg_inline_up.children().get(1);
		System.out.println(data_pjax+"\n\n\n");
		
//		
//		verdict=div.children().get(0).children().get(2).children().get(1).children().get(1).text();
//		score=div.children().get(0).children().get(2).children().get(1).children().get(3).text();
	}
}
