package persistence.tool.table;

import java.util.List;

import domain.Table;

public interface TableDao {
	
	public List<Table> findByBusinessRuleID(int businessID);
	
	public boolean findByName(String name);
	
	public boolean save(Table t);
	
	public boolean update(Table t);
	
}
