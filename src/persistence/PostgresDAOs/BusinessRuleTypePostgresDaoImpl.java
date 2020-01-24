package persistence.PostgresDAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.BusinessRuleType;

public class BusinessRuleTypePostgresDaoImpl implements BusinessRuleTypeDao {

	private static Connection conn = PostgresBaseDao.getConnection();
	private CategoryDao cpdi = new CategoryPostgresDaoImpl();

	@Override
	public BusinessRuleType findByCode(String code) {
		BusinessRuleType b = new BusinessRuleType();

		try {
			String strQuery = "SELECT * FROM BusinessRuleType CODE = ?";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setString(0, code);
			ResultSet rs = pstmt.executeQuery(strQuery);

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
