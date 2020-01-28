package persistence.tool.tableKoppelBusinessRule;

import java.util.List;

import domain.Table;

public interface TableKoppelBusinessRuleDao {
	
	public boolean save(int businessRuleID, String tableName);
	
	boolean deleteByBusinessRuleID(int businessRuleID);

}
