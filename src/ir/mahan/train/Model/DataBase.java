package ir.mahan.train.Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataBase {

	Connection connectionString;

	public void connect() throws Exception {

		if (connectionString != null) {
			return;
		}
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		String connectionURL = "jdbc:sqlserver://swsql.mahanair.aero;user=sa;password=123;database=JavaTraining";
		connectionString = DriverManager.getConnection(connectionURL);

	}

	public void disconnect() throws Exception {

		if (connectionString != null) {
			connectionString.close();
		}

	}

	public void saveToFile(File file, List<Person> personList)
			throws IOException {

		FileOutputStream fileStream = new FileOutputStream(file);
		ObjectOutputStream os = new ObjectOutputStream(fileStream);

		Person[] personArray = personList
				.toArray(new Person[personList.size()]);

		os.writeObject(personArray);
		os.close();

	}

	public List<Person> loadFromFile(File file) throws IOException {

		Person[] arrPerson = null;
		FileInputStream fileStream;
		ObjectInputStream os = null;

		try {

			fileStream = new FileInputStream(file);
			os = new ObjectInputStream(fileStream);
			arrPerson = (Person[]) os.readObject();

		} catch (Exception exp) {
			exp.setStackTrace(null);
		}

		finally {

			if (os != null)
				os.close();
		}

		return Arrays.asList(arrPerson);
	}

	public List<Person> loadFromDB() throws SQLException, Exception {

		List<Person> listPersonFromDB = new ArrayList<Person>();

		listPersonFromDB = selectPersonFromDatabase();

		return listPersonFromDB;

	}

	private List<Person> selectPersonFromDatabase() throws Exception,
			SQLException {

		List<Person> listPersonFromDB = new ArrayList<Person>();
		Person personFromDB = null;

		String selectQuery = "Select * from [G3].[Masoumeh_Hamideh]  order by id ";

		PreparedStatement preparedStatementforSelectQuery = connectionString
				.prepareStatement(selectQuery);

		ResultSet resultSet = preparedStatementforSelectQuery.executeQuery();

		while (resultSet.next()) {

			personFromDB = new Person();

			personFromDB.setfName(resultSet.getString("FirstName"));
			personFromDB.setlName(resultSet.getString("LastName"));
			personFromDB.setGender(resultSet.getString("Gender"));
			personFromDB.setAge(resultSet.getString("Age"));
			personFromDB.setCategoty(resultSet.getString("Category"));
			personFromDB.setCity(resultSet.getString("City"));
			personFromDB.setSport(resultSet.getString("Sport"));
			personFromDB.setIsEmployee(resultSet.getBoolean("IsEmployee"));
			personFromDB.setSalary(resultSet.getString("Salary"));
			personFromDB.setID(resultSet.getInt("ID"));

			listPersonFromDB.add(personFromDB);
		}

		return listPersonFromDB;

	}

	public void saveToDB(Person person) throws SQLException, Exception {

		if (existThisPersonInDatabase(person.getID())) {

			updatePersoninDatabase(person);

		} else {

			insertPersontoDatabase(person);

		}

	}

	private void updatePersoninDatabase(Person person) throws SQLException {

		String updateQuery = createUpdatePersonQuery(person);

		PreparedStatement preparedStatementforUpdateQuery = connectionString
				.prepareStatement(updateQuery);

		preparedStatementforUpdateQuery.executeUpdate();

	}

	private void insertPersontoDatabase(Person person) throws SQLException,
			Exception {

		String insertQuery = createInsertPersonQuery(person);

		PreparedStatement preparedStatementforInsertQuery = connectionString
				.prepareStatement(insertQuery);

		preparedStatementforInsertQuery.executeUpdate();
	}

	private String createUpdatePersonQuery(Person person) {

		String updateQuery = "UPDATE [G3].[Masoumeh_Hamideh]"
				+ " SET [FirstName] = '" + person.getfName()
				+ "' ,[LastName] = '" + person.getlName() + "' ,[Gender] = '"
				+ person.getGender() + "' ,[Age] = '" + person.getAge()
				+ "' ,[Category] = '" + person.getCategoty() + "' ,[City] = '"
				+ person.getCity() + "' ,[Sport] = '" + person.getSport()
				+ "' ,[IsEmployee] = '" + person.getIsEmployee()
				+ "' ,[Salary] = '" + person.getSalary() + "' where [ID] = "
				+ person.getID();

		return updateQuery;
	}

	private String createInsertPersonQuery(Person person) throws SQLException,
			Exception {

		String insertQuery = "INSERT INTO [G3].[Masoumeh_Hamideh] "
				+ "([ID], [FirstName] ,[LastName] ,[Gender] ,[Age] ,[Category] ,[City] ,[Sport] ,[IsEmployee] ,[Salary]) "
				+ "VALUES(" + person.getID() + ", '" + person.getfName()
				+ "', '" + person.getlName() + "', '" + person.getGender()
				+ "', '" + person.getAge() + "', '" + person.getCategoty()
				+ "', '" + person.getCity() + "', '" + person.getSport()
				+ "', '" + String.valueOf(person.getIsEmployee()) + "', '"
				+ person.getSalary() + "' )";

		return insertQuery;
	}

	private boolean existThisPersonInDatabase(int id) throws Exception,
			SQLException {

		String sqlQuery = "select count(*) as count from [G3].[Masoumeh_Hamideh] where [ID] = "
				+ id;

		PreparedStatement preparedStatementforSelectQuery = connectionString
				.prepareStatement(sqlQuery);

		ResultSet resultSet = preparedStatementforSelectQuery.executeQuery();

		if (resultSet.next()) {
			int count = resultSet.getInt("count");

			if (count != 0)
				return true;
		}

		return false;
	}

	public int getIDFromDB() throws SQLException, Exception {

		String sqlQuery = "select max(ID) from [G3].[Masoumeh_Hamideh]";
		int ID = 0;

		Statement stm = connectionString.createStatement();

		ResultSet resultSet = stm.executeQuery(sqlQuery);

		if (resultSet.next()) {
			ID = resultSet.getInt(1);
		}

		return ++ID;

	}

	public boolean userAuthentication(String username, String password)
			throws SQLException, Exception {

		String sqlQuery = "select count(*) as count from [G3].[UserLogin] where [UserName] = '"
				+ username + "' and " + "[PassWord]= '" + password + "'";

		PreparedStatement preparedStatementforSelectQuery = connectionString
				.prepareStatement(sqlQuery);

		ResultSet resultSet = preparedStatementforSelectQuery.executeQuery();

		if (resultSet.next()) {
			if (resultSet.getInt(1) > 0)
				return true;
		}

		return false;
	}

	public void deletePerson(int id) throws Exception, SQLException {

		if (existThisPersonInDatabase(id)) {

			String deleteQuery = createDeletePersonQuery(id);

			PreparedStatement preparedStatementforUpdateQuery = connectionString
					.prepareStatement(deleteQuery);

			preparedStatementforUpdateQuery.executeUpdate();

		}

	}

	private String createDeletePersonQuery(int id) {

		String deleteQuery = "DELETE [G3].[Masoumeh_Hamideh]  where [ID] = "
				+ id;

		return deleteQuery;
	}

}
