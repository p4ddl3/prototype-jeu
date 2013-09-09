package server;

import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
public class Peer {
	private Connection tcpConnection;
	private String name;
	private static Map<Connection, Peer> allPeers = new HashMap<Connection, Peer>();
	
	public static Peer getByConnection(Connection c){
		return allPeers.get(c);
	}
	public Peer(Connection c) {
		this.tcpConnection = c;
		name = "Unamed";
		allPeers.put(c,  this);
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	public Connection getConnection(){
		return tcpConnection;
	}
	public void unregister(){
		allPeers.put(tcpConnection, null);
	}

}
