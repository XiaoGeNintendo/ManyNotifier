package com.hhs.notifiers.luogu;

import java.awt.Color;
import java.awt.Font;

import com.hhs.notifiers.common.MovingWindow;

/**
 * Window Class of URAL.
 * 
 * @author XGN,Zzzyt
 *
 */
public class LuoguWindow extends MovingWindow {

	private static final long serialVersionUID = 1L;

	int subId;

	int bci;
	String bcf;

	LuoguWindow self = this;

	LuoguNotifier cn;

	/**
	 * Constructor with content.
	 * 
	 * @param subId
	 *            Submission ID
	 * @param cn
	 *            Its manager {@link LuoguNotifier} object
	 */
	public LuoguWindow(int subId, LuoguNotifier cn) {
		super(" ", " ", " ", " ", "Luogu", "Luogu");

		this.subId = subId;

		this.cn = cn;

		this.user.setFont(new Font("ËÎÌו", Font.BOLD, 15));

		update();
	}

	/**
	 * Update contents of this window.
	 */
	@Override
	public void update() {
		LuoguSubmission f = cn.mp.get(subId);

		if (f == null) {
			user.setText("??");
			pid.setText("??");
			tc.setText("??");
			sta.setText("OOS");
			sta.setToolTipText("Out of sync");
			cn.mp.remove(subId);
			return;
		}
		user.setText(f.author);
		user.setToolTipText(f.author);

		pid.setText("" + f.probId.split(" ")[0]);
		pid.setToolTipText("ProbID:" + pid.getText());

		if (f.score.equals("")) {
			tc.setText("");
			tc.setToolTipText("(No score)");
		} else {
			tc.setText("" + f.score);
			tc.setToolTipText("Score:" + tc.getText());
		}

		StatusCheck(f.verdict);
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
		if (v.equals("Unaccepted")) {
			sta.setText("UA");
			sta.setForeground(Color.red);
			return;
		}
		if (v.equals("Judging")) {
			sta.setText("WJ");
			sta.setForeground(Color.black);
			return;
		}
		if (v.equals("Waiting")) {
			sta.setText("WJ");
			sta.setForeground(Color.black);
			return;
		}
		if (v.equals("Compile err")) {
			sta.setText("CE");
			sta.setForeground(Color.gray);
			return;
		}

		sta.setText(v);
		sta.setToolTipText(v);

	}
}
