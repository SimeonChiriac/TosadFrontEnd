package persistence.PostgresDAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import domain.BusinessRule;
import domain.Column;
import domain.Table;
import domain.Value;

public class BusinessRulePostgresDaoImpl implements BusinessRuleDao {

	private static Connection conn = PostgresBaseDao.getConnection();
	private BusinessRuleTypeDao brtpdi = new BusinessRuleTypePostgresDaoImpl();
	private ValueDao vpdi = new ValuePostgresDaoImpl();
	private ColumnDao cpdi = new ColumnPostgresDaoImpl();
	private TableDao tpdi = new TablePostgresDaoImpl();

	@Override
	public List<BusinessRule> findAll() {

		try {

			Statement stmt = conn.createStatement();
			String strQuery = "SELECT * FROM KLAS";
			ResultSet rs = stmt.executeQuery(strQuery);
			while (rs.next()) {
				BusinessRule b = new BusinessRule();
				int businessRuleID = rs.getInt("ID");
				b.setDeValues(vpdi.findByBusinessRuleID(businessRuleID));
				b.setDeColumns(cpdi.findByBusinessRuleID(businessRuleID));
				b.setDeTables(tpdi.findByBusinessRuleID(businessRuleID));
				b.setExample(rs.getString("EXAMPLE"));
				b.setID(businessRuleID);
				b.setRuleType(brtpdi.findByCode(rs.getString("BUSINESSRULETYPE_CODE")));
//				b.setTypeOfConstraint(typeOfConstraint);
			}

		} catch (SQLException sqle) {

		}

		return null;
	}

	@Override
	public BusinessRule findById(int id) {
		BusinessRule b = new BusinessRule();

		try {
			String strQuery = "SELECT * FROM BUSINESSRULE WHERE ID = ?";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery(strQuery);

			while (rs.next()) {
				int businessRuleID = rs.getInt("ID");
				b.setDeValues(vpdi.findByBusinessRuleID(businessRuleID));
				b.setDeColumns(cpdi.findByBusinessRuleID(businessRuleID));
				b.setDeTables(tpdi.findByBusinessRuleID(businessRuleID));
				b.setExample(rs.getString("EXAMPLE"));
				b.setID(businessRuleID);
				b.setRuleType(brtpdi.findByCode(rs.getString("BUSINESSRULETYPE_CODE")));

			}
		} catch (SQLException sqle) {

		}

		return b;
	}

	@Override
	public boolean save(BusinessRule b) {
		try {

			String strQuery = "INSERT INTO BUSINESSRULE (ID, BUSINESSRULETYPE_CODE, EXAMPLE) VALUES(?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setInt(1, b.getID());
			pstmt.setString(2, b.getRuleType().getCode());
			pstmt.setString(3, b.getExample());

			pstmt.executeUpdate();
			for (Value i : b.getDeValues()) {
				vpdi.save(i);
			}
			
			for (Table i : b.getDeTables()) {
				tpdi.save(i);
			}
			for (Column i : b.getDeColumns()) {
				cpdi.save(i);
			}

			return true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(BusinessRule b) {
		try {

			String strQuery = "update BUSINESSRULE SET ID = ?, BUSINESSRULETYPE_CODE = ?, EXAMPLE = ? WHERE ID = ?";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setInt(1, b.getID());
			pstmt.setString(2, b.getRuleType().getCode());
			pstmt.setString(3, b.getExample());
			pstmt.setInt(4, b.getID());

			pstmt.executeUpdate();
			
			for (Value i : b.getDeValues()) {
				vpdi.update(i);
			}
			return true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(BusinessRule b) {
		try {

			String strQuery = "DELETE FROM BUSINESSRULE WHERE ID = ?";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setInt(1, b.getID());
			pstmt.executeUpdate();
			for (Value i : b.getDeValues()) {
				vpdi.delete(i);
			}
			return true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
	}

}
