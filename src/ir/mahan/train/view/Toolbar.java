package ir.mahan.train.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class Toolbar extends JToolBar implements ActionListener {

	private JButton saveBtn;
	private JButton refreshBtn;
	private ToolbarListener toolbarListener;

	public Toolbar() {

		setBorder(BorderFactory.createEtchedBorder());
		setFloatable(false);

		setSaveButton();
		setRefreshButton();
		addButtonstoToolbar();

	}

	private void addButtonstoToolbar() {
		add(saveBtn);
		addSeparator();
		add(refreshBtn);
	}

	private void setRefreshButton() {
		
		refreshBtn = new JButton();
		refreshBtn.setIcon(Utils.createIcon("/Images/Refresh16.gif"));
		refreshBtn.setToolTipText("Refresh data");
		refreshBtn.addActionListener(this);
		
	}

	private void setSaveButton() {

		saveBtn = new JButton();
		saveBtn.setIcon(Utils.createIcon("/Images/Save16.gif"));
		saveBtn.setToolTipText("Save to Database");
		saveBtn.addActionListener(this);

	}

	public void setToolbarListener(ToolbarListener toolbarListener) {

		this.toolbarListener = toolbarListener;

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JButton clicked = (JButton) e.getSource();

		if (clicked == saveBtn) {

			if (this.toolbarListener != null) {
				toolbarListener.saveEventOccured();
			}

		} else {

			if (this.toolbarListener != null)
				toolbarListener.refreshOccured();

		}

	}

}
