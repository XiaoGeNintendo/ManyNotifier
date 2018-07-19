package com.hhs.xgn.notifiers.common;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JDialog;

/**
 * You should not construct the window with ID because the {@link DialogQueue} has done so
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

	public void setId(int nid) {
		id = nid;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
		int x = (int) (dim.getWidth() - 300 - 3);
		int y = (int) (dim.getHeight() - screenInsets.bottom - 30 * id - 3);
		this.setBounds(x, y, 300, 30);
	}
	
	
}
