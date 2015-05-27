package units;

public class Team {
	private Unit[] team = new Unit[3];
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
}
