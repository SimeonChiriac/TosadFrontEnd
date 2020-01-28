package persistence.tool.columnKoppelBusinessRule.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import persistence.tool.columnKoppelBusinessRule.ColumnKoppelBusinessRuleDao;
import persistence.tool.connection.postgres.PostgresBaseDao;

public class ColumnKoppelBusinessRulePostgresDaoImpl implements ColumnKoppelBusinessRuleDao{
	
	private static Connection conn = PostgresBaseDao.getConnection();

	@Override
	public boolean save(int businessRuleID, String columnName) {
		try {

			String strQuery = "INSERT INTO BUSINESSRULEKOPPELCOLUMNNAME (BUSINESSRULE_ID, COLUMNNAME_NAME) VALUES(?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setInt(1, businessRuleID);
			pstmt.setString(2, columnName);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteByBusinessRuleID(int businessRuleID) {
		try {

			String strQuery = "DELETE FROM BUSINESSRULEKOPPELCOLUMNNAME WHERE BUSINESSRULE_ID = ?";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setInt(1, businessRuleID);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
	}
	
	
	
	

}
