import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

	static int JET_SPEED1 = 4;
	static int JET_SPEED2 = 4;
	static int maxBullets1 = 3;
	static int maxBullets2 = 3;
	static int BULLET_SPEED1 = JET_SPEED1 * 2; 
	static int BULLET_SPEED2 = JET_SPEED2 * 2; 
	static final int GAME_WIDTH = 1000;
	static final int GAME_HEIGHT = 800;
	static final int JET_SIZE = 20;
	static final int BULLET_DIAMETER = 8;
	static final double ANGLE_CHANGE =5;
	static final int abilitySize = 7;
	private boolean wKey, upKey;
	private int score1, score2;
	private double lastRemoveTime = System.currentTimeMillis();
	private double lastAddTime = System.currentTimeMillis();
	List<Bullet> bulletStream1;
	List<Bullet> bulletStream2;
	List<Ability> abilityList;
	static Jet jet1;
	static Jet jet2;
	Thread thread;
	
	public GamePanel()  {
		this.setVisible(true);
		this.setFocusable(true);
		this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				jet1.keyPressed(e);
				jet2.keyPressed(e);
				if(e.getKeyChar() == 'w') 
					wKey = true;
				if(e.getKeyCode() == KeyEvent.VK_UP)
					upKey = true;
				addBullet();
			
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				jet1.keyReleased(e);
				jet2.keyReleased(e);
				if(e.getKeyChar() == 'w') 
					wKey = false;
				if(e.getKeyCode() == KeyEvent.VK_UP)
					upKey = false;
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}

				
			
		});
		
		jet1 = new Jet(100, GAME_HEIGHT - 100, JET_SIZE, JET_SIZE, 1);
		jet2 = new Jet(GAME_HEIGHT - 100, GAME_HEIGHT - 100, JET_SIZE, JET_SIZE, 2);
		bulletStream1 = new ArrayList<Bullet>(); //each jet has its own bullet
		bulletStream2 = new ArrayList<Bullet>();
		abilityList = new ArrayList<Ability>();
		thread = new Thread(this);
		thread.start();
		
		
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2D = (Graphics2D)g;
		Font font = new Font("Arial", Font.PLAIN, 40);
		g2D.setFont(font);
		g2D.setColor(Color.BLUE);
		g2D.drawString(score1 + "", GAME_WIDTH/2-100 , 35);
		g2D.setColor(Color.RED);
		g2D.drawString(score2 + "", GAME_WIDTH/2 + 100, 35);
		jet1.drawJet(g2D);
		jet2.drawJet(g2D);
		for(Bullet bullet: bulletStream1) {
			g.setColor(Color.BLUE);
			bullet.drawBullet(g);
		}
		for(Bullet bullet: bulletStream2) {
			g.setColor(Color.RED);
			bullet.drawBullet(g);
		}
		for(Ability ability: abilityList) {
			ability.drawAbility(g);
			ability.abilityName(g2D);
		}
		
	}
	
	public void checkCollision() {
		if(jet1.y + JET_SIZE<0) {	// if jet goes out of the map it comes back on the other side 
			jet1.y +=GAME_HEIGHT;
		}
		if(jet1.y>GAME_HEIGHT) {
			jet1.y -=GAME_HEIGHT;
		}
		if(jet1.x + JET_SIZE<0) {
			jet1.x +=GAME_WIDTH;
		}
		if(jet1.x + JET_SIZE>GAME_WIDTH) {
			jet1.x -=GAME_WIDTH;
		}
		if(jet2.y + JET_SIZE<0) {
			jet2.y +=GAME_HEIGHT;
		}
		if(jet2.y>GAME_HEIGHT) {
			jet2.y -=GAME_HEIGHT;
		}
		if(jet2.x + JET_SIZE<0) {
			jet2.x +=GAME_WIDTH;
		}
		if(jet2.x + JET_SIZE>GAME_WIDTH) {
			jet2.x -=GAME_WIDTH;
		}
		
		for(Bullet bullet: bulletStream1) { //checks collisions for bullets and jets
			if(bullet.intersects(jet2)) {
			score1++;
			bulletStream1.remove(bullet);
			break;
			}
		}
		for(Bullet bullet: bulletStream2) {
			if(bullet.intersects(jet1)) {
			score2++;
			bulletStream2.remove(bullet);
			break;
			}
		}
		for(Ability ability: abilityList) {
			if(jet1.intersects(ability) && ability.isVisible) {
				ability.collide(jet1);
				//abilityList.remove(ability);
				break;
			}
			if(jet2.intersects(ability) && ability.isVisible) {
				ability.collide(jet2);
				//abilityList.remove(ability);
				break;
			}
			
				
			
		}
		
	}
	
	public void addBullet() {
		
		if(wKey && bulletStream1.size()<maxBullets1) {
			double angle = jet1.angle;
			bulletStream1.add(new Bullet(jet1.x + JET_SIZE/2 - BULLET_DIAMETER/2 , jet1.y - BULLET_DIAMETER, angle));
		}
		if(upKey && bulletStream2.size()<maxBullets2) {
			double angle = jet2.angle;
			bulletStream2.add(new Bullet(jet2.x + JET_SIZE/2 - BULLET_DIAMETER/2, jet2.y - BULLET_DIAMETER, angle));
		}
			
	}
	
	public void moveBullet() {
		for(int i = 0; i<bulletStream1.size(); i++) {
			
			bulletStream1.get(i).y-=BULLET_SPEED1 * Math.cos(Math.toRadians(bulletStream1.get(i).anglePath));
			bulletStream1.get(i).x+=BULLET_SPEED1 * Math.sin(Math.toRadians(bulletStream1.get(i).anglePath));
			if(bulletStream1.get(i).y + BULLET_DIAMETER<0) {
				bulletStream1.remove(i);
				break;
				
			}
			if(bulletStream1.get(i).y>GAME_HEIGHT) {
				bulletStream1.remove(i);
				break;
			}
			if(bulletStream1.get(i).x + BULLET_DIAMETER<0) {
				bulletStream1.remove(i);
				break;
			}
			if(bulletStream1.get(i).x + BULLET_DIAMETER>GAME_WIDTH) {
				bulletStream1.remove(i);
				break;
			}
		}
		
		for(int i = 0; i<bulletStream2.size(); i++) {
			bulletStream2.get(i).y-=BULLET_SPEED2 * Math.cos(Math.toRadians(bulletStream2.get(i).anglePath));
			bulletStream2.get(i).x+=BULLET_SPEED2 * Math.sin(Math.toRadians(bulletStream2.get(i).anglePath));
			
			if(bulletStream2.get(i).y + BULLET_DIAMETER<0) {
				bulletStream2.remove(i);
				break;
				
			}
			if(bulletStream2.get(i).y>GAME_HEIGHT) {
				bulletStream2.remove(i);
				break;
			}
			if(bulletStream2.get(i).x + BULLET_DIAMETER<0) {
				bulletStream2.remove(i);
				break;
			}
			if(bulletStream2.get(i).x + BULLET_DIAMETER>GAME_WIDTH) {
				bulletStream2.remove(i);
				break;
			}
		}
	}
	
	public void addAbility() {
		int randX = (int) (Math.random() * GAME_WIDTH);
		int randY = (int) (Math.random() * GAME_HEIGHT);
		abilityList.add(new Ability(randX, randY));
		
	}
	
	public void removeAbility() {
		if(abilityList.size()>0)
			abilityList.remove(0);
	}
	
	
	public void callEveryTimeIncrement() { //calls methods every N seconds
		double currentTime = System.currentTimeMillis();
		double addTimeChange = currentTime - lastAddTime;
		double removeTimeChange = currentTime - lastRemoveTime;
		
		if(addTimeChange >= 5000) {
			addAbility();
			lastAddTime = currentTime;
		}
		if(removeTimeChange>=17000) {
			removeAbility();
			lastRemoveTime = currentTime;
		}
			
	}
	

	@Override
	public void run() {
		double timeChange = 0;
		double lastTime = System.nanoTime(); 
		double currentTime;
		
		
		while(true) {
			currentTime = System.nanoTime();	
			timeChange += (currentTime - lastTime) / 1000000000; 
			lastTime = currentTime; 
			
			callEveryTimeIncrement();
			
			
			if(timeChange >1/60) { 
				timeChange=0;
				repaint();
				checkCollision();
				jet1.moveJet();
				jet2.moveJet();
				moveBullet();
				
				try{					//makes it so that everything doesn't run super fast
					Thread.sleep(10);
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
}


