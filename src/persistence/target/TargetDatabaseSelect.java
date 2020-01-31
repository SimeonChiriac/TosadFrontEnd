package persistence.target;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.Column;
import domain.Table;

public class TargetDatabaseSelect {

	private static Connection conn = TargetDatabaseConnector.getInstance();

	public ArrayList<Table> findAllTables() {

		ArrayList<Table> deTables = new ArrayList<Table>();

		try {

			Statement stmt = conn.createStatement();
			String strQuery = "SELECT TABLE_NAME FROM DBA_TABLES WHERE OWNER = 'SIMEON'";
			ResultSet rs = stmt.executeQuery(strQuery);
			while (rs.next()) {
				Table t = new Table();
				t.setName(rs.getString("TABLE_NAME"));
				deTables.add(t);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return deTables;

	}

	public ArrayList<Column> findColumnByTable(String tableName) {

		ArrayList<Column> deColumns = new ArrayList<Column>();
		try {

			String strQuery = "SELECT column_name from all_tab_cols where table_name=?";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setString(1, tableName);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Column c = new Column();
				c.setName(rs.getString("COLUMN_NAME"));
				deColumns.add(c);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return deColumns;

	}

}
