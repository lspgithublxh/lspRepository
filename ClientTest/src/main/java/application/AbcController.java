package application;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bj58.im.client.ClientTest.ReadCallBack;
import com.bj58.im.client.ClientTest.WriteCallBack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;


public class AbcController implements ReadCallBack{
	@FXML
	private Button send;
	@FXML
	private TextArea text_field;
	@FXML
	private TextArea textArea;

	private WriteCallBack wc;
	
	// Event Listener on Button[#send].onAction
	@FXML
	public void getTextFieldValue(ActionEvent event) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		textArea.appendText(String.format("						client:  %s\n						", format.format(new Date())) + text_field.getText() + "\n");
//		textArea.setText(textArea.getText() + String.format("client:  %s\n", format.format(new Date())) + text_field.getText() + "\n");
		wc.setLine(text_field.getText());
		text_field.clear();
	}
	// Event Listener on TextField[#text_field].onKeyPressed
	
	boolean pressControl = false;
	
	@FXML
	public void keyPressOccured(KeyEvent event) {
//		System.out.println(event.getCharacter());
//		System.out.println(event.getSource());
//		System.out.println(event.isShiftDown());
//		System.out.println(event.getSource());
//		System.out.println(event.getCode() == KeyCode.ENTER);
//		System.out.println(event.getEventType());
//		System.out.println(event.getEventType().getName());
//		System.out.println(event.getCode());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if(pressControl && event.getCode() == KeyCode.ENTER) {
//			Font font = new Font(24);
//			textArea.appendText(text);
//			textArea.setFont(font);//style来设置，或者换textArea,使用另一种textArea
//			textArea.setText(textArea.getText() + String.format("client:  %s\n", format.format(new Date())) + text_field.getText() + "\n");
			textArea.appendText(String.format("						client:  %s\n						", format.format(new Date())) + text_field.getText() + "\n");
			wc.setLine(text_field.getText());
			text_field.clear();
		}else if(event.getCode() == KeyCode.CONTROL) {
			pressControl = true;
		}
	}
	
	@FXML
	public void keyReleasedOccrured(KeyEvent event) {
		if(event.getCode() == KeyCode.CONTROL) {
			pressControl = false;
		}
	}
	
	@Override
	public String callback(String input) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		textArea.setText(textArea.getText() + String.format("server:  %s\n", format.format(new Date())) + input + "\n");
		return null;
	}
	public void setWc(WriteCallBack wc) {
		this.wc = wc;
	}

	public static void main(String[] args) {
		try {
			System.out.println(Inet4Address.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
