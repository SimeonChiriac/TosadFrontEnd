package persistence.tool.businessRule.postgres;

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
import persistence.tool.businessRule.BusinessRuleDao;
import persistence.tool.businessRuleType.BusinessRuleTypeDao;
import persistence.tool.businessRuleType.postgres.BusinessRuleTypePostgresDaoImpl;
import persistence.tool.column.ColumnDao;
import persistence.tool.column.postgres.ColumnPostgresDaoImpl;
import persistence.tool.columnKoppelBusinessRule.ColumnKoppelBusinessRuleDao;
import persistence.tool.columnKoppelBusinessRule.postgres.ColumnKoppelBusinessRulePostgresDaoImpl;
import persistence.tool.connection.postgres.PostgresBaseDao;
import persistence.tool.table.TableDao;
import persistence.tool.table.postgres.TablePostgresDaoImpl;
import persistence.tool.tableKoppelBusinessRule.TableKoppelBusinessRuleDao;
import persistence.tool.tableKoppelBusinessRule.postgres.TableKoppelBusinessRulePostgresDaoImpl;
import persistence.tool.value.ValueDao;
import persistence.tool.value.postgres.ValuePostgresDaoImpl;

public class BusinessRulePostgresDaoImpl implements BusinessRuleDao {

	private static Connection conn = PostgresBaseDao.getConnection();
	private BusinessRuleTypeDao brtpdi = new BusinessRuleTypePostgresDaoImpl();
	private ValueDao vpdi = new ValuePostgresDaoImpl();
	private ColumnDao cpdi = new ColumnPostgresDaoImpl();
	private TableDao tpdi = new TablePostgresDaoImpl();
	private ColumnKoppelBusinessRuleDao ckbrpdi = new ColumnKoppelBusinessRulePostgresDaoImpl();
	private TableKoppelBusinessRuleDao tkbrpdi = new TableKoppelBusinessRulePostgresDaoImpl();

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
				b.setNaam(rs.getString("NAAM"));
				b.setExample(rs.getString("EXAMPLE"));
				b.setConstraint(rs.getString("CONSTRAINT_CODE"));
				b.setTrigger(rs.getString("TRIGGER_CODE"));
				b.setTypeOfCode(rs.getString("TYPE_OF_CODE"));
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
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int businessRuleID = rs.getInt("ID");
				b.setDeValues(vpdi.findByBusinessRuleID(businessRuleID));
				b.setDeColumns(cpdi.findByBusinessRuleID(businessRuleID));
				b.setDeTables(tpdi.findByBusinessRuleID(businessRuleID));
				b.setNaam(rs.getString("NAAM"));
				b.setExample(rs.getString("EXAMPLE"));
				b.setConstraint(rs.getString("CONSTRAINT_CODE"));
				b.setTrigger(rs.getString("TRIGGER_CODE"));
				b.setTypeOfCode(rs.getString("TYPE_OF_CODE"));
				b.setID(businessRuleID);
				b.setRuleType(brtpdi.findByCode(rs.getString("BUSINESSRULETYPE_CODE")));

			}
			
			return b;
		} catch (SQLException sqle) {

		}

		return b;
	}

	@Override
	public boolean save(BusinessRule b) {
		try {

			String strQuery = "INSERT INTO BUSINESSRULE (ID, BUSINESSRULETYPE_CODE, NAAM, EXAMPLE, CONSTRAINT_CODE, TRIGGER_CODE, TYPE_OF_CODE) VALUES(?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setInt(1, b.getID());
			pstmt.setString(2, b.getRuleType().getCode());
			pstmt.setString(3, b.getNaam());
			pstmt.setString(4, b.getExample());
			pstmt.setString(5, b.getConstraint());
			pstmt.setString(6, b.getTrigger());
			pstmt.setString(7, b.getTypeOfCode());
			pstmt.executeUpdate();
			for (Value i : b.getDeValues()) {
				vpdi.save(i, b.getID());
			}
			for (Table i : b.getDeTables()) {
				tkbrpdi.save(b.getID(), i.getName());
			}
			for (Column i : b.getDeColumns()) {
				ckbrpdi.save(b.getID(), i.getName());
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

			String strQuery = "update BUSINESSRULE SET ID = ?, BUSINESSRULETYPE_CODE = ?, TYPE_OF_CODE = ?, EXAMPLE = ?, CONSTRAINT_CODE = ?, TRIGGER_CODE = ?, TYPE_OF_CODE = ? WHERE ID = ?";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setInt(1, b.getID());
			pstmt.setString(2, b.getRuleType().getCode());
			pstmt.setString(3, b.getTypeOfCode());
			pstmt.setString(4, b.getExample());
			pstmt.setString(5, b.getConstraint());
			pstmt.setString(6, b.getTrigger());
			pstmt.setString(7, b.getTypeOfCode());
			pstmt.setInt(8, b.getID());
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
			for (Table i : b.getDeTables()) {
				tkbrpdi.save(b.getID(), i.getName());
			}
			for (Column i : b.getDeColumns()) {
				ckbrpdi.save(b.getID(), i.getName());
			}
			return true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
	}

}
