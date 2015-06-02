package main;

import intrface.Refresh;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import nav.Screen;

public class MiniMap extends JFrame implements Refresh{
	private static final long serialVersionUID = 1L;
	private BufferedImage background;
	//private Screen s;
	private static int WIDTH, HEIGHT;
	public MiniMap(BufferedImage miniMap) {
		super("MINIMAP");
		setDefaultLookAndFeelDecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		setExtendedState(JFrame.NORMAL);
		
		add(Run.frame,BorderLayout.CENTER);
		setUndecorated(true);
		pack();
		
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(Run.frame);
		
		//this.s = s;
		
		background = miniMap;
		WIDTH = (int) (Screen.WIDTH / 10);
		HEIGHT = (int) ((double) (background.getHeight()) / background.getWidth()) * WIDTH;
		setLocation(WIDTH / 8, (int) (Screen.HEIGHT - HEIGHT - WIDTH / 8));
	}
	@Override
	public void update() {}
	@Override
	public void draw(Graphics g) {
		g.drawImage(background, 0, 0, WIDTH, HEIGHT,null);
	}
	@Override
	public void newTurn() {}
}
