import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Ability extends Rectangle {
	
	boolean isVisible = true;
	double collideTime;
	private int num;
	

	public Ability(int x, int y) {
		super(x,y,GamePanel.abilitySize,GamePanel.abilitySize);
		num  = (int) ( Math.random()* 4);
	}
	
	
	public void collide(Jet jet) {
		//jet.speedUp();
		collideTime = System.currentTimeMillis();
		isVisible = false;
		switch(num) { //equal probability to get each ability
		case 0: 
			speedUp(jet);
			break;
		case 1:
			slowDown(jet);
			break;
		case 2: 
			increaseBulletLimit(jet);
			break;
		case 3:
			decreaseBulletLimit(jet);
			break;
		}
		
		
	}
	
	
	public void speedUp(Jet jet) {
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

		if(jet.playerNum == 1) {

			GamePanel.JET_SPEED1 *= 2;
            GamePanel.BULLET_SPEED1 = GamePanel.JET_SPEED1 * 2;



			scheduler.schedule( new Runnable(){ //this essentially uses a separate thread from the game loop to execute this
			@Override
				public void run() {
				 	GamePanel.JET_SPEED1/=2;
	                GamePanel.BULLET_SPEED1 = GamePanel.JET_SPEED1 * 2;

				 	scheduler.shutdown(); 	
				}
			}, 5, TimeUnit.SECONDS);

			}
		
		
		else {
			GamePanel.JET_SPEED2 *=2;
            GamePanel.BULLET_SPEED2 = GamePanel.JET_SPEED2 * 2;

			scheduler.schedule( new Runnable(){ //this essentially uses a separate thread from the game loop to execute this
			@Override
				public void run() {
				 	GamePanel.JET_SPEED2/=2;
	                GamePanel.BULLET_SPEED2 = GamePanel.JET_SPEED2* 2;

				 	scheduler.shutdown(); 	
				}
			}, 5, TimeUnit.SECONDS);
			
			
		}
	}
	
	public void slowDown(Jet jet) {
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

		if(jet.playerNum == 1) {
			GamePanel.JET_SPEED1/=2;
            GamePanel.BULLET_SPEED1 = GamePanel.JET_SPEED1 * 2;

			scheduler.schedule( new Runnable(){ //this essentially uses a separate thread from the game loop to execute this
			@Override
				public void run() {
				 	GamePanel.JET_SPEED1*=2;
	                GamePanel.BULLET_SPEED1 = GamePanel.JET_SPEED1 * 2;

				 	scheduler.shutdown(); 	
				}
			}, 5, TimeUnit.SECONDS);
			}
		
		else {
			GamePanel.JET_SPEED2 /= 2;
            GamePanel.BULLET_SPEED2 = GamePanel.JET_SPEED2 * 2;

			scheduler.schedule( new Runnable(){ //this essentially uses a separate thread from the game loop to execute this
			@Override
				public void run() {
				 	GamePanel.JET_SPEED2*=2;
	                GamePanel.BULLET_SPEED2 = GamePanel.JET_SPEED2 * 2;

				 	scheduler.shutdown(); 	
				}
			}, 5, TimeUnit.SECONDS);
			
			
		}
	}
		
	
	public void increaseBulletLimit(Jet jet) {
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

		if(jet.playerNum == 1) {

			GamePanel.maxBullets1 = 6;

			scheduler.schedule( new Runnable(){ //this essentially uses a separate thread from the game loop to execute this
			@Override
				public void run() {
				 	GamePanel.maxBullets1 = 3;

				 	scheduler.shutdown(); 	
				}
			}, 5, TimeUnit.SECONDS);

			}
		
		
		else {
			GamePanel.maxBullets2 = 6;

			scheduler.schedule( new Runnable(){ //this essentially uses a separate thread from the game loop to execute this
			@Override
				public void run() {
					GamePanel.maxBullets2 = 3;
				 	scheduler.shutdown(); 	
				}
			}, 5, TimeUnit.SECONDS);
			
			
		}
	}
	
	public void decreaseBulletLimit(Jet jet) {
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

		if(jet.playerNum == 1) {

			GamePanel.maxBullets1 = 1;

			scheduler.schedule( new Runnable(){ //this essentially uses a separate thread from the game loop to execute this
			@Override
				public void run() {
				 	GamePanel.maxBullets1 = 3;
				 	scheduler.shutdown(); 	
				}
			}, 5, TimeUnit.SECONDS);

			}
		
		
		else {
			GamePanel.maxBullets2 = 1;

			scheduler.schedule( new Runnable(){ //this essentially uses a separate thread from the game loop to execute this
			@Override
				public void run() {
					GamePanel.maxBullets2 = 3;
				 	scheduler.shutdown(); 	
				}
			}, 5, TimeUnit.SECONDS);
			
			
		}
	}
	
	public void abilityName(Graphics g) {
		Graphics2D g2D = (Graphics2D)g;
		if(System.currentTimeMillis() -collideTime<1000) {
			Font font = new Font("Arial", Font.PLAIN, 30);
			g2D.setFont(font);
			g2D.setColor(Color.BLACK);
			switch(num) {
			case 0: 
				g2D.drawString("Speed Up", GamePanel.GAME_WIDTH/3, 65);
				break;
			case 1:
				g2D.drawString("Slow Down", GamePanel.GAME_WIDTH/3, 65);
				break;
			case 2: 
				g2D.drawString("Increased Bullet Capacity", GamePanel.GAME_WIDTH/3, 65);
				break;
			case 3:
				g2D.drawString("Decreased Bullet Capacity", GamePanel.GAME_WIDTH/3, 65);
				break;
			}
			
		}
		
	
		
	}
	
	public void drawAbility(Graphics g) {
		if(isVisible) {
		g.setColor(Color.BLACK);
		g.fillRect(x,y,width,height);
		}
	}
}



