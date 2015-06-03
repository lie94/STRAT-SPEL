package units;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Unit {
	protected int health;
	protected int attack;
	protected static BufferedImage spites;
	public Unit(int health, int attack){
		this.health = health;
		this.attack = attack;
		if(spites == null)
			try {
				spites = ImageIO.read(getClass().getResourceAsStream("/res/img/spites.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	public Unit(){
		health = 0;
		attack = 0;
	}
	public int getAttack(){
		return attack;
	}
	public int getHealth(){
		return health;
	}
}
