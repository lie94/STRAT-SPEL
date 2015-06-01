package square;

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
	private Team t;
	private int imp = 0;
	public Square(final int type){
		if(type < 0 || type > 5){
			throw new IllegalArgumentException("A square can only be of type 0-5. type = " + type);
		}
		this.type = type;
	}
	public int getType(){
		return type;
	}
	public int getImp(){
		return imp;
	}
	public Team getTeam(){
		return t;
	}
	public void setImp(int imp){
		this.imp = imp;
	}
	@Override
	public String toSaveFormat(StringBuilder s) {
		s.append(type + ", " + imp);
		return null;
	}
}
