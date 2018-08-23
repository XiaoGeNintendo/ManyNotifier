package com.hhs.notifiers.ural;

import java.awt.Color;

import com.hhs.notifiers.common.MovingWindow;

/**
 * Window Class of URAL.
 * 
 * @author XGN,Zzzyt
 *
 */
public class URALWindow extends MovingWindow {

	private static final long serialVersionUID = 1L;

	int subId;

	int bci;
	String bcf;

	URALWindow self = this;

	URALNotifier cn;

	/**
	 * Constructor with content.
	 * 
	 * @param subId
	 *            Submission ID
	 * @param cn
	 *            Its manager {@link URALNotifier} object
	 */
	public URALWindow(int subId, URALNotifier cn) {
		super(" ", "WJ", " ", " ", "URAL", "Timus Online Judge");

		this.subId = subId;

		this.cn = cn;

		update();
	}

	/**
	 * Update contents of this window.
	 */
	@Override
	public void update() {
		URALSubmission f = cn.mp.get(subId);

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

		if (f.TestNo.equals("")) {
			tc.setText("");
			tc.setToolTipText("(No test case)");
		} else {
			tc.setText("Test#" + f.TestNo);
			tc.setToolTipText("Test#:" + tc.getText());
		}

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
		if (v.equals("Runtime error")) {
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
		if (v.equals("Presentation error")) {
			sta.setText("PE");
			sta.setForeground(Color.pink);
			return;
		}

		sta.setText(v);
		sta.setToolTipText(v);

	}
}
