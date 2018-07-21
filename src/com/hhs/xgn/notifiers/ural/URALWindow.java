package com.hhs.xgn.notifiers.ural;


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

public class URALWindow extends MovingWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int subId;

	int bci;
	String bcf;

	URALWindow self = this;

	JLabel user, pid, sta, tc,sid;

	URALNotifier cn;
	
	@Override
	public void setId(int nid) {
		this.id=nid;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
		int x = (int) (dim.getWidth() - 320 - 3);
		int y = (int) (dim.getHeight() - screenInsets.bottom - 30 * id - 3);
		this.setBounds(x, y, 320, 30);
		
	}
	public URALWindow(int subId,URALNotifier cn) {
		this.subId=subId;
		
		this.cn=cn;
		
		this.setTitle("Moving Window");
		this.setLayout(new GridLayout(1, 5));
		this.setAlwaysOnTop(true);
		getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		user = new JLabel("XiaoGeNintendo");
		pid = new JLabel("1000");
		sta = new JLabel("Compiling...");
		tc=new JLabel("Test#0");
		sid=new JLabel("OJ:URAL");
		sid.setToolTipText("Timus Online Judge");
		
		user.setFont(new Font("Consolas", Font.BOLD, 15));
		pid.setFont(new Font("Consolas", Font.PLAIN, 15));
		sid.setFont(new Font("Consolas", Font.PLAIN, 10));
		sta.setFont(new Font("Consolas", Font.PLAIN, 15));
		tc.setFont(new Font("Consolas", Font.PLAIN, 15));

		this.add(user);
		add(pid);
		add(sta);
		add(tc);
		add(sid);
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
		URALSubmission f = cn.mp.get(subId);
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
		
		if(f.TestNo.equals("")){
			tc.setText("");
			tc.setToolTipText("(No test case)");
		}else{
			tc.setText("Test#"+f.TestNo);
			tc.setToolTipText("Test#:"+sid.getText());
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
