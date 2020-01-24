package persistence;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.Table;

public class PostgresGetTableNamesTargetDb {

	static ArrayList<Table> getNamesTable = null;

	public static ArrayList<Table> getTableNames() throws SQLException {
		if (getNamesTable == null) {
			getNamesTable = new ArrayList<Table>();
			Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/kledingWinkel", "postgres",
					"S()nt5LogE");
			Statement st = con.createStatement();

			DatabaseMetaData dbmd = con.getMetaData();
			String[] types = { "TABLE" };
			ResultSet rs = dbmd.getTables(null, null, "%", types);
			while (rs.next()) {

				Table newTable = new Table();
				newTable.setName(rs.getString("TABLE_NAME"));
				getNamesTable.add(newTable);
				// System.out.println(rs.getString("TABLE_NAME"));
				// System.out.println(tableNames);

			}
		}
		return getNamesTable;

	}
}
