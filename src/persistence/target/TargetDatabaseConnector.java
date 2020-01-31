package persistence.target;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TargetDatabaseConnector {

	static Connection targetDatabaseConnection = null;

	public static Connection getInstance() {
		if (targetDatabaseConnection == null) {
			String DB_DRIV = "oracle.jdbc.driver.OracleDriver";
			String DB_URL = "jdbc:oracle:thin:@//ondora04.hu.nl:8521/EDUC24";
			String DB_USER = "Simeon";
			String DB_PASS = "simeon";

			try {
				Class.forName(DB_DRIV).newInstance();
				targetDatabaseConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
				System.out.println("Ik heb verbinding!!!");
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		return targetDatabaseConnection;
	}
}