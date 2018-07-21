package com.hhs.xgn.notifiers.mysbz;


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

import org.omg.PortableServer.SERVANT_RETENTION_POLICY_ID;

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

	JLabel user, pid, sta, tc,sid;

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
		this.subId=subId;
		
		this.cn=cn;
		
		this.setTitle("Moving Window");
		this.setLayout(new GridLayout(1, 5));
		this.setAlwaysOnTop(true);
		getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		user = new JLabel("XiaoGeNintendo");
		sid=new JLabel("0");
		pid = new JLabel("1000");
		sta = new JLabel("Compiling...");
		tc=new JLabel("OJ:MYSBZ");
		tc.setToolTipText("Dashiye Online Judge");
		
		user.setFont(new Font("Consolas", Font.BOLD, 15));
		pid.setFont(new Font("Consolas", Font.PLAIN, 15));
		sid.setFont(new Font("Consolas", Font.PLAIN, 10));
		sta.setFont(new Font("Consolas", Font.PLAIN, 15));
		tc.setFont(new Font("Consolas", Font.PLAIN, 15));

		this.add(user);
		add(sid);
		add(pid);
		add(sta);
		add(tc);

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
		MYSBZSubmission f = cn.mp.get(subId);
		//System.out.println("Getting " + subId + " from " + f);
		if (f == null) {
			user.setText("??");
			pid.setText("??");
			sid.setText("??");
			//tc.setText("??");
			sta.setText("OOS");
			sta.setToolTipText("Out of sync");
			return;
		}
		user.setText(f.User);
		user.setToolTipText(f.User);
		
		pid.setText(""+f.PID);
		pid.setToolTipText("ProbID:"+pid.getText());
		
		sid.setText(""+f.RunID);
		sid.setToolTipText("RunID:"+sid.getText());
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
