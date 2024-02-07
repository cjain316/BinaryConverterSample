

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Main extends JPanel implements KeyListener, ActionListener, MouseListener {
	private AffineTransform tx;
	boolean PLAYING = false;
	private Image Sprite;
	int COUNTER = 0;
	boolean CLICKING = false;
	int type;
	boolean DRAWING = true;
	private Rectangle cursor = new Rectangle(0,0,1,1);
	private int[] currentlyShowing = randBinary();
	private int currentDecimal = randDecimal();
	
	private Rectangle showButton = new Rectangle(1500,400,200,100);
	private Rectangle newBinary = new Rectangle(50,620,200,100);
	private Rectangle newDecimal = new Rectangle(50,820,200,100);
	private boolean showingConversion = false;
	
	private Rectangle[] buttons = {
			new Rectangle(1000,400,100,100),
			new Rectangle(900,400,100,100),
			new Rectangle(800,400,100,100),
			new Rectangle(700,400,100,100),
			new Rectangle(600,400,100,100),
			new Rectangle(500,400,100,100),
			new Rectangle(400,400,100,100),
			new Rectangle(300,400,100,100)
	};
	private boolean[] active = {
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false
	};
	
	public int[] randBinary() {
		int[] output = new int[8];
		for (int i = 0; i < 8; i++) {
			output[i] = (int)(Math.random()*2);
		}
		return output;
	}
	
	public int randDecimal() {
		return (int)(Math.random()*256);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main f = new Main();
	}
	
	private int convertBinaryToDecimal(int[] binary) {
		int output = 0;
		for (int i = 0; i < buttons.length; i++) {
			if (active[i]) {
				output += (int)Math.pow(2, i);
			}
		}
		return output;
	}
	
	private void paintData(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Apple Casual",Font.PLAIN,95));
		String show = "";
		for (int i = 0; i < currentlyShowing.length; i++) {
			show += currentlyShowing[i] + "  ";
		}
		g.drawString(show, 300, 700);
		
		g.drawString("" + currentDecimal, 300, 900);
		
		if (showingConversion) {
			g.drawString(""+convertBinaryToDecimal(currentlyShowing), 
					(int)showButton.getX() + 220, (int)showButton.getY()+80);
		}
	}
	
	private void paintButton(Graphics g, int index) {
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(Color.WHITE);
		g.drawRect((int)buttons[index].getX(), (int)buttons[index].getY(),
				(int)buttons[index].getWidth(), (int)buttons[index].getHeight());
		if (active[index]) {
			g.drawRect((int)buttons[index].getX(), (int)buttons[index].getY()-(int)buttons[index].getHeight(),
					(int)buttons[index].getWidth(), (int)buttons[index].getHeight());
			g.setFont(new Font("Apple Casual",Font.PLAIN,22));
			g.drawString(""+index, (int)buttons[index].getX()+((int)buttons[index].getWidth()/2)-25,
					(int)buttons[index].getY()-((int)buttons[index].getHeight()/2)-20);
			g.setFont(new Font("Apple Casual",Font.PLAIN,32));
			g.drawString("2 =", 
					-20+(int)buttons[index].getX()+((int)buttons[index].getWidth()/2)-20,
					(int)buttons[index].getY()-((int)buttons[index].getHeight()/2)+0);
			g.drawString(""+(int)Math.pow(2, index), 
					-20+(int)buttons[index].getX()+((int)buttons[index].getWidth()/2)-20,
					(int)buttons[index].getY()-((int)buttons[index].getHeight()/2)+30);
			g.setFont(new Font("Apple Casual",Font.PLAIN,40));
			g.drawString("1", (int)buttons[index].getX()+((int)buttons[index].getWidth()/2)-15,
					(int)buttons[index].getY()+60);
		} else {
			g.setFont(new Font("Apple Casual",Font.PLAIN,40));
			g.drawString("0", (int)buttons[index].getX()+((int)buttons[index].getWidth()/2)-15,
					(int)buttons[index].getY()+60);
		}
		
		tx = AffineTransform.getTranslateInstance((int)showButton.getX(), (int)showButton.getY());
		Sprite = getImage("Resources\\showbutton.png");
		g2.drawImage(Sprite, tx, null);
		
		tx = AffineTransform.getTranslateInstance((int)newBinary.getX(), (int)newBinary.getY());
		Sprite = getImage("Resources\\newbutton.png");
		g2.drawImage(Sprite, tx, null);
		
		tx = AffineTransform.getTranslateInstance((int)newDecimal.getX(), (int)newDecimal.getY());
		Sprite = getImage("Resources\\newbutton.png");
		g2.drawImage(Sprite, tx, null);
	}
	
	public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        updateMouse();
        
        //Painting
        g.setColor(new Color(45,45,45));
        g.fillRect(0, 0, 2000, 2000);
        paintData(g);
        for (int i = 0; i < buttons.length;i++) {
        	paintButton(g,i);
        }
        
        
	}
	
	private void updateMouse() {
		PointerInfo p = MouseInfo.getPointerInfo();
        Point point = p.getLocation();
        SwingUtilities.convertPointFromScreen(point, getFocusCycleRootAncestor());
        point.setLocation(point.getX()-5,point.getY()-30);
        cursor.setLocation(point);
	}
	
	private boolean toggle(boolean b) {return !b;}
	
	
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println(arg0.getExtendedKeyCode());
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	Timer t;
    
    public Main() {
    	
        JFrame f = new JFrame("Binary Converter Example");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1617,1140);

        f.add(this);
        f.addMouseListener(this);
        f.addKeyListener(this);
        f.setResizable(false);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        
        t = new Timer(1, this);
        t.start();
        f.setVisible(true);
        
       
        
    }
    
    private void update() {

    }
    
    protected Image getImage(String path) {

        Image tempImage = null;
        try {
            URL imageURL = Background.class.getResource(path);
            tempImage    = Toolkit.getDefaultToolkit().getImage(imageURL);
        } catch (Exception e) {e.printStackTrace();}
        return tempImage;
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		CLICKING = true;
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		CLICKING = false;
		for (int i = 0;i < buttons.length;i++) {
			if (cursor.intersects(buttons[i])) {active[i] = toggle(active[i]);}
		}
		if (cursor.intersects(showButton)) {showingConversion = toggle(showingConversion);}
		if (cursor.intersects(newBinary)) {currentlyShowing = randBinary();}
		if (cursor.intersects(newDecimal)) {currentDecimal = randDecimal();}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	} 

}
