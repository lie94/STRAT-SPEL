package intrface;

import java.awt.Graphics;

public interface Refresh {
	/**
	 * Updates images and things that need to be updated each refresh cycle
	 */
	public void update();
	/**
	 * Paints the current object onto the graphics g
	 * @param g
	 */
	public void draw(Graphics g);
	/**
	 * Changes everything to a new turn
	 */
	public void newTurn();
}
