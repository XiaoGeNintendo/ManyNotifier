package com.hhs.xgn.notifiers.hrbust;


import java.awt.Color;
import com.hhs.xgn.notifiers.common.DialogQueue;
import com.hhs.xgn.notifiers.common.MovingWindow;

public class HRBUSTWindow extends MovingWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int subId;

	int bci;
	String bcf;

	HRBUSTWindow self = this;

	HRBUSTNotifier cn;
	
	public HRBUSTWindow(int subId,HRBUSTNotifier cn) {
		super(" ","WJ"," "," ","HRBUST","Hrbust Online Judge");

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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				dispose();
				DialogQueue.del(self);
			}
		};

		t.start();
	}
	
	@Override
	public void update() {
		HRBUSTSubmission f = cn.mp.get(subId);
		
		if (f == null) {
			user.setText("??");
			pid.setText("??");
			tc.setText("??");
			sta.setText("OOS");
			sta.setToolTipText("Out of sync");
			return;
		}
		
		user.setText(f.User);
		user.setToolTipText(f.User);
		
		pid.setText(""+f.PID);
		pid.setToolTipText("ProbID:"+pid.getText());
		
		tc.setText(""+f.RunID);
		tc.setToolTipText("RunID:"+tc.getText());
		StatusCheck(f.Result);
	}

	void StatusCheck(String v) {
		sta.setToolTipText(v);
		if (v.equals("Accepted")) {
			sta.setText("AC");
			sta.setForeground(Color.GREEN);
			return;
		}
		if (v.equals("Compile Error")) {
			sta.setText("CE");
			sta.setForeground(Color.gray);
			return;
		}
		if (v.equals("Runtime Error")) {
			sta.setText("RE");
			sta.setForeground(Color.cyan);
			return;
		}
		if (v.equals("Wrong Answer")) {
			sta.setText("WA");
			sta.setForeground(Color.red);
			return;
		}
		if (v.equals("Time Limit Exceeded")) {
			sta.setText("TLE");
			sta.setForeground(Color.blue);
			return;
		}
		if (v.equals("Memory Limit Exceeded")) {
			sta.setText("MLE");
			sta.setForeground(Color.orange);
			return;
		}
		if (v.equals("Output Limit Exceeded")) {
			sta.setText("OLE");
			sta.setForeground(Color.magenta);
			return;
		}
		if (v.equals("Judging")) {
			sta.setText("WJ");
			sta.setForeground(Color.black);
			return;
		}
		if(v.equals("Presentation Error")){
			sta.setText("PE");
			sta.setForeground(Color.pink);
			return;
		}
		if(v.equals("System Error")){
			sta.setText("SE");
			sta.setForeground(Color.GRAY);
			return;
		}
		if(v.equals("Restricted Function")){
			sta.setText("RF");
			sta.setForeground(Color.YELLOW);
			return;
		}
		sta.setText(v);
		sta.setToolTipText(v);

	}
}
