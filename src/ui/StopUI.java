package ui;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.Connection;
import com.Connection.ActionListener;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Stop;
public class StopUI implements Initializable, ActionListener {
	@FXML
	private Label title;
	@FXML
	private Button stopBtn;
	@FXML
	private TextField nameAnswer;
	@FXML
	private TextField animalAnswer;
	@FXML
	private TextField locationAnswer;
	@FXML
	private TextField objectAnswer;
	@FXML
	void stop(ActionEvent event) {
		if (!nameAnswer.getText().trim().isEmpty()||!animalAnswer.getText().trim().isEmpty()||!locationAnswer.getText().trim().isEmpty()||!objectAnswer.getText().trim().isEmpty()) {
			ownStop = new Stop(nameAnswer.getText(),animalAnswer.getText(),locationAnswer.getText(),objectAnswer.getText());
			json = gson.toJson(ownStop);
			connection.sendMessage(json + "\n");
		}
	}
	private Connection connection;
	private Gson gson;
	private Stop ownStop;
	private String json;
	public void remoteStop() {
		ownStop = new Stop(nameAnswer.getText(),animalAnswer.getText(),locationAnswer.getText(),objectAnswer.getText());
		json = gson.toJson(ownStop);
		connection.sendMessage(json + "\n");
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gson = new Gson();
		title.setText("Esperando oponente");
		stopBtn.setDisable(true);
		nameAnswer.setDisable(true);
		animalAnswer.setDisable(true);
		locationAnswer.setDisable(true);
		objectAnswer.setDisable(true);
		connection = new Connection();
		connection.setListener(this);
		connection.start();
	}
	@Override
	public void beginGame(char l) {
		title.setText("Letra "+l);
		stopBtn.setDisable(false);
		nameAnswer.setDisable(false);
		animalAnswer.setDisable(false);
		locationAnswer.setDisable(false);
		objectAnswer.setDisable(false);
	}
	@Override
	public void endGame(String msg) {
		stopBtn.setDisable(true);
		nameAnswer.setDisable(true);
		animalAnswer.setDisable(true);
		locationAnswer.setDisable(true);
		objectAnswer.setDisable(true);
		if (ownStop == null) {
			remoteStop();
		}
		Stop opponent = gson.fromJson(msg, Stop.class);
		Platform.runLater(() -> {
			try {
				Stage current = (Stage)stopBtn.getScene().getWindow();
				current.hide();
				FXMLLoader loader = new FXMLLoader(Main.class.getResource("V2.fxml"));
				Parent p = (Parent) loader.load();
				Scene scene = new Scene(p);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.show();
				((ResultsUI) loader.getController()).fillData(ownStop, opponent);
				((ResultsUI) loader.getController()).setInstance(this);
			} catch (IOException e) {
				e.printStackTrace();
			} 
		});
	}
	public void playAgain() {
		Stage current = (Stage)stopBtn.getScene().getWindow();
		current.show();
		title.setText("Esperando oponente");
		stopBtn.setDisable(true);
		nameAnswer.setDisable(true);
		animalAnswer.setDisable(true);
		locationAnswer.setDisable(true);
		objectAnswer.setDisable(true);
		nameAnswer.setText("");
		animalAnswer.setText("");
		locationAnswer.setText("");
		objectAnswer.setText("");
		ownStop = null;
		json = null;
		connection = new Connection();
		connection.setListener(this);
		connection.start();
	}
	public void close() {
		Stage stage = (Stage)stopBtn.getScene().getWindow();
		stage.close();
	}
}