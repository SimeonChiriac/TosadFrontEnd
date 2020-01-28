package persistence.tool.businessRuleType.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.BusinessRuleType;
import persistence.tool.businessRuleType.BusinessRuleTypeDao;
import persistence.tool.category.CategoryDao;
import persistence.tool.category.postgres.CategoryPostgresDaoImpl;
import persistence.tool.connection.postgres.PostgresBaseDao;

public class BusinessRuleTypePostgresDaoImpl implements BusinessRuleTypeDao {

	private static Connection conn = PostgresBaseDao.getConnection();
	private CategoryDao cpdi = new CategoryPostgresDaoImpl();

	@Override
	public BusinessRuleType findByCode(String code) {
		BusinessRuleType b = new BusinessRuleType();

		try {
			String strQuery = "SELECT * FROM BusinessRuleType WHERE CODE = ?";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setString(1, code);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				b.setCategory(cpdi.findByCode(rs.getString("CATEGORY_CODE")));
				b.setDescription(rs.getString("DESCRIPTION"));
				b.setCode(rs.getString("CODE"));
				b.setName(rs.getString("NAME"));

			}
		} catch (SQLException sqle) {

		}

		return b;
	}

}
