package service;

import java.sql.SQLException;

import domain.BusinessRule;
import persistence.PostgresDAOs.BusinessRuleDao;
import persistence.PostgresDAOs.BusinessRulePostgresDaoImpl;

public class PostgresInsertBusinessRule {
	
	public boolean insertBusinessRule(BusinessRule bRule) throws SQLException {
		BusinessRuleDao toolDb = new BusinessRulePostgresDaoImpl();
		return toolDb.save(bRule);
	}

}
