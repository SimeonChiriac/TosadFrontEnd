package service;

import java.util.ArrayList;

import domain.BusinessRule;
import domain.BusinessRuleType;
import domain.Column;
import domain.Table;
import domain.Value;

public class BusinessRuleBuilderImpl implements BusinessRuleBuilder {

	private ArrayList<Value> values = new ArrayList<Value>();

	private ArrayList<Table> tableNames = new ArrayList<Table>();

	private ArrayList<Column> columnNames = new ArrayList<Column>();

	private int ID;
	private String naam;
	private BusinessRuleType ruleType;
	private String constraintOrTrigger;
	IDUtil idGenerator = new IDUtil();


	public void setID(int iD) {
		ID = iD;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public void setRuleType(BusinessRuleType ruleType) {
		this.ruleType = ruleType;
	}

	public void setConstraintOrTrigger(String constraintOrTrigger) {
		this.constraintOrTrigger = constraintOrTrigger;
	}

	public void addValue(String value, String valueType, int id) {
		Value newValue = new Value();
		newValue.setGiven(value);
		newValue.setDataType(valueType);
		newValue.setID(id);

		values.add(newValue);
	}

	public void addTable(String tableName) {
		Table newTable = new Table();
		newTable.setName(tableName);

		tableNames.add(newTable);
	}

	public void addColumn(String columnName) {
		Column newColumn = new Column();
		newColumn.setName(columnName);

		columnNames.add(newColumn);
	}

	private float generateID() {
		return idGenerator.getNextId();
	}
	
	public void addInterColumn(String columnName, String columnType) {
		Column newColumn = new Column();
		newColumn.setName(columnName);
		newColumn.setDataType(columnType);
		
		columnNames.add(newColumn);
	}

	@Override
	public BusinessRule createBusinessRule() {
		BusinessRule businessRule = new BusinessRule();
		businessRule.setDeTables(this.tableNames);
		businessRule.setDeColumns(this.columnNames);
		businessRule.setDeValues(this.values);
		businessRule.setID(ID);
		businessRule.setConstraintOrTrigger(constraintOrTrigger);
		businessRule.setTypeOfCode(constraintOrTrigger);
		businessRule.setNaam(naam);
		businessRule.setRuleType(ruleType);
		return businessRule;
	}
}
