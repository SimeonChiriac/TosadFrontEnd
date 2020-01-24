package presentation;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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
import service.PostgresGetTables;
import service.WindowController;

public class SearchExistingBusinessRulesController {
	
	private ArrayList<Table> tableNames;
	private ObservableList<String> namesTable;
	private ObservableList<String> businessRuleTypes;
	private String chosenTable;
	private String chosenType;
	private String chosenName;

    @FXML
    private ComboBox<String> chooseTable;

    @FXML
    private ComboBox<String> chooseRuleType;

    @FXML
    private TextField bRuleName;

    @FXML
    void chooseTable1(ActionEvent event) {
    	chosenTable = chooseTable.getValue();
    }

    @FXML
    void chooseTable2(ActionEvent event) {
    	chosenType = chooseRuleType.getValue();
    }

    @FXML
    void searchBRule(ActionEvent event) throws IOException, SQLException {
    	chosenName = bRuleName.getText();
    	if (chosenType == "Attribute Range Rule") {
    		WindowController.setDeleteRule(true);
    		AttributeRangeRuleController range = new AttributeRangeRuleController();
    		range.createAttributeRangeRuleUI(chosenType);
    	}

    }
    
    public void initialize() throws IOException, SQLException {
    	businessRuleTypes = WindowController.getMessage();
    	chooseRuleType.setItems(businessRuleTypes);
		namesTable = FXCollections.observableArrayList();
		PostgresGetTables postgresTables = new PostgresGetTables();
		tableNames = postgresTables.getTablesPostgresToolDb();
		for (Table tabel : tableNames) {
			namesTable.add(tabel.getName());
		}
		chooseTable.setItems(namesTable);
		
    }
    
    public void createUI() throws IOException, SQLException {
    	Stage stage = new Stage();
		Pane mainWindow = FXMLLoader.load(Main.class.getResource("SearchExistingBusinessRules.fxml"));
		stage.setScene(new Scene(mainWindow));
		stage.show();
    }
    


}
