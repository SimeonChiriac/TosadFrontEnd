package presentation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.BusinessRuleType;
import domain.Column;
import domain.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import service.OracleGetColumns;
import service.OracleGetTables;


public class ModifyRuleController {

    private String BusinessRuleType;

    private String constraint2 = "=";
    private String constraint3 = ">";
    private String constraint4 = "<";
    private String constraint5 = "!=";
    private String constraint6 = "=<";
    private String constraint7 = "=>";

    private BusinessRuleType ruleType = new BusinessRuleType();

    private ObservableList<String> constraintTypes = FXCollections.observableArrayList("op1", "op2");

    ArrayList<Table> tableNames;
    private ObservableList<String> namesTable = FXCollections.observableArrayList("tabel1", "tabel2");

    ArrayList<Column> columnNames;
    private ObservableList<String> namesColumn = FXCollections.observableArrayList("column1", "column2");

    ArrayList<Table> tableRuleNames;
    private ObservableList<String> namesTableRule = FXCollections.observableArrayList("tablerule1", "tablerule2");

    ArrayList<Column> columnRuleNames;
    private ObservableList<String> namesColumnRule = FXCollections.observableArrayList("columnRule1", "columnRule2");


    @FXML
    private ChoiceBox<String> chooseConstraint;

    @FXML
    private ComboBox<String> chooseTable;

    @FXML
    private ComboBox<String> chooseColumn;

    @FXML
    private ComboBox<String> chooseTableRule;

    @FXML
    private ComboBox<String> chooseColumnRule;

    @FXML
    private TextField value;

    @FXML
    private TextField ruleNameId;


    public void createModifyRuleUI(String modifyRule) throws IOException{
        BusinessRuleType = modifyRule;
        Stage stage = new Stage();
        Pane mainWindow = FXMLLoader.load(Main.class.getResource("ModifyRule.fxml"));
        stage.setScene(new Scene(mainWindow));
        stage.show();
    }

    public void initialize() throws IOException, SQLException {
        setConstraints();
        chooseConstraint.setValue(constraint2);
		OracleGetTables oracleTables = new OracleGetTables();
		tableNames = oracleTables.getTablesOracleTargetDb();
		for (Table tabel : tableNames) {
			namesTable.add(tabel.getName());
		}
        chooseTable.setItems(namesTable);
        chooseTableRule.setItems(namesTableRule);
    }

    public void setConstraints() {
        constraintTypes = FXCollections.observableArrayList();
        constraintTypes.add(constraint2); constraintTypes.add(constraint3);
        constraintTypes.add(constraint4); constraintTypes.add(constraint5);
        constraintTypes.add(constraint6); constraintTypes.add(constraint7);

        chooseConstraint.setItems(constraintTypes);
    }

    @FXML
    public void addColumns (ActionEvent event) throws IOException, SQLException {
        if(chooseTable.getValue() != null) {
    		OracleGetColumns oracleColumns = new OracleGetColumns();
    		columnNames = oracleColumns.getColumnsOracleTargetDb(chooseTable.getValue());
    		for (Column column : columnNames) {
    			namesColumn.add(column.getName());
    		}

            chooseColumn.setItems(namesColumn);
        }
    }

    @FXML
    public void addColumnRule (ActionEvent event) throws IOException, SQLException {
        if(chooseTableRule.getValue() != null) {
    		OracleGetColumns oracleColumns = new OracleGetColumns();
    		columnNames = oracleColumns.getColumnsOracleTargetDb(chooseTable.getValue());
    		for (Column column : columnNames) {
    			namesColumn.add(column.getName());
    		}

            chooseColumnRule.setItems(namesColumnRule);
        }
    }

    @FXML
    public void errorCheck (ActionEvent event) throws IOException, SQLException{
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        Alert confirmAlert = new Alert (Alert.AlertType.CONFIRMATION);
        if(chooseTable.getValue() == null || chooseColumn.getValue() == null) {
            errorAlert.setHeaderText("No table and/or column selected");
            errorAlert.showAndWait();
        }
        else if(value.getText().isEmpty()) {
                errorAlert.setHeaderText("No value entered.");
                errorAlert.showAndWait();
            }
        else if(chooseTableRule.getValue() == null || chooseColumnRule.getValue() == null) {
            errorAlert.setHeaderText("No Modify table and/or Modify column selected");
            errorAlert.showAndWait();
        }
        else if(ruleNameId.getText().isEmpty()) {
            errorAlert.setHeaderText("fill in a rule name");
            errorAlert.showAndWait();
        }
        else {
            confirmAlert.setHeaderText("goed gegaan");
            confirmAlert.show();
            this.generateRule();
        }
    }

    public void generateRule() throws IOException, SQLException{
        ruleType.setCode("AOTH");

    }
}
