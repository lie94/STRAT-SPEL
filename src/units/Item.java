package units;

public class Item {
	private Stat[] stats;
	private final int slot;
	Item(int slot, Stat ... stats){
		this.slot = slot;
		this.stats = stats;
	}
	public int getSlot(){
		return slot;
	}
	public Stat[] getStats(){
		return stats;
	}
	
}

