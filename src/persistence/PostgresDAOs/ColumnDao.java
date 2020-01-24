package persistence.PostgresDAOs;

import java.util.List;

import domain.Column;

public interface ColumnDao {
	
	public List<Column> findByBusinessRuleID(int BusinessRuleID);
	
	public boolean save(Column c);
	
	public boolean update(Column c);
	
	public boolean delete(Column c);

}
