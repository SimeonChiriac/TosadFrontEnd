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

public class ModifyRuleController {

    private String BusinessRuleType;

    public void intitialize() throws IOException, SQLException {

    }

    public void createModifyRuleUI(String modifyRule) throws IOException{
        BusinessRuleType = modifyRule;
        Stage stage = new Stage();
        Pane mainWindow = FXMLLoader.load(Main.class.getResource("ModifyRule.fxml"));
        stage.setScene(new Scene(mainWindow));
        stage.show();
    }
}
