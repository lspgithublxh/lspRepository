package application;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class AbcController {
	@FXML
	private Button send;
	@FXML
	private TextField text_field;
	@FXML
	private TextArea textArea;

	// Event Listener on Button[#send].onAction
	@FXML
	public void getTextFieldValue(ActionEvent event) {
		textArea.setText(textArea.getText() + text_field.getText() + "\n");
	}
	// Event Listener on TextField[#text_field].onKeyPressed
	@FXML
	public void keyPressOccured(KeyEvent event) {
//		System.out.println(event.getCharacter());
//		System.out.println(event.getSource());
//		System.out.println(event.isShiftDown());
//		System.out.println(event.getSource());
//		System.out.println(event.getCode() == KeyCode.ENTER);
//		System.out.println(event.getEventType());
//		System.out.println(event.getEventType().getName());
		if(event.getCode() == KeyCode.ENTER) {
			textArea.setText(textArea.getText() + text_field.getText() + "\n");
		}
	}
}
