package nav;

public class Pos {
	protected double x,y;
	public Pos(int x, int y){
		this.x = x;
		this.y = y;
	}
	public Pos(double x, double y){
		this.x = x;
		this.y = y;
	}
	public Pos(){
		x = 0;
		y = 0;
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public void setX(double x){
		this.x = x;
	}
	public void setY(double y){
		this.y = y;
	}
	public void add(double x, double y){
		addX(x);
		addY(y);
	}
	public void addX(double x){
		this.x += x;
	}
	public void addY(double y){
		this.y += y;
	}
	public Pos minus(Pos p){
		x -= p.getX();
		y -= p.getY();
		return this;
	}
	public Pos plus(Pos p){
		x += p.getX();
		y -= p.getY();
		return this;
	}
	public String toString(){
		return "(" + x + ", " + y + ")";
	}
}
