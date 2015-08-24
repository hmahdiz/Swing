import javax.swing.SwingUtilities;

import ir.mahan.train.view.MainFrame;
import ir.mahan.train.view.login.loginMainFrame;

public class StartApp {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new loginMainFrame("Login to Application");
				//new MainFrame("");

			}
		});

	}

}
