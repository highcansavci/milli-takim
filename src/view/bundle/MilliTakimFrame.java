package view.bundle;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MilliTakimFrame extends JFrame{

	private static final long serialVersionUID = 1L;

	public MilliTakimFrame() {
		this.getContentPane().add(new TakimPanel(), BorderLayout.CENTER);
	}
}
