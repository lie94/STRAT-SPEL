package square;

import java.awt.Color;

import intrface.SaveAble;
import units.Team;

/**
 * There are 6 different types of squares
 * Type 	| Meaning
 * ---------|--------------
 * 0		|Ocean
 * 1		|Shallow water
 * 2 		|Grass
 * 3		|Ice
 * 4		|Sand
 * 5		|Tundra
 * @author Felix
 *
 */
public class Square implements Cloneable, SaveAble{
	private int type;
	private int imp = 0;
	private Team team;
	public Square(final int type){
		if(type < 0 || type > 5){
			throw new IllegalArgumentException("A square can only be of type 0-5. type = " + type);
		}
		this.type = type;
	}
	public Team getTeam(){
		return team;
	}
	public void setTeam(Team t){
		team = t;
	}
	public int getType(){
		return type;
	}
	public int getImp(){
		return imp;
	}
	public void setImp(int imp){
		this.imp = imp;
	}
	@Override
	public String toSaveFormat(StringBuilder s) {
		s.append(type + ", " + imp);
		return null;
	}
	public Color getColor(){
		return getColor(type);
	}
	public static Color getColor(int i){
		switch(i){
		case 0:
			return new Color(33,60,255);
		case 1:
			return new Color(81,188,255);
		case 2:
			return new Color(38,127,1);
		case 3:
			return new Color(127,255,255);
		case 4:
			return new Color(245,254,145);
		case 5:
			return new Color(128,128,128);
		}
		return null;
	}
}
