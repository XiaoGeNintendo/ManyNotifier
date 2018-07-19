package com.hhs.xgn.notifiers.codeforces;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.hhs.xgn.notifiers.common.DialogQueue;
import com.hhs.xgn.notifiers.common.MovingWindow;

public class CodeforcesWindow extends MovingWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int subId;

	int bci;
	String bcf;

	CodeforcesWindow self = this;

	JLabel user, pid, sta, tc,oj;

	String handle;

	CodeforcesNotifier cn;
	
	@Override
	public void setId(int nid) {
		this.id=nid;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
		int x = (int) (dim.getWidth() - 320 - 3);
		int y = (int) (dim.getHeight() - screenInsets.bottom - 30 * id - 3);
		this.setBounds(x, y, 320, 30);
		
	}
	public CodeforcesWindow(int subId, String handle,CodeforcesNotifier cn) {
		this.subId=subId;
		
		this.handle = handle;
		this.cn=cn;
		
		this.setTitle("Moving Window");
		this.setLayout(new GridLayout(1, 5));
		this.setAlwaysOnTop(true);
		getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		user = new JLabel("gwq2017");
		pid = new JLabel("1A");
		sta = new JLabel("WJ");
		tc = new JLabel("T1");
		oj=new JLabel("OJ:CF");
		oj.setToolTipText("Codeforces");
		
		user.setFont(new Font("Consolas", Font.BOLD, 15));
		pid.setFont(new Font("Consolas", Font.PLAIN, 15));
		sta.setFont(new Font("Consolas", Font.PLAIN, 15));
		tc.setFont(new Font("Consolas", Font.ITALIC, 15));
		oj.setFont(new Font("Consolas", Font.PLAIN, 15));

		this.add(user);
		add(pid);
		add(sta);
		add(tc);
		add(oj);
		
		this.setUndecorated(true);
		this.setVisible(true);

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
	
	void update() {
		FullSubmission f = cn.mp.get(subId);
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

		tc.setText("Test#" + (f.passedTestCount + 1));
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

		sta.setText("Other");
		sta.setToolTipText(v);

	}
}
