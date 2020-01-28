package persistence.tool.category.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.Category;
import persistence.tool.category.CategoryDao;
import persistence.tool.connection.postgres.PostgresBaseDao;

public class CategoryPostgresDaoImpl implements CategoryDao {

	private static Connection conn = PostgresBaseDao.getConnection();

	@Override
	public Category findByCode(String code) {
		Category c = new Category();

		try {
			String strQuery = "SELECT * FROM CATEGORY WHERE CODE = ?";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setString(0, code);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				c.setName(rs.getString("NAME"));
				c.setCode(rs.getString("CODE"));

			}
		} catch (SQLException sqle) {

		}

		return c;
	}

}
