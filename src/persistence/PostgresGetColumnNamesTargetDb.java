package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.Column;

public class PostgresGetColumnNamesTargetDb {

	ArrayList<Column> getNamesColumn = null;

	public ArrayList<Column> getColumnNames(String chosenTable) throws SQLException {
		ResultSet rs = null;
		getNamesColumn = new ArrayList<Column>();
		Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/kledingWinkel", "postgres",
				"S()nt5LogE");
		Statement st = con.createStatement();

		System.out.println("postgresClass " + chosenTable);

		String sql = "select * from " + chosenTable;
		rs = st.executeQuery(sql);
		ResultSetMetaData metaData = rs.getMetaData();

		int rowCount = metaData.getColumnCount();

		for (int i = 0; i < rowCount; i++) {
			Column newColumn = new Column();
			newColumn.setName(metaData.getColumnName(i + 1));
			getNamesColumn.add(newColumn);
//                System.out.println(metaData.getColumnName(i + 1));
		}

		return getNamesColumn;

	}

}