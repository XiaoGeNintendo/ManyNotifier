package com.hhs.xgn.notifiers.common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * You should not construct the window with ID because the {@link DialogQueue}
 * has done so
 * 
 * @author XGN
 *
 */
public class MovingWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int id;
	public JLabel user, pid, sta, tc, oj;

	public MovingWindow() {

	}

	public MovingWindow(String HANDLE, String PROBLEM, String STATUS, String TESTCASE, String OJ, String FULLOJ) {
		this.setTitle("Moving Window");
		this.setAlwaysOnTop(true);
		getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));

		this.setLayout(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		user = new JLabel(HANDLE);
		pid = new JLabel(PROBLEM);
		sta = new JLabel(STATUS);
		tc = new JLabel(TESTCASE);
		oj = new JLabel(OJ);
		oj.setToolTipText(FULLOJ);

		user.setBounds(0, 0, 120, 30);
		pid.setBounds(120, 0, 80, 30);
		sta.setBounds(200, 0, 30, 30);
		tc.setBounds(230, 0, 40, 30);
		oj.setBounds(270, 0, 70, 30);

		user.setFont(new Font("Consolas", Font.BOLD, 15));
		pid.setFont(new Font("Consolas", Font.PLAIN, 15));
		sta.setFont(new Font("Consolas", Font.PLAIN, 15));
		tc.setFont(new Font("Consolas", Font.ITALIC, 15));
		oj.setFont(new Font("Consolas", Font.PLAIN, 15));
		
		add(user);
		add(pid);
		add(sta);
		add(tc);
		add(oj);

		this.setUndecorated(true);
		this.setVisible(true);
	}

	public void setId(int nid) {
		id = nid;

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
		int x = (int) (dim.getWidth() - 340 - 30);
		int y = (int) (dim.getHeight() - screenInsets.bottom - 30 * id - 3);
		this.setBounds(x, y, 340, 30);
	}
}
