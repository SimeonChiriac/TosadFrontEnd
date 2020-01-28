package presentation;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.BusinessRule;
import domain.BusinessRuleType;
import domain.Column;
import domain.Table;
import domain.Value;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import persistence.PostgresGetColumnNamesTargetDb;
import service.ClientClass;
import service.PostgresGetColumns;
import service.PostgresGetTables;

public class AttributeOtherRuleController {

    private String BusinessRuleType;
    private String startWith = "Start with";
    private String endWith = "End with";
    private String trigger = "trigger";
    private String constraint = "constraint";

    private BusinessRuleType ruleType = new BusinessRuleType();

    private ObservableList<String> startOrEndWith = FXCollections.observableArrayList(startWith, endWith);

    private ObservableList<String> triggerOrConstraint = FXCollections.observableArrayList(trigger, constraint);

    ArrayList<Table> tableNames;
    private ObservableList<String> namesTable = FXCollections.observableArrayList("tabel1", "tabel2");

    ArrayList<Column> columnNames;
    private ObservableList<String> namesColumn = FXCollections.observableArrayList("column1", "colomn2");

    @FXML
    private ComboBox<String> chooseTable;

    @FXML
    private ComboBox<String> chooseColumn;

    @FXML
    private ChoiceBox<String> startOrEnd;

    @FXML
    private CheckBox checkInBetween;

    @FXML
    private CheckBox checkNotInBetween;

    @FXML
    private TextField value1;

    @FXML
    private TextField value2;

    @FXML
    private ChoiceBox<String> chooseTriggerOrConstraint;

    @FXML
    private Button generateRuleId;

    @FXML
    private TextField ruleNameId;

    public void initialize() throws IOException, SQLException {
        startOrEnd.setItems(startOrEndWith);
        startOrEnd.setValue("Start with");
        chooseTriggerOrConstraint.setItems(triggerOrConstraint);
        chooseTriggerOrConstraint.setValue("constraint");

//        PostgresGetTables postgresTables = new PostgresGetTables();
//        tableNames = postgresTables.getTablesPostgresTargetDb();
//        for (Table tabel : tableNames) {
//            namesTable.add(tabel.getName());
//        }
        chooseTable.setItems(namesTable);
    }

    public void createAttributeOtherRuleUI(String AttributeOtherRule) throws IOException{
        BusinessRuleType = AttributeOtherRule;
        Stage stage = new Stage();
        Pane mainWindow = FXMLLoader.load(Main.class.getResource("AttributeOtherRule.fxml"));
        stage.setScene(new Scene(mainWindow));
        stage.show();
    }

    @FXML
    public void between (ActionEvent event) throws IOException, SQLException{
        if(checkInBetween.isSelected()){
            checkNotInBetween.setDisable(true);
            value2.setDisable(false);
        }
        else if(checkNotInBetween.isSelected()) {
            checkInBetween.setDisable(true);
            value2.setDisable(false);
        }
        else {
            checkInBetween.setDisable(false);
            checkNotInBetween.setDisable(false);
            value2.setDisable(true);
            value2.clear();
        }
    }

    @FXML
    public void addColumns (ActionEvent event) throws IOException, SQLException {
        if(chooseTable.getValue() != null) {
//            PostgresGetColumns postgresColumns = new PostgresGetColumns();
//            columnNames = postgresColumns.getColumnsPostgresTargetDb(chooseTable.getValue());
//            for (Column column : columnNames) {
//                namesColumn.add(column.getName());
//            }
            chooseColumn.setItems(namesColumn);
        }
    }

    @FXML
    public void errorCheck (ActionEvent event) throws IOException, SQLException{
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        Alert confirmAlert = new Alert (Alert.AlertType.CONFIRMATION);
//        System.out.println(chooseTable.getValue());
//        System.out.println(startOrEnd.getValue());
        if(chooseTable.getValue() == null || chooseColumn.getValue() == null) {
            errorAlert.setHeaderText("No table and/or column selected");
            errorAlert.showAndWait();
        }
        else if(checkInBetween.isSelected() || checkNotInBetween.isSelected()) {
            if(value1.getText().isEmpty() || value2.getText().isEmpty()) {
                errorAlert.setHeaderText("No values entered.");
                errorAlert.showAndWait();
            }
            else {
                this.generateRule();
            }
        }
        else if(value1.getText().isEmpty()) {
            errorAlert.setHeaderText("fill in a value");
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
        System.out.println("in generate rule");
        ruleType.setCode("MODI");

        ArrayList<Value> values = new ArrayList<Value>();
        ArrayList<Table> tableNames = new ArrayList<Table>();
        ArrayList<Column> columnNames = new ArrayList<Column>();

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setHeaderText("rule is aangemaakt");
        confirmAlert.showAndWait();
    }
}
