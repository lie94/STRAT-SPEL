package main;
import gamestate.GameStateManager;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import animations.ImageRelayer;

public class Run extends Canvas implements Runnable{
	private static final long serialVersionUID = 1L;
	private static final int TARGET_FPS = 60;
	private final String NAME = "STRAT-GAME";
	public JFrame frame; //Can be showed in package
	private boolean running;
	private GameStateManager gsm;
	/**
	 * Starts the program
	 * @param args
	 */
	public static void main(String[] args){
		//Makes the game run smoother on unixbased operatingsystems
		if(!System.getProperty("os.name").startsWith("Windows"))
			System.setProperty("sun.java2d.opengl", "True");
		new Run().start();
	}
	/**
	 * Initiates the frame and starts the key listener
	 */
	@SuppressWarnings("static-access")
	Run(){
		frame = new JFrame(NAME);
		
		frame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(this,BorderLayout.CENTER);
		
		if(!System.getProperty("os.name").startsWith("Windows")){
			setMinimumSize(new Dimension(1280,720));
			setMaximumSize(new Dimension(1280,720));
			setPreferredSize(new Dimension(1280,720));
			frame.setResizable(false);
		}else{
			frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
			frame.setResizable(false);
			frame.setUndecorated(true);
		}
		
		frame.pack();
		
		frame.setVisible(true);
		
		frame.setLocationRelativeTo(null);
	}
	public synchronized void start(){
		running = true;
		LoadingScreen l = new LoadingScreen();
		new Thread(l).start();
		try {
			new ImageRelayer(this);
		} catch (Exception e1) {
			e1.printStackTrace();
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		}
		gsm = new GameStateManager(this);
		addKeyListener((KeyListener) gsm);
		addMouseWheelListener(gsm);
		addMouseListener(gsm);
		try { //SIMULATE LOADING
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		l.stop();
		new Thread(this).start();
	}
	public synchronized void stop(){
		running = false;
	}
	@Override
	public void run() {	
		while(running){
			long t0 = System.currentTimeMillis();
			gsm.update();
			render();
			long t1 = System.currentTimeMillis();
			if(t1-t0 < 1000.0 / TARGET_FPS){
				while(t1-t0 < 1000.0 / TARGET_FPS){
					t1 = System.currentTimeMillis();
				}
			}
		}
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.clearRect(0,0,getWidth(), getHeight());
		gsm.draw(g);
		g.dispose();
		bs.show();
	}
	private class LoadingScreen implements Runnable{
		private boolean running;
		LoadingScreen(){
			running = true;
		}
		@Override
		public void run() {
			while(running){
				long t0 = System.currentTimeMillis();
				showLoadingScreen();
				long t1 = System.currentTimeMillis();
				if(t1-t0 < 1000.0 / TARGET_FPS){
					while(t1-t0 < 1000.0 / TARGET_FPS){
						t1 = System.currentTimeMillis();
					}
				}
			}
		}
		public synchronized void stop(){
			running = false;
		}
		private void showLoadingScreen() {
			BufferStrategy bs = getBufferStrategy();
			if(bs == null){
				createBufferStrategy(3);
				return;
			}
			Graphics g = bs.getDrawGraphics();
			g.clearRect(0,0,getWidth(), getHeight());
			try {
				g.drawImage(ImageIO.read(getClass().getResourceAsStream("/res/img/intro.jpg")), 0, 0,getWidth(),getHeight(), null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.dispose();
			bs.show();
		}
	}
}
