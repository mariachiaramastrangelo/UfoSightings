/**
 * Sample Skeleton for 'Ufo.fxml' Controller Class
 */

package it.polito.tdp.ufo;

import java.awt.Dialog.ModalExclusionType;
import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.ufo.model.AnniAvvistamenti;
import it.polito.tdp.ufo.model.Model;
import it.polito.tdp.ufo.model.StatiAvvistamenti;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class UfoController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<AnniAvvistamenti> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxStato"
    private ComboBox<StatiAvvistamenti> boxStato; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

	private Model model;

    @FXML
    void handleAnalizza(ActionEvent event) {
    	
    	txtResult.appendText(model.adiacentiEntrantiUscenti(this.boxStato.getValue())+"\n");
    	for(StatiAvvistamenti sa: model.trovaRaggiungibili(this.boxStato.getValue())) {
    		txtResult.appendText(sa.toString()+"\n");
    	}
    }

    @FXML
    void handleAvvistamenti(ActionEvent event) {
    	txtResult.clear();
    	model.creaGrafo(this.boxAnno.getValue().getAnno());
    	this.boxStato.getItems().addAll(model.getStatiAvvistamenti(this.boxAnno.getValue().getAnno()));
    	txtResult.appendText(model.grafoCreato()+"\n\n");
    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	txtResult.appendText("\n"+ model.percorsoLungo(this.boxStato.getValue()));
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert boxStato != null : "fx:id=\"boxStato\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Ufo.fxml'.";
        

    }

	public void setModel(Model model) {
		// TODO Auto-generated method stub
		this.model=model;
		this.boxAnno.getItems().addAll(model.getAnniAvvistamenti());
		
	}
}
