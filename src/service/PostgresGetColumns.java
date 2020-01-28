package service;

import java.sql.SQLException;
import java.util.ArrayList;

import domain.Column;
import persistence.PostgresGetColumnNamesTargetDb;
import persistence.PostgresGetColumnNamesToolDb;


public class PostgresGetColumns {
	
	public ArrayList<Column> getColumnsPostgresTargetDb(String chosenTable) throws SQLException {		
		PostgresGetColumnNamesTargetDb names = new PostgresGetColumnNamesTargetDb();
		return names.getColumnNames(chosenTable) ;
		
	}
	
	public ArrayList<Column> getColumnsPostgresToolDb(String chosenTable) throws SQLException {
		PostgresGetColumnNamesToolDb names = new PostgresGetColumnNamesToolDb();
		return names.getColumnNames(chosenTable);
	}

}
