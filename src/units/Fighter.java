package units;

import java.util.Random;

public class Fighter extends Unit{
	protected final int [] base_stats;  //0 attack 1 defence 2 health 3 inteligence
	protected int [] curr_stats;
	protected Item[] items;
	public Fighter(){
		Random rn = new Random();
		base_stats = new int[4];
		items = new Item[5];
		for(int i = 0; i < base_stats.length; i++){
			base_stats[i] = 5 + rn.nextInt(6);
		}
		refreshStats();
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
}
