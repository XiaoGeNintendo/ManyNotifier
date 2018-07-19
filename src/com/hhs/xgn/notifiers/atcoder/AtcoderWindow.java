package com.hhs.xgn.notifiers.atcoder;


import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.omg.PortableServer.SERVANT_RETENTION_POLICY_ID;

import com.hhs.xgn.notifiers.common.DialogQueue;
import com.hhs.xgn.notifiers.common.MovingWindow;

public class AtcoderWindow extends MovingWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String subId;

	int bci;
	String bcf;

	AtcoderWindow self = this;

	JLabel user, pid, sta, tc,sid;

	AtcoderNotifier cn;
	
	@Override
	public void setId(int nid) {
		this.id=nid;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
		int x = (int) (dim.getWidth() - 320 - 3);
		int y = (int) (dim.getHeight() - screenInsets.bottom - 30 * id - 3);
		this.setBounds(x, y, 320, 30);
		
	}
	public AtcoderWindow(String subId,String contest,AtcoderNotifier cn) {
		this.subId=subId;
		
		this.cn=cn;
		
		this.setTitle("Moving Window");
		this.setLayout(new GridLayout(1, 4));
		this.setAlwaysOnTop(true);
		getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		user = new JLabel("XiaoGeNintendo");
		sid=new JLabel("0");
		pid = new JLabel("1000");
		sta = new JLabel("Compiling...");
		tc=new JLabel("OJ:Atcoder");
		tc.setToolTipText("Atcoder "+contest);
		
		user.setFont(new Font("Consolas", Font.BOLD, 15));
		pid.setFont(new Font("Consolas", Font.PLAIN, 15));
		sid.setFont(new Font("Consolas", Font.PLAIN, 10));
		sta.setFont(new Font("Consolas", Font.PLAIN, 15));
		tc.setFont(new Font("Consolas", Font.PLAIN, 15));

		sid.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(e.getButton()==2){
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(subId), null);
				}
			}
		});
		
		
		this.add(user);
		add(pid);
		add(sta);
		add(tc);

		this.setUndecorated(true);
		this.setVisible(true);

		Thread t = new Thread() {
			public void run() {
				while (true) {
					update();
					if (sta.getText().contains("/") == false && sta.getText().contains("WJ")==false && sta.getText().contains("??") == false) {
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
		AtcoderSubmission f = cn.mp.get(subId);
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
		
		pid.setText(""+f.Task);
		pid.setToolTipText("ProbID:"+pid.getText());
		StatusCheck(f.Status);
	}

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
