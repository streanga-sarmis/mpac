package com.stephancode.net.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.stephancode.entities.MPlayer;
import com.stephancode.levelMachine.LevelManager;
import com.stephancode.stateMachine.StateManager;
import com.stephancode.stateMachine.components.MenuState;

public class Client{

	private int port;
	private InetAddress ip;
	private DatagramSocket socket;
	
	public Client(int port, String ip){
	
		this.port = port;
		
		try {
			this.ip = InetAddress.getByName(ip);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		
		try {
			socket = new DatagramSocket();
			socket.connect(this.ip, this.port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
	}

	public void startReceiving(){
		
		Thread rec = new Thread("ReceiveThread"){
			public void run(){
				while(true){
					byte data[] = new byte[1024];
					DatagramPacket packet = new DatagramPacket(data, data.length);
			
					try {
						socket.receive(packet);
						evaluateAndExecute(new String(packet.getData()), packet);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			public void evaluateAndExecute(String msg, DatagramPacket packet){
				
				int packetCode = Integer.parseInt(msg.split("#")[0]);
				
				switch(packetCode){
				
				case 0: // connected user 00#username
					String username = msg.split("#")[1].trim();
					
					if (StateManager.getCurrentState() instanceof MenuState){
						((MenuState)StateManager.getCurrentState()).startTimer(new MPlayer(username));
					}
					
					break;
					
				case 1: // direction 01#username#dir
					String cusername = msg.split("#")[1].trim();

					String x = msg.split("#")[2].trim();
					String y = msg.split("#")[3].trim();
					
					int ix = Integer.parseInt(x);
					int iy = Integer.parseInt(y);
					if(LevelManager.getMPlayer() != null)
						LevelManager.getMPlayer().setPosition(ix, iy);
					

					break;

				case 3: // send my dir user 00#username
					send("01#" + LevelManager.getPlayer().username + "#"
				+ LevelManager.getPlayer().x + "#" + LevelManager.getPlayer().y);
					break;
				}
				
			}

		};
		rec.start();
	}
	
	public void send(String msg){		
		DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length, ip, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
