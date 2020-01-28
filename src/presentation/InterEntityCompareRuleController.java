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

public class InterEntityCompareRuleController {
	private String BusinessRuleType;
	private BusinessRuleType ruleType = new BusinessRuleType();
	IDUtil generateID = new IDUtil();
	
	private String chosenConstraint;
	private String constraint2 = "=";
	private String constraint3 = ">";
	private String constraint4 = "<";
	private String constraint5 = "!=";
	private String constraint6 = "=<";
	private String constraint7 = "=>";
	
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
		PostgresGetColumns postgresColumns = new PostgresGetColumns();
		columnNames1 = postgresColumns.getColumnsPostgresTargetDb(chooseTable1.getValue());
		for (Column column : columnNames1) {
			namesColumn1.add(column.getName());
		}
		chooseColumn1.setItems(namesColumn1);
    }

    @FXML
    void chooseTable2(ActionEvent event) throws SQLException {
		namesColumn2 = FXCollections.observableArrayList();
		PostgresGetColumns postgresColumns = new PostgresGetColumns();
		columnNames2 = postgresColumns.getColumnsPostgresTargetDb(chooseTable2.getValue());

		for (Column column : columnNames2) {
			namesColumn2.add(column.getName());
		}
		chooseColumn2.setItems(namesColumn2);
    }

    
    public void setConstraints() {
    	constraintTypes = FXCollections.observableArrayList();
    	constraintTypes.add(constraint2); constraintTypes.add(constraint3); 
    	constraintTypes.add(constraint4); constraintTypes.add(constraint5); 
    	constraintTypes.add(constraint6); constraintTypes.add(constraint7);
    	
    	chooseConstraint.setItems(constraintTypes);
    }
    
    public void initialize() throws IOException, SQLException {
    	setConstraints();
		namesTable = FXCollections.observableArrayList();
		PostgresGetTables postgresTables = new PostgresGetTables();
		tableNames = postgresTables.getTablesPostgresTargetDb();
		for (Table tabel : tableNames) {
			namesTable.add(tabel.getName());
		}
		chooseTable1.setItems(namesTable);
		chooseTable2.setItems(namesTable);
	}
    
    public void createInterEntityCompareRuleUI(String InterEntityCompareRule) throws IOException{
    	BusinessRuleType = InterEntityCompareRule;
    	Stage stage = new Stage();
		Pane mainWindow = FXMLLoader.load(Main.class.getResource("InterEntityCompareRule.fxml"));
		stage.setScene(new Scene(mainWindow));
		stage.show();
		
    }
    
    @FXML
    void generateRule(ActionEvent event) throws IOException, SQLException {
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
		businessBuilder.addValue(chosenConstraint, "Operator", (int) generateID.getNextId());
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
    
	private void sendRule(Message message) throws IOException {
		ClientClass client = new ClientClass();
		client.sendBusinessRule(message);
	}
    

}
