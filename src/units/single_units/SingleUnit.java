package units.single_units;

import square.Square;
import units.Unit;

public abstract class SingleUnit extends Unit{
	protected Square square;
	SingleUnit(Square s){
		super();
		square = s;
	}
}
