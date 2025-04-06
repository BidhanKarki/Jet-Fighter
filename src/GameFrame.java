import javax.swing.JFrame;
public class GameFrame extends JFrame {
	GamePanel panel = new GamePanel();
	
	public GameFrame() {
		this.add(panel);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		
		
	}
}
