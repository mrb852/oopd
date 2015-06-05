import java.awt.Point;
import java.awt.Rectangle;


public class Player extends Rectangle {

	
	private Point velocity;
	private int speed;
	
	public Player(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		this.velocity = new Point(0, 0);
	}
	
	public void update() {
		this.x += velocity.x;
		this.y += velocity.y;
	}
	
	public void setVelocity(Point newVel) {
		this.velocity = newVel;
	}
	
	public Point getVelocity() {
		return this.velocity;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
}
