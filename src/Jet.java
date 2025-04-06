import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;


public class Jet extends Rectangle  {

	private boolean aKey, dKey, rightKey, leftKey;
	int playerNum;
	double angle = 0;
	

	
	public Jet(int x, int y, int width, int height, int playerNum)  {
		super(x,y,width,height); //automatically does the bottom part with rectangle constructor 
	/*	this.width = width; 
		this.height = height;
		this.x = x;
		this.y = y;*/
		this.playerNum = playerNum; 
	
	}
	
	public void drawJet(Graphics g) {
		Graphics2D g2D = (Graphics2D)g;
		
		if(playerNum ==1) {
			
			g2D.setColor(Color.BLUE);
			AffineTransform original = g2D.getTransform(); //get the original transformations
			
			
			g2D.translate(x+width/2, y+height/2); //translates the origin to the center of the rectangle 
			g2D.rotate(Math.toRadians(angle)); //rotates about the origin
			g2D.fillRect(-width/2,-height/2,width,height);	//fills the rectangle with regard to the new origin
			g2D.setTransform(original); // sets everything back to normal 
			
			
		}else {
			g2D.setColor(Color.RED);
			AffineTransform original = g2D.getTransform();
			
			
			g2D.translate(x+width/2, y+height/2);
			g2D.rotate(Math.toRadians(angle));
			g2D.fillRect(-width/2,-height/2,width,height);	
			g2D.setTransform(original);
			
			
		}
	}
	
	public void moveJet() {
		if(playerNum ==1) {
			y-=GamePanel.JET_SPEED1*Math.cos(Math.toRadians(angle));
			x+=GamePanel.JET_SPEED1*Math.sin(Math.toRadians(angle));	
		}
		if(playerNum ==2) {
			y-=GamePanel.JET_SPEED2*Math.cos(Math.toRadians(angle));
			x+=GamePanel.JET_SPEED2*Math.sin(Math.toRadians(angle));	
		}
		if(dKey && playerNum ==1) {
			angle+=GamePanel.ANGLE_CHANGE;
		}
		if(aKey && playerNum ==1) {
			angle-=GamePanel.ANGLE_CHANGE;
		}
		if(rightKey && playerNum ==2) {
			angle+=GamePanel.ANGLE_CHANGE;
		}
		if(leftKey && playerNum ==2) {
			angle-=GamePanel.ANGLE_CHANGE;
			
		}
		
		
	}
	
	
	

	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == 'a') {
			aKey = true;
		}
		if(e.getKeyChar() == 'd') 
			dKey = true;
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			leftKey = true;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			rightKey = true;
		
	}
	
	public void keyReleased(KeyEvent e) {
		
		if(e.getKeyChar() == 'a') 
			aKey = false;
		if(e.getKeyChar() == 'd') 
			dKey = false;
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			leftKey = false;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			rightKey = false;
	}
	
	
}



