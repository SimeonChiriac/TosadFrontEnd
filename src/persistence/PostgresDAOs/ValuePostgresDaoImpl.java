package persistence.PostgresDAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Value;

public class ValuePostgresDaoImpl implements ValueDao {

	private static Connection conn = PostgresBaseDao.getConnection();

	@Override
	public List<Value> findByBusinessRuleID(int BusinessRuleID) {
		List<Value> deValues = new ArrayList<Value>();

		try {
			String strQuery = "SELECT * FROM VALUE WHERE BUSINESSRULE_ID = ?";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setInt(0, BusinessRuleID);
			ResultSet rs = pstmt.executeQuery(strQuery);

			while (rs.next()) {
				Value v = new Value();
				v.setID(rs.getInt("ID"));
				v.setGiven(rs.getString("GIVEN"));
				v.setDataType(rs.getString("DATATYPE"));
				deValues.add(v);

			}
		} catch (SQLException sqle) {

		}

		return deValues;
	}

	@Override
	public boolean save(Value v) {
		try {

			String strQuery = "INSERT INTO VALUE (ID, GIVEN, DATATYPE) VALUES(?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setInt(1, v.getID());
			pstmt.setString(2, v.getGiven());
			pstmt.setString(3, v.getDataType());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Value v) {
		try {

			String strQuery = "update VALUE SET ID = ?, GIVEN = ?, DATATYPE = ? WHERE ID = ?";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setInt(1, v.getID());
			pstmt.setString(2, v.getGiven());
			pstmt.setString(3, v.getDataType());
			pstmt.setInt(4, v.getID());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Value v) {
		try {

			String strQuery = "DELETE FROM VALUE WHERE ID = ?";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setInt(1, v.getID());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
	}

}
