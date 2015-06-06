package units;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

public class Unit {
	protected final int [] base_stats;  //0 attack 1 defence 2 health 3 inteligence
	protected int [] curr_stats;
	protected Item[] items;
	public static BufferedImage spites;
	protected String name;
	protected boolean isMale;
	public Unit(){
		if(spites == null)
			try {
				spites = ImageIO.read(getClass().getResourceAsStream("/res/img/sprites.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		Random rn = new Random();
		base_stats = new int[4];
		items = new Item[5];
		for(int i = 0; i < base_stats.length; i++){
			base_stats[i] = 5 + rn.nextInt(6);
		}
		if(new Random().nextBoolean())
			isMale = true;
		else
			isMale = false;
		refreshStats();
		name = generateName();
	}
	public String toString(){
		return name;
	}
	/**
	 * If there is no item in the slot, return null
	 * else return the item
	 * 
	 * There are 5 slots
	 * - 0 Head
	 * - 1 Chest
	 * - 2 Hands
	 * - 3 Legs
	 * - 4 Feet
	 * @return
	 */
	public Item setItem(Item i){
		Item temp = items[i.getSlot()];
		items[i.getSlot()] = i;
		refreshStats();
		return temp;
	}
	public int[] getStats(){
		return curr_stats;
	}
	private void refreshStats(){
		curr_stats = base_stats.clone(); 
		for(Item i : items){
			if(i != null)
				for(units.Stat s : i.getStats()){
					curr_stats[s.getStat()] += s.getValue();
				}
		}
	}
	private String generateName(){
		Random rn = new Random();
		StringBuilder s = new StringBuilder();
		List<String> temp;
		boolean prefix = rn.nextBoolean();
		if(prefix){
			try {
				temp = Files.readAllLines(Paths.get("src/res/text/prefix.txt"), StandardCharsets.UTF_8);
				s.append(temp.get(rn.nextInt(temp.size())));
			} catch (IOException e) {
				s.append("Nullis");
				e.printStackTrace();
			}
			s.append(" ");
		}
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
		if(!prefix){
			s.append(" ");
			try {
				temp = Files.readAllLines(Paths.get("src/res/text/postfix.txt"), StandardCharsets.UTF_8);
				s.append(temp.get(rn.nextInt(temp.size())));
			} catch (IOException e) {
				s.append("Nullis");
				e.printStackTrace();
			}
		}
		return s.toString();
	}
}
