package persistence.PostgresDAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.BusinessRuleType;
import domain.Category;

public class CategoryPostgresDaoImpl implements CategoryDao {

	private static Connection conn = PostgresBaseDao.getConnection();

	@Override
	public Category findByCode(String code) {
		Category c = new Category();

		try {
			String strQuery = "SELECT * FROM CATEGORY WHERE CODE = ?";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setString(0, code);
			ResultSet rs = pstmt.executeQuery(strQuery);

			while (rs.next()) {
				c.setName(rs.getString("NAME"));
				c.setCode(rs.getString("CODE"));

			}
		} catch (SQLException sqle) {

		}

		return c;
	}

}
