package presentation;
import java.io.IOException;
import java.net.UnknownHostException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import persistence.PostgresGetColumnNamesTargetDb;
import service.ClientClass;
import service.PostgresGetColumns;
import service.PostgresGetTables;

public class AttributeCompareRuleController {
	
	private String BusinessRuleType;
	
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
	
    @FXML
    private ComboBox<String> chooseTable;

    @FXML
    private ComboBox<String> chooseColumn;

    @FXML
    private TextField enteredValue;

    @FXML
    private ComboBox<String> chooseConstraint;
    

    @FXML
    void selectConstraint(ActionEvent event) {
    	if(chooseConstraint.getValue() != null) {
			chosenConstraint = chooseConstraint.getValue();
			System.out.println(chooseConstraint.getValue());
		}
    }
    
//    @FXML
//    void selectColumn(ActionEvent event) {
//
//    }

    @FXML
    void generateRule(ActionEvent event) throws UnknownHostException, IOException {
		String newBusinessRuleType = "ACR";
//		BusinessRule bRule = new BusinessRule();
//		bRule.setTableName1(chooseTable.getValue());
//		bRule.setFirstColumn(chooseColumn.getValue());
//		bRule.setCompareRule(chosenConstraint);
//		bRule.setMaxValue(enteredValue.getText());
//		sendRule(bRule);

		Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
		confirmAlert.setHeaderText("rule is aangemaakt");
		confirmAlert.showAndWait();
    }


    @FXML
    void selectTable(ActionEvent event) throws SQLException {
		if(chooseTable.getValue() != null) {
			namesColumn = FXCollections.observableArrayList();
			PostgresGetColumns postgresColumns = new PostgresGetColumns();
			columnNames = postgresColumns.getColumnsPostgresTargetDb(chooseTable.getValue());
			for (Column column : columnNames) {
				namesColumn.add(column.getName());
			}
			chooseColumn.setItems(namesColumn);
		}

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
		chooseTable.setItems(namesTable);
	}
    
    public void createAttributeCompareRuleUI(String AttributeCompareRule) throws IOException{
    	BusinessRuleType = AttributeCompareRule;
    	Stage stage = new Stage();
		Pane mainWindow = FXMLLoader.load(Main.class.getResource("AttributeCompareRule.fxml"));
		stage.setScene(new Scene(mainWindow));
		stage.show();
    }
    
//	private void sendRule(BusinessRule bRule) throws UnknownHostException, IOException {
//		ClientClass client = new ClientClass();
//		client.sendBusinessRule(bRule);
//	}

}
