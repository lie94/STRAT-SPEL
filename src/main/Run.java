package main;
import gamestate.GameStateManager;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

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
		
		//setMinimumSize(new Dimension(MAXW,MAXH));
		//setMaximumSize(new Dimension(MAXW,MAXH));
		//setPreferredSize(new Dimension(MAXW,MAXH));
		frame = new JFrame(NAME);
		
		
		frame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.setExtendedState(JFrame.NORMAL);
		
		frame.add(this,BorderLayout.CENTER);
		frame.setUndecorated(true);
		frame.pack();
		
		frame.setResizable(true);
		frame.setVisible(true);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setLocationRelativeTo(null);
		
		
	}
	/**
	 * Initiates all of the program
	 */
	public synchronized void start(){
		running = true;
		gsm = new GameStateManager(this);
		addKeyListener((KeyListener) gsm);
		addMouseWheelListener(gsm);
		addMouseListener(gsm);
		frame.toFront();
		new Thread(this).start();
	}
	/**
	 * Kills the program
	 */
	public synchronized void stop(){
		running = false;
	}
	/**
	 * Starts as the thread is initiated
	 * The main game loop that updates the gamestate
	 * and draws the current map
	 */
	@Override
	public void run() {
		while(running){
			long t0 = System.currentTimeMillis();
			render();
			gsm.update();
			long t1 = System.currentTimeMillis();
			if(t1-t0 < 1000.0 / TARGET_FPS){
				while(t1-t0 < 1000.0 / TARGET_FPS){
					t1 = System.currentTimeMillis();
				}
			}
		}
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
	/**
	 * Renders the current map and all the blocks in it
	 */
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.clearRect(0,0,getWidth(), getHeight());
		gsm.getGameState().draw(g);
		g.dispose();
		bs.show();
	}
}
