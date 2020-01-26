package presentation;
import java.io.IOException;
import java.net.UnknownHostException;
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
import persistence.PostgresGetColumnNamesTargetDb;
import service.ClientClass;
import service.PostgresGetColumns;
import service.PostgresGetTables;

public class TupleCompareRuleController {
	
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
    private ComboBox<String> chooseColumn1;

    @FXML
    private ComboBox<String> chooseConstraint;

    @FXML
    private ComboBox<String> chooseColumn2;

    @FXML
    void generateRule(ActionEvent event) throws UnknownHostException, IOException {
		String newBusinessRuleType = "TCR";
//		BusinessRule bRule = new BusinessRule();
//		bRule.setTableName1(chooseTable.getValue());
//		bRule.setFirstColumn(chooseColumn1.getValue());
//		bRule.setSecondColumn(chooseColumn2.getValue());
//		bRule.setOperator(chosenConstraint);
//		sendRule(bRule);

    }
    
    @FXML
    void selectConstraint(ActionEvent event) {
    	 chosenConstraint = chooseConstraint.getValue();
    }
    
    @FXML
    void selectTable(ActionEvent event) throws SQLException {
    	namesColumn = FXCollections.observableArrayList();
		PostgresGetColumns postgresColumns = new PostgresGetColumns();
		columnNames = postgresColumns.getColumnsPostgresTargetDb(chooseTable.getValue());
		for (Column column : columnNames) {
			namesColumn.add(column.getName());
		}
		
		chooseColumn1.setItems(namesColumn);
		chooseColumn2.setItems(namesColumn);
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
    
    
    public void createTupleCompareRuleUI(String TupleCompareRule) throws IOException{
    	BusinessRuleType = TupleCompareRule;
    	Stage stage = new Stage();
		Pane mainWindow = FXMLLoader.load(Main.class.getResource("TupleCompareRule.fxml"));
		stage.setScene(new Scene(mainWindow));
		stage.show();
		
    }
    
//	private void sendRule(BusinessRule bRule) throws UnknownHostException, IOException {
//		ClientClass client = new ClientClass();
//		client.sendBusinessRule(bRule);
//	}

}
