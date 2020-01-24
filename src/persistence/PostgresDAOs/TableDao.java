package persistence.PostgresDAOs;

import java.util.List;

import domain.Table;

public interface TableDao {
	
	public List<Table> findByBusinessRuleID(int businessRuleID);
	
	public boolean save(Table t);
	
	public boolean update(Table t);
	
	public boolean delete(Table t);

}
