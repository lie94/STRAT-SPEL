package animations;

import java.awt.Graphics;

public abstract class Animation {
	private final int tickRate;
	protected ImageRelayer ir;
	protected int tick;
	private int sub_tick;
	Animation(final int tick_rate){
		tickRate = tick_rate;
		ir = new ImageRelayer();
	}
	public void update(){
		sub_tick++;
		if(sub_tick > tickRate){
			sub_tick = 0;
			tick = (tick + 1) % ImageRelayer.CHAR_SPRITES;
		}
	}
	int getTick(){
		return tick;
	}
	public void draw(Graphics g, int x, int y){
		ir.draw(g, x, y, this);
	}
}
