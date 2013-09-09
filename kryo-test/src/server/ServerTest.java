package server;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import message.ChatMessage;
import message.GreetingsMessage;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class ServerTest {
	private Server server;
	private String name;
	private List<Peer> peers;
	public static void main(String[] args) throws IOException{
		String name = args[0];
		int port = Integer.parseInt(args[1]);
		new ServerTest(name, port);
	}
	public ServerTest(String mname , int port) throws IOException{
		this.name = mname;
		server = new Server();
		Kryo kryo = server.getKryo();
		kryo.register(ChatMessage.class);
		kryo.register(GreetingsMessage.class);
		peers = new LinkedList<Peer>();
		server.start();
		server.bind(port);
		server.addListener(new Listener(){
				public void received(Connection connection, Object object){
					if(object instanceof ChatMessage){
						ChatMessage message = (ChatMessage) object;
						Peer peer = Peer.getByConnection(connection);
						message.sender = peer.getName();
						System.out.println("["+message.sender+"]:"+ message.text);
						sendToAll(message);
					}
					if(object instanceof GreetingsMessage){
						GreetingsMessage greetings = (GreetingsMessage) object;
						Peer p = Peer.getByConnection(connection);
						p.setName(greetings.name);
						ChatMessage message = new ChatMessage();
						message.sender = "Server";
						message.text = greetings.name +" join "+name;
						sendToAll(message);
						
					}
				}
				public void connected(Connection connection){
					
					GreetingsMessage greetings = new GreetingsMessage();
					greetings.name = name;
					connection.sendTCP(greetings);
					
					
					ChatMessage welcome = new ChatMessage();
					welcome.sender = "Server";
					welcome.text = "Welcome to "+name;
					connection.sendTCP(welcome);
					peers.add(new Peer(connection));
				}
				public void disconnected(Connection connection){
					Peer peer = Peer.getByConnection(connection);
					String nick = peer.getName();
					peers.remove(peer);
					peer.unregister();
					ChatMessage disconnectNotification = new ChatMessage();
					disconnectNotification.sender = "Server";
					disconnectNotification.text = nick+" leaved the chat";
					sendToAll(disconnectNotification);
				}
		
	});
	}
	public void sendToAll(ChatMessage message){
		for(Peer p : peers){
			server.sendToTCP(p.getConnection().getID(), message);
		}
	}
}
