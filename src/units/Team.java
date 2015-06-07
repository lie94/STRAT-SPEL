package units;

import java.util.ArrayList;

import square.Square;
import intrface.SaveAble;

public class Team implements SaveAble {
	private ArrayList<Unit> team;
	private Square square;
	public Team(){
		team = new ArrayList<Unit>();
	}
	public boolean add(Unit u){
		if(team.size() >= 3){
			return false;
		}
		team.add(u);
		return true;
	}
	public Square getSquare(){
		return square;
	}
	public void setSquare(Square s){
		square = s;
	}
	public boolean remove(Unit u){
		return team.remove(u);
	}
	@Override
	public String toSaveFormat(StringBuilder s) {
		return null;
	}
	public ArrayList<Unit> getUnits(){
		return team;
	}
	public Team generateRandom(){
		team.add(new Unit());
		team.add(new Unit());
		team.add(new Unit());
		return this;
	}
	public String toString(){
		if(team.size() == 0){
			return "()";
		}
		StringBuilder s = new StringBuilder();
		s.append("(");
		s.append(team.get(0));
		if(team.size() > 1){
			for(int i = 1; i < team.size(); i++){
				s.append(", ");
				s.append(team.get(i));
			}
		}
		s.append(")");
		return s.toString();
	}
}
