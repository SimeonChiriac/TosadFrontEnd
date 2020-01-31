package presentation;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.BusinessRule;
import domain.BusinessRuleType;
import domain.Column;
import domain.Message;
import domain.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import service.BusinessRuleBuilderImpl;
import service.ClientClass;
import service.ColumnService;
import service.IDUtil;
import service.OracleGetColumns;
import service.OracleGetTables;
import service.PostgresInsertBusinessRule;
import service.TableService;
import service.WindowController;

public class AttributeListRuleController {

	private String trigger = "trigger";
	private String constraint = "constraint";

	IDUtil generateID = new IDUtil();
	private BusinessRuleType ruleType = new BusinessRuleType();
	private ObservableList<String> triggerOrConstraint = FXCollections.observableArrayList(trigger, constraint);
	private ObservableList<String> setValuesTargetDb = FXCollections.observableArrayList();


	private TableService tableService = new TableService();
	private ColumnService columnService = new ColumnService();

	private ArrayList<Table> tables = new ArrayList<Table>();

	private ArrayList<Column> columns = new ArrayList<Column>();
	
	private String constraint2 = "=";
	private String constraint3 = ">";
	private String constraint4 = "<";
	private String constraint5 = "!=";
	private String constraint6 = "=<";
	private String constraint7 = "=>";
	
	private Button deleteRuleButton;

	private ObservableList<String> constraintTypes;

	private String BusinessRuleType;

	ArrayList<Table> tableNames;
	private ObservableList<String> namesTable;

	ArrayList<Column> columnNames;
	private ObservableList<String> namesColumn;

	ArrayList<String> listOfValues = new ArrayList<String>();

	@FXML
	private ComboBox<String> chooseTable;

	@FXML
	private ComboBox<String> chooseColumn;

	@FXML
	private TextField enteredValue;

	@FXML
	private TextField ruleName;

	@FXML
	private ChoiceBox<String> chooseTriggerOrConstraint;

	@FXML
	private ChoiceBox<String> ruleOperator;
	
	public void setConstraints() {
		constraintTypes = FXCollections.observableArrayList();
		constraintTypes.add(constraint2);
		constraintTypes.add(constraint3);
		constraintTypes.add(constraint4);
		constraintTypes.add(constraint5);
		constraintTypes.add(constraint6);
		constraintTypes.add(constraint7);

		ruleOperator.setItems(constraintTypes);
	}

	public void initialize() throws IOException, SQLException {
		
		
		try {
			
			if (WindowController.getMessage().get(4).equals("trigger")
					|| WindowController.getMessage().get(4).equals("constraint")) {
				setValuesTargetDb = WindowController.getMessage();
				setValuesFromTarget(setValuesTargetDb);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		setConstraints();
		chooseTriggerOrConstraint.setItems(triggerOrConstraint);
		namesTable = FXCollections.observableArrayList();
		
		OracleGetTables oracleTables = new OracleGetTables();
		tableNames = oracleTables.getTablesOracleTargetDb();
		for (Table tabel : tableNames) {
			namesTable.add(tabel.getName());
		}
		chooseTable.setItems(namesTable);
	}

	public void createAttributeListRuleUI(String AttributeListRule) throws IOException {
		BusinessRuleType = AttributeListRule;
		Stage stage = new Stage();
		Pane mainWindow = FXMLLoader.load(Main.class.getResource("AttributeListRule.fxml"));
		if (WindowController.getDeleteRule()) {
			createDeleteButton();
			mainWindow.getChildren().add(deleteRuleButton);
		}
		stage.setScene(new Scene(mainWindow));
		stage.show();
	}
	
	private void createDeleteButton() {
		deleteRuleButton = new Button("Delete Rule");
		deleteRuleButton.setStyle("-fx-font-size:20;" + "-fx-text-fill: white;" + "-fx-background-color: #7d0000;");
		deleteRuleButton.setTranslateX(270);
		deleteRuleButton.setTranslateY(360);
		deleteRuleButton.setPadding(new Insets(10, 20, 10, 20));
	}

	@FXML
	void selectTable(ActionEvent event) throws SQLException {
		namesColumn = FXCollections.observableArrayList();
		
		OracleGetColumns oracleColumns = new OracleGetColumns();
		columnNames = oracleColumns.getColumnsOracleTargetDb(chooseTable.getValue());
		for (Column column : columnNames) {
			namesColumn.add(column.getName());
		}

		chooseColumn.setItems(namesColumn);
	}

	@FXML
	void addValue(ActionEvent event) throws IOException {
		listOfValues.add(enteredValue.getText());
	}

	private void checkForErrors() {
		WindowController.setSuccessAlertBox(true);

		if (chooseTable.getValue() == null || chooseColumn.getValue() == null || enteredValue.getText().isEmpty()) {
			WindowController.setSuccessAlertBox(false);
		}

		if (ruleName.getText().isEmpty() || ruleOperator.getValue() == null
				|| chooseTriggerOrConstraint.getValue() == null) {
			WindowController.setSuccessAlertBox(false);
		}

	}
	
	public void setValuesFromTarget(ObservableList<String> message) {

		ruleName.setText(message.get(0));
		chooseTable.setValue(message.get(1));
		chooseColumn.setValue(message.get(2));
		ruleOperator.setValue(message.get(3));
		chooseTriggerOrConstraint.setValue(message.get(4));
		enteredValue.setText(message.get(5));

	}

	@FXML
	void generateRule(ActionEvent event) throws UnknownHostException, IOException, SQLException {
		checkForErrors();
		
		if(WindowController.getSuccessAlertBox() == false) {
		    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
	          errorAlert.setContentText("you either haven't selected and/or filled in value or minValue > maxValue");
	          errorAlert.showAndWait();
		};
		
		if(WindowController.getSuccessAlertBox() == true) {
			
			
		ruleType.setCode("ALIS");

		Table table = new Table();
		table.setName(chooseTable.getValue());
		tables.add(table);

		Column column = new Column();
		column.setName(chooseColumn.getValue());
		columns.add(column);

		tableService.saveTables(tables);
		columnService.saveColumns(columns);

		BusinessRule bRule = new BusinessRule();

		BusinessRuleBuilderImpl businessBuilder = new BusinessRuleBuilderImpl();
		businessBuilder.addColumn(chooseColumn.getValue());
		businessBuilder.addTable(chooseTable.getValue());
		for (String value : listOfValues) {
			businessBuilder.addValue(value, "adb", (int) generateID.getNextId());
		}
		System.out.println("iuaffbhjaafbhasbhsadbhasdhb  " + ruleOperator.getValue());
		businessBuilder.addValue(ruleOperator.getValue(), "operator", (int) generateID.getNextId());
		businessBuilder.setConstraintOrTrigger(chooseTriggerOrConstraint.getValue());
		businessBuilder.setRuleType(ruleType);
		businessBuilder.setID((int) generateID.getNextId());
		businessBuilder.setRuleType(ruleType);
		businessBuilder.setNaam(ruleName.getText());

		System.out.println(ruleOperator.getValue());
		System.out.println(ruleName.getText());
		System.out.println(chooseTriggerOrConstraint.getValue());

		bRule = businessBuilder.createBusinessRule();

		PostgresInsertBusinessRule insert = new PostgresInsertBusinessRule();
		boolean insertCompleted = insert.insertBusinessRule(bRule);
		System.out.println(insertCompleted);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Message message = new Message();
		message.setBusinessRuleID(bRule.getID());
		message.setTypeOfSQL(bRule.getConstraintOrTrigger());

		sendRule(message);

		Alert confirmAlert = new Alert(Alert.AlertType.INFORMATION);
		confirmAlert.setTitle("Business Rule Generator");
		confirmAlert.setHeaderText("Succes!");
		confirmAlert.setContentText("Your business rule was generated and saved succesfully in the database");
		confirmAlert.showAndWait();
		
		}
	}

	private void sendRule(Message message) throws UnknownHostException, IOException {
		ClientClass client = new ClientClass();
		client.sendBusinessRule(message);
	}

}
