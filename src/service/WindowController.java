package service;

import javafx.collections.ObservableList;

public class WindowController {
	
	static ObservableList<String> message = null;
	static boolean showDeleteButton;
	
	public static void setMessage(ObservableList<String> Message) {
		message = Message;
	}
	
	public static ObservableList<String> getMessage() {
		return message;
	}
	
	public static void setDeleteRule(boolean show) {
		showDeleteButton = show;
	}
	
	public static boolean getDeleteRule() {
		return showDeleteButton;
	}
}
