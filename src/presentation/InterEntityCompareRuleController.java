package presentation;

import java.io.IOException;
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

public class InterEntityCompareRuleController {
	private String BusinessRuleType;
	private BusinessRuleType ruleType = new BusinessRuleType();
	private String trigger = "trigger";
	private String constraint = "constraint";
	private ObservableList<String> triggerOrConstraint = FXCollections.observableArrayList(trigger, constraint);
	private ObservableList<String> setValuesTargetDb = FXCollections.observableArrayList();


	IDUtil generateID = new IDUtil();

	private String chosenConstraint;
	private String constraint2 = "=";
	private String constraint3 = ">";
	private String constraint4 = "<";
	private String constraint5 = "!=";
	private String constraint6 = "=<";
	private String constraint7 = "=>";
	
	private Button deleteRuleButton;

	private TableService tableService = new TableService();
	private ColumnService columnService = new ColumnService();

	private ArrayList<Table> tables = new ArrayList<Table>();

	private ArrayList<Column> columns = new ArrayList<Column>();

	private ObservableList<String> constraintTypes;

	ArrayList<Table> tableNames;
	private ObservableList<String> namesTable;

	ArrayList<Column> columnNames1;
	private ObservableList<String> namesColumn1;

	ArrayList<Column> columnNames2;
	private ObservableList<String> namesColumn2;

	@FXML
	private ComboBox<String> chooseTable1;

	@FXML
	private ComboBox<String> chooseColumn1;

	@FXML
	private ComboBox<String> chooseConstraint;

	@FXML
	private ComboBox<String> chooseTable2;

	@FXML
	private ComboBox<String> chooseColumn2;

	@FXML
	private ChoiceBox<String> chooseTriggerOrConstraint;

	@FXML
	private TextField ruleName;

	@FXML
	void chooseConstraint(ActionEvent event) {
		chosenConstraint = chooseConstraint.getValue();
	}

	@FXML
	void chooseTable1(ActionEvent event) throws SQLException {
		namesColumn1 = FXCollections.observableArrayList();
		
		OracleGetColumns oracleColumns = new OracleGetColumns();
		columnNames1 = oracleColumns.getColumnsOracleTargetDb(chooseTable1.getValue());
		for (Column column : columnNames1) {
			namesColumn1.add(column.getName());
		}
		chooseColumn1.setItems(namesColumn1);
	}

	@FXML
	void chooseTable2(ActionEvent event) throws SQLException {
		namesColumn2 = FXCollections.observableArrayList();
		
		OracleGetColumns oracleColumns = new OracleGetColumns();
		columnNames2 = oracleColumns.getColumnsOracleTargetDb(chooseTable2.getValue());

		for (Column column : columnNames2) {
			namesColumn2.add(column.getName());
		}
		chooseColumn2.setItems(namesColumn2);
	}

	public void setConstraints() {
		constraintTypes = FXCollections.observableArrayList();
		constraintTypes.add(constraint2);
		constraintTypes.add(constraint3);
		constraintTypes.add(constraint4);
		constraintTypes.add(constraint5);
		constraintTypes.add(constraint6);
		constraintTypes.add(constraint7);

		chooseConstraint.setItems(constraintTypes);
	}

	public void initialize() throws IOException, SQLException {
		

		try {
			if (WindowController.getMessage().get(6).equals("trigger")
					|| WindowController.getMessage().get(6).equals("constraint")) {
				setValuesTargetDb = WindowController.getMessage();
				setValuesFromTarget(setValuesTargetDb);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		chooseTriggerOrConstraint.setItems(triggerOrConstraint);
		setConstraints();
		namesTable = FXCollections.observableArrayList();
		
		OracleGetTables oracleTables = new OracleGetTables();
		tableNames = oracleTables.getTablesOracleTargetDb();
		for (Table tabel : tableNames) {
			namesTable.add(tabel.getName());
		}
		chooseTable1.setItems(namesTable);
		chooseTable2.setItems(namesTable);
	}

	public void createInterEntityCompareRuleUI(String InterEntityCompareRule) throws IOException {
		BusinessRuleType = InterEntityCompareRule;
		Stage stage = new Stage();
		Pane mainWindow = FXMLLoader.load(Main.class.getResource("InterEntityCompareRule.fxml"));
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
		deleteRuleButton.setTranslateX(250);
		deleteRuleButton.setTranslateY(450);
		deleteRuleButton.setPadding(new Insets(10, 20, 10, 20));
	}

	private void checkForErrors() {
		WindowController.setSuccessAlertBox(true);


		if (chooseTable1.getValue() == null || chooseTable2.getValue() == null || chooseColumn1.getValue() == null
				|| chooseColumn2.getValue() == null) {
			WindowController.setSuccessAlertBox(false);
		}

		if (ruleName.getText().isEmpty() || chosenConstraint == null
				|| chooseTriggerOrConstraint.getValue() == null) {
			WindowController.setSuccessAlertBox(false);
		}

	}
	
	public void setValuesFromTarget(ObservableList<String> message) {

		ruleName.setText(message.get(0));
		chooseTable1.setValue(message.get(1));
		chooseTable2.setValue(message.get(2));
		chooseColumn1.setValue(message.get(3));
		chooseColumn2.setValue(message.get(4));
		chooseConstraint.setValue(message.get(5));
		chooseTriggerOrConstraint.setValue(message.get(6));

	}

	@FXML
	void generateRule(ActionEvent event) throws IOException, SQLException {
		checkForErrors();

		if(WindowController.getSuccessAlertBox() == false) {
		    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
	          errorAlert.setContentText("you either haven't selected and/or filled in a value");
	          errorAlert.showAndWait();
		};
		
		if(WindowController.getSuccessAlertBox() == true) {
			
			
		ruleType.setCode("ICMP");

		Table table1 = new Table();
		table1.setName(chooseTable1.getValue());
		tables.add(table1);

		Table table2 = new Table();
		table2.setName(chooseTable2.getValue());
		tables.add(table2);

		Column column1 = new Column();
		column1.setName(chooseColumn1.getValue());
		columns.add(column1);

		tableService.saveTables(tables);
		columnService.saveColumns(columns);

		BusinessRule bRule = new BusinessRule();

		BusinessRuleBuilderImpl businessBuilder = new BusinessRuleBuilderImpl();
		businessBuilder.addColumn(chooseColumn1.getValue());
		businessBuilder.addColumn(chooseColumn2.getValue());
		businessBuilder.addTable(chooseTable1.getValue());
		businessBuilder.addTable(chooseTable2.getValue());
		businessBuilder.addValue(chosenConstraint, "operator", (int) generateID.getNextId());
		businessBuilder.setConstraintOrTrigger(chooseTriggerOrConstraint.getValue());
		businessBuilder.setRuleType(ruleType);
		businessBuilder.setID((int) generateID.getNextId());
		businessBuilder.setNaam(ruleName.getText());

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

	private void sendRule(Message message) throws IOException {
		ClientClass client = new ClientClass();
		client.sendBusinessRule(message);
	}

}
