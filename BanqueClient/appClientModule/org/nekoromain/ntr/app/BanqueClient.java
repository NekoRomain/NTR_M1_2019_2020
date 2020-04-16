package org.nekoromain.ntr.app;

import java.util.List;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;
import org.nekoromain.ntr.entities.Client;
import org.nekoromain.ntr.entities.Operation;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;




public class BanqueClient extends Application{
	
	public static void main(String[] args) {	
		launch(args);
	}
	
  /**definir la troupe des objets graphiques*/
       Group root;
       
  //old_id
       int old_id = -1;
       
    /**lancement de l'application*/
    public void start(Stage primaryStage) {

       construireScene( primaryStage);
    }
    
    public String getClient(int id) {
    	String uri = "http://localhost:8080/BanqueService-0.0.1-SNAPSHOT/clients/" + id; 
		try {
			/*
			URL url = new URL(uri);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");  
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put("eclipselink.media-type", "application/json");
			properties.put(MarshallerProperties.JSON_INCLUDE_ROOT, false); 
			//POUR DU JSON
			Class[] listClassUnMarshallerJson = {Client.class, Operation.class};
			JAXBContext ctx = (JAXBContext) JAXBContextFactory.createContext(listClassUnMarshallerJson, properties);
			JAXBUnmarshaller jsonUmarshaller = ctx.createUnmarshaller();
			StreamSource jsonStream = new StreamSource(connection.getInputStream());
			Client customer = (Client) jsonUmarshaller.unmarshal(jsonStream, Client.class).getValue();
			connection.disconnect();
			return customer.toString();
			*/
			javax.ws.rs.client.Client clientWeb = ClientBuilder.newBuilder().register(Client.class).build();
			WebTarget webTarget = clientWeb.target(uri);
			Response reponse = webTarget.request().accept("application/json").get();
			if(reponse.getStatusInfo().equals(Response.Status.OK)) {
				Client client =  reponse.readEntity(Client.class);
				reponse.close();
				return client.toString();
			}
			if(reponse.getStatusInfo() == Response.Status.NOT_FOUND) {
				//on récupère le json
				String message = reponse.readEntity(String.class);
				//on créer un objet json puis récupère le champs erreur de l'obj
				JSONObject obj = new JSONObject(message);
				reponse.close();
				return obj.getString("Erreur");
			}
		} catch (Exception e) {
			 e.printStackTrace();
			 return "Exception";
		}
		return "ERROR";
    }
    
    //obtenir la liste des opération en string
    public String getListOpClient(int id) {
    	String uri = "http://localhost:8080/BanqueService-0.0.1-SNAPSHOT/clients/" + id +"/operations"; 
    	String retour = "";
		try {	
			javax.ws.rs.client.Client clientWeb = ClientBuilder.newBuilder().register(Operation.class).build();
			WebTarget webTarget = clientWeb.target(uri);
			Response reponse = webTarget.request().accept("application/json").get();
			if(reponse.getStatusInfo().equals(Response.Status.OK)) {
				List<Operation> listOp =  reponse.readEntity(new GenericType<List<Operation>>() {});
				if(!listOp.isEmpty()){
					retour = "LISTE OPÉRATION(S) DU CLIENT ID: " + id + "\n";
					retour += "------------------------------------------------\n";
					for(Operation op : listOp) {
						retour += op.toString() + "\n";
						retour += "------------------------------------------------\n";
					}
				}
				reponse.close();
				return retour;
			}
			if(reponse.getStatusInfo().equals(Response.Status.NO_CONTENT)) {
				retour = "Pas d'opération";
				return retour;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ERROR";
    }
 
    
   /**construction des objets affichés*/
    void construireScene(Stage primaryStage)  
    {
       int largeur = 500;
       int hauteur = 450;
       //definir la troupe
       root = new Group();
       //definir la scene principale
       Scene scene = new Scene(root, largeur, hauteur, Color.WHITE);
       primaryStage.setTitle("Banque Client");
       primaryStage.setScene(scene);
       primaryStage.setResizable(false);
       
       //Appelle GET /client/id + affichage info
       Button getClientButton  = new Button("Get client");
       TextField idFiled = new TextField();
       Label label = new Label();
       label.setText("ID:");
       idFiled.setText("1");
       idFiled.textProperty().addListener(new ChangeListener<String>() {
    	   @Override
    	   public void changed(ObservableValue<? extends String> observable, String oldString, String newValue) {
    		   if(!newValue.matches("\\d*"))
    			   idFiled.setText(oldString);
    	   }
       });
       
       VBox boxClient = new VBox();
       boxClient.setPadding(new Insets(5, 5, 5, 5));
       HBox boxId = new HBox();
       boxId.getChildren().add(label);
       boxId.getChildren().add(idFiled);
       boxId.getChildren().add(getClientButton);
       
       TextArea client_info = new TextArea();
       client_info.setEditable(false);
       
       
       
       //Appelle GET /client/id/operations + affichage info
       Button getClientListeOpButton  = new Button("Get liste opérations client");
       VBox boxClientListeOp = new VBox();
       boxClientListeOp.setPadding(new Insets(5,5,5,5));
       TextArea client_liste_op_info = new TextArea();
       client_liste_op_info.setEditable(false);
       boxClientListeOp.getChildren().add(getClientListeOpButton);
       boxClientListeOp.getChildren().add(client_liste_op_info);
       
       /*
       //CREDIT ou DEBIT AU CLIENT ID : /client/id/credit/amount /client/id/debit/amount
       
       Button creditButton = new Button("CREDIT");
       Button debitButton = new Button("DEBIT");
       Label labelAmount = new Label("Somme: ");
       TextField amountField = new TextField();
       Label reponseDebitCredit = new Label();
       amountField.textProperty().addListener(new ChangeListener<String>() {
    	   @Override
    	   public void changed(ObservableValue<? extends String> observable, String oldString, String newValue) {
    		   if(!newValue.matches("\\d*(\\d\\.\\d{0,2})?"))
    			   amountField.setText(oldString);
    	   }
       });
       HBox amountLabelFieldBox = new HBox();
       amountLabelFieldBox.setPadding(new Insets(0,3,0,0));
       amountLabelFieldBox.getChildren().add(labelAmount);
       amountLabelFieldBox.getChildren().add(amountField);
       HBox creditDebitBox = new HBox();
       creditDebitBox.setPadding(new Insets(5,5,5,5));
       
       creditDebitBox.getChildren().add(amountLabelFieldBox);
       creditDebitBox.getChildren().add(creditButton);
       creditDebitBox.getChildren().add(debitButton);*/
       
       //Mise en page
       VBox vBoxFenertre = new VBox();
       boxClient.getChildren().add(boxId);
       boxClient.getChildren().add(client_info);
       vBoxFenertre.getChildren().add(boxClient);
       vBoxFenertre.getChildren().add(boxClientListeOp);
      // vBoxFenertre.getChildren().add(creditDebitBox);
      // vBoxFenertre.getChildren().add(reponseDebitCredit);
       root.getChildren().add(vBoxFenertre);
       
       
       // Evènements
      getClientButton.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			//client_info.setText(getClient(Integer.parseInt(idFiled.getText())));
			client_info.setText(getClient(Integer.parseInt(idFiled.getText())));
			if(!(Integer.parseInt(idFiled.getText()) == old_id)) {
				client_liste_op_info.setText("");
				updateOldId(Integer.parseInt(idFiled.getText()));
			}
		}
	});
      
      getClientListeOpButton.setOnAction(new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent event) {
			client_liste_op_info.setText(getListOpClient(Integer.parseInt(idFiled.getText())));
			
		}
	});
      
      /*
      creditButton.setOnAction(new EventHandler<ActionEvent>() {
    	  @Override
  		public void handle(ActionEvent event) {
  			reponseDebitCredit.setText(credit(Integer.parseInt(idFiled.getText()), Double.parseDouble(amountField.getText())));
  		} 
	});
      
      debitButton.setOnAction(new EventHandler<ActionEvent>() {
    	  @Override
  		public void handle(ActionEvent event) {
  			reponseDebitCredit.setText(debit(Integer.parseInt(idFiled.getText()), Double.parseDouble(amountField.getText())));
  		} 
	});*/
       //afficher le theatre
       primaryStage.show();      
    }
    
    private void updateOldId(int id) {
    	old_id = id;
    }
   
}
