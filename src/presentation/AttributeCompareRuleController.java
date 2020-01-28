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
import service.PostgresGetColumns;
import service.PostgresGetTables;
import service.PostgresInsertBusinessRule;
import service.TableService;
import service.WindowController;

public class AttributeCompareRuleController {

	private String BusinessRuleType;
	
	IDUtil generateID = new IDUtil();

	
	private BusinessRuleType ruleType = new BusinessRuleType();

	private TableService tableService = new TableService();
	private ColumnService columnService = new ColumnService();
	
	private ArrayList<Table> tables = new ArrayList<Table>();

	private ArrayList<Column> columns = new ArrayList<Column>();

	private String chosenConstraint;
	private String constraint2 = "=";
	private String constraint3 = ">";
	private String constraint4 = "<";
	private String constraint5 = "!=";
	private String constraint6 = "=<";
	private String constraint7 = "=>";

	private ObservableList<String> constraintTypes;

	ArrayList<Table> tableNames;
	private ObservableList<String> namesTable;

	ArrayList<Column> columnNames;
	private ObservableList<String> namesColumn;

	Button deleteRuleButton;
	boolean deleteButtonPressed;

	@FXML
	private ComboBox<String> chooseTable;

	@FXML
	private ComboBox<String> chooseColumn;

	@FXML
	private TextField enteredValue;

	@FXML
	private ComboBox<String> chooseOperator;

	@FXML
	void selectConstraint(ActionEvent event) {
		chosenConstraint = chooseOperator.getValue();
	}

	@FXML
	void selectColumn(ActionEvent event) {

	}

	@FXML
	private TextField ruleName;

	@FXML
	private ChoiceBox<String> chooseTriggerOrConstraint;
	

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

	private void createDeleteButton() {
		deleteRuleButton = new Button("Delete Rule");
		deleteRuleButton.setStyle("-fx-font-size:20;" + "-fx-text-fill: white;" + "-fx-background-color: #7d0000;");
		deleteRuleButton.setTranslateX(270);
		deleteRuleButton.setTranslateY(390);
		deleteRuleButton.setPadding(new Insets(10, 20, 10, 20));
	}

	public void setConstraints() {
		constraintTypes = FXCollections.observableArrayList();
		constraintTypes.add(constraint2);
		constraintTypes.add(constraint3);
		constraintTypes.add(constraint4);
		constraintTypes.add(constraint5);
		constraintTypes.add(constraint6);
		constraintTypes.add(constraint7);

		chooseOperator.setItems(constraintTypes);
	}

	public void initialize() throws IOException, SQLException {
		setConstraints();
		namesTable = FXCollections.observableArrayList();
		PostgresGetTables postgresTables = new PostgresGetTables();
		tableNames = postgresTables.getTablesPostgresTargetDb();
		for (Table tabel : tableNames) {
			namesTable.add(tabel.getName());
		}
		chooseTable.setItems(namesTable);
	}

	public void createAttributeCompareRuleUI(String AttributeCompareRule) throws IOException {
		BusinessRuleType = AttributeCompareRule;
		Stage stage = new Stage();
		Pane mainWindow = FXMLLoader.load(Main.class.getResource("AttributeCompareRule.fxml"));
		if (WindowController.getDeleteRule()) {
			createDeleteButton();
			mainWindow.getChildren().add(deleteRuleButton);
			stage.setScene(new Scene(mainWindow));
			stage.show();

		}
	}
		
		@FXML
		void generateRule(ActionEvent event) throws UnknownHostException, IOException, SQLException {
			ruleType.setCode("ACMP");
			
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
			businessBuilder.addValue(enteredValue.getText(), "maxValue", (int) generateID.getNextId());
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

		private void sendRule(Message message) throws UnknownHostException, IOException {
			ClientClass client = new ClientClass();
			client.sendBusinessRule(message);
		}


	
}
