package org.nekoromain.ntr;
import java.net.MalformedURLException;
import java.net.URL;


import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.nekoromain.ntr.services.RemboursementAchatOpeImp;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GatewayClient extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	/**definir la troupe des objets graphiques*/
    Group root;

 /**lancement de l'application*/
    public void start(Stage primaryStage) throws MalformedURLException {
    	construireScene( primaryStage);
    }
    
    void construireScene(Stage primaryStage) throws MalformedURLException {
    	//URL du WSDl
    	URL url = new URL("	http://localhost:10080/Gateway_Banque_Service_SOAP/soap-api?wsdl");
    	//On récupère le nom de où sont les services  
    	QName qname = new QName("http://services.ntr.nekoromain.org/", "RemboursementAchatOpWSService");
    	//On récupère les services
    	Service service = Service.create(url, qname);
    	//On récupère le endpoint (interface de l'api SOAP)
    	RemboursementAchatOpeImp endPoint = service.getPort(RemboursementAchatOpeImp.class);
    	//utilisation des services
    	/*System.out.println(endPoint.testHello("Romain"));
    	System.out.println(endPoint.testHello("World"));
    	System.out.println(endPoint.remboursement(39, 1000)); */
    	
       int largeur = 200;
       int hauteur = 130;
       //definir la troupe
       root = new Group();
       //definir la scene principale
       Scene scene = new Scene(root, largeur, hauteur, Color.WHITE);
       primaryStage.setTitle("E-commerce Client");
       primaryStage.setScene(scene);
       primaryStage.setResizable(false);
       
       
       Button remboursementButton  = new Button("Remboursement");
       Button achatButton = new Button("Achat");
       Label labelId = new Label();
       TextField idFiled = new TextField();
       idFiled.setPadding(new Insets(0,0,0,5));
       Label labelAmount = new Label();
       TextField amountField = new TextField();
       
       HBox idBox = new HBox();
       idBox.setPadding(new Insets(5,5,5,5));
       idBox.getChildren().add(labelId);
       idBox.getChildren().add(idFiled);
       labelId.setText("ID:");
       idFiled.setText("1");
       labelAmount.setText("Somme: ");
       idFiled.textProperty().addListener(new ChangeListener<String>() {
    	   @Override
    	   public void changed(ObservableValue<? extends String> observable, String oldString, String newValue) {
    		   if(!newValue.matches("\\d*"))
    			   idFiled.setText(oldString);
    	   }
       });
       
       HBox amountBox = new HBox();
       amountBox.setPadding(new Insets(5,5,5,5));
       amountBox.getChildren().add(labelAmount);
       amountBox.getChildren().add(amountField);
       amountField.setText("0");
       amountField.textProperty().addListener(new ChangeListener<String>() {
    	   @Override
    	   public void changed(ObservableValue<? extends String> observable, String oldString, String newValue) {
    		   if(!newValue.matches("\\d*(\\d\\.\\d{0,2})?"))
    			   amountField.setText(oldString);
    	   }
       });
       
       HBox buttonBox = new HBox();
       buttonBox.setPadding(new Insets(5,5,5,5));
       buttonBox.getChildren().add(achatButton);
       buttonBox.getChildren().add(remboursementButton);
       
       Label reponseLabel = new Label();
       
       VBox box = new VBox();
       box.setPadding(new Insets(5,5,5,5));
       box.getChildren().add(idBox);
       box.getChildren().add(amountBox);
       box.getChildren().add(buttonBox);
       box.getChildren().add(reponseLabel);
       
       
       remboursementButton.setOnAction(new EventHandler<ActionEvent>() {
    	   @Override
    	   public void handle(ActionEvent event) {
    		   reponseLabel.setText(endPoint.remboursement(Integer.parseInt(idFiled.getText()), Double.parseDouble(amountField.getText())));
    	   }
       });
       
       achatButton.setOnAction(new EventHandler<ActionEvent>() {
    	   @Override
    	   public void handle(ActionEvent event) {
    		   reponseLabel.setText(endPoint.achat(Integer.parseInt(idFiled.getText()), Double.parseDouble(amountField.getText())));
    	   }
       });
       
       root.getChildren().add(box);
       primaryStage.show();    
    }
}
