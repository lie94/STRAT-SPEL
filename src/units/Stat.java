package units;

public class Stat{
	private final int stat;
	private final int value;
	public Stat(final int stat, final int value){
		this.stat = stat;
		this.value = value;
	}
	public int getValue(){
		return value;
	}
	public int getStat(){
		return stat;
	}
}
