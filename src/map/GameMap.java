package map;

import gamestate.GameStateManager;
import intrface.Refresh;
import intrface.SaveAble;
import intrface.ScreenDependent;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.StatFunc;
import nav.Pos;
import nav.Screen;
import square.Square;
import units.Unit;

public class GameMap implements Refresh,SaveAble,ScreenDependent{
	private Square[][] squares;
	public static int MAX_WIDTH, MAX_HEIGHT;
	public static final int STD_SQUARE_MAX_SIZE = 40;
	private static int SQUARE_SIZE;
	private Screen s;
	private BufferedImage tiles;
	public GameMap(final int size, Screen s) throws IOException{
		this.s = s;
		SQUARE_SIZE = STD_SQUARE_MAX_SIZE; 
		squares = StatFunc.generateMap(size);
		MAX_WIDTH = squares.length * SQUARE_SIZE;
		MAX_HEIGHT = squares[0].length * SQUARE_SIZE;
		tiles = ImageIO.read(getClass().getResourceAsStream("/res/img/tiles.jpg"));
		//Random rn = new Random();
		/*for(int i = 0; i < squares.length; i++){
			for(int j = 0; j < squares[0].length; j++){
				if(squares[i][j].getType() != 0 && squares[i][j].getType() != 1 && rn.nextInt(101) > 99){
					
					Team t = new Team();
					t.addUnit(new Unit());
					System.out.println(t.getUnit(0).toString());
					squares[i][j].setTeam(t);
					t.setSquare(squares[i][j]);
				}
			}
		}*/
	}
	public GameMap(final int size, Screen s, final int square_size){
		this.s = s;
		SQUARE_SIZE = square_size; 
		squares = StatFunc.generateMap(size);
		MAX_WIDTH = squares.length * SQUARE_SIZE;
		MAX_HEIGHT = squares[0].length * SQUARE_SIZE;
		try {
			tiles = ImageIO.read(getClass().getResourceAsStream("/res/img/tiles.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public GameMap(final int width, final int height, Screen s){
		squares = new Square[width][height];
		this.s = s;
		SQUARE_SIZE = STD_SQUARE_MAX_SIZE; 
		MAX_WIDTH = squares.length * SQUARE_SIZE;
		MAX_HEIGHT = squares[0].length * SQUARE_SIZE;
		try {
			tiles = ImageIO.read(getClass().getResourceAsStream("/res/img/tiles.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String toString(){
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < 2 * squares.length + 2; i++){
			s.append("-");
		}
		s.append(System.getProperty("line.separator"));
		for(int j = 0; j < squares[0].length; j++){
			s.append("|");
			for(int i = 0; i < squares.length; i++){
				s.append(squares[i][j].getType());
				s.append(",");
			}
			s.append("|");
			s.append(System.getProperty("line.separator"));
		}
		for(int i = 0; i < 2 * squares.length + 2; i++){
			s.append("-");
		}
		return s.toString();
	}
	@Override
	public void update() {
		updateScreenDependency();
	}
	@Override
	public void draw(Graphics g) {
		Pos p;
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
	}
	public static int getSquareSize(){
		return SQUARE_SIZE;
	}
	
	public Pos getPosOfSquare(int i, int j){
		return new Pos(SQUARE_SIZE * i, SQUARE_SIZE * j);
	}
	@Override
	public void newTurn() {
	}
	public void setSquareDim(double d){
		if((int) d * squares[0].length * 0.9 < Screen.HEIGHT || (int) d * squares.length * 0.9 < Screen.WIDTH)
			return;
		if(d > 300)
			return;
		Pos oldMiddle = s.getRelativeMiddle();
		GameMap.SQUARE_SIZE = (int) d;
		updateScreenDependency();
		s.setPos(new Pos(oldMiddle.getX() * MAX_WIDTH - Screen.WIDTH / 2, oldMiddle.getY() * MAX_HEIGHT - Screen.HEIGHT / 2));
	}
	public Square[][] getSquares(){
		return squares;
	}
	@Override
	public String toSaveFormat(StringBuilder s) {
		String new_line = System.getProperty("line.separator");
		s.append("SX LENGTH: " + squares.length);
		s.append(new_line);
		s.append("SY LENGTH: " + squares[0].length);
		s.append(new_line);
		for(int x = 0; x < squares.length; x++){
			for(int y = 0; y < squares[0].length; y++){
				//s.append("(" + x + ", " + y + "): ");
				squares[x][y].toSaveFormat(s);
				s.append(new_line);
			}
		}
		return s.toString();
	}
	private void drawSquare(Graphics g, final Pos p, final int x, final int y){
		if(getSquareSize() < 100){
			g.setColor(squares[x][y].getColor());
			g.fillRect((int) p.getX(), (int) p.getY(), (int) getSquareSize(), (int) getSquareSize());
			if(squares[x][y].getTeam() != null){
				g.drawImage(Unit.spites, (int) p.getX(), (int) p.getY(), (int) (Unit.spites.getWidth() * getSquareSize() / 500.0), (int) (Unit.spites.getHeight() *  getSquareSize() / 500.0),null);
			}
		}else{
			Pos temp = StatFunc.typeToMapPart(squares[x][y]);
			g.drawImage(tiles, 
					(int) p.getX()						, (int) p.getY()					,
					(int) p.getX() + getSquareSize()	, (int) p.getY() + getSquareSize(),
					(int) temp.getX()					, (int) temp.getY()					,
					(int) temp.getX() + 500				, (int) temp.getY() + 500		
					,null);
			if(squares[x][y].getTeam() != null){
				g.drawImage(Unit.spites, (int) p.getX(), (int) p.getY(), (int) (Unit.spites.getWidth() * getSquareSize() / 500.0), (int) (Unit.spites.getHeight() *  getSquareSize() / 500.0),null);
			}
		}
	}
	@Override
	public void updateScreenDependency() {
		MAX_WIDTH = squares.length * SQUARE_SIZE;
		MAX_HEIGHT = squares[0].length * SQUARE_SIZE;
	}
}
