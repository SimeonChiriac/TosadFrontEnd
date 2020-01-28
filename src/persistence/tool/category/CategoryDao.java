package persistence.tool.category;

import domain.Category;

public interface CategoryDao {
	
	public Category findByCode(String code);

}
