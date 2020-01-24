package persistence;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.BusinessRule;
import domain.Table;

public class PostgresInsertBusinessRuleToolDb {
	
	static BusinessRule businessRule = null;
	private static boolean insertCompleted;



	public static boolean insertBusinessRule(BusinessRule bRule) throws SQLException{

			if (businessRule == null) {
//				businessRule = new BusinessRule();
				Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Tool", "postgres",
						"S()nt5LogE");
				Statement st = con.createStatement();

				}
			
		
		return insertCompleted;
	}
	
	

}
