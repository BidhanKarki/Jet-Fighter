import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet extends Rectangle{
	
	//double x,y;
	double anglePath; //in degrees

	public Bullet(double x, double y, double anglePath) {
		
		super((int)x, (int) y, GamePanel.BULLET_DIAMETER, GamePanel.BULLET_DIAMETER);
		//this.x = x;
		//this.y = y;
		this.anglePath = anglePath;
	}
	
	public void drawBullet(Graphics g) {
		g.fillOval((int)x,(int)y,GamePanel.BULLET_DIAMETER,GamePanel.BULLET_DIAMETER );	
	}
	
	
	
}



