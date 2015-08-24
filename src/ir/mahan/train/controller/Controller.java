package ir.mahan.train.controller;

import ir.mahan.train.Model.DataBase;
import ir.mahan.train.Model.Person;
import ir.mahan.train.Model.User;
import ir.mahan.train.view.FormEvent;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

	DataBase db;

	public Controller() {
		db = new DataBase();
	}

	public void connectToDatabase() throws Exception, SQLException {

		db.connect();
	}

	public void disconnectDatabase() throws Exception, SQLException {

		db.disconnect();
	}

	private List<FormEvent> convertToPersonListToFormEventList(
			List<Person> personList) {

		List<FormEvent> formEventList = new ArrayList<FormEvent>();

		for (Person fe : personList) {
			formEventList.add(convertFormPersonToFormEvent(fe));
		}

		return formEventList;
	}

	private List<Person> convertFormEventListToPersonList(
			List<FormEvent> formEventList) {

		List<Person> personList = new ArrayList<Person>();

		for (FormEvent fe : formEventList) {
			personList.add(convertFormEventtoPerson(fe));
		}

		return personList;
	}

	private Person convertFormEventtoPerson(FormEvent ev) {

		String firstName = ev.getfName();
		String lastName = ev.getlName();
		String category = ev.getCategoty();
		String age = ev.getAge();
		String gender = ev.getGender();
		String city = ev.getCity();
		String sport = ev.getSport();
		String salary = ev.getSalary();
		int id = ev.getID();
		boolean isEmp = ev.getIsEmployee();

		Person p = new Person(id, firstName, lastName, category, age, gender,
				city, sport, salary, isEmp);

		return p;
	}

	private FormEvent convertFormPersonToFormEvent(Person per) {

		String firstName = per.getfName();
		String lastName = per.getlName();
		String age = per.getAge();
		String category = per.getCategoty();
		String gender = per.getGender();
		String salary = per.getSalary();
		String city = per.getCity();
		String sport = per.getSport();
		int id = per.getID();

		FormEvent event = new FormEvent(id, firstName, lastName, category, age,
				gender, city, sport, salary);

		return event;
	}

	public boolean enableUserToLogin(User user) throws SQLException, Exception {

		return db.userAuthentication(user.getUsername(), user.getPassword());

	}

	public void saveToFile(File file, List<FormEvent> formEventList)
			throws IOException {

		List<Person> personList = convertFormEventListToPersonList(formEventList);

		db.saveToFile(file, personList);
	}

	public List<FormEvent> loadFromFile(File file) throws IOException {

		List<Person> personList = db.loadFromFile(file);
		List<FormEvent> formEventList = new ArrayList<FormEvent>();

		for (Person per : personList) {
			formEventList.add(convertFormPersonToFormEvent(per));
		}

		return formEventList;
	}

	public void saveToDB(List<FormEvent> formEventList) throws Exception,
			SQLException {

		List<Person> personList = convertFormEventListToPersonList(formEventList);

		for (Person person : personList) {
			db.saveToDB(person);
		}

	}

	public void saveToDB(FormEvent formEvent) throws Exception, SQLException {

		Person person = convertFormEventtoPerson(formEvent);
		db.saveToDB(person);

	}

	public List<FormEvent> loadFromDB() throws Exception, SQLException {

		List<FormEvent> formEventList = convertToPersonListToFormEventList(db
				.loadFromDB());

		return formEventList;

	}

	public void deletePerson(FormEvent formEvent) throws Exception,
			SQLException {

		db.deletePerson(formEvent.getID());

	}
}
