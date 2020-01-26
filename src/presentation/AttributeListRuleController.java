package presentation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.BusinessRule;
import domain.Column;
import domain.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import service.ClientClass;
import service.PostgresGetColumns;
import service.PostgresGetTables;

public class AttributeListRuleController {

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
	void generateRule(ActionEvent event) throws IOException {
		String newBusinessRuleType = "ALR";
//		BusinessRule bRule = new BusinessRule(chooseTable.getValue(), chooseColumn.getValue(),newBusinessRuleType);
//		//
//		sendRule(bRule);
	}
	
	@FXML
	void addValue(ActionEvent event) throws IOException {
			listOfValues.add(enteredValue.getText());
	}
 	
//	private void sendRule(BusinessRule bRule) throws IOException {
//		ClientClass client = new ClientClass();
//		client.sendBusinessRule(bRule);
//	}
}
