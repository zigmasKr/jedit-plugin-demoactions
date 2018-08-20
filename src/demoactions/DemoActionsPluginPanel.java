
package demoactions;

import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class DemoActionsPluginPanel extends JPanel {

	public DemoActionsPluginPanel() {
		DemoActionsPanel dap = new DemoActionsPanel();
		dap.setCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		add(dap);
	}
}
