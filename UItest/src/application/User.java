package application;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

public class User {

	private StringProperty password;
	private ReadOnlyStringWrapper username;
	
	private SimpleIntegerProperty age;
	private SimpleFloatProperty score;
	
	
	public User() {
		super();
	}
	public User(StringProperty password, ReadOnlyStringWrapper username, SimpleIntegerProperty age) {
		super();
		this.password = password;
		this.username = username;
		this.age = age;
	}
	public StringProperty getPassword() {
		return password;
	}
	public void setPassword(StringProperty password) {
		this.password = password;
	}
	public ReadOnlyStringWrapper getUsername() {
		return username;
	}
	public void setUsername(ReadOnlyStringWrapper username) {
		this.username = username;
	}
	public SimpleIntegerProperty getAge() {
		return age;
	}
	public void setAge(SimpleIntegerProperty age) {
		this.age = age;
	}
	public SimpleFloatProperty getScore() {
		return score;
	}
	public void setScore(SimpleFloatProperty score) {
		this.score = score;
	}
	
	
}
