package units.single_units;

import java.util.ArrayList;

import square.Square;
import units.Unit;
import intrface.ControlledUnits;

public class Settler extends SingleUnit implements ControlledUnits{
	Settler(Square s) {
		super(s);
	}

	public boolean buildCity(String s){
		return false;
	}

	@Override
	public ArrayList<? extends Unit> getUnits() {
		ArrayList<Settler> temp = new ArrayList<Settler>();
		temp.add(this);
		return temp;
	}

}
