package ir.mahan.train.Model;

import java.io.Serializable;

public class Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9178641744681530948L;

	// Instance Variables
	private int ID;
	private String fName;
	private String lName;
	private String categoty;
	private String age;
	private String gender;
	private String city;
	private String sport;
	private boolean isEmployee;
	private String salary;

	public Person() {

	}

	public Person(int id, String fName, String lName, String category,
			String age, String gender, String city, String sport,
			String salery, boolean isEmp) {

		this.setfName(fName);
		this.setlName(lName);
		this.setCategoty(category);
		this.setAge(age);
		this.setGender(gender);
		this.setCity(city);
		this.setSport(sport);
		this.setSalary(salery);
		this.setIsEmployee(isEmp);
		this.setID(id);

	}

	public void setIsEmployee(boolean isEmp) {
		this.isEmployee = isEmp;

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

	public int getID() {

		return this.ID;
	}

	public void setID(int id) {

		this.ID = id;
	}

	public boolean getIsEmployee() {
		return isEmployee;
	}

}
