package client;

import java.io.IOException;

import javafx.application.Platform;
import message.ChatMessage;
import message.GreetingsMessage;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientTest {
	private Client client;
	private OnMessageReceivcedListener callback;
	public ClientTest(OnMessageReceivcedListener callback, String ipAddress, int port) throws IOException{
		client = new Client();
		Kryo kryo = client.getKryo();
		kryo.register(ChatMessage.class);
		kryo.register(GreetingsMessage.class);
		this.callback = callback;
		client.start();
		client.connect(5000,ipAddress, port);
		
		client.addListener(new Listener(){
			public void received(Connection connection, Object object){
				if( object instanceof ChatMessage){
					final ChatMessage  message = (ChatMessage)object;
					Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							ClientTest.this.callback.onMessageReceived(message);
						}
					});
					
				}
				if(object instanceof GreetingsMessage){
					GreetingsMessage greetings = (GreetingsMessage) object;
					ClientTest.this.callback.setServerName(greetings.name);
				}
			}
		});
			
	}
	public void send(Object object){
		client.sendTCP(object);
	}
}
