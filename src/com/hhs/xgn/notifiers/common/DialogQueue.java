package com.hhs.xgn.notifiers.common;

import java.util.Vector;

/**
 * For displaying the {@link MovingWindow} from different notifiers and updating
 * them.<br>
 * The main purpose of this class is to give each {@link MovingWindow} an id.
 * 
 * @author XGN,Zzzyt
 *
 */
public class DialogQueue {
	static Vector<MovingWindow> mv = new Vector<MovingWindow>();

	/**
	 * Add(register) a {@link MovingWindow} into the queue.
	 * 
	 * @param mw
	 */
	public synchronized static void add(MovingWindow mw) {
		mv.add(mw);
		mw.setId(mv.size());
		System.out.println("[QUEUE]Added window " + mw);
	}

	/**
	 * Keep updating all {@link MovingWindow} in the queue.
	 */
	public static void run() {
		while (true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			for (int i = 0; i < mv.size(); i++) {
				MovingWindow tmp = mv.get(i);
				tmp.update();
				System.out.println("[QUEUE]Updating window id=" + i+"(Window inside id="+(i+1)+")");

				// Remove outdated windows
				if (tmp.sta.getText().equals("OOS")) {
					del(tmp);
					i--;
				}
				//tmp.sta.getText().equals("WJ") == false && tmp.sta.getText().equals("??") == false
			}
		}
	}

	/**
	 * Delete a {@link MovingWindow} from the queue.<br>
	 * Deprecated because not Thread-safe.<br>
	 * <br>
	 * <i>But now it should be safe?</i>
	 * 
	 * @param id
	 */
	@Deprecated
	public synchronized static void del(int id) {
		System.out.println("[QUEUE]Removed window id=" + id);
		mv.get(id).dispose();
		mv.remove(id - 1);
		for (int i = 0; i < mv.size(); i++) {
			mv.get(i).setId(i + 1);
		}
	}

	/**
	 * Delete a {@link MovingWindow} from the queue.<br>
	 * 
	 * @param id
	 */
	public synchronized static void del(MovingWindow obj) {
		obj.dispose();
		mv.remove(obj);

		// Reset ids
		for (int i = 0; i < mv.size(); i++) {
			mv.get(i).setId(i + 1);
		}

		System.out.println("[QUEUE]Removed window " + obj);
	}
}
