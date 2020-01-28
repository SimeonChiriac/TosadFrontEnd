package service;

import java.util.List;

import domain.Column;
import domain.Table;
import persistence.tool.column.ColumnDao;
import persistence.tool.column.postgres.ColumnPostgresDaoImpl;

public class ColumnService {
	
	ColumnDao cpdi = new ColumnPostgresDaoImpl();
	
	public void saveColumns(List<Column> deColumns) {
		
		for (Column c : deColumns) {
			if (!cpdi.findByName(c.getName())) {
				cpdi.save(c);
			}else {
				System.out.println("Column al bekend in de database");
			}
		}
	}
	
}
