package client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import message.ChatMessage;
import message.GreetingsMessage;

public class ChatClient extends Application implements OnMessageReceivcedListener{

	private ClientTest client;
	private TextField textArea;
	private ListView<String> messagesList;
	private ObservableList<String> messages;
	
	//config scene
	private Text configErrorText;
	private TextField portField;
	private TextField ipField;
	private TextField nickField;
	
	private String nickName;
	private String ipServer;
	private int tcpPort;
	private String serverName;
	
	private Stage stage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		showConfigDialog();
	}
	
	
	public void showConfigDialog(){
		stage.setTitle("Chat server configuration");
		
		GridPane layout = new GridPane();
		layout.setAlignment(Pos.CENTER);
		layout.setHgap(10);
		layout.setVgap(10);
		layout.setPadding(new Insets(25));
		
		Text configText = new Text("Please configure the connection to the server");
		layout.add(configText,0,0,2,1);
		
		Label ipLabel = new Label("ip address:");
		layout.add(ipLabel,0,1);
		ipField = new TextField();
		layout.add(ipField,1,1);
		
		Label portLabel = new Label("port:");
		layout.add(portLabel,0,2);
		portField = new TextField();
		layout.add(portField,1,2);
		
		Label nickLabel = new Label("nick-name:");
		layout.add(nickLabel,0,3);
		nickField = new TextField();
		layout.add(nickField,1,3);
		
		Button connectBtn = new Button("Connect");
		connectBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try{
				tcpPort = Integer.parseInt(portField.getText());
				}catch(NumberFormatException e){
					configErrorText.setFill(Color.FIREBRICK);
					configErrorText.setText("port value must be an integer");
					return;
				}
				nickName = nickField.getText();
				if(nickName.length() == 0){
					configErrorText.setFill(Color.FIREBRICK);
					configErrorText.setText("please, choose a nick-name");
					return;
				}
					ipServer =ipField.getText();
				try {
					client = new ClientTest(ChatClient.this, ipServer, tcpPort);
					GreetingsMessage greetings = new GreetingsMessage();
					greetings.name = nickName;
					client.send(greetings);
					showClient();
				} catch (IOException e) {
					configErrorText.setFill(Color.FIREBRICK);
					configErrorText.setText("impossible to connect ("+e.getMessage()+")");
					e.printStackTrace();
					return;
				}
				
			}
		});
		layout.add(connectBtn,1,4);
		configErrorText = new Text();
		layout.add(configErrorText, 0, 5,2,1);
		stage.setScene(new Scene(layout,400,250));
		stage.show();
	}
	
	
	public void showClient(){
		updateStageTitle();
		
		messages = FXCollections.observableArrayList();
		
		BorderPane sceneLayout = new BorderPane();
		
		messagesList = new ListView<String>();
		
		messagesList.setItems(messages);
		
		sceneLayout.setCenter(messagesList);
		
		StackPane pane = new StackPane();

		
		
		textArea = new TextField();
		textArea.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				if(!textArea.getText().equals("")){
					ChatMessage message = new ChatMessage();
					message.text = textArea.getText();
					client.send(message);
					textArea.setText("");
				}
			}
			
		});
		pane.getChildren().add(textArea);
		sceneLayout.setBottom(pane);
		
		stage.setScene(new Scene(sceneLayout, 300,250));
		stage.show();
	}

	public static void main(String[] args) throws IOException{
		launch(args);
	}

	@Override
	public void onMessageReceived(ChatMessage message) {
		messages.add("["+message.sender+"]:" + message.text);
		
	}


	@Override
	public void setServerName(String name) {
		serverName = name;
		updateStageTitle();
	}
	public void updateStageTitle(){
		stage.setTitle("Chat Client "+nickName+"@"+serverName);
	}

}
