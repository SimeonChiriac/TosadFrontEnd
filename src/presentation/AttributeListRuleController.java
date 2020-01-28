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
import domain.Value;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import service.BusinessRuleBuilderImpl;
import service.ClientClass;
import service.ColumnService;
import service.IDUtil;
import service.PostgresGetColumns;
import service.PostgresGetTables;
import service.PostgresInsertBusinessRule;
import service.TableService;

public class AttributeListRuleController {

	IDUtil generateID = new IDUtil();
	private BusinessRuleType ruleType = new BusinessRuleType();

	
	private TableService tableService = new TableService();
	private ColumnService columnService = new ColumnService();
	
	private ArrayList<Table> tables = new ArrayList<Table>();

	private ArrayList<Column> columns = new ArrayList<Column>();

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

    
	
	
	public void initialize() throws IOException, SQLException {
		namesTable = FXCollections.observableArrayList();
		PostgresGetTables postgresTables = new PostgresGetTables();
		tableNames = postgresTables.getTablesPostgresTargetDb();
		for (Table tabel : tableNames) {
			namesTable.add(tabel.getName());
		}
		chooseTable.setItems(namesTable);
	}
	
	public void createAttributeListRuleUI(String AttributeListRule) throws IOException {
		BusinessRuleType = AttributeListRule;
		Stage stage = new Stage();
		Pane mainWindow = FXMLLoader.load(Main.class.getResource("AttributeListRule.fxml"));
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
	void addValue(ActionEvent event) throws IOException {
			listOfValues.add(enteredValue.getText());
	}
 	
	
	
	@FXML
	void generateRule(ActionEvent event) throws UnknownHostException, IOException, SQLException {
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
		for (String value: listOfValues) {
		businessBuilder.addValue(value, "adb", (int) generateID.getNextId());
		}
		businessBuilder.addValue(ruleOperator.getValue(), "Operator", (int) generateID.getNextId());
		businessBuilder.setConstraintOrTrigger(chooseTriggerOrConstraint.getValue());
		businessBuilder.setRuleType(ruleType);
		businessBuilder.setID((int) generateID.getNextId());
		businessBuilder.setNaam(BusinessRuleType);

		
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

	private void sendRule(Message message) throws UnknownHostException, IOException {
		ClientClass client = new ClientClass();
		client.sendBusinessRule(message);
	}

}
