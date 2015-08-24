package ir.mahan.train.view;

import ir.mahan.train.controller.Controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

public class MainFrame extends JFrame {

	private Controller controller;

	private FormPanel formPanel;
	private TablePanel tablePanel;
	private TxtPanel txtPanel;

	private JMenuBar menuBar;
	private Toolbar toolbar;
	private JFileChooser fileChooser;

	private JSplitPane splitPane;
	private JTabbedPane tabbedPane;

	private List<FormEvent> formEventList;

	public MainFrame(Controller controller, String title) {

		super(title);

		this.setView();
		this.initComponent(controller);
		this.addComponenttoMainFrame();

		addComponentToTabbedPane();

		clickonSubmitButton();
		clickOnIconOfToolbarListener();

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {

				exitConfirmation();
			}
		});

	}

	private void connectToDB() {

		try {

			controller.connectToDatabase();

		} catch (SQLException e) {

			JOptionPane.showMessageDialog(formPanel, e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);

		} catch (Exception e) {

			JOptionPane.showMessageDialog(formPanel, e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	private void setView() {

		this.setSize(1000, 600);
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

	}

	private void initComponent(Controller ctrl) {

		formEventList = new ArrayList<FormEvent>();
		this.controller = ctrl;

		txtPanel = new TxtPanel();
		formPanel = new FormPanel();
		tablePanel = new TablePanel();

		toolbar = new Toolbar();

		tabbedPane = new JTabbedPane();

	}

	private void addComponenttoMainFrame() {

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel,
				tabbedPane);
		splitPane.setOneTouchExpandable(true);

		this.setJMenuBar(this.createMenuBar());
		this.add(splitPane, BorderLayout.CENTER);

		add(toolbar, BorderLayout.PAGE_START);

	}

	private void addComponentToTabbedPane() {

		tabbedPane.add("Person Panel", tablePanel);
		tabbedPane.add("Text Area", txtPanel);

		tablePanel.addPersonTableListener(new PersonTableListener() {

			@Override
			public void deleteRow(int row) {

				FormEvent formShouldBeDelete = formEventList.get(row);

				try {

					controller.deletePerson(formShouldBeDelete);

				} catch (SQLException e) {

					JOptionPane.showMessageDialog(
							formPanel,
							"Can not delete this record from DB: "
									+ e.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);

				} catch (Exception e) {

					JOptionPane.showMessageDialog(
							formPanel,
							"Can not delete this record from DB: "
									+ e.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);

				}

				formEventList.remove(row);

				tablePanel.addFormEventListToTableAndRefresh(formEventList);

			}

			@Override
			public void refreshTable() {

				try {

					refreshFormTable();

				} catch (SQLException e) {

					JOptionPane.showMessageDialog(formPanel, e.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);

				} catch (Exception e) {

					JOptionPane.showMessageDialog(formPanel, e.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);

				}
			}

			@Override
			public void saveRow(int row) {

				FormEvent formShouldBeSave = formEventList.get(row);

				try {

					controller.saveToDB(formShouldBeSave);

				} catch (SQLException e) {

					JOptionPane.showMessageDialog(formPanel,
							"Can not add this record to DB: " + e.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);

				} catch (Exception e) {
					JOptionPane.showMessageDialog(formPanel,
							"Can not add this record to DB: " + e.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
	}

	public JMenuBar createMenuBar() {

		fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.addChoosableFileFilter(new PersonFileFilter());

		menuBar = new JMenuBar();

		// Menus
		JMenu fileMenu = new JMenu("File");
		JMenu windowMenu = new JMenu("Window");

		// Add menus to menuBar
		menuBar.add(fileMenu);
		menuBar.add(windowMenu);

		// Create Sub menus for File Menu
		createExportMenuItem(fileMenu);
		createImportMenuItem(fileMenu);
		fileMenu.addSeparator();
		createExitMenuItem(fileMenu);

		// Create Sub menus for Window Menu
		createPreferencesMenuItem(windowMenu);
		createShowMenuItem(windowMenu);

		fileMenu.setMnemonic(KeyEvent.VK_F);

		return menuBar;

	}

	private void clickonSubmitButton() {

		formPanel.setIFormListener(new IFormListener() {

			@Override
			public void sendNewFormEventFromPanelToFrame(FormEvent formEvent) {

				formEventList.add(formEvent);

				tablePanel.addFormEventListToTableAndRefresh(formEventList);

			}

		});
	}

	private void createExportMenuItem(JMenu menu) {

		JMenuItem exportDataItem = new JMenuItem("Export Data ...");
		menu.add(exportDataItem);

		exportDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));

		exportDataItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

					try {
						controller.saveToFile(fileChooser.getSelectedFile(),
								formEventList);

					} catch (Exception e) {

						JOptionPane.showMessageDialog(formPanel,
								e.getMessage(), "Error",
								JOptionPane.ERROR_MESSAGE);

					}
				}
			}

		});

	}

	private void createImportMenuItem(JMenu menu) {
		JMenuItem importDataItem = new JMenuItem("Import Data ...");
		menu.add(importDataItem);
		importDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
				ActionEvent.CTRL_MASK));

		importDataItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

					try {

						List<FormEvent> loadedFormEventListFromFile = controller
								.loadFromFile(fileChooser.getSelectedFile());

						formEventList.clear();
						formEventList.addAll(loadedFormEventListFromFile);

						tablePanel
								.addFormEventListToTableAndRefresh(formEventList);

					} catch (IOException e) {

						JOptionPane.showMessageDialog(formPanel,
								e.getMessage(), "Error",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			}
		});

	}

	private void clickOnIconOfToolbarListener() {

		toolbar.setToolbarListener(new ToolbarListener() {

			@Override
			public void saveEventOccured() {
				try {

					controller.saveToDB(formEventList);

				} catch (SQLException e) {

					JOptionPane.showMessageDialog(formPanel, e.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);

				} catch (Exception e) {

					JOptionPane.showMessageDialog(formPanel, e.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);

				}

			}

			@Override
			public void refreshOccured() {

				try {

					refreshFormTable();

				} catch (SQLException e) {

					JOptionPane.showMessageDialog(formPanel, e.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);

				} catch (Exception e) {

					JOptionPane.showMessageDialog(formPanel, e.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);

				}

			}

		});
	}

	public void refreshFormTable() throws Exception, SQLException {
		List<FormEvent> loadedFormEventListFromDatabase = controller
				.loadFromDB();

		formEventList.clear();
		formEventList.addAll(loadedFormEventListFromDatabase);

		tablePanel.addFormEventListToTableAndRefresh(formEventList);
	}

	private void createShowMenuItem(JMenu windowMenu) {

		JMenu showMenu = new JMenu("Show");
		windowMenu.add(showMenu);

		JMenuItem showFormItem = new JMenuItem("Person Form");

		JCheckBoxMenuItem showFormCheckBoxItem = new JCheckBoxMenuItem(
				"Show the Form");
		showFormCheckBoxItem.setSelected(true);

		showMenu.add(showFormItem);
		showMenu.add(showFormCheckBoxItem);
	}

	private void createPreferencesMenuItem(JMenu menu) {

		JMenuItem prefsItem = new JMenuItem("Preferences");
		menu.add(prefsItem);
		prefsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				ActionEvent.CTRL_MASK));

	}

	private void createExitMenuItem(JMenu menu) {

		JMenuItem exitItem = new JMenuItem("Exit");
		menu.add(exitItem);

		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.CTRL_MASK));

		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				exitConfirmation();
			}

		});

	}

	public void exitConfirmation() {

		int action = JOptionPane.showConfirmDialog(null,
				"Are you sure you want to exit?", "User Confirmation",
				JOptionPane.YES_NO_OPTION);

		if (action == JOptionPane.OK_OPTION) {

			try {

				controller.disconnectDatabase();

			} catch (SQLException e) {

				JOptionPane.showMessageDialog(formPanel, e.getMessage(),
						"Error", JOptionPane.ERROR_MESSAGE);

			} catch (Exception e) {

				JOptionPane.showMessageDialog(formPanel, e.getMessage(),
						"Error", JOptionPane.ERROR_MESSAGE);

			}

			System.exit(0);

		}
	}

}
