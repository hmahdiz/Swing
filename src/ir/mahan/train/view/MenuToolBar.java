package ir.mahan.train.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.swing.*;

public class MenuToolBar {

	private JMenuBar menuBar;
	private JFileChooser fileChooser;

	public void createComponent() {

		fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.addChoosableFileFilter(new PersonFileFilter());

		menuBar = new JMenuBar();

		createAndAddComponent();

	}

	private void createAndAddComponent() {
		
		// Menus
		JMenu fileMenu = new JMenu("File");
		JMenu windowMenu = new JMenu("Window");

		// Create Sub menus for File menu
		JMenuItem exportDataItem = new JMenuItem("Export Data ...");
		JMenuItem importDataItem = new JMenuItem("Import Data ...");
		JMenuItem exitItem = new JMenuItem("Exit");
		JMenuItem prefsItem = new JMenuItem("Preferences");

		fileMenu.add(exportDataItem);
		fileMenu.add(importDataItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);

		JMenu showMenu = new JMenu("Show");
		
		JMenuItem showFormItem = new JMenuItem("Person Form");
		
		JCheckBoxMenuItem showFormCheckBoxItem = new JCheckBoxMenuItem(
				"Show the Form");
		showFormCheckBoxItem.setSelected(true);

		showMenu.add(showFormItem);
		showMenu.add(showFormCheckBoxItem);
		
		windowMenu.add(showMenu);
		windowMenu.add(prefsItem);
		
		menuBar.add(fileMenu);
		menuBar.add(windowMenu);

		fileMenu.setMnemonic(KeyEvent.VK_F);
		exitItem.setMnemonic(KeyEvent.VK_X);

		prefsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				ActionEvent.CTRL_MASK));
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.CTRL_MASK));
		importDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
				ActionEvent.CTRL_MASK));
		exportDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));

		importDataItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

					try {
						FileInputStream fileStream = new FileInputStream(
								fileChooser.getSelectedFile());

						ObjectInputStream os = new ObjectInputStream(fileStream);
						FormEvent readEmployee = (FormEvent) os.readObject();

					} catch (Exception exp) {
						exp.printStackTrace();
					}

				}
			}
		});
	}

}
