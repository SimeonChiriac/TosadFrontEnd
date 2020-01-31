package service;

import java.sql.SQLException;

import domain.BusinessRule;
import persistence.tool.businessRule.BusinessRuleDao;
import persistence.tool.businessRule.postgres.BusinessRulePostgresDaoImpl;


public class PostgresInsertBusinessRule {
	
	public boolean insertBusinessRule(BusinessRule bRule) throws SQLException {
		BusinessRuleDao toolDb = new BusinessRulePostgresDaoImpl();
		return toolDb.save(bRule);
	}

	
}
