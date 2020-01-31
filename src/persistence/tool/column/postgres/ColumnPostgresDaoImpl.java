package persistence.tool.column.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.Column;
import persistence.tool.column.ColumnDao;
import persistence.tool.connection.postgres.PostgresBaseDao;

public class ColumnPostgresDaoImpl implements ColumnDao {

	private static Connection conn = PostgresBaseDao.getConnection();

	@Override
	public List<Column> findByBusinessRuleID(int BusinessRuleID) {
		List<Column> deColumns = new ArrayList<Column>();

		try {
			String strQuery = "SELECT * FROM COLUMNNAME JOIN BUSINESSRULEKOPPELCOLUMNNAME AS BKC ON COLUMNNAME_NAME = NAME WHERE BKC.BUSINESSRULE_ID = ?";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setInt(1, BusinessRuleID);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Column c = new Column();
				c.setName(rs.getString("NAME"));
				deColumns.add(c);

			}
		} catch (SQLException sqle) {

		}

		return deColumns;
	}

	@Override
	public boolean save(Column c) {
		try {
			String strQuery = "INSERT INTO COLUMNNAME (NAME) values(?)";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setString(1, c.getName());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Column c) {
		try {

			String strQuery = "update COLUMNNAME SET NAME = ? WHERE NAME = ?";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setString(1, c.getName());
			pstmt.setString(2, c.getName());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean findByName(String name) {

		try {
			String strQuery = "SELECT * FROM COLUMNNAME WHERE NAME = ?";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				return true;

			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return false;
	}

}
