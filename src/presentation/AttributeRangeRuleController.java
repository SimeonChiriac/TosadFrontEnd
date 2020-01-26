package presentation;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.BusinessRule;
import domain.Column;
import domain.Message;
import domain.Table;
import domain.Value;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import service.ClientClass;
import service.IDUtil;
import service.PostgresGetColumns;
import service.PostgresGetTables;
import service.PostgresInsertBusinessRule;
import service.WindowController;
import domain.BusinessRuleType;

public class AttributeRangeRuleController {
	
	IDUtil generateID = new IDUtil();
	private String BusinessRuleType = "Attribute Range Rule";
	private String between = "between";
	private String notBetween = "not between";
	private String trigger = "trigger";
	private String constraint = "constraint";
	
	private BusinessRuleType ruleType = new BusinessRuleType();

	ArrayList<Table> tableNames;
	private ObservableList<String> namesTable;

	ArrayList<Column> columnNames;
	private ObservableList<String> namesColumn;
	
    private ObservableList<String> operator = FXCollections.observableArrayList(between, notBetween);
    
    private ObservableList<String> triggerOrConstraint = FXCollections.observableArrayList(trigger, constraint);

    		
	@FXML
	private ComboBox<String> chooseTable;
	
	@FXML
	private ComboBox<String> chooseColumn;

	@FXML
	private TextField rangeFrom;

	@FXML
	private TextField rangeTo;
	
    @FXML
    private TextField ruleName;

    @FXML
    private ChoiceBox<String> ruleOperator;

    @FXML
    private ChoiceBox<String> chooseTriggerOrConstraint;
	
	Button deleteRuleButton;
	boolean deleteButtonPressed;



	public void initialize() throws IOException, SQLException {
		ruleOperator.setItems(operator);
		chooseTriggerOrConstraint.setItems(triggerOrConstraint);
		namesTable = FXCollections.observableArrayList();
		PostgresGetTables postgresTables = new PostgresGetTables();
		tableNames = postgresTables.getTablesPostgresTargetDb();
		for (Table tabel : tableNames) {
			namesTable.add(tabel.getName());
		}
		chooseTable.setItems(namesTable);
	}
	
	private void createDeleteButton() {
		deleteRuleButton = new Button("Delete Rule");
		deleteRuleButton.setStyle("-fx-font-size:20;" + "-fx-text-fill: white;" + "-fx-background-color: #7d0000;");
		deleteRuleButton.setTranslateX(490);
		deleteRuleButton.setTranslateY(280);
		deleteRuleButton.setPadding(new Insets(10, 20, 10, 20));
	}

	public void createAttributeRangeRuleUI(String AttributeRangeRule) throws IOException, SQLException {
		BusinessRuleType = AttributeRangeRule;
		Stage stage = new Stage();
		Pane mainWindow = FXMLLoader.load(Main.class.getResource("AttributeRangeRule.fxml"));
		if(WindowController.getDeleteRule()) {
			createDeleteButton();
			mainWindow.getChildren().add(deleteRuleButton);
		}		
		stage.setScene(new Scene(mainWindow));
		stage.show();

	}
	
	@FXML
	void selectTable(ActionEvent event) throws SQLException {
		namesColumn = FXCollections.observableArrayList();
		PostgresGetColumns postgresColumns = new PostgresGetColumns();
		columnNames = postgresColumns.getColumnsPostgresTargetDb(chooseTable.getValue());
		for (Column column : columnNames) {
			namesColumn.add(column.getName());
		}
		
		chooseColumn.setItems(namesColumn);
	}
 
	@FXML
	void generateRule(ActionEvent event) throws UnknownHostException, IOException, SQLException {
		ruleType.setCode("ARR");
		
		System.out.println(chooseTriggerOrConstraint.getValue());
		System.out.println(ruleOperator.getValue());
		System.out.println(ruleName.getText());
		
		ArrayList<Value> values = new ArrayList<Value>();
		
		ArrayList<Table> tableNames = new ArrayList<Table>();
		
		ArrayList<Column> columnNames = new ArrayList<Column>();
		
		Table table = new Table();
		table.setName(chooseTable.getValue());
		tableNames.add(table);
		
		Column column = new Column();
		column.setName(chooseColumn.getValue());
		columnNames.add(column);

		Value minValue = new Value();
		minValue.setID((int) generateID.getNextId());
		minValue.setGiven(rangeFrom.getText());
		minValue.setDataType("minValue");
		
		Value maxValue = new Value();
		maxValue.setID((int) generateID.getNextId());
		maxValue.setGiven(rangeTo.getText());
		maxValue.setDataType("maxValue");
		
		Value operator = new Value();
		operator.setID((int) generateID.getNextId());
		operator.setGiven(ruleOperator.getValue());
		operator.setDataType("Operator");
		
		values.add(minValue);
		values.add(maxValue);
		values.add(operator);
		


		BusinessRule bRule = new BusinessRule();
		
		
		bRule.setID((int) generateID.getNextId());
		bRule.setNaam(ruleName.getText());
		bRule.setConstraintOrTrigger(chooseTriggerOrConstraint.getValue());
		bRule.setRuleType(ruleType);
		bRule.setDeTables(tableNames);
		bRule.setDeColumns(columnNames);
		bRule.setDeValues(values);
		bRule.setConstraintOrTrigger("constraint");
		
		PostgresInsertBusinessRule insert = new PostgresInsertBusinessRule();
		boolean insertCompleted = insert.insertBusinessRule(bRule);
		System.out.println(insertCompleted);
		
		Message message = new Message();
		message.setBusinessRuleID(bRule.getID());
		message.setTypeOfSQL(bRule.getConstraintOrTrigger());
		
		sendRule(message);
	}
	
	
	private void sendRule(Message message) throws UnknownHostException, IOException {
		ClientClass client = new ClientClass();
		client.sendBusinessRule(message);
	}

}
