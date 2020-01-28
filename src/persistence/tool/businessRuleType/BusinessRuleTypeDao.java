package persistence.tool.businessRuleType;

import domain.BusinessRuleType;

public interface BusinessRuleTypeDao {
	
	public BusinessRuleType findByCode(String code);

}
