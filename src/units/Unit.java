package units;

public class Unit {
	private int health;
	private int attack;
	public Unit(int health, int attack){
		this.health = health;
		this.attack = attack;
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
