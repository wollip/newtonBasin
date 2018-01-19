package newtonBasin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Plotter extends JPanel{
	private final static int DIMEN = 1000;
	
	private final static double XPLOTMAX = 50;
	private final static double XPLOTMIN = -50;
	private final static double XSCALE = 0.1;
	private final static double YPLOTMAX = 1;
	private final static double YPLOTMIN = -1;
	private final static double YSCALE = 0.1;
	
	private final static double XMAX = 5;
	private final static double XMIN = -5;
	private final static double YMAX = 5;
	private final static double YMIN = -5;
	
	private final static double XRANGE = XMAX - XMIN;
	private final static double YRANGE = YMAX - YMIN;
	
	public static int[][][] domain 
	= new int[(int) Math.ceil((XPLOTMAX - XPLOTMIN)/XSCALE) + 2]
			[(int) Math.ceil((YPLOTMAX - YPLOTMIN)/YSCALE) + 2][2];
	public static int[][][] range 
	= new int[(int) Math.ceil((XPLOTMAX - XPLOTMIN)/XSCALE) + 2]
			[(int) Math.ceil((YPLOTMAX - YPLOTMIN)/YSCALE) + 2][2];
	
	public Plotter(JFrame window) {	
		window.setTitle("Plotter"); 
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		setBackground(Color.BLACK);
		
		drawBorder(g);
		drawLegend(g);
		drawPixels(g, domain, Color.blue);
		drawPixels(g, range, Color.red);
	}
	
	private static void drawLegend(Graphics g) {
		Font f = new Font("Comic Sans MS", 0, 40);
		g.setFont(f);
		g.setColor(Color.blue);
		g.drawString("Domain", 15, 50);
		g.setColor(Color.red);
		g.drawString("Range", 15, 100);
	}
	
	private static void drawBorder(Graphics g){
		g.setColor(Color.green);
		ComplexNumber zero = new ComplexNumber(0,0);
		int[] pixel = convertCordinate(zero);
		g.drawLine(0, pixel[1], DIMEN, pixel[1]);
		g.drawLine(pixel[0], 0, pixel[0], DIMEN);
	}
	
	private static void drawPixels(Graphics g, int[][][] points, Color c) {
		g.setColor(c);
		for (int i = 0; i < points.length; i++) {
			for (int i2 = 1; i2 < points[i].length; i2++) {
				if (points[i][i2] == null || points[i][i2-1] == null) {
					continue;
				}
				g.drawLine(points[i][i2-1][0], points[i][i2-1][1], 
						points[i][i2][0], points[i][i2][1]);
			}
		}
			
		for (int i2 = 0; i2 < points[0].length; i2++) {
			for (int i = 1; i < points.length; i++) {
				if (points[i][i2] == null || points[i-1][i2] == null) {
					continue;
				}
				g.drawLine(points[i-1][i2][0], points[i-1][i2][1], 
						points[i][i2][0], points[i][i2][1]);
			}
		}
	}
	
	public static void main(String args[]) throws InterruptedException{
		JFrame window = new JFrame();
		Plotter plot = new Plotter(window);
		window.add(plot);
		
		Insets insets = window.getInsets();
		window.setSize(DIMEN + insets.left + insets.right , DIMEN + insets.top + insets.bottom);
		
		plot.repaint();
		Thread.sleep(100);
		int i, i2;
		double x,y;
		for(x = XPLOTMIN, i = 0; i < domain.length; x+=XSCALE, i++) {
			for(y = YPLOTMIN, i2 = 0; i2 < domain[i].length; y+=YSCALE, i2++) {
				ComplexNumber num = new ComplexNumber(x, y);
				if (inrange(num)) {
					domain[i][i2] = convertCordinate(num);
					num = function(num);
					range[i][i2] = convertCordinate(num);
				} else {
					domain[i][i2] = null;
					range[i][i2] = null;
				}
			}
		}
		plot.repaint();
	}
	
	private static double[] convertIndex(int x, int y){
		double[] cordinates = new double[2];
		//x
		cordinates[0] = ((double) x) /DIMEN *XRANGE + XMIN;
		//y 
		cordinates[1] = YMAX - ((double) y) / DIMEN * YRANGE;
		return cordinates;
	}
	
	private static int[] convertCordinate(ComplexNumber x){
		int[] indices = new int[2];
		//System.out.println(x.getReal() + "," + x.getImaginary());
		//x
		indices[0] = (int) ((x.getReal() - XMIN) / XRANGE * DIMEN);
		//y
		indices[1] = (int) ((YMAX - x.getImaginary()) / YRANGE * DIMEN);
		return indices;
	}

	private static ComplexNumber function(ComplexNumber x){
		return MathLibrary.power(Math.E, MathLibrary.scalarMultiply(x, Math.PI/2));
	}
	
	private static boolean inrange(ComplexNumber x) {
		return Math.abs(x.getImaginary()) <= 1.01; 
	}
}

