package presentation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.BusinessRule;
import domain.Table;
import domain.Value;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import persistence.tool.businessRule.BusinessRuleDao;
import persistence.tool.businessRule.postgres.BusinessRulePostgresDaoImpl;
import service.PostgresGetTables;
import service.WindowController;

public class SearchExistingBusinessRulesController {

	private ArrayList<Table> tableNames;
	private ObservableList<String> namesTable;
	private ObservableList<String> businessRuleTypes;

	private ArrayList<String> listOfValuesTarget = new ArrayList<String>();


	private String chosenTable;
	private String chosenType;
	private String chosenName;

	private String valueTarget;
	private String triggerOrConstraintTarget;
	private String tableName1Target;
	private String tableName2Target;
	private String columnName1Target;
	private String columnName2Target;
	private String bRuleNameTarget;
	private String bRuleTypeTarget;
	private String minValueTarget;
	private String maxValueTarget;
	private String operatorTarget;
	
	private ObservableList<String> attributeRangeRuleValuesTarget = FXCollections.observableArrayList();
	private ObservableList<String> attributeCompareRuleValuesTarget = FXCollections.observableArrayList();
	private ObservableList<String> attributeListRuleValuesTarget = FXCollections.observableArrayList();
	private ObservableList<String> tupleCompareRuleValuesTarget = FXCollections.observableArrayList();
	private ObservableList<String> interEntityCompareRuleValuesTarget = FXCollections.observableArrayList();



	@FXML
	private ComboBox<String> chooseTable;

	@FXML
	private ComboBox<String> chooseRuleType;

	@FXML
	private TextField bRuleName;

	@FXML
	private CheckBox selectTable;

	@FXML
	private CheckBox selectType;

	@FXML
	private CheckBox selectName;

	@FXML
	void chooseTable1(ActionEvent event) {
		chosenTable = chooseTable.getValue();
	}

	@FXML
	void chooseBusinessRuleType(ActionEvent event) {
		chosenType = chooseRuleType.getValue();
	}

	@FXML
	void searchNameClick(ActionEvent event) {

		if (selectType.isDisabled() == true || selectTable.isDisabled() == true) {
			chooseTable.setDisable(false);
			selectType.setDisable(false);
			selectTable.setDisable(false);
			chooseRuleType.setDisable(false);
		}

		else if (selectType.isDisabled() == false || selectTable.isDisabled() == false) {
			chooseTable.setDisable(true);
			selectType.setDisable(true);
			selectTable.setDisable(true);
			chooseRuleType.setDisable(true);
		}

	}

	@FXML
	void searchTableClick(ActionEvent event) {

		if (selectType.isDisabled() == true || selectName.isDisabled() == true) {
			chooseRuleType.setDisable(false);
			bRuleName.setDisable(false);
			selectType.setDisable(false);
			selectName.setDisable(false);
		}

		else if (selectType.isDisabled() == false || selectName.isDisabled() == false) {
			chooseRuleType.setDisable(true);
			bRuleName.setDisable(true);
			selectType.setDisable(true);
			selectName.setDisable(true);
		}

	}

	@FXML
	void searchTypeClick(ActionEvent event) {

		if (bRuleName.isDisabled() == true || chooseTable.isDisabled() == true) {
			bRuleName.setDisable(false);
			selectName.setDisable(false);
			chooseTable.setDisable(false);
		}

		else if (bRuleName.isDisabled() == false || chooseTable.isDisabled() == false) {
			bRuleName.setDisable(true);
			selectName.setDisable(true);
			chooseTable.setDisable(true);
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

	@FXML
	void searchBRule(ActionEvent event) throws IOException, SQLException {
		chosenName = bRuleName.getText();
		WindowController.setDeleteRule(true);
		
		BusinessRuleDao brpdi = new BusinessRulePostgresDaoImpl();
		BusinessRule bRule = brpdi.findByName(chosenName);
		
		tableName1Target = bRule.getDeTables().get(0).getName();

		if (bRule.getDeTables().size() > 1) {
			tableName2Target = bRule.getDeTables().get(1).getName();
		}

		columnName1Target = bRule.getDeColumns().get(0).getName();
		System.out.println(columnName1Target);

		if (bRule.getDeColumns().size() > 1) {
			columnName2Target = bRule.getDeColumns().get(1).getName();
		}

		bRuleNameTarget = bRule.getNaam();
		bRuleTypeTarget = bRule.getRuleType().getName();
		triggerOrConstraintTarget = bRule.getTypeOfCode();

		for (Value v : bRule.getDeValues()) {
			listOfValuesTarget.add(v.toString());
		}

		for (Value v : bRule.getDeValues()) {
			if (v.getDataType().equals("minValue")) {
				minValueTarget = v.getGiven();
			} else if (v.getDataType().equals("maxValue")) {
				maxValueTarget = v.getGiven();
			} else if (v.getDataType().equals("operator")) {
				operatorTarget = v.getGiven();
			} else if (v.getDataType().equals("value")) {
				valueTarget = v.getGiven();
			}
		}
		
		if (bRuleTypeTarget.equals("Attribute Range Rule")) {
			setAttributeRangeRuleMessage();
			AttributeRangeRuleController range = new AttributeRangeRuleController();
			range.createAttributeRangeRuleUI(bRuleTypeTarget);
		}

		if (bRuleTypeTarget.equals("Attribute List Rule")) {
			setAttributeListRuleMessage();
			AttributeListRuleController listController = new AttributeListRuleController();
			listController.createAttributeListRuleUI(bRuleTypeTarget);
		}

		if (bRuleTypeTarget.equals("Attribute Compare Rule")) {
			setAttributeCompareRuleMessage();
			AttributeCompareRuleController compare = new AttributeCompareRuleController();
			compare.createAttributeCompareRuleUI(bRuleTypeTarget);
		}

		if (bRuleTypeTarget.equals("Tuple Compare Rule")) {
			setTupleCompareRuleMessage();
			TupleCompareRuleController tuple = new TupleCompareRuleController();
			tuple.createTupleCompareRuleUI(bRuleTypeTarget);
		}

		if (bRuleTypeTarget.equals("Inter-Entity Compare Rule")) {
			setInterEntityCompareRuleMessage();
			InterEntityCompareRuleController inter = new InterEntityCompareRuleController();
			inter.createInterEntityCompareRuleUI(bRuleTypeTarget);
		}

	}
	
	private void setAttributeRangeRuleMessage() throws IOException, SQLException {
		attributeRangeRuleValuesTarget.add(bRuleNameTarget);
		attributeRangeRuleValuesTarget.add(tableName1Target);
		attributeRangeRuleValuesTarget.add(columnName1Target);
		attributeRangeRuleValuesTarget.add(minValueTarget);
		attributeRangeRuleValuesTarget.add(maxValueTarget);
		attributeRangeRuleValuesTarget.add(operatorTarget);
		attributeRangeRuleValuesTarget.add(triggerOrConstraintTarget);
		WindowController.setMessage(attributeRangeRuleValuesTarget);
	};
	
	private void setAttributeCompareRuleMessage() throws IOException, SQLException {
		attributeCompareRuleValuesTarget.add(bRuleNameTarget);
		attributeCompareRuleValuesTarget.add(tableName1Target);
		attributeCompareRuleValuesTarget.add(columnName1Target);
		attributeCompareRuleValuesTarget.add(operatorTarget);
		attributeCompareRuleValuesTarget.add(triggerOrConstraintTarget);
		attributeCompareRuleValuesTarget.add(valueTarget);
		WindowController.setMessage(attributeCompareRuleValuesTarget);
	};
	
	private void setAttributeListRuleMessage() throws IOException, SQLException {
		attributeListRuleValuesTarget.add(bRuleNameTarget);
		attributeListRuleValuesTarget.add(tableName1Target);
		attributeListRuleValuesTarget.add(columnName1Target);
		attributeListRuleValuesTarget.add(operatorTarget);
		attributeListRuleValuesTarget.add(triggerOrConstraintTarget);
		attributeListRuleValuesTarget.addAll(listOfValuesTarget.get(0));
		WindowController.setMessage(attributeListRuleValuesTarget);

	}
	
	private void setTupleCompareRuleMessage() throws IOException, SQLException {
		tupleCompareRuleValuesTarget.add(bRuleNameTarget);
		tupleCompareRuleValuesTarget.add(tableName1Target);
		tupleCompareRuleValuesTarget.add(columnName1Target);
		tupleCompareRuleValuesTarget.add(columnName2Target);
		tupleCompareRuleValuesTarget.add(operatorTarget);
		tupleCompareRuleValuesTarget.add(triggerOrConstraintTarget);
		WindowController.setMessage(tupleCompareRuleValuesTarget);
	}
	
	private void setInterEntityCompareRuleMessage() throws IOException, SQLException{
		interEntityCompareRuleValuesTarget.add(bRuleNameTarget);
		interEntityCompareRuleValuesTarget.add(tableName1Target);
		interEntityCompareRuleValuesTarget.add(tableName2Target);
		interEntityCompareRuleValuesTarget.add(columnName1Target);
		interEntityCompareRuleValuesTarget.add(columnName2Target);
		interEntityCompareRuleValuesTarget.add(operatorTarget);
		interEntityCompareRuleValuesTarget.add(triggerOrConstraintTarget);
		WindowController.setMessage(interEntityCompareRuleValuesTarget);
	}


}
