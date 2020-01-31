package service;

import java.sql.SQLException;
import java.util.ArrayList;

import domain.Column;
import persistence.PostgresGetColumnNamesTargetDb;
import persistence.PostgresGetColumnNamesToolDb;
import persistence.target.TargetDatabaseSelect;

public class OracleGetColumns {
	
	public ArrayList<Column> getColumnsOracleTargetDb(String chosenTable) throws SQLException {		
		TargetDatabaseSelect oracleColumns = new TargetDatabaseSelect();
		return oracleColumns.findColumnByTable(chosenTable) ;
		
	}
	
	public ArrayList<Column> getColumnsPostgresToolDb(String chosenTable) throws SQLException {
		PostgresGetColumnNamesToolDb names = new PostgresGetColumnNamesToolDb();
		return names.getColumnNames(chosenTable);
	}

}
