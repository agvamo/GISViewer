package testGUI_SE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class SelectAndMoveDrawing extends JPanel{
	private int width;
	private int height;
	private Rectangle2D.Double rect1 = new Rectangle2D.Double(50, 75, 100, 250);
	MovingAdapter ma = new MovingAdapter();
	
	public SelectAndMoveDrawing(int w, int h){
		this.width = w;
		this.height= h;
		addMouseMotionListener(ma);
		addMouseListener(ma);	
	}
	
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(100, 149, 237));
		g2d.fill(rect1);	
	}
	
	class MovingAdapter extends MouseAdapter{
		private int x;
		private int y;
		public void mousePressed(MouseEvent e){
			x = e.getX();
			y = e.getY();
		}
		
		public void mouseDragged(MouseEvent e){
			int dx = e.getX()-x;
			int dy = e.getY()-y;
			if(rect1.getBounds2D().contains(x, y)){
				rect1.x += dx;
				rect1.y += dy;
				repaint();
			}
			x += dx;
			y += dy;
		}	
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int w= 640;
		int h= 480;
		JFrame frame = new JFrame("Drawing With Java 2D");
		SelectAndMoveDrawing dc = new SelectAndMoveDrawing(w, h);
		dc.setDoubleBuffered(true);
		frame.setSize(w, h);
		frame.add(dc);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}

