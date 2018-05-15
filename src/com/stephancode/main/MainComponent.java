package com.stephancode.main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.stephancode.graphics.Screen;
import com.stephancode.net.client.Client;
import com.stephancode.stateMachine.StateManager;
import com.stephancode.stateMachine.components.MenuState;

public class MainComponent extends Canvas implements Runnable{

	public static Client c = new Client(8888, "localhost");
	public static String username = JOptionPane.showInputDialog(null, "Your username ?");
	
	private final int oldWidth = 320 + 5 * 16 + 3;
	private final int oldHeight = 250 + 5 * 16 + 4;

	private final int SCALE = 2;
	private final int WIDTH = oldWidth * SCALE;
	private final int HEIGHT = oldHeight * SCALE;
	
	private boolean running = false;
	
	private BufferedImage screenImage;
	private BufferedImage hudImage;
	private int[] pixels;
	private int[] hudPixels;
	
	private Thread thisThread;
	
	private JFrame jf;
	private Dimension d;
	
	private Screen screen;
	private Screen hud;
	private KeyHandler keys;
	
	private final int OFFSET = 6 * 16;
	
	public MainComponent(){
		jf = new JFrame("MPac");
		d = new Dimension(WIDTH, HEIGHT);
		
		screenImage = new BufferedImage(oldWidth, oldHeight, BufferedImage.TYPE_INT_RGB);
		hudImage = new BufferedImage(oldWidth, oldHeight - OFFSET / SCALE, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)screenImage.getRaster().getDataBuffer()).getData();
		hudPixels = ((DataBufferInt)hudImage.getRaster().getDataBuffer()).getData();
		screen = new Screen(oldWidth, oldHeight);
		hud = new Screen(oldWidth, oldHeight - OFFSET / SCALE);
		keys = new KeyHandler(this);

		jf.setPreferredSize(d);
		jf.pack();
		jf.add(this);
		jf.setVisible(true);
		jf.setResizable(true);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		StateManager.pushState(new MenuState());
		
	}
	
	public synchronized void start(){
		thisThread = new Thread(this, "MainThread");
		thisThread.start();
		running = true;
	}

	public synchronized void stop(){
		try {
			thisThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		running = false;
	}
		
	public void run(){
		
		c.startReceiving();
		c.send("0#" + username);
		
		float fps = 0;
		float ups = 0;
		
		double delta = 0;
		double then = System.nanoTime();
		double fpsTime = System.currentTimeMillis();
		double nsPerTick = 1000000000 / 60d;
		
		boolean canRender;
		
		while(running){
			canRender = false;
			double now = System.nanoTime();
			delta += (now - then) / nsPerTick;
			then = now;
			
			while(delta >= 1){
				delta--;
				ups++;
				keys.update();
				StateManager.getCurrentState().update(keys);
				canRender = true;
			}
			
			if(canRender){
				fps++;
				render();
			}
			
			if(System.currentTimeMillis() - fpsTime >= 1000){
				//System.out.println("fps " + fps + ", ups " + ups);
				fps = 0;
				ups = 0;
				fpsTime += 1000;
			}
			
		}
	}
	
	private void render(){
		BufferStrategy bs = getBufferStrategy();
		
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		StateManager.getCurrentState().render(screen);
		HUD.render(hud);
		
		for(int i = 0; i < oldWidth * oldHeight; i++){
			pixels[i] = screen.pixels[i];
		}
		
		for(int i = 0; i < oldWidth * (oldHeight - OFFSET); i++){
			hudPixels[i] = hud.pixels[i];
		}
		
		
		g.drawImage(hudImage, 0, 0, getWidth(), OFFSET * SCALE * 3, null);
		g.drawImage(screenImage, 0, OFFSET, getWidth(), getHeight(), null);
		
		g.dispose();
		bs.show();
		
	}
	
	public static void main(String args[]){
		new MainComponent().start();
	}
	
}
