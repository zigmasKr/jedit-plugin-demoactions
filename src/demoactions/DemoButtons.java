/**
*
*/

package demoactions;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DemoButtons extends JFrame {

	public DemoButtons() {

		super("== Demo Buttons ==");
		// There is a constructor for JFrame: JFrame(String title)

		addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent we) {
					System.exit(0);
				}
		});
		// main code part:
		setContentPane(new DemoActionsPanel());
		pack();
		setVisible(true);
	}
	// Main method:
	public static void main (String[] args) {
		new DemoButtons();
	}
}
