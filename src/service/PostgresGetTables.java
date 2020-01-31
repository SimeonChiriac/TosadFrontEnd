package service;

import java.sql.SQLException;
import java.util.ArrayList;

import domain.Table;
import persistence.PostgresGetTableNamesTargetDb;
import persistence.PostgresGetTableNamesToolDb;

public class PostgresGetTables {

	public ArrayList<Table> getTablesPostgresTargetDb() throws SQLException {
		return PostgresGetTableNamesTargetDb.getTableNames();

	}

	public ArrayList<Table> getTablesPostgresToolDb() throws SQLException {
		return PostgresGetTableNamesToolDb.getTableNames();
	}

}
