
package demoactions;

import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.GUIUtilities;
import org.gjt.sp.jedit.EditPlugin;


public class DemoActionsPlugin extends EditPlugin {
	/** Name for plugin manager */
	public final static String NAME = "Demo Buttons AB";

	static {
		String dir = jEdit.getSettingsDirectory();
		if (dir == null)
			dir = System.getProperty("user.home");
		System.setProperty("demoactions.home", dir);
	}
}

