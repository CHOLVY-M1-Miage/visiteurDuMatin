package edu.uga.miage.m1.polygons.gui.graphique;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;


public class GUIHelper {
	
	public static void showOnFrame(String frameName) {
		JFrame frame = new JDrawingFrame(frameName);
		WindowAdapter wa = new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
		frame.addWindowListener(wa);
		frame.pack();
		frame.setVisible(true);
	}

}