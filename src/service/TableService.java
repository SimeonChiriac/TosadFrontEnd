package service;

import java.util.List;

import domain.Table;
import persistence.tool.table.TableDao;
import persistence.tool.table.postgres.TablePostgresDaoImpl;

public class TableService {

	TableDao tpdi = new TablePostgresDaoImpl();

	public void saveTables(List<Table> deTables) {
		for (Table t : deTables) {
			if (!tpdi.findByName(t.getName())) {
				tpdi.save(t);
			}else{
				System.out.println("Table al bekend in de database");
			}
		}
	}

}
