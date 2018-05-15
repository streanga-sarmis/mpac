package com.stephancode.net.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class Server{

	private static Map<String, ConnectedClient> connectedClients = new HashMap<String, ConnectedClient>();
	
	private static DatagramSocket socket;

	public static void main(String args[]){
		try {
			socket = new DatagramSocket(8888);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		startReceiving();
	}
	
	
	public static void startReceiving() {
		Thread rec = new Thread("MainServerThread"){
			public void run(){
				while(true){
					byte[] msg = new byte[1024]; // 1 kb
					DatagramPacket packet = new DatagramPacket(msg, msg.length);
					try {
						socket.receive(packet);
						evaluate(new String(packet.getData()).trim(), packet);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			public void evaluate(String msg, DatagramPacket packet){
				
				int packetCode = Integer.parseInt(msg.split("#")[0]);
				
				switch(packetCode){
				
					case 0: // connected user 00#username
						String username = msg.split("#")[1].trim();
						
						connectedClients.put(username, new ConnectedClient(socket, packet.getAddress(), packet.getPort(), username));
						ConnectedClient currentConnected = connectedClients.get(username);
						System.out.println(username + " has connected !");
						
						for(String u : connectedClients.keySet()){
							ConnectedClient c = connectedClients.get(u);
							
							if(!currentConnected.equals(c)){
								currentConnected.send("00#" + c.username);
								currentConnected.send("03#" + c.username);
								c.send("00#" + currentConnected.username);
							}
							
						}
						break;
					
					case 1:
						String cusername = msg.split("#")[1].trim();

						for(String u : connectedClients.keySet()){
							ConnectedClient c = connectedClients.get(u);
							
							if(!c.username.equals(cusername)){
								c.send(msg);
							}
							
						}
						break;
				}
				
			}
			
		};
		rec.start();
	}


	
}

class ConnectedClient {
	
	public InetAddress ip;
	public String username;
	public int port;
	private DatagramSocket socket;

	public ConnectedClient(DatagramSocket socket, InetAddress ip, int port, String username){
		this.ip = ip;
		this.username = username;
		this.socket = socket;
		this.port = port;
	}
	
	public void send(String msg){
		DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length, ip, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean equals(ConnectedClient other){
		return this.username.equals(other.username);
	}
	
}
