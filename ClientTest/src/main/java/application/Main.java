package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
//			Parent root = FXMLLoader.load(getClass().getResource("Abc.fxml"));
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Abc.fxml"));
			Parent root = loader.load();
			AbcController cmain = loader.getController();
//			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,500,522);//
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);//
			primaryStage.setTitle("聊天软件");//
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
