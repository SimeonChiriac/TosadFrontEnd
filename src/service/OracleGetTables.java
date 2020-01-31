package service;

import java.sql.SQLException;
import java.util.ArrayList;

import domain.Table;
import persistence.target.TargetDatabaseSelect;

public class OracleGetTables {
	
	public ArrayList<Table> getTablesOracleTargetDb() throws SQLException {
		TargetDatabaseSelect oracleTables = new TargetDatabaseSelect();
		return oracleTables.findAllTables();
	}


}
