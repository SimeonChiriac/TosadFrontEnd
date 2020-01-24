package persistence;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgresConnection {
	
	public void Connection() throws SQLException {
	Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/kledingWinkel", "postgres", "S()nt5LogE" );
	Statement st = con.createStatement();
	
	DatabaseMetaData dbmd = con.getMetaData();
    String[] types = {"TABLE"};
    ResultSet rs = dbmd.getTables(null, null, "%", types);
    while (rs.next()) {
        System.out.println(rs.getString("TABLE_NAME"));
    }
	}
}
	
//	public void test
//	
//	ResultSet rs = st.executeQuery("SELECT type FROM broeken");
//	
//	while (rs.next())
//	{
//	    System.out.println(rs.getString("type"));
//	}
//	rs.close();
//	st.close();
//
//}
