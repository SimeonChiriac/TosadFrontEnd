package persistence.tool.businessRule;

import java.util.List;

import domain.BusinessRule;

public interface BusinessRuleDao {
	
	public List<BusinessRule> findAll();
	
	public BusinessRule findById(int id);
	
	public boolean save(BusinessRule b);
	
	
	public boolean delete(BusinessRule b);

	BusinessRule findByName(String name);

	public void update(BusinessRule b);

}
