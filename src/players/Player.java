package players;

import intrface.Refresh;
import intrface.SaveAble;

import java.util.ArrayList;

import units.Team;

public abstract class Player implements SaveAble,Refresh{
	protected ArrayList<Team> teams;
	Player(){
		teams = new ArrayList<>();
	}
	public void addTeam(Team t){
		teams.add(t);
	}
	public ArrayList<Team> getTeams(){
		return teams;
	}
}
