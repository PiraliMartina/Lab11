/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.rivers;

import java.net.URL;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import it.polito.tdp.rivers.model.Model;
import it.polito.tdp.rivers.model.River;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="boxRiver"
	private ComboBox<River> boxRiver; // Value injected by FXMLLoader

	@FXML // fx:id="txtStartDate"
	private TextField txtStartDate; // Value injected by FXMLLoader

	@FXML // fx:id="txtEndDate"
	private TextField txtEndDate; // Value injected by FXMLLoader

	@FXML // fx:id="txtNumMeasurements"
	private TextField txtNumMeasurements; // Value injected by FXMLLoader

	@FXML // fx:id="txtFMed"
	private TextField txtFMed; // Value injected by FXMLLoader

	@FXML // fx:id="txtK"
	private TextField txtK; // Value injected by FXMLLoader

	@FXML // fx:id="btnSimula"
	private Button btnSimula; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML
	void doAggiorna(ActionEvent event) {
		try {
			River fiume = boxRiver.getValue();
			if (fiume == null) {
				txtResult.setText("Seleziona un fiume");
				return;
			}

			txtStartDate.setText(model.getPrimaData(fiume).toString());
			txtEndDate.setText(model.getUltimaData(fiume).toString());
			txtNumMeasurements.setText(String.valueOf(model.getNumeroMisurazioni(fiume)));
			txtFMed.setText(String.format("%.3f", model.getMedia(fiume)));
		} catch (Exception e) {
			txtResult.setText("ERRORE!!");
		}
	}

	@FXML
	void doSimula(ActionEvent event) {
		try {
			River fiume = boxRiver.getValue();
			if (fiume == null) {
				txtResult.setText("Seleziona un fiume");
				return;
			}

			double k = -1;
			k = Double.parseDouble(txtK.getText());
			if (k < 0) {
				txtResult.setText("Valore di k non ammissibile");
				return;
			}

			model.simula(k, fiume);
			txtResult.setText(String.format("Capacità totale: %.3f \n", model.getQ()));
			txtResult.appendText(String.format("Occupazione media: %.3f \n", model.getOccupazioneMedia()));
			txtResult.appendText("Giornate di carenza idrica: " + model.getInsoddisfatti());
			double media = model.getInsoddisfatti()*100/model.getGiorniTotali();
			txtResult.appendText(String.format("\nPercentuale di insoddisfatti: %.2f", media ));
			txtResult.appendText("%");

		} catch (InvalidParameterException e) {
			txtResult.setText("Parametro non valido");
		} catch (Exception e) {
			txtResult.setText("ERRORE!! -");
		}
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert boxRiver != null : "fx:id=\"boxRiver\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtStartDate != null : "fx:id=\"txtStartDate\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtEndDate != null : "fx:id=\"txtEndDate\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtNumMeasurements != null : "fx:id=\"txtNumMeasurements\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtFMed != null : "fx:id=\"txtFMed\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
	}

	public void setModel(Model model) {
		this.model = model;

		List<River> fiumi = model.getAllRivers();
		boxRiver.getItems().addAll(fiumi);
	}
}
