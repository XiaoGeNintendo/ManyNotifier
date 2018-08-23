package com.hhs.notifiers.zoj;

import java.awt.Color;

import com.hhs.notifiers.common.MovingWindow;

/**
 * Window Class of ZOJ.
 * 
 * @author XGN,Zzzyt
 *
 */
public class ZOJWindow extends MovingWindow {

	private static final long serialVersionUID = 1L;

	int subId;

	int bci;
	String bcf;

	ZOJWindow self = this;

	ZOJNotifier cn;

	/**
	 * Constructor with content.
	 * 
	 * @param subId
	 *            Submission ID
	 * @param cn
	 *            Its manager {@link ZOJNotifier} object
	 */
	public ZOJWindow(int subId, ZOJNotifier cn) {
		super(" ", "WJ", " ", " ", "ZOJ", "Zhejiang University Online Judge");

		this.subId = subId;

		this.cn = cn;

		update();
	}

	/**
	 * Update contents of this window.
	 */
	@Override
	public void update() {
		ZOJSubmission f = cn.mp.get(subId);

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
		if (v.equals("Compilation Error")) {
			sta.setText("CE");
			sta.setForeground(Color.gray);
			return;
		}
		if (v.equals("Segmentation Fault")) {
			sta.setText("SF");
			sta.setForeground(Color.LIGHT_GRAY);
			return;
		}
		if (v.equals("Non-zero Exit Code")) {
			sta.setText("NEC");
			sta.setForeground(Color.DARK_GRAY);
			return;
		}
		if (v.equals("Floating Point Error")) {
			sta.setText("FPE");
			sta.setForeground(Color.magenta);
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
		if (v.equals("Presentation Error")) {
			sta.setText("PE");
			sta.setForeground(Color.pink);
			return;
		}

		sta.setText(v);
		sta.setToolTipText(v);

	}
}
