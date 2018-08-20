
package demoactions;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ThreadsDemoInputDialog extends JFrame {
	// https://www.softlab.ntua.gr/facilities/documentation/unix/java/tutorial/uiswing/layout/gridbagExample.html
	// https://www.math.uni-hamburg.de/doc/java/tutorial/uiswing/layout/gridbag.html

	// data:
	int limit;
	String txta;
	String txtb;

	// components:
	JLabel lblLimit;
	JLabel lblTextA;
	JLabel lblTextB;
	JTextField txtL;
	JTextField txtA;
	JTextField txtB;
	JButton button;

	public ThreadsDemoInputDialog() {

		// initialization:
		limit = 0;
		txta = "";
		txtb = "";

		lblLimit = new JLabel("Loop limit: ");
		lblTextA = new JLabel("Text A: ");
		lblTextB = new JLabel("Text B: ");
		txtL = new JTextField(10);
		txtA = new JTextField(40);
		txtB = new JTextField(40);
		button = new JButton("GO");

		// layout:
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints cons = new GridBagConstraints();
		Container contentPane = getContentPane();
		contentPane.setLayout(layout);

		// new Insets(int top, int left, int bottm, int right)
		cons.insets = new Insets(3, 3, 0, 3); // 3-pixel margins on all sides

		// ROW 0
		// (0,0) lblLimit
		cons.gridx = 0;
		cons.gridy = 0;
		/*
		gridx, gridy
		Specify the row and column at the upper left of the component.
		The leftmost column has address gridx=0 and the top row has address gridy=0.
		Use GridBagConstraints.RELATIVE (the default value) to specify that
		the component be placed just to the right of (for gridx) or
		just below (for gridy) the component that was added to the container
		just before this component was added. We recommend specifying the
		gridx and gridy values for each component;
		this tends to result in more predictable layouts.
		*/
		cons.gridwidth = 1;
		cons.gridheight = 1;
		/*
		Specify the number of columns (for gridwidth) or rows (for gridheight)
		in the component's display area. These constraints specify the number
		of cells the component uses, not the number of pixels it uses.
		The default value is 1. Use GridBagConstraints.REMAINDER to specify
		that the component be the last one in its row (for gridwidth)
		or column (for gridheight). Use GridBagConstraints.RELATIVE to specify
		that the component be the next to last one in its row (for gridwidth)
		or column (for gridheight).
		Note: GridBagLayout doesn't allow components to span multiple rows unless
		the component is in the leftmost column or you've specified positive
		gridx and gridy values for the component.
		*/
		cons.fill = GridBagConstraints.NONE;
		/*
		Used when the component's display area is larger than the component's
		requested size to determine whether and how to resize the component.
		Valid values (defined as GridBagConstraints constants) are NONE (the default),
		HORIZONTAL (make the component wide enough to fill its display area horizontally,
		but don't change its height), VERTICAL (make the component tall enough
		to fill its display area vertically, but don't change its width),
		and BOTH (make the component fill its display area entirely).
		*/
		cons.anchor = GridBagConstraints.WEST;
		/*
		Used when the component is smaller than its display area to determine where
		(within the area) to place the component. Valid values (defined as
		GridBagConstraints constants) are CENTER (the default), PAGE_START, PAGE_END,
		LINE_START, LINE_END, FIRST_LINE_START, FIRST_LINE_END, LAST_LINE_END,
		and LAST_LINE_START.
		Version note:  The PAGE_* and *LINE_* constants were introduced in 1.4.
		Previous releases require values named after points of the compass.
		For example, NORTHEAST indicates the top-right part of the display area.
		We recommend that you use the new constants, instead, since they
		enable easier localization.
		*/
		cons.weightx = 1;
		cons.weighty = 2;
		/*
		Specifying weights is an art that can have a significant impact on the appearance
		of the components a GridBagLayout controls. Weights are used to determine how
		to distribute space among columns (weightx) and among rows (weighty);
		this is important for specifying resizing behavior.
		Unless you specify at least one nonzero value for weightx or weighty,
		all the components clump together in the center of their container.
		This is because when the weight is 0.0 (the default), the GridBagLayout
		puts any extra space between its grid of cells and the edges of the container.
		Generally weights are specified with 0.0 and 1.0 as the extremes: the numbers
		in between are used as necessary. Larger numbers indicate that the component's row
		or column should get more space. For each column, the weight is related
		to the highest weightx specified for a component within that column,
		with each multicolumn component's weight being split somehow between
		the columns the component is in. Similarly, each row's weight is related
		to the highest weighty specified for a component within that row.
		Extra space tends to go toward the rightmost column and bottom row.
		*/
		layout.setConstraints(lblLimit, cons);
		contentPane.add(lblLimit);
		// (1,0) txtL
		cons.gridx = 1;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.WEST;
		cons.weightx = 1;
		cons.weighty = 2;
		layout.setConstraints(txtL, cons);
		contentPane.add(txtL);

		// ROW 1
		// (0,1) lblTextA
		cons.gridx = 0;
		cons.gridy = 1;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.WEST;
		cons.weightx = 1;
		cons.weighty = 2;
		layout.setConstraints(lblTextA, cons);
		contentPane.add(lblTextA);
		// (1,1) txtA
		cons.gridx = 1;
		cons.gridy = 1;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 1;
		cons.weighty = 2;
		layout.setConstraints(txtA, cons);
		contentPane.add(txtA);

		// ROW 2
		// (0,2) lblTextB
		cons.gridx = 0;
		cons.gridy = 2;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.WEST;
		cons.weightx = 1;
		cons.weighty = 2;
		layout.setConstraints(lblTextB, cons);
		contentPane.add(lblTextB);
		// (1-3,1) txtB
		cons.gridx = 1;
		cons.gridy = 2;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 1;
		cons.weighty = 2;
		layout.setConstraints(txtB, cons);
		contentPane.add(txtB);

		// ROW 3
		// (2,3) button
		cons.gridx = 1;
		cons.gridy = 3;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 1;
		cons.weighty = 1; //?
		layout.setConstraints(button, cons);
		contentPane.add(button);

		addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					dispose();
				}
		});

		button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// at java.lang.NumberFormatException.forInputString(Unknown Source)
					String lt = txtL.getText();
					try {
						if (lt != null && !lt.isEmpty()) {
							limit = Integer.parseInt(txtL.getText());
						}
						else {
							limit = 0;
						}
					} catch (NumberFormatException nfe) {
						limit = 0;
						System.out.println(nfe);
					}
					txta = txtA.getText();
					txtb = txtB.getText();
					// here TreadsDemo can start
					new demobuttons.ThreadsDemo(limit, txta, txtb).startDemo();
				}
		});

		setTitle("Threads Demo Input Dialog");
		setSize(500, 200);
		pack();
		setVisible(true);
	}

	/*
	// main:
	public static void main(String s[]) {
		ThreadsDemoInputDialog tdid = new ThreadsDemoInputDialog();
	}
	*/
}