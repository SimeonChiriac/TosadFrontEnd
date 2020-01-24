package domain;

import java.util.ArrayList;
import java.util.List;

public class BusinessRule {

	private int ID;
	private String naam;
	private List<Value> deValues;
	private List<Table> deTables;
	private List<Column> deColumns;
	private String constraint;
	private String trigger;
	private BusinessRuleType ruleType;
	private String example;
	private String constraintOrTrigger;

	public BusinessRule() {
		deValues = new ArrayList<Value>();
		deTables = new ArrayList<Table>();
	}

	public int getID() {
		return ID;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public String getConstraint() {
		return constraint;
	}

	public void setConstraint(String constraint) {
		this.constraint = constraint;
	}

	public String getTrigger() {
		return trigger;
	}

	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public List<Column> getDeColumns() {
		return deColumns;
	}

	public void setDeColumns(List<Column> list) {
		this.deColumns = list;
	}

	public List<Value> getDeValues() {
		return deValues;
	}

	public void setDeValues(List<Value> list) {
		this.deValues = list;
	}

	public BusinessRuleType getRuleType() {
		return ruleType;
	}

	public void setRuleType(BusinessRuleType ruleType) {
		this.ruleType = ruleType;
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	public List<Table> getDeTables() {
		return deTables;
	}

	public void setDeTables(List<Table> list) {
		this.deTables = list;
	}

	public String getConstraintOrTrigger() {
		return constraintOrTrigger;
	}

	public void setConstraintOrTrigger(String constraintOrTrigger) {
		this.constraintOrTrigger = constraintOrTrigger;
	}

}
