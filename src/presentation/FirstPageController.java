package presentation;
import java.io.IOException;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    void toSelectedRule(ActionEvent event) throws IOException, SQLException {
    	
    	String ruleType = chooseRuleType.getValue();

    	if (ruleType.equals(AttributeRangeRule)) {
    		AttributeRangeRuleController rangeController = new AttributeRangeRuleController();
    		rangeController.createAttributeRangeRuleUI(AttributeRangeRule);
    	}
    	
    	if (ruleType.equals(AttributeCompareRule)) {
    		AttributeCompareRuleController compareController = new AttributeCompareRuleController();
    		compareController.createAttributeCompareRuleUI(AttributeCompareRule);
    	}
    	
    	if (ruleType.equals(TupleCompareRule)) {
    		TupleCompareRuleController tupleController = new TupleCompareRuleController();
    		tupleController.createTupleCompareRuleUI(TupleCompareRule);
    	}
    	
    	if (ruleType.equals(InterEntityCompareRule)) {
    		InterEntityCompareRuleController interCompareController = new InterEntityCompareRuleController();
    		interCompareController.createInterEntityCompareRuleUI(InterEntityCompareRule);
    	}
    	
    	if (ruleType.equals(AttributeListRule)) {
    		AttributeListRuleController listController = new AttributeListRuleController();
    		listController.createAttributeListRuleUI(AttributeListRule);
    	}

		if (ruleType.equals(AttributeOtherRule)) {
			AttributeOtherRuleController attrOtherController = new AttributeOtherRuleController();
			attrOtherController.createAttributeOtherRuleUI(AttributeOtherRule);
		}

		if (ruleType.equals(ModifyRule)) {
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
