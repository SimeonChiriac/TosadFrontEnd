package presentation;

import java.sql.SQLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {
	public static void main(String[] args) throws SQLException {
		Application.launch(args);
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane mainWindow = FXMLLoader.load(Main.class.getResource("FirstPage.fxml"));		
		primaryStage.setScene(new Scene(mainWindow));
		primaryStage.show();
	}
}