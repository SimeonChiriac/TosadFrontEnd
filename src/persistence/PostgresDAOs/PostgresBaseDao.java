package persistence.PostgresDAOs;

import java.sql.Connection;
import java.sql.DriverManager;




public class PostgresBaseDao {
	
	public static Connection getConnection() {
		
		Connection result = null;
		
	    try {
	        result = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Tool","postgres", "S()nt5LogE");
	      } catch (Exception ex) {
	        System.out.println(ex.getMessage());;
	      }

		return result;
	}

}
