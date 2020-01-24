package persistence.PostgresDAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Column;

public class ColumnPostgresDaoImpl implements ColumnDao{
	
	private static Connection conn = PostgresBaseDao.getConnection();

	@Override
	public List<Column> findByBusinessRuleID(int BusinessRuleID) {
		List<Column> deColumns = new ArrayList<Column>();

		try {
			String strQuery = "SELECT * FROM COLUMN WHERE BUSINESSRULE_ID = ?";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setInt(0, BusinessRuleID);
			ResultSet rs = pstmt.executeQuery(strQuery);

			while (rs.next()) {
				Column c = new Column();
				c.setName("NAME");
				deColumns.add(c);

			}
		} catch (SQLException sqle) {

		}

		return deColumns;
	}

	@Override
	public boolean save(Column c) {
		try {

			String strQuery = "INSERT INTO COLUMN (NAME) values(?)";
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

			String strQuery = "update COLUMN SET NAME = ? WHERE NAME = ?";
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
	public boolean delete(Column c) {
		try {

			String strQuery = "DELETE FROM COLUMN WHERE NAME = ?";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setString(1, c.getName());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
	}

}
