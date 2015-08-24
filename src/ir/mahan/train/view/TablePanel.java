package ir.mahan.train.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class TablePanel extends JPanel {

	private JTable table;
	private PersonTableModel personTableModel;
	private JPopupMenu popMenue;
	private PersonTableListener personTableListener;

	public TablePanel() {

		Dimension dim = getPreferredSize();
		dim.width = 250;
		setPreferredSize(dim);
		setMinimumSize(dim);

		addComponent();

		setLayout(new BorderLayout());
		add(new JScrollPane(table), BorderLayout.CENTER);

	}

	private void addComponent() {

		personTableModel = new PersonTableModel();
		table = new JTable(personTableModel);
		popMenue = new JPopupMenu();

		addSaveItem();
		addRemoveItem();
		addRefreshItem();

		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

				int row = table.rowAtPoint(e.getPoint());
				table.getSelectionModel().setSelectionInterval(row, row);

				if (e.getButton() == MouseEvent.BUTTON3) {
					popMenue.show(table, e.getX(), e.getY());
				}

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {

			}
		});
	}

	private void addRemoveItem() {

		JMenuItem removeItem = new JMenuItem("Delete");
		popMenue.add(removeItem);

		removeItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				int row = table.getSelectedRow();

				if (personTableListener != null) {

					personTableListener.deleteRow(row);
					refresh();
				}

			}
		});
	}

	private void addRefreshItem() {

		JMenuItem refreshItem = new JMenuItem("Refresh");
		popMenue.add(refreshItem);

		refreshItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				personTableListener.refreshTable();

			}
		});
	}

	private void addSaveItem() {

		JMenuItem saveItem = new JMenuItem("Save");
		popMenue.add(saveItem);

		saveItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				int row = table.getSelectedRow();

				if (personTableListener != null) {

					personTableListener.saveRow(row);
					refresh();
				}

			}
		});
	}

	public void addDataToTable(List<FormEvent> formEventList) {

		personTableModel.fillFormEventTable(formEventList);

	}

	public void addDataToTable(FormEvent formEvent) {

		personTableModel.fillFormEventTable(formEvent);

	}

	public void refresh() {

		personTableModel.fireTableDataChanged();

	}

	public void tableRemoveAll() {
		personTableModel.clearFormEventTable();
	}

	public void addPersonTableListener(PersonTableListener personTableListener) {
		this.personTableListener = personTableListener;

	}

	public void addFormEventListToTableAndRefresh(List<FormEvent> formEventList) {

		this.tableRemoveAll();
		this.addDataToTable(formEventList);
		this.refresh();

	}

}
