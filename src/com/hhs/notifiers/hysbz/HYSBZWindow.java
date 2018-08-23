package com.hhs.notifiers.hysbz;

import java.awt.Color;
import com.hhs.notifiers.common.MovingWindow;
import com.hhs.notifiers.poj.POJNotifier;

/**
 * Window Class of HYSBZ.
 * 
 * @author XGN,Zzzyt
 *
 */
public class HYSBZWindow extends MovingWindow {

	private static final long serialVersionUID = 1L;

	int subId;

	int bci;
	String bcf;

	HYSBZWindow self = this;

	HYSBZNotifier cn;

	/**
	 * Constructor with content.
	 * 
	 * @param subId
	 *            Submission ID
	 * @param cn
	 *            Its manager {@link POJNotifier} object
	 */
	public HYSBZWindow(int subId, HYSBZNotifier cn) {
		super(" ", "WJ", " ", " ", "HYSBZ", "Dashiye Online Judge");

		this.subId = subId;

		this.cn = cn;

		update();
	}

	@Override
	public void update() {
		HYSBZSubmission f = cn.mp.get(subId);

		if (f == null) {
			user.setText("??");
			pid.setText("??");
			tc.setText("??");
			sta.setText("OOS");
			sta.setToolTipText("Out of sync");
			cn.mp.remove(subId);
			return;
		}
		user.setText(f.User);
		user.setToolTipText(f.User);

		pid.setText("" + f.PID);
		pid.setToolTipText("ProbID:" + pid.getText());

		tc.setText("" + f.RunID);
		tc.setToolTipText("RunID:" + tc.getText());
		StatusCheck(f.Result);
	}

	/**
	 * Set status text format.
	 * 
	 * @param v
	 *            Status string
	 */
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
		if (v.equals("Pending") || v.equals("Pending_Rejudging") || v.equals("Compiling")
				|| v.equals("Running_&_Judging")) {
			sta.setText("WJ");
			sta.setForeground(Color.black);
			return;
		}
		if (v.equals("Presentation_Error")) {
			sta.setText("PE");
			sta.setForeground(Color.pink);
			return;
		}
		if (v.equals("Restrict Function Call")) {
			sta.setText("RFC");
			sta.setForeground(Color.yellow);
			return;
		}
		sta.setText(v);
		sta.setToolTipText(v);

	}
}
