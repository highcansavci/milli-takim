package view.bundle;

import javax.swing.JFrame;

public class MilliTakim {

	public static void main(String args[]) {
		MilliTakimFrame mtf = new MilliTakimFrame();
		mtf.setLocationRelativeTo(null);
		mtf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mtf.pack();
		mtf.setVisible(true);
		mtf.setResizable(false);
	}
}
