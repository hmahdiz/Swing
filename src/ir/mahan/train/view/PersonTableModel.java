package ir.mahan.train.view;

import java.util.*;

import javax.swing.table.AbstractTableModel;

public class PersonTableModel extends AbstractTableModel {

	private List<FormEvent> formEventTable = new ArrayList<FormEvent>();

	private String[] colName = { "ID", "First Name", "Last Name", "Category",
			"Age", "Gender", "City", "Sport", "Salary" };

	public PersonTableModel(){

	}
	
	public void fillFormEventTable(List<FormEvent> formEventList) {
		this.formEventTable.addAll(formEventList);
	}

	public void fillFormEventTable(FormEvent formEvent) {
		this.formEventTable.add(formEvent);
	}

	public void clearFormEventTable() {
		this.formEventTable.clear();
	}

	@Override
	public int getColumnCount() {
		return colName.length;
	}

	@Override
	public int getRowCount() {
		return formEventTable.size();
	}

	@Override
	public Object getValueAt(int row, int col) {

		FormEvent person = formEventTable.get(row);

		switch (col) {
		case 0:
			return person.getID();
		case 1:
			return person.getfName();
		case 2:
			return person.getlName();
		case 3:
			return person.getCategoty();
		case 4:
			return person.getAge();
		case 5:
			return person.getGender();
		case 6:
			return person.getCity();
		case 7:
			return person.getSport();
		case 8:
			return person.getSalary();

		}

		return null;
	}

	@Override
	public String getColumnName(int column) {
		return colName[column];
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		switch (col) {
		case 1:
		case 2:
		case 4:
			return true;

		default:
			return false;
		}
	}

	@Override
	public void setValueAt(Object value, int row, int col) {

		if (formEventTable == null) {
			return;
		}
		FormEvent p = (FormEvent) formEventTable.get(row);

		switch (col) {
		case 1:
			p.setfName(value.toString());
			break;

		case 2:
			p.setlName(value.toString());
			break;

		case 4:
			p.setAge(value.toString());
			break;

		case 5:
			p.setGender(value.toString());
			break;

		default:
			return;
		}

	}

}
