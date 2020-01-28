package persistence.tool.value;

import java.util.List;

import domain.Value;

public interface ValueDao {
	
	public List<Value> findByBusinessRuleID(int BusinessRuleID);
	
	public boolean save(Value v, int businessRuleID);
	
	public boolean update(Value v);
	
	public boolean delete(Value v);

}
