package ir.mahan.train.Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import java.util.Properties;

public class DataBase {

	private Connection connectionString;
	
	public void connect() throws Exception {

		if (connectionString != null) {
			return;
		}
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		Properties properties = new Properties();
		
		String propertiesFileName = "config.properties";
		
		InputStream inputStream;
		inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFileName);

		if (inputStream != null) {
			properties.load(inputStream);
		} else {
			throw new FileNotFoundException("property file '" + propertiesFileName + "' not found in the classpath.");
		}

		
		String server = properties.getProperty("server");
		String database = properties.getProperty("database");
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");
		
		String connectionURL = "jdbc:sqlserver://"+server+";user="+user+";password="+password+";database="+database+"";
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

	public List<Person> loadFromFile(File file) throws IOException, ClassNotFoundException {

		Person[] arrPerson = null;
		FileInputStream fileStream;
		ObjectInputStream os = null;

		fileStream = new FileInputStream(file);
		os = new ObjectInputStream(fileStream);
		arrPerson = (Person[]) os.readObject();

		os.close();

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

		String updateQuery = "UPDATE [G3].[Masoumeh_Hamideh] "
				+ "SET [FirstName] = ? ,[LastName] = ?, [Gender] = ? ,[Age] = ? ,[Category] = ? ,[City] = ? ,[Sport] = ? ,[IsEmployee] = ? ,[Salary] = ? "
				+ "where [ID] = ?";

		PreparedStatement preparedStatement = connectionString.prepareStatement(updateQuery);
		
		preparedStatement.setString(1, person.getfName());
		preparedStatement.setString(2, person.getlName());
		preparedStatement.setString(3, person.getGender());
		preparedStatement.setString(4, person.getAge());
		preparedStatement.setString(5, person.getCategoty());
		preparedStatement.setString(6, person.getCity());
		preparedStatement.setString(7, person.getSport());
		preparedStatement.setBoolean(8, person.getIsEmployee());
		preparedStatement.setString(9, person.getSalary());
		preparedStatement.setInt(10, person.getID());

		preparedStatement.executeUpdate();

	}

	private void insertPersontoDatabase(Person person) throws SQLException, Exception {

		String insertQuery = "INSERT INTO [G3].[Masoumeh_Hamideh] "
				+ "([ID], [FirstName] ,[LastName] ,[Gender] ,[Age] ,[Category] ,[City] ,[Sport] ,[IsEmployee] ,[Salary]) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement preparedStatement = connectionString.prepareStatement(insertQuery);

		preparedStatement.setInt(1, person.getID());
		preparedStatement.setString(2, person.getfName());
		preparedStatement.setString(3, person.getlName());
		preparedStatement.setString(4, person.getGender());
		preparedStatement.setString(5, person.getAge());
		preparedStatement.setString(6, person.getCategoty());
		preparedStatement.setString(7, person.getCity());
		preparedStatement.setString(8, person.getSport());
		preparedStatement.setBoolean(9, person.getIsEmployee());
		preparedStatement.setString(10, person.getSalary());
		
		preparedStatement.executeUpdate();
	}


	private boolean existThisPersonInDatabase(int id) throws Exception, SQLException {

		String sqlQuery = "select count(*) as count from [G3].[Masoumeh_Hamideh] where [ID] = ?";

		PreparedStatement preparedStatement = connectionString.prepareStatement(sqlQuery);
		
		preparedStatement.setInt(1, id);
		
		ResultSet resultSet = preparedStatement.executeQuery();

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

	public boolean userAuthentication(String username, String password) throws SQLException, Exception {

		String sqlQuery = "select count(*) as count from [G3].[UserLogin] where [UserName] = ? and [PassWord]= ?";

		PreparedStatement preparedStatement = connectionString.prepareStatement(sqlQuery);

		preparedStatement.setString(1, username);
		preparedStatement.setString(2, password);
		
		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			if (resultSet.getInt(1) > 0)
				return true;
		}

		return false;
	}

	public void deletePerson(int id) throws Exception, SQLException {

		if (existThisPersonInDatabase(id)) {

			String deleteQuery = "DELETE [G3].[Masoumeh_Hamideh]  where [ID] = ?";
			
			PreparedStatement preparedStatement = connectionString.prepareStatement(deleteQuery);

			preparedStatement.setInt(1, id);
			
			preparedStatement.executeUpdate();

		}

	}

}
