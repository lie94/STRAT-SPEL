package main;
import gamestate.GameStateManager;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

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
		
		frame.setExtendedState(JFrame.NORMAL);
		
		frame.add(this,BorderLayout.CENTER);
		frame.setUndecorated(true);
		frame.pack();
		
		frame.setResizable(true);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.toFront();
		
	}
	public synchronized void start(){
		showLoadingScreen();
		running = true;
		gsm = new GameStateManager(this);
		addKeyListener((KeyListener) gsm);
		addMouseWheelListener(gsm);
		addMouseListener(gsm);
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
			long t1 = System.currentTimeMillis();
			if(t1-t0 < 1000.0 / TARGET_FPS){
				while(t1-t0 < 1000.0 / TARGET_FPS){
					t1 = System.currentTimeMillis();
				}
			}
			render();
		}
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
	private void showLoadingScreen() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			showLoadingScreen();
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.clearRect(0, 0, getWidth(), getHeight());
		System.out.println(frame.getWidth() + ", " + frame.getHeight());
		try {
			System.out.println("SWAG");
			g.drawImage(ImageIO.read(getClass().getResourceAsStream("/res/img/intro.jpg")), 0, 0, frame.getWidth(),frame.getHeight(), null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		g.dispose();
		bs.show();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		/*if(bs == null){
			createBufferStrategy(3);
			return;
		}*/
		Graphics g = bs.getDrawGraphics();
		g.clearRect(0,0,getWidth(), getHeight());
		gsm.getGameState().draw(g);
		g.dispose();
		bs.show();
	}
}
