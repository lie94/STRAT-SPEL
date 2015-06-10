package animations;

import java.io.IOException;

public class CharAnimation extends Animation{
	//private Unit unit;
	public CharAnimation() {}

	CharAnimation(boolean b) throws IOException {
		if(b){
			init(4,100,160,"char");
		}
	}
	
}
