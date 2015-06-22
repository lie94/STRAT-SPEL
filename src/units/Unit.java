package units;

import java.awt.Graphics;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import nav.Screen;
import square.Square;

public abstract class Unit{
	protected String name;
	protected boolean isMale;
	protected boolean canFight;
	protected Square square;
	
	protected Unit(boolean can_fight, Square s) throws IllegalArgumentException{
		isMale = new Random().nextBoolean();
		name = generateName();
		canFight = can_fight;
		if(!setSquare(s)){
			throw new IllegalArgumentException("The given square is occupied");
		}
	}
	public boolean canFight(){
		return canFight;
	}
	public boolean move(Square s){
		if(s.hasUnit()){
			return false;
		}
		square.setUnit(null);
		s.setUnit(this);
		square = s;
		return true;
	}
	public abstract String getName();
	public boolean setSquare(Square s){
		if(!s.hasUnit()){
			s.setUnit(this);
			square = s;
			return true;
		}
		return false;
	}
	public abstract void draw(Graphics g, Screen s, int x, int y);
	private String generateName(){
		Random rn = new Random();
		StringBuilder s = new StringBuilder();
		List<String> temp;
		//boolean prefix = rn.nextBoolean();
		/*if(prefix){
			try {
				temp = Files.readAllLines(Paths.get("src/res/text/prefix.txt"), StandardCharsets.UTF_8);
				s.append(temp.get(rn.nextInt(temp.size())));
			} catch (IOException e) {
				s.append("Nullis");
				e.printStackTrace();
			}
			s.append(" ");
		}*/
		if(isMale){
			try {
				temp = Files.readAllLines(Paths.get("src/res/text/male_names.txt"), StandardCharsets.UTF_8);
				s.append(temp.get(rn.nextInt(temp.size())));
			} catch (IOException e) {
				s.append("Nullis");
				e.printStackTrace();
			}
		}else{
			try {
				temp = Files.readAllLines(Paths.get("src/res/text/female_names.txt"), StandardCharsets.UTF_8);
				s.append(temp.get(rn.nextInt(temp.size())));
			} catch (IOException e) {
				s.append("Nullis");
				e.printStackTrace();
			}
		}
		/*if(!prefix){
			
			s.append(" ");
			try {
				temp = Files.readAllLines(Paths.get("src/res/text/postfix.txt"), StandardCharsets.UTF_8);
				s.append(temp.get(rn.nextInt(temp.size())));
			} catch (IOException e) {
				s.append("Nullis");
				e.printStackTrace();
			}
		}*/
		return s.toString();
	}
	public abstract void fight(Unit u);
	public abstract void ability();
}
