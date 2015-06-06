package units;

import square.Square;
import intrface.SaveAble;

public class Team implements SaveAble {
	private Unit[] team = new Unit[3];
	private Square square;
	public Team(){}
	public boolean addUnit(Unit u){
		for(int i = 0; i < 3; i++){
			if(team[i] == null){
				team[i] = u;
				return true;
			}
		}
		return false;
	}
	public Square getSquare(){
		return square;
	}
	public void setSquare(Square s){
		square = s;
	}
	public boolean removeUnit(Unit u){
		for(int i = 0; i < 3; i++){
			if(team[i].equals(u)){
				team[i] = null;
				if(i != 2)
					sort();
				return true;	
			}
		}
		return false;
	}
	private void sort(){
		for(int i = 0; i < 2; i++){
			if(team[i] == null){
				for(int j = i + 1; team[i] == null; j++){
					if(team[j] != null){
						team[i] = team[j];
						team[j] = null;
					}
				}
			}
		}
	}
	@Override
	public String toSaveFormat(StringBuilder s) {
		return null;
	}
	public Unit getUnit(int i){
		return team[i];
	}
	public String toString(){
		StringBuilder s = new StringBuilder();
		s.append("(");
		s.append(team[0]);
		if(team[1] != null){
			s.append(", ");
			s.append(team[1]);
		}
		if(team[2] != null){
			s.append(", ");
			s.append(team[2]);
		}
		s.append(")");
		return s.toString();
	}
}
