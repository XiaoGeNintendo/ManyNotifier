package com.hhs.xgn.notifiers.ural;


import java.awt.Color;
import com.hhs.xgn.notifiers.common.DialogQueue;
import com.hhs.xgn.notifiers.common.MovingWindow;

public class URALWindow extends MovingWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int subId;

	int bci;
	String bcf;

	URALWindow self = this;

	URALNotifier cn;
	
	public URALWindow(int subId,URALNotifier cn) {
		super(" ","WJ"," "," ","URAL","Timus Online Judge");

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
		URALSubmission f = cn.mp.get(subId);
		
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
		
		if(f.TestNo.equals("")){
			tc.setText("");
			tc.setToolTipText("(No test case)");
		}else{
			tc.setText("Test#"+f.TestNo);
			tc.setToolTipText("Test#:"+tc.getText());
		}
		
		
		StatusCheck(f.Result);
	}

	void StatusCheck(String v) {
		sta.setToolTipText(v);
		if (v.equals("Accepted")) {
			sta.setText("AC");
			sta.setForeground(Color.GREEN);
			return;
		}
		if(v.equals("Runtime error")){
			sta.setText("RE");
			sta.setForeground(Color.CYAN);
		}
		if (v.equals("Wrong answer")) {
			sta.setText("WA");
			sta.setForeground(Color.red);
			return;
		}
		if (v.equals("Time limit exceeded")) {
			sta.setText("TLE");
			sta.setForeground(Color.blue);
			return;
		}
		if (v.equals("Memory limit exceeded")) {
			sta.setText("MLE");
			sta.setForeground(Color.orange);
			return;
		}
		if (v.equals("Output limit exceeded")) {
			sta.setText("OLE");
			sta.setForeground(Color.magenta);
			return;
		}
		if (v.equals("Running")) {
			sta.setText("WJ");
			sta.setForeground(Color.black);
			return;
		}
		if(v.equals("Presentation error")){
			sta.setText("PE");
			sta.setForeground(Color.pink);
			return;
		}
		
		sta.setText(v);
		sta.setToolTipText(v);

	}
}
