package ir.mahan.train.view;

import java.io.Serializable;

public class FormEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fName;
	private String lName;
	private String categoty;
	private String age;
	private String gender;
	private String city;
	private String sport;
	private boolean isEmployee;
	private String salary;
	private int ID;
	private static int count = 0;

	public FormEvent(String fName, String lName, String category, String age,
			String gender, String city, String Sport, String salary) {

		this.setfName(fName);
		this.setlName(lName);
		this.setCategoty(category);
		this.setAge(age);
		this.setGender(gender);
		this.setCity(city);
		this.setSport(Sport);
		this.setSalary(salary);
		this.generateNewID();

	}

	public FormEvent(int id, String fName, String lName, String category,
			String age, String gender, String city, String Sport, String salary) {

		this.setCounter(id);
		this.setfName(fName);
		this.setlName(lName);
		this.setCategoty(category);
		this.setAge(age);
		this.setGender(gender);
		this.setCity(city);
		this.setSport(Sport);
		this.setSalary(salary);

	}

	private void generateNewID() {
		FormEvent.count++;
		setID();
	}

	public int getID() {
		return this.ID;
	}

	public void setID() {
		this.ID = FormEvent.count;
	}

	public void setCounter(int id) {
		count = id;
		setID();
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getCategoty() {
		return categoty;
	}

	public void setCategoty(String categoty) {
		this.categoty = categoty;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	public boolean isEmployee() {
		return isEmployee;
	}

	public void setEmployee(boolean isEmployee) {
		this.isEmployee = isEmployee;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public boolean getIsEmployee() {
		return isEmployee;
	}

}
