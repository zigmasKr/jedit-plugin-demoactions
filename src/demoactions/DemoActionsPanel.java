
/**
* Demo JButton.
* Herb Schildt's Java Programming Cookbook, p. 386
* Named class of listeners
*/

package demoactions;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class DemoActionsPanel
		extends JPanel
		implements WindowConstants {

	JLabel jlabel;
	JButton jbuttonA;
	JButton jbuttonB;
	JPanel panelMain;
	//
	public DemoActionsPanel() {
		// Create a label.
		jlabel = new JLabel("Press a button.");
		// Make two buttons.
		jbuttonA = new JButton("Alpha");
		jbuttonB = new JButton("Beta");
		// Add action listener.
		jbuttonA.addActionListener(new buttonActionListener());
		jbuttonB.addActionListener(new buttonActionListener());
		//
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		JPanel panelMain = createPanelMain();
		this.add(panelMain);
	}

	private JPanel createPanelMain() {
		JPanel panelM = new JPanel();
		panelM.setLayout(new BoxLayout(panelM, BoxLayout.Y_AXIS));
		// Inner panels:
		panelM.add(createPanelAB());
		panelM.add(createPanelL());
		return panelM;
	}

	private JPanel createPanelAB() {
		JPanel panelAB = new JPanel();
		panelAB.setLayout(new BoxLayout(panelAB, BoxLayout.X_AXIS));
		panelAB.add(jbuttonA);
		panelAB.add(jbuttonB);
		return panelAB;
	}

	private JPanel createPanelL() {
		JPanel panelL = new JPanel();
		panelL.setLayout(new BoxLayout(panelL, BoxLayout.Y_AXIS));
		panelL.add(jlabel);
		return panelL;
	}

	// Handle button events; named action listener
	public class buttonActionListener
		implements ActionListener {
		public void actionPerformed(ActionEvent aevt) {
			String ac = aevt.getActionCommand();
			// See which button was pressed.
			if(ac.equals("Alpha")) {
				// Change the state of Beta each time that Alpha is pressed.
				if(jbuttonB.isEnabled()) {
					jlabel.setText("Alpha pressed. Beta is disabled.");
					jbuttonB.setEnabled(false);
				}
				else {
					jlabel.setText("Alpha pressed. Beta is enabled.");
					jbuttonB.setEnabled(true);
				}
			}
			else if(ac.equals("Beta")) {
				jlabel.setText("Beta pressed.");
			}
		}
	}

	/**
	* Sets the closeOperation attribute of the DemoButtonsPanel object
	*
	* @param operation  The new closeOperation value,
	* one of DISPOSE_ON_CLOSE, DO_NOTHING_ON_CLOSE, EXIT_ON_CLOSE, or HIDE_ON_CLOSE.
	*/
	private int close_operation = EXIT_ON_CLOSE;

	public void setCloseOperation(int operation) {
		switch (operation) {
			case DISPOSE_ON_CLOSE:
			case DO_NOTHING_ON_CLOSE:
			case EXIT_ON_CLOSE:
			case HIDE_ON_CLOSE:
				break;
			default:
				throw new IllegalArgumentException("Invalid close operation, see javax.swing.WindowConstants.");
		}
		close_operation = operation;
	}

	/**
	* Gets the closeOperation attribute of the DemoButtonsPanel object
	*
	* @return   The closeOperation value,
	* one of DISPOSE_ON_CLOSE, DO_NOTHING_ON_CLOSE, EXIT_ON_CLOSE, or HIDE_ON_CLOSE.
	*/
	public int getCloseOperation() {
		return close_operation;
	}

}
