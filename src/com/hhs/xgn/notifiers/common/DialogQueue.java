package com.hhs.xgn.notifiers.common;

import java.util.List;

import java.util.ArrayList;

/**
 * For displaying the {@link MovingWindow} from different notifiers
 * @author XGN
 *
 */
public class DialogQueue {
	static List<MovingWindow> mv=new ArrayList<MovingWindow>();
	
	/**
	 * Show up a {@link MovingWindow}
	 * @param mw
	 */
	public synchronized static void add(MovingWindow mw){
		mv.add(mw);
		mw.setId(mv.size());
	}
	
	/**
	 * Delete a current window
	 * @param id
	 */
	public synchronized static void del(int id){
		//System.out.println("Remove:"+id);
		mv.remove(id-1);
		for(int i=0;i<mv.size();i++){
			mv.get(i).setId(i+1);
		}
		
		//System.out.println(mv);
	}
	
	public synchronized static void del(MovingWindow obj){
		mv.remove(obj);
		for(int i=0;i<mv.size();i++){
			mv.get(i).setId(i+1);
		}
	}
}
