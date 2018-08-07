package com.hhs.xgn.notifiers.mysbz;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import com.hhs.xgn.notifiers.common.DialogQueue;
import com.hhs.xgn.notifiers.common.MovingWindow;

public class MYSBZWindow extends MovingWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int subId;

	int bci;
	String bcf;

	MYSBZWindow self = this;

	MYSBZNotifier cn;
	
	@Override
	public void setId(int nid) {
		this.id=nid;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
		int x = (int) (dim.getWidth() - 320 - 3);
		int y = (int) (dim.getHeight() - screenInsets.bottom - 30 * id - 3);
		this.setBounds(x, y, 320, 30);
		
	}
	public MYSBZWindow(int subId,MYSBZNotifier cn) {
		super(" ","WJ"," "," ","MYSBZ","Dashiye Online Judge");

		Thread t = new Thread() {
			public void run() {
				while (true) {
					try{
						update();
					}
					catch(NullPointerException e) {
						user.setText("??");
						pid.setText("??");
						tc.setText("??");
						sta.setText("OOS");
						sta.setToolTipText("Out of sync");
					}
					
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
	}
	
	@Override
	public void update() {
		MYSBZSubmission f = cn.mp.get(subId);
		
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
		if (v.equals("Compile_Error")) {
			sta.setText("CE");
			sta.setForeground(Color.gray);
			return;
		}
		if (v.equals("Runtime_Error")) {
			sta.setText("RE");
			sta.setForeground(Color.cyan);
			return;
		}
		if (v.equals("Wrong_Answer")) {
			sta.setText("WA");
			sta.setForeground(Color.red);
			return;
		}
		if (v.equals("Time_Limit_Exceed")) {
			sta.setText("TLE");
			sta.setForeground(Color.blue);
			return;
		}
		if (v.equals("Memory_Limit_Exceed")) {
			sta.setText("MLE");
			sta.setForeground(Color.orange);
			return;
		}
		if (v.equals("Output_Limit_Exceed")) {
			sta.setText("OLE");
			sta.setForeground(Color.magenta);
			return;
		}
		if (v.equals("Pending") || v.equals("Pending_Rejudging") || v.equals("Compiling") || v.equals("Running_&_Judging")) {
			sta.setText("WJ");
			sta.setForeground(Color.black);
			return;
		}
		if(v.equals("Presentation_Error")){
			sta.setText("PE");
			sta.setForeground(Color.pink);
			return;
		}
		if(v.equals("Restrict Function Call")){
			sta.setText("RFC");
			sta.setForeground(Color.yellow);
			return;
		}
		sta.setText(v);
		sta.setToolTipText(v);

	}
}
