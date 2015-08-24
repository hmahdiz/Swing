package ir.mahan.train.view;

import ir.mahan.train.view.FormPanel.MyEnumCategory;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class FormPanel extends JPanel implements ActionListener {
	
	private JLabel firstnameLabel;
	private JLabel lastnameLabel;
	private JLabel categoryLabel;
	private JLabel ageLabel;
	private JLabel genderLabel;
	private JLabel cityLabel;
	private JLabel sportLabel;
	private JLabel salaryLabel;

	private JTextField firstnameTxt;
	private JTextField lastnameTxt;
	private JTextField salaryTxt;

	private JComboBox<MyEnumCategory> category;
	private JComboBox<String> gender;

	private JButton buttonSubmit;
	private JRadioButton age18;
	private JRadioButton age20To30;
	private JRadioButton agemore;

	private JCheckBox cityTehran;
	private JCheckBox cityKerman;
	private JCheckBox empTxt;

	private JList<String> sportList;

	private IFormListener iFormListener;
	private FormEvent formEvent;
	private ButtonGroup buttonGroupAge;

	public IFormListener setIFormListener(IFormListener iForm) {
		return this.iFormListener = iForm;
	}

	public FormPanel() {

		this.setLayout(new GridBagLayout());

		createComponent();

		empTxt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (empTxt.isSelected())
					salaryTxt.setEditable(true);
				else
					salaryTxt.setEditable(false);

			}
		});

		addComponent();
		setBorder();
		layoutComponent();

	}

	private void createComponent() {
		firstnameLabel = new JLabel("Name: ");
		firstnameTxt = new JTextField();

		lastnameLabel = new JLabel("Family: ");
		lastnameTxt = new JTextField();

		categoryLabel = new JLabel("Category: ");
		category = new JComboBox<MyEnumCategory>();

		age18 = new JRadioButton("18");
		age20To30 = new JRadioButton("20 to 30");
		agemore = new JRadioButton("more");

		gender = new JComboBox<String>();

		buttonSubmit = new JButton("Submit");

		ageLabel = new JLabel("Age: ");
		genderLabel = new JLabel("Gender: ");
		cityLabel = new JLabel("City: ");

		cityTehran = new JCheckBox("Tehran");
		cityKerman = new JCheckBox("Kerman");

		String[] myList = { "Football", "Vollyball", "Others" };
		sportList = new JList(myList);		
		sportList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sportList.setSelectedIndex(0);
		sportLabel = new JLabel("Favorite sport:");

		empTxt = new JCheckBox("Employee");

		salaryLabel = new JLabel("Salary:");
		salaryTxt = new JTextField();
		salaryTxt.setEditable(false);
	}

	private void layoutComponent() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.weightx = 1;
		gc.weighty = 0.1;

		// user label
		gc.gridx = 0;
		gc.gridy = 0;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_START;
		add(firstnameLabel, gc);

		// user textField
		gc.gridx = 1;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(firstnameTxt, gc);

		// Family Label
		gc.gridx = 0;
		gc.gridy++;
		gc.anchor = GridBagConstraints.LINE_END;
		add(lastnameLabel, gc);

		// Family textField
		gc.gridx++;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(lastnameTxt, gc);

		// Category Label
		gc.gridx = 0;
		gc.gridy++;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(categoryLabel, gc);

		// Category comboBox
		gc.gridx++;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(category, gc);

		// Age Label
		gc.gridx = 0;
		gc.gridy++;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(ageLabel, gc);

		// Age 18 radio Button
		gc.gridx++;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(age18, gc);

		// Age 20 to 30 radio Button
		gc.gridx++;
		gc.anchor = GridBagConstraints.CENTER;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(age20To30, gc);

		// Age more radio Button
		gc.gridx++;
		gc.anchor = GridBagConstraints.CENTER;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(agemore, gc);

		// Gender male Label
		gc.gridx = 0;
		gc.gridy++;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(genderLabel, gc);

		// Gender male radio Button
		gc.gridx++;
		gc.anchor = GridBagConstraints.CENTER;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(gender, gc);

		// City Label
		gc.gridx = 0;
		gc.gridy++;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(cityLabel, gc);

		// City Tehran checkBox
		gc.gridx++;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(cityTehran, gc);

		// City Kerman checkBox
		gc.gridx++;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(cityKerman, gc);

		// Sport Label
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(sportLabel, gc);

		// Sport List
		gc.gridx++;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(sportList, gc);

		// Employee Checkbox
		gc.gridy++;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(empTxt, gc);

		// Salary label
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(salaryLabel, gc);

		// Salary Txt
		gc.gridx++;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(salaryTxt, gc);

		// Submit Button
		gc.gridy++;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(buttonSubmit, gc);

	}

	public enum MyEnumCategory {
		Student, Teacher, Staff

	}

	public void addComponent() {

		this.add(firstnameLabel);
		this.add(firstnameTxt);

		this.add(lastnameLabel);
		this.add(lastnameTxt);
		lastnameTxt.setLocation(20, 30);

		this.add(buttonSubmit);
		buttonSubmit.addActionListener(this);
		this.add(category);

		buttonGroupAge = new ButtonGroup();
		buttonGroupAge.add(age18);
		buttonGroupAge.add(age20To30);
		buttonGroupAge.add(agemore);

		this.add(age18);
		this.add(age20To30);
		this.add(agemore);
		this.add(gender);
		this.add(cityTehran);
		this.add(cityKerman);
		this.add(sportList);
		this.add(empTxt);

		ComboBox();

	}

	public void setBorder() {

		setBorder(BorderFactory.createTitledBorder("User Form"));

	}

	public void ComboBox() {

		category.addItem(MyEnumCategory.Student);
		category.addItem(MyEnumCategory.Teacher);
		category.addItem(MyEnumCategory.Staff);

		gender.addItem("male");
		gender.addItem("female");
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		CreatformEvent();
	}

	private void CreatformEvent() {

		String firstName = firstnameTxt.getText();
		String lastName = lastnameTxt.getText();
		String age = this.ageAction();
		String category = this.categoryAction();
		String gender = this.genderAction();
		String salary = this.salaryAction();
		String city = this.cityAction();
		String sport = this.sportAction();

		formEvent = new FormEvent(firstName, lastName, category, age, gender,
				city, sport, salary);

		iFormListener.sendNewFormEventFromPanelToFrame(formEvent);

	}

	public String cityAction() {

		String city = "";
		if (cityTehran.isSelected() && !(cityKerman.isSelected()))
			city = cityTehran.getText();
		else if (cityKerman.isSelected() && !(cityTehran.isSelected()))
			city = cityKerman.getText();
		else if (cityKerman.isSelected() && cityKerman.isSelected())
			city = cityTehran.getText() + "," + cityKerman.getText();
		return city;
	}

	public String categoryAction() {
		String value = category.getSelectedItem().toString();
		return value;
	}

	public String genderAction() {
		String value = gender.getSelectedItem().toString();
		return value;
	}

	public String sportAction() {
		String value = sportList.getSelectedValue().toString();
		return value;
	}

	public String salaryAction() {
		String value = "";
		if (empTxt.isSelected())
			value = salaryTxt.getText();
		return value;
	}

	public String ageAction() {
		String value = "";
		if (age18.isSelected())
			value = age18.getText();
		if (age20To30.isSelected())
			value = age20To30.getText();
		if (agemore.isSelected())
			value = agemore.getText();
		return value;
	}
}
