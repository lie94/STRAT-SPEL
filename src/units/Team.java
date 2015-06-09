package units;

import java.util.ArrayList;

import square.Square;

public class Team{
	private ArrayList<Fighter> team;
	private Square square;
	public Team(){
		team = new ArrayList<Fighter>();
	}
	public boolean add(Fighter u){
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
	public boolean remove(Fighter u){
		return team.remove(u);
	}
	public StringBuilder save(StringBuilder s) {
		return null;
	}
	public ArrayList<? extends Unit> getUnits(){
		return team;
	}
	public Team generateRandom(){
		team.add(new Fighter());
		team.add(new Fighter());
		team.add(new Fighter());
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
