package nav;

public class Vector extends Pos{
	Vector(double x, double y){
		this.x = x;
		this.y = y;
	}
	public double length(){
		return Math.sqrt((Math.pow(x, 2) + Math.pow(y, 2)));
	}
	public Vector norm(){
		return norm(this);
	}
	public static Vector norm(Vector v){
		double length = v.length();
		v.setX(v.getX() / length);
		v.setY(v.getY() / length);
		return v;
	}
	public Vector multiplyLength(double d){
		if(d < 0){
			x = -x;
			y = -y;
			d = -d;
		}
		x *= Math.sqrt(d);
		y *= Math.sqrt(d);
		return this;
	}
	public Vector setLength(double d){
		norm();
		multiplyLength(d);
		return this;
	}
}
