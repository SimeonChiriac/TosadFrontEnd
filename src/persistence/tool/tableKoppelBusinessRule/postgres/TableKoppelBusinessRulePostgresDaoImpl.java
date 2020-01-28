package persistence.tool.tableKoppelBusinessRule.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import domain.Column;
import domain.Table;
import domain.Value;
import persistence.tool.connection.postgres.PostgresBaseDao;
import persistence.tool.table.TableDao;
import persistence.tool.table.postgres.TablePostgresDaoImpl;
import persistence.tool.tableKoppelBusinessRule.TableKoppelBusinessRuleDao;

public class TableKoppelBusinessRulePostgresDaoImpl implements TableKoppelBusinessRuleDao{
	
	private static Connection conn = PostgresBaseDao.getConnection();
	
	@Override
	public boolean save(int businessRuleID, String tableName) {
		try {

			String strQuery = "INSERT INTO BUSINESSRULEKOPPELTABLENAME (BUSINESSRULE_ID, TABLENAME_NAME) VALUES(?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setInt(1, businessRuleID);
			pstmt.setString(2, tableName);
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

			String strQuery = "DELETE FROM BUSINESSRULEKOPPELTABLENAME WHERE BUSINESSRULE_ID = ?";
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
