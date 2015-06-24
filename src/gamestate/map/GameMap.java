package gamestate.map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import java.util.Collections;

import nav.Pos;
import nav.Screen;
import square.Square;
import units.Settler;
import utility.Utility;
import gamestate.GameState;
import gamestate.GameStateManager;

public class GameMap extends GameState{
	private Square[][] squares;
	public static int MAX_WIDTH, MAX_HEIGHT;
	public static final int STD_SQUARE_SIZE = 60;
	private static int SQUARE_SIZE;
	private boolean[] moveScreen = new boolean[4];
	private MiniMap minimap;
	// Grid
	private boolean showGrid;
	private static Color gridColor;
	
	// Shift screen when pressing left click
	private Pos shiftScreen;
	private boolean setSSNull;
	// Zoom with mouse wheel
	private int mouseWheelRot = 0;
	// Highligt square
	private Pos highlightedSquare;
	private static Color highlight;
	// Select square to be highlighted
	private Pos testSquare;
	private Pos startPos;
	// Decides if info should be shown
	private boolean showInfo;
	
	public GameMap(Screen s, GameStateManager gsm) throws IOException {
		super(s,gsm);
		this.s = s;
		SQUARE_SIZE = STD_SQUARE_SIZE; 
		squares = generateMap(true);
		MAX_WIDTH = squares.length * SQUARE_SIZE;
		MAX_HEIGHT = squares[0].length * SQUARE_SIZE;
		minimap = new MiniMap(s,this);
		showGrid = false;
		if(gridColor == null)
			gridColor = new Color(165,140,62,128);
		if(highlight == null)
			highlight = new Color(0,0,0,128);
		highlightedSquare = null;
		showInfo = false;
		Random rn = new Random();
		int max = 10;
		for(int i = 0; i < max; i++){
			Square sq = squares[rn.nextInt(squares.length)][rn.nextInt(squares[0].length)];
			if(!sq.hasUnit() && sq.isLand())
				 new Settler(sq);
			else
				max++;
		}
			
			
	}
	public GameMap(int width, int height, Screen s, GameStateManager gsm) throws IOException {
		super(s,gsm);
		squares = new Square[width][height];
		this.s = s;
		SQUARE_SIZE = STD_SQUARE_SIZE; 
		squares = generateMap(false);
		MAX_WIDTH = squares.length * SQUARE_SIZE;
		MAX_HEIGHT = squares[0].length * SQUARE_SIZE;
		showGrid = false;
		if(gridColor == null)
			gridColor = new Color(165,140,62,96);
		highlightedSquare = null;
	}
	@Override
	public void update() {
		minimap.update();
		for(int i = 0; i < 4; i++) {
			if(moveScreen[i]){
				s.move(i,26);
			}
		}
		if(shiftScreen != null) {
			Pos temp = new Pos(MouseInfo.getPointerInfo().getLocation());
			Pos ScreenPos = getScreenPos();
			temp.add(ScreenPos.changeSign());
			s.add(shiftScreen.sub(temp));
			shiftScreen = temp.clone();
			s.correctPos();
		}
		if(setSSNull) {
			shiftScreen = null;
			setSSNull = false;
		}
		changeSquareSize(mouseWheelRot);
	}
	@Override
	public void draw(Graphics g) {
		Pos p = null;
		int isIn;
		for(int i = 0; i < squares.length; i++){
			for(int j = 0; j < squares[0].length; j++){
				p = getPosOfSquare(i,j);
				isIn = GameStateManager.s.isSquareIn(p); 
				if(	 isIn != 0){
					if(isIn == 1)
						p.minus(GameStateManager.s);
					else
						if(GameStateManager.s.getX() < 0)
							p.addX(-MAX_WIDTH).minus(GameStateManager.s);
						else
							p.addX(MAX_WIDTH).minus(GameStateManager.s);
					drawSquare(g,p,i,j);
				}
			}
		}
		// Draw highlight on square
		if(highlightedSquare != null){
			p = getPosOfSquare((int) highlightedSquare.getX(),(int) highlightedSquare.getY());
			isIn = GameStateManager.s.isSquareIn(p); 
			if(	 isIn != 0){
				if(isIn == 1)
					p.minus(GameStateManager.s);
				else
					if(GameStateManager.s.getX() < 0)
						p.addX(-MAX_WIDTH).minus(GameStateManager.s);
					else
						p.addX(MAX_WIDTH).minus(GameStateManager.s);
				g.setColor(highlight);
				for(int i = 0; i < 3; i ++){
					g.drawLine(	(int) p.getX()						- 1 + i		, (int) p.getY()	- 1 + i						, 
								(int) p.getX() + getSquareSize() 	+ 1 - i		, (int) p.getY() 	- 1 + i);
					
					g.drawLine(	(int) p.getX() + getSquareSize()	+ 1 - i		, (int) p.getY()					+ i	, 
								(int) p.getX() + getSquareSize() 	+ 1 - i 	, (int) p.getY() + getSquareSize() 	- i);
					
					g.drawLine(	(int) p.getX()						- 1 + i		, (int) p.getY()	 				+ i	,
								(int) p.getX() 						- 1 + i		, (int) p.getY() + getSquareSize() 	- i);
					
					
					
					g.drawLine(	(int) p.getX()						- 1 + 1		, (int) p.getY() + getSquareSize() + 1 - i		, 
								(int) p.getX() + getSquareSize() 	+ 1 - i		, (int) p.getY() + getSquareSize() + 1 - i);
				}
				
			}
		}
		
		minimap.draw(g);
		if(showInfo)
			drawInfo(g);
	}
	public StringBuilder save(StringBuilder s) {
		String new_line = System.getProperty("line.separator");
		s.append("SX LENGTH: " + squares.length);
		s.append(new_line);
		s.append("SY LENGTH: " + squares[0].length);
		s.append(new_line);
		for(int x = 0; x < squares.length; x++){
			for(int y = 0; y < squares[0].length; y++){
				//s.append("(" + x + ", " + y + "): ");
				squares[x][y].save(s);
				s.append(new_line);
			}
		}
		return s;
	}
	@Override
	public void endTurn() {
		// TODO Auto-generated method stub
	}
	@Override
	public void sendKeyboardPress(int k) {
		switch(k){
		case 38:
			moveScreen[0] = true;
			break;
		case 39:
			moveScreen[1] = true;
			break;
		case 40:
			moveScreen[2] = true;
			break;
		case 37:
			moveScreen[3] = true;
			break;
		case KeyEvent.VK_ESCAPE:
			Utility.save(this,"test");
			stop();
			break;
		case KeyEvent.VK_G:
			showGrid = !showGrid;
			break;
		case 0:
			showInfo = !showInfo;
		}
	}
	@Override
	public void sendKeyboardRelease(int k) {
		switch(k){
		case 38:
			moveScreen[0] = false;
			break;
		case 39:
			moveScreen[1] = false;
			break;
		case 40:
			moveScreen[2] = false;
			break;
		case 37:
			moveScreen[3] = false;
			break;
		}
	}
	@Override
	public void sendMousePress(final int k, int x, int y) {
		if(minimap.isInMiniMap(x,y)){
			x = x - (int) minimap.getPos().getX() - minimap.getThickness();
			y = y - (int) minimap.getPos().getY() - minimap.getThickness();
			if(x > 0 && y > 0 && x <= minimap.getSize().getX() && y <= minimap.getSize().getY())
				minimap.sendMousePress(k,x,y);
			return;
		}
		if(k == 1){
			testSquare = mousePosToSquarePos(x,y);
			startPos = s.clone();
			shiftScreen = new Pos(x,y);
		}
	}
	@Override
	public void sendMouseRelease(int k, int x, int y) {
		if(minimap.isInMiniMap(x,y)){
			x = x - (int) minimap.getPos().getX() - minimap.getThickness();
			y = y - (int) minimap.getPos().getY() - minimap.getThickness();
			if(x > 0 && y > 0 && x <= minimap.getSize().getX() && y <= minimap.getSize().getY())
				minimap.sendMouseRelease(k,x,y);
		}
		if(k == 1){
			setSSNull = true;
			if(testSquare.equals(mousePosToSquarePos(x,y)) && startPos.sub(s).length() < 2)
				highlightedSquare = testSquare.clone();
		}else if(k == 3 && squares[highlightedSquare.getiX()][highlightedSquare.getiY()].hasUnit()){
			if(squares[highlightedSquare.getiX()][highlightedSquare.getiY()]
					.getUnit().move(mousePosToSquare(x,y)))
				highlightedSquare = mousePosToSquarePos(x,y);
		}
			
	}
	@Override
	public void sendMouseWheel(int k, int x, int y) {
		mouseWheelRot = k;
	}
	public Square[][] getSquares() {
		return squares;
	}
	public Square mousePosToSquare(int x, int y){
		Pos p = mousePosToSquarePos(x,y);
		return squares[p.getiX()][p.getiY()];
	}
	public Pos mousePosToSquarePos(Pos p){
		return mousePosToSquarePos((int) p.getX(), (int) p.getY());
	}
	public Pos mousePosToSquarePos(int x, int y){
		Pos temp = s.clone();
		temp.add(x, y);
		if(temp.getiX() >= MAX_WIDTH){
			temp.add(-MAX_WIDTH, 0);
		}
		if(temp.getiX() < 0){
			temp.add(MAX_WIDTH, 0);
		}
		return mapPosToSquarePos(temp);
	}
	public Pos mapPosToSquarePos(Pos p){
		
		return new Pos((p.getX() / MAX_WIDTH) * squares.length , (p.getY() / MAX_HEIGHT) * squares[0].length);
	}
	public static int getSquareSize() {
		return SQUARE_SIZE;
	}
	public static Square[][] generateMap(boolean b){
		if(b){
			return generateDFSMap();
		}
		return generateBlockMap();
	}
	private static Square[][] generateDFSMap(){
		Square[][] map = new Square[160][100];
		map = (Square[][]) Utility.setAllElements(new Square(0),map);
		
		int number_of_islands = 40;
		int island_size;
		Random rn = new Random();
		boolean [][] visited;
		for(int i = 0; i < number_of_islands; i++){
			visited = new boolean[map.length][map[0].length];
			island_size = 300 + rn.nextInt(500);
			Pos start = new Pos(rn.nextInt(map.length),rn.nextInt(map[0].length));
			int type = getType(start.getiY(),map[0].length);
			for(int count = 0; count < island_size; count++){
				dfs(start,type,map,visited,rn);
			}
		}
		return map;
	}
	private static void dfs(Pos start, int type, Square[][] map, boolean[][] visited, Random rn){
		if(!visited[start.getiX()][start.getiY()] && rn.nextBoolean()){
			visited[start.getiX()][start.getiY()] = true;
			map[start.getiX()][start.getiY()] = new Square(type);
			return;
		}
		ArrayList<Pos> temp_list = Utility.getNeighbors(start, visited.length, visited[0].length);
		Collections.shuffle(temp_list);
		for(Pos p: temp_list){
			if(!visited[p.getiX()][p.getiY()] && rn.nextBoolean()){
				visited[p.getiX()][p.getiY()] = true;
				map[p.getiX()][p.getiY()] = new Square(type);
				return;
			}else if(rn.nextBoolean()){
				dfs(p,type,map,visited,rn);
				return;
			}
		}
	}
	private static Square[][] generateBlockMap(){
		Square[][] temp = new Square[160][100];
		temp = (Square[][]) Utility.setAllElements(new Square(0),temp);
		Random rn = new Random();
		int radius = temp[1].length / 5;
		int points = 25 + rn.nextInt(11);
		for(int i = 0; i < points; i++){
			int x = rn.nextInt(temp.length);
			int y = rn.nextInt(temp[0].length);
			int l_limit = (y + 5 + rn.nextInt(radius + 1));
			if(l_limit >= temp[0].length){
				l_limit = temp[0].length;
			}
			int type = getType(y,l_limit,temp[0].length);
			temp[x][y] = new Square(type);
			int last_k_limit = x + radius + rn.nextInt(5);
			int k_limit;
			int last_k = x;
			//Shallower water on top
			int modifier = rn.nextInt(2);
			for(int temp_x = last_k - 2 - modifier; temp_x < last_k_limit + + 2 + rn.nextInt(2) + modifier; temp_x++){
				if(y - 1 >= 0 && y - 1 < temp[0].length && temp[Utility.mod(temp_x,temp.length)][y - 1].getType() == 0){
					temp[Utility.mod(temp_x,temp.length)][y - 1] = new Square(1);
				}
			}
			for(int l = y; l < l_limit; l++){
				k_limit = last_k_limit - 2 + rn.nextInt(5);
				int k = last_k;
				if(last_k_limit < k_limit){
					k += rn.nextInt((k_limit - last_k_limit) + 1);
				}else{
					k += - rn.nextInt(last_k_limit - k_limit + 1);
				}
				
				//Shallower water at the left side
				modifier = rn.nextInt(2) + 3 + Utility.abs(last_k - k);
				for(int n = k - modifier; n < k; n++){
					if(temp[Utility.mod(n,temp.length)][l].getType() == 0)
						temp[Utility.mod(n,temp.length)][l] = new Square(1);
				}
				// Right side
				modifier = rn.nextInt(2) + 2 + Utility.abs(last_k_limit - k_limit);
				for(int n = k_limit; n < k_limit + modifier; n++){
					if(temp[Utility.mod(n,temp.length)][l].getType() == 0)
						temp[Utility.mod(n,temp.length)][l] = new Square(1);
				}
				//Stop shallow water
				last_k = k;
				for(; k < k_limit; k++){
					temp[Utility.mod(k,temp.length)][l] = new Square(type);
				}
				last_k_limit = k_limit;
			}
			//Shallower water on the bottom
			modifier = rn.nextInt(3);
			for(int temp_x = last_k - 2 - modifier; temp_x < last_k_limit + 1 + rn.nextInt(2) + modifier; temp_x++){
				if(l_limit >= 0 && l_limit < temp[0].length && temp[Utility.mod(temp_x,temp.length)][l_limit].getType() == 0){
					temp[Utility.mod(temp_x,temp.length)][l_limit] = new Square(1);
				}
			}
		}
		return temp;
	}
	private void drawInfo(Graphics g) {
		if(highlightedSquare != null){
			Square sq = squares[Utility.mod((int) highlightedSquare.getX(), squares.length)][(int) highlightedSquare.getY()];
			g.setColor(Color.YELLOW);
			String hlS = "(" + Utility.mod((int) highlightedSquare.getX(), squares.length) + ", " + (int) highlightedSquare.getY() + ")";
			int a = g.getFontMetrics().stringWidth(hlS);
			if(sq.hasUnit()){
				int b = g.getFontMetrics().stringWidth(sq.getUnit().getName());
				g.fillRect(0, 0, a > b ? a : b, 40);
			}else{
				int b = g.getFontMetrics().stringWidth("" + sq.getType());
				g.fillRect(0, 0, a > b ? a : b, 30);
			}
			g.setColor(Color.BLACK);
			g.drawString(hlS					, 0	, 10);
			g.drawString("" + sq.getType()		, 0	, 20);
			g.drawString("" + getSquareSize()	, 0	, 30);
			if(sq.hasUnit()){
				g.drawString(sq.getUnit().getName(), 0, 40);
			}
		} else{
			g.setColor(Color.YELLOW);
			g.fillRect(0, 0, g.getFontMetrics().stringWidth("No highlighted square"), 20);;
			g.setColor(Color.BLACK);
			g.drawString("No highlighted square", 0	, 10);
			g.drawString("" + getSquareSize()	, 0	, 20);
		}
		
	}
	private void changeSquareSize(int i) {
		if(mouseWheelRot != 0){
			if(i == -1){
				setSquareSize(1.2 * SQUARE_SIZE);
			}else{
				setSquareSize(0.8 * SQUARE_SIZE);
			}
		}else{
			return;
		}
		mouseWheelRot = 0;
	}
	private void setSquareSize(double d) {
		if(d < 40)
			d = 40;
		if(d > 150)
			d = 150;
		Pos oldMiddle = s.getRelativeMiddle();
		GameMap.SQUARE_SIZE = (int) d;
		updateScreenDependency();
		s.setPos(new Pos(oldMiddle.getX() * MAX_WIDTH - Screen.WIDTH / 2, oldMiddle.getY() * MAX_HEIGHT - Screen.HEIGHT / 2));
	}
	private Pos getPosOfSquare(final int i, final int j) {
		return new Pos(SQUARE_SIZE * i, SQUARE_SIZE * j);
	}
	private void updateScreenDependency() {
		MAX_WIDTH = squares.length * SQUARE_SIZE;
		MAX_HEIGHT = squares[0].length * SQUARE_SIZE;
	}
	private void drawSquare(Graphics g, final Pos p, final int x, final int y) {
		//if(getSquareSize() < 100) {
			g.setColor(squares[x][y].getColor());
			g.fillRect((int) p.getX(), (int) p.getY(), (int) getSquareSize(), (int) getSquareSize());
		/*} else {
			Pos temp = StatFunc.typeToMapPart(squares[x][y]);
			g.drawImage(tiles, 
					(int) p.getX()						, (int) p.getY()					,
					(int) p.getX() + getSquareSize()	, (int) p.getY() + getSquareSize(),
					(int) temp.getX()					, (int) temp.getY()					,
					(int) temp.getX() + 500				, (int) temp.getY() + 500		
					,null);
		}*/
		if(squares[x][y].hasUnit()){
			squares[x][y].getUnit().draw(g, s, (int) p.getX(), (int) p.getY()); 
		}
		if(showGrid){
			g.setColor(gridColor);
			//g.drawLine((int) p.getX() + getSquareSize()	, (int) p.getY()					, (int) p.getX() + getSquareSize()	, (int) p.getY() + getSquareSize());
			g.drawLine((int) p.getX()					, (int) p.getY()					, (int) p.getX()					, (int) p.getY() + getSquareSize());
			g.drawLine((int) p.getX()					, (int) p.getY()					, (int) p.getX() + getSquareSize()	, (int) p.getY());
			//g.drawLine((int) p.getX()					, (int) p.getY() + getSquareSize()	, (int) p.getX() + getSquareSize()	, (int) p.getY() + getSquareSize());
		}
		
	}
	private static int getType(int y, int y_end, int y_max){
		double temp = Utility.avrage(y,y_end);
		return getType((int) temp,y_max);
		
	}
	private static int getType(int y, int y_max){
		double procent = 2 * Utility.abs((y_max / 2.0) - y) / y_max;
		Random rn = new Random();
		if(procent > 0.85){
			if(procent < 0.90 && rn.nextInt(2) == 0){
				return 5; //TUNDRA
			}
			return 3; //ICE
		}else if(procent > 0.40){
			if(procent < 0.60 && rn.nextInt(2) == 0){
				return 2;
			}
			return 5;
		}else if(procent < 0.15 && rn.nextInt(2) == 0){
			return 4;
		}else{
			return 2;
		}
	}
}
