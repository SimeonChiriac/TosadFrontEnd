package service;

import javafx.collections.ObservableList;

public class WindowController {
	
	static ObservableList<String> message = null;
	static boolean showDeleteButton;
	//If successOrFailureAlertBox is set to false, an alertbox with an error will show up instead.
	static boolean successOrFailureAlertBox;
	
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
	
	public static void setSuccessAlertBox(boolean showSuccess) {
		successOrFailureAlertBox = showSuccess;
	}
	
	public static boolean getSuccessAlertBox() {
		return successOrFailureAlertBox;
	}
}
