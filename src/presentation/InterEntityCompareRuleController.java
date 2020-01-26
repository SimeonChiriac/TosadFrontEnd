package presentation;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import service.ClientClass;
import service.PostgresGetColumns;
import service.PostgresGetTables;

public class InterEntityCompareRuleController {
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

    @FXML
    void generateRule(ActionEvent event) throws IOException {
    	String newBusinessRuleType = "IECR";
//		BusinessRule bRule = new BusinessRule(chooseTable1.getValue(), chooseTable2.getValue(), chooseColumn1.getValue(), chooseColumn2.getValue(), newBusinessRuleType);
//		bRule.setOperator(chosenConstraint);
//		sendRule(bRule);
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
    
//	private void sendRule(BusinessRule bRule) throws IOException {
//		ClientClass client = new ClientClass();
//		client.sendBusinessRule(bRule);
//	}
    

}
