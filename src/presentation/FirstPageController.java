package presentation;
import java.io.IOException;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import service.WindowController;

public class FirstPageController {
	
	String AttributeRangeRule = "Attribute Range Rule";
	String AttributeCompareRule = "Attribute Compare Rule";
	String TupleCompareRule = "Tuple Compare Rule";
	String InterEntityCompareRule = "Inter-Entity Compare Rule";
	String AttributeListRule = "Attribute List Rule";
	String AttributeOtherRule = "Attribute Other Rule";
	String TupleOtherRule = "Tuple Other Rule";
	String ModifyRule = "Modify Rule";
	String EntityOtherRule = "Entity Other Rule";

    @FXML
    private ComboBox<String> chooseRuleType;
    
    private ObservableList<String> ruleTypes = FXCollections.observableArrayList(AttributeRangeRule, AttributeCompareRule, TupleCompareRule, 
    		InterEntityCompareRule, TupleCompareRule, TupleOtherRule, AttributeListRule, AttributeOtherRule, EntityOtherRule, ModifyRule);
    
    public void initialize(){chooseRuleType.setItems(ruleTypes);
    }

    @FXML
	public void errorCheck() throws IOException, SQLException{
    	if (this.chooseRuleType.getValue() == null) {
			Alert errorAlert = new Alert(Alert.AlertType.ERROR);
			errorAlert.setHeaderText("No businessRule selected");
			errorAlert.showAndWait();
		}
    	else {
    		this.toSelectedRule();
		}
	}
    @FXML
    void toSelectedRule() throws IOException, SQLException {
    	String ruleType = chooseRuleType.getValue();
    	if (ruleType.equals(AttributeRangeRule)) {
    		AttributeRangeRuleController rangeController = new AttributeRangeRuleController();
    		rangeController.createAttributeRangeRuleUI(AttributeRangeRule);
    	}
    	else if (ruleType.equals(AttributeCompareRule)) {
    		AttributeCompareRuleController compareController = new AttributeCompareRuleController();
    		compareController.createAttributeCompareRuleUI(AttributeCompareRule);
    	}
    	else if (ruleType.equals(TupleCompareRule)) {
    		TupleCompareRuleController tupleController = new TupleCompareRuleController();
    		tupleController.createTupleCompareRuleUI(TupleCompareRule);
    	}
    	else if (ruleType.equals(InterEntityCompareRule)) {
    		InterEntityCompareRuleController interCompareController = new InterEntityCompareRuleController();
    		interCompareController.createInterEntityCompareRuleUI(InterEntityCompareRule);
    	}
    	else if (ruleType.equals(AttributeListRule)) {
    		AttributeListRuleController listController = new AttributeListRuleController();
    		listController.createAttributeListRuleUI(AttributeListRule);
    	}
    	else if (ruleType.equals(AttributeOtherRule)) {
			AttributeOtherRuleController attrOtherController = new AttributeOtherRuleController();
			attrOtherController.createAttributeOtherRuleUI(AttributeOtherRule);
		}
		else if (ruleType.equals(ModifyRule)) {
			ModifyRuleController modController = new ModifyRuleController();
			modController.createModifyRuleUI(ModifyRule);
		}
    }
    
    
    @FXML
    void viewDatabase(ActionEvent event) throws IOException, SQLException {
    	WindowController controller = new WindowController();
    	controller.setMessage(ruleTypes);
    	SearchExistingBusinessRulesController searchRules = new SearchExistingBusinessRulesController();
    	searchRules.createUI();
    }
    
}
