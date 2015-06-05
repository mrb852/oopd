import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel implements KeyListener, Runnable
{
	private static final int KEY_LEFT = 37;
	private static final int KEY_UP = 38;
	private static final int KEY_RIGHT = 39;
	private static final int KEY_DOWN = 40;
	
    private Player player;
    private JFrame mainFrame;
    
    public static void main(String[] args) {
    	Thread game = new Thread(new Game("Move player game", 480, 320));
    	game.start();
    }

	public Game(String title, int width, int height)
	{
		this.player = new Player((width/2), (height/2), 20, 20);
		this.player.setSpeed(10);
		this.mainFrame = new JFrame(title);
		this.mainFrame.setSize(width, height);
		this.setSize(this.mainFrame.getSize());
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainFrame.setLayout(new BorderLayout());
		this.mainFrame.setLocationRelativeTo(null);
		this.mainFrame.add(this);
		this.mainFrame.setVisible(true);
		this.mainFrame.addKeyListener(this);
	}
    
	private void keepPlayerOnScreen()
	{
		int x = this.player.x;
		int y = this.player.y;
		int width = this.player.width;
		int height = this.player.height;
		x = Math.max(Math.min(this.getSize().width - width, x), 0);
		y = Math.max(Math.min(this.getSize().height - height, y), 0);
		this.player.setLocation(x, y);
	}
	
    @Override
    protected void paintComponent(Graphics g)
    {
    	super.paintComponent(g);
    	g.setColor(Color.BLACK);
        g.fillRect(this.player.x, this.player.y, this.player.width, this.player.height);;
    }

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode())
		{
		case KEY_UP:
			this.player.y -= this.player.height;
			break;
		case KEY_DOWN:
			this.player.y += this.player.height;
			break;
		case KEY_LEFT:
			this.player.x -= this.player.width;
			break;
		case KEY_RIGHT:
			this.player.x += this.player.width;
			break;
		default:
			break;
		}
		this.keepPlayerOnScreen();
	}
	
	
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(100);
			repaint();
			run();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
