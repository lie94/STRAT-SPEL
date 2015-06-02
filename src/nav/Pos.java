package nav;

import java.awt.Point;

public class Pos implements Cloneable{
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
	public Pos setPos(double x, double y){
		this.x = x;
		this.y = y;
		return this;
	}
	public Vector sub(Pos p){
		return new Vector(x - p.getX(), y - p.getY());
	}
	public Vector sub(Point p){
		return new Vector(x - p.getX(), y - p.getY());
	}
	public Pos setPos(Point p){
		x = p.getX();
		y = p.getY();
		return this;
	}
	public Pos add(Pos p){
		x += p.getX();
		y += p.getY();
		return this;
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
	public Pos add(double x, double y){
		addX(x);
		addY(y);
		return this;
	}
	public Pos addX(double x){
		this.x += x;
		return this;
	}
	public Pos addY(double y){
		this.y += y;
		return this;
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
	public Vector toVector(){
		return new Vector(x,y);
	}
	public String toString(){
		return "(" + x + ", " + y + ")";
	}
}
