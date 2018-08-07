package com.hhs.xgn.notifiers.codeforces;

import java.awt.Color;
import com.hhs.xgn.notifiers.common.DialogQueue;
import com.hhs.xgn.notifiers.common.MovingWindow;

public class CodeforcesWindow extends MovingWindow {

	/**etText*()
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int subId;

	int bci;
	String bcf;

	CodeforcesWindow self = this;
	FullSubmission f;
	String handle;

	CodeforcesNotifier cn;
	
	public CodeforcesWindow(String handle,int subId,CodeforcesNotifier cn) {
		super(handle," ","WJ","T1","CF","CodeForces");
		
		
		this.subId=subId;
		
		this.handle = handle;
		this.cn=cn;
		
		/*
		Thread t = new Thread() {
			public void run() {
				while (true) {
					update();
					if (sta.getText().equals("WJ") == false && sta.getText().equals("??") == false) {
						break;
					}

					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				dispose();
				DialogQueue.del(self);
			}
		};

		t.start();
		*/
	}
	
	@Override
	public void update() {
		f = cn.mp.get(subId);
		//System.out.println("Getting " + subId + " from " + f);
		if (f == null) {
			user.setText("??");
			pid.setText("??");
			tc.setText("??");
			sta.setText("OOS");
			sta.setToolTipText("Out of sync");
			return;
		}
		user.setText(this.handle);
		user.setToolTipText(this.handle);
		
		pid.setText(f.problem.contestId + f.problem.index);
		pid.setToolTipText(pid.getText());
		
		bci = f.problem.contestId;
		bcf = f.problem.index;

		tc.setText("T" + (f.passedTestCount + 1));
		tc.setToolTipText(tc.getText());
		
		StatusCheck(f.verdict);
	}

	void StatusCheck(String v) {
		if (v.equals("OK")) {
			sta.setText("AC");
			sta.setToolTipText("Accepted");
			sta.setForeground(Color.GREEN);
			return;
		}
		if (v.equals("COMPILATION_ERROR")) {
			sta.setText("CE");
			sta.setToolTipText("Compilation Error");
			sta.setForeground(Color.gray);
			return;
		}
		if (v.equals("RUNTIME_ERROR")) {
			sta.setText("RE");
			sta.setToolTipText("Runtime Error");
			sta.setForeground(Color.cyan);
			return;
		}
		if (v.equals("WRONG_ANSWER")) {
			sta.setText("WA");
			sta.setToolTipText("Wrong Answer");
			sta.setForeground(Color.red);
			return;
		}
		if (v.equals("TIME_LIMIT_EXCEEDED")) {
			sta.setText("TLE");
			sta.setToolTipText("Time Limit Exceeded");
			sta.setForeground(Color.blue);
			return;
		}
		if (v.equals("MEMORY_LIMIT_EXCEEDED")) {
			sta.setText("MLE");
			sta.setToolTipText("Memory Limit Exceeded");
			sta.setForeground(Color.orange);
			return;
		}
		if (v.equals("IDLENESS_LIMIT_EXCEEDED")) {
			sta.setText("ILE");
			sta.setToolTipText("Idleness Limit Exceeded");
			sta.setForeground(Color.magenta);
			return;
		}
		if (v.equals("TESTING")) {
			sta.setText("WJ");
			sta.setToolTipText("In queue/Testing");
			sta.setForeground(Color.black);
			return;
		}

		//I tried to keep serious but there is only a small space of 3 chars to display...
		//So I choosed "IDK" :P
		sta.setText("IDK");
		sta.setToolTipText(v);

	}
}
