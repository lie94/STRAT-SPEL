package animations;

import units.Fighter;
import units.Unit;

public class AnimatedChar extends Animation{
	//private Unit unit;
	AnimatedChar(int tick_rate, Fighter u) {
		super(tick_rate);
		//unit = u;
	}
	public AnimatedChar(Unit u) {
		super(5);
		//unit = u;
	}	
}
