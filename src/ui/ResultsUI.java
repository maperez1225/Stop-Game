package ui;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Stop;
public class ResultsUI {
	@FXML
	private Label ownNameResult;
	@FXML
	private Label opponentNameResult;
	@FXML
	private Label ownAnimalResult;
	@FXML
	private Label opponentAnimalResult;
	@FXML
	private Label ownLocationResult;
	@FXML
	private Label opponentLocationResult;
	@FXML
	private Label ownObjectResult;
	@FXML
	private Label opponentObjectResult;
	@FXML
	private Label ownResult;
	@FXML
	private Label opponentResult;
	@FXML
	private Button finishBtn;
	@FXML
	void finish(ActionEvent event) {
		Stage stage = (Stage)finishBtn.getScene().getWindow();
		stage.close();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setContentText("¿Desea jugar otra partida?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK)
		    instance.playAgain();
		else 
		    instance.close();
	}
	private StopUI instance;
	public void fillData(Stop ownStop, Stop opponent) {
		int[] ownScores = ownStop.calculateScore(opponent);
		int[] opponentScores = opponent.calculateScore(ownStop);
		ownNameResult.setText(ownStop.getName()+" ("+ownScores[1]+")");
		opponentNameResult.setText(opponent.getName()+" ("+opponentScores[1]+")");
		ownAnimalResult.setText(ownStop.getAnimal()+" ("+ownScores[2]+")");
		opponentAnimalResult.setText(opponent.getAnimal()+" ("+opponentScores[2]+")");
		ownLocationResult.setText(ownStop.getPlace()+" ("+ownScores[3]+")");
		opponentLocationResult.setText(opponent.getPlace()+" ("+opponentScores[3]+")");
		ownObjectResult.setText(ownStop.getObject()+" ("+ownScores[4]+")");
		opponentObjectResult.setText(opponent.getObject()+" ("+opponentScores[4]+")");
		ownResult.setText(Integer.toString(ownScores[0]));
		opponentResult.setText(Integer.toString(opponentScores[0]));
	}
	public void setInstance(StopUI i) {
		instance = i;
	}
}