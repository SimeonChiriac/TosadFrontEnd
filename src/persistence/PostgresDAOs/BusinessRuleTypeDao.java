package persistence.PostgresDAOs;

import domain.BusinessRuleType;

public interface BusinessRuleTypeDao {
	
	public BusinessRuleType findByCode(String code);

}
