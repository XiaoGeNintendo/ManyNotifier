package com.hhs.notifiers.atcoder;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.hhs.notifiers.common.MovingWindow;

/**
 * Window class of AtCoder.
 * 
 * @author XGN,Zzzyt
 *
 */
public class AtcoderWindow extends MovingWindow {

	private static final long serialVersionUID = 1L;
	String subId;

	int bci;
	String bcf;

	AtcoderWindow self = this;

	AtcoderNotifier cn;

	/**
	 * Constructor with content.
	 * 
	 * @param subId
	 *            submission ID
	 * @param contest
	 *            contest number
	 * @param cn
	 *            Its manager {@link AtcoderNotifier} object
	 */
	public AtcoderWindow(String subId, String contest, AtcoderNotifier cn) {
		super(" ", " ", "WJ", "", "AtCoder", "AtCoder");

		this.subId = subId;

		this.cn = cn;

		update();
	}

	@Override
	/**
	 * Update contents of this window.
	 */
	public void update() {
		AtcoderSubmission f = cn.mp.get(subId);

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

		tc.setToolTipText("RunID:" + f.RunID);
		String[] tmp = f.RunID.split("/");
		tc.setText(tmp[tmp.length - 1]);

		pid.setText("" + f.Task);
		pid.setToolTipText("ProbID:" + pid.getText());

		StatusCheck(f.Status);

		tc.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				Toolkit.getDefaultToolkit().getSystemClipboard()
						.setContents(new StringSelection("https://beta.atcoder.jp" + cn.mp.get(subId).RunID), null);
			}
		});
	}

	/**
	 * Set status text format.
	 * 
	 * @param v
	 *            Status string
	 */
	void StatusCheck(String v) {
		sta.setToolTipText(v);
		sta.setText(v);
		if (v.contains("AC")) {
			sta.setForeground(Color.GREEN);
			return;
		}
		if (v.contains("CE")) {
			sta.setForeground(Color.gray);
			return;
		}
		if (v.contains("RE")) {
			sta.setForeground(Color.cyan);
			return;
		}
		if (v.contains("WA")) {
			sta.setForeground(Color.red);
			return;
		}
		if (v.contains("TLE")) {
			sta.setForeground(Color.blue);
			return;
		}
		if (v.contains("MLE")) {
			sta.setForeground(Color.orange);
			return;
		}
		if (v.contains("ILE")) {
			sta.setForeground(Color.magenta);
			return;
		}
		if (v.contains("WJ")) {
			sta.setForeground(Color.black);
			return;
		}

	}
}
