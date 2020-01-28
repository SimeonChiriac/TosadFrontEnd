package persistence.tool.columnKoppelBusinessRule;

public interface ColumnKoppelBusinessRuleDao {
	
	public boolean save(int businessRuleID, String columnName);
	
	public boolean deleteByBusinessRuleID(int businessRuleID);
}
