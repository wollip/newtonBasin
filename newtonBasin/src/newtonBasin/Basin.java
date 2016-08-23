package newtonBasin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Basin  extends JPanel{
	private final static int DIMEN = 1000;
	
	private final static double XMAX = 2;
	private final static double XMIN = -2;
	private final static double YMAX = 2;
	private final static double YMIN = -2;
	
	private final static double XRANGE = XMAX - XMIN;
	//private final static double XMID = XMIN + XRANGE/2;
	private final static double YRANGE = YMAX - YMIN;
	//private final static double YMID = YMIN + YRANGE/2;
	
	private final static double DISTANCE = 0.6;
	private final static int waitTime = 20;
	private final static int numOfRoots = 4;
	
	private static Color[] colours = {Color.red, Color.yellow, Color.blue, Color.green};
	private static ComplexNumber[] roots = new ComplexNumber[numOfRoots];
	private static Color[][] cordinates = new Color[DIMEN][DIMEN];
	private static int[][] path = new int[waitTime][2];
	

	public Basin(JFrame window) {	
		window.setTitle("Basin"); 
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		setBackground(Color.BLACK);
		
		
		drawCordinates(g);
		drawBorder(g);
		//drawPath(g);
	}
	
	private static void drawPath(Graphics g){
		g.setColor(Color.MAGENTA);
		for(int i = 1; i < waitTime; i++){
			g.drawLine(path[i-1][0], path[i-1][1], path[i][0], path[i][1]);
			//System.out.println(path[i-1][0] + " " + path[i-1][1] + " " + path[i][0] + " " +path[i][1]);
		}
	}
	
	private static void drawCordinates(Graphics g){
		for(int x = 0; x< DIMEN; x++){
			for(int y = 0; y < DIMEN; y ++){
				g.setColor(cordinates[x][y]);
				g.drawLine(x, y, x, y);
			}
		}
	}
	
	private static void drawBorder(Graphics g){
		g.setColor(Color.green);
		//g.drawRect(0 , 0, DIMEN, DIMEN);
		g.drawLine(0, DIMEN/2, DIMEN, DIMEN/2);
		g.drawLine(DIMEN/2, 0, DIMEN/2, DIMEN);
	}
	
	public static void main(String args[]){
		JFrame window = new JFrame();
		Basin basin = new Basin(window);
		window.add(basin);
		
		Insets insets = window.getInsets();
		window.setSize(DIMEN + insets.left + insets.right , DIMEN + insets.top + insets.bottom);
		
		basin.repaint();
		
		initializeRoots();
		initializeCordinates();
		
		for(int x = 0; x < DIMEN; x++){
			for(int y = 0; y < DIMEN; y++){
				System.out.printf("\ncordinate : %d, %d\n", x,y);
				double[] cordinate = convertIndex(x,y);
				ComplexNumber x0 = new ComplexNumber(cordinate[0], cordinate[1]);
				//System.out.println(x0.getString());
				
				/*
				boolean atRoot = false;
				int time = 0;
				
				while(!atRoot){
					for(int i = 0; i < numOfRoots; i++){
						atRoot = MathLibrary.distance(x0, roots[i], DISTANCE);
						if(atRoot){
							cordinates[x][y] = colours[i];
							System.out.println("setting color to" + i);
							break;
						}
					}
					//System.out.print(time + "\t");
					x0 = newtonMethod(x0);
					time++;
					if(time > waitTime){						
						cordinates[x][y] = Color.black;
						break;
					}
				}
				*/
				for(int t = 0; t < waitTime; t++){
					x0 = newtonMethod(x0);
					/*
					int[] newCordinate = convertCordinates(x0);
					path[t][0] = newCordinate[0];
					path[t][1] = newCordinate[1];
					*/
					
				}
				boolean atRoot = false;
				for(int i = 0; i < numOfRoots; i++){
					atRoot = MathLibrary.distance(x0, roots[i], DISTANCE);
					if(atRoot){
						cordinates[x][y] = colours[i];
						System.out.println("setting color to" + i);
						break;
					}
				}
				if(!atRoot) cordinates[x][y] = Color.black;
				
				basin.repaint();
				/*
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
				
			}
		}
		
		//System.out.println(cordinates[10][15]);
	}
	
	
	
	private static ComplexNumber newtonMethod(ComplexNumber x){
		ComplexNumber numerator = function(x);
		ComplexNumber denominator = functionPrime(x);
		return MathLibrary.subtract(x, MathLibrary.divide(numerator, denominator));
	}
	
	private static void initializeRoots(){
		
		roots[0] = new ComplexNumber(-1,0);
		roots[1] = new ComplexNumber(0,1);
		roots[2] = new ComplexNumber(0,-1);
		roots[3]  = new ComplexNumber(1,0);
	}
	
	private static void initializeCordinates() {
		for(int x = 0; x < DIMEN; x++){
			for(int y = 0; y < DIMEN; y++){
				cordinates[x][y] = Color.white;
			}
		}
	}

	private static double[] convertIndex(int x, int y){
		double[] cordinates = new double[2];
		//x
		cordinates[0] = ((double) x) /DIMEN *XRANGE + XMIN;
		//y 
		cordinates[1] = YMAX - ((double) y) / DIMEN * YRANGE;
		return cordinates;
	}
	
	private static int[] convertCordinates(ComplexNumber x){
		int[] indices = new int[2];
		System.out.println(x.getReal() + "," + x.getImaginary());
		//x
		indices[0] = (int) ((x.getReal() - XMIN) / XRANGE * DIMEN);
		//y
		indices[1] = (int) ((YMAX - x.getImaginary()) / YRANGE * DIMEN);
		return indices;
	}

	private static ComplexNumber function(ComplexNumber x){
		// x^3 + x^2 + x + 1
		/*
		ComplexNumber[] ys = new ComplexNumber[4];
		ys[0] = MathLibrary.power(x, 3);
		ys[1] = MathLibrary.power(x, 2);
		ys[2] = x;
		ys[3] = new ComplexNumber(1,0);
		
		ComplexNumber ret = new ComplexNumber(0,0);
		for(ComplexNumber y : ys){
			ret = MathLibrary.add(ret, y);
		}
		
		return ret;	
		*/
		
		ComplexNumber y1 = MathLibrary.power(x, 4);
		ComplexNumber y2 = new ComplexNumber(1,0);
		return MathLibrary.subtract(y1, y2);
		
		//return  MathLibrary.subtract( MathLibrary.power(x, 3), x);
	}
	
	// 3x^2 + 2x + 1
	private static ComplexNumber functionPrime(ComplexNumber x){
		/*
		ComplexNumber[] ys = new ComplexNumber[3];
		ys[0] = MathLibrary.scalarMultiply(MathLibrary.power(x, 2), 3);
		ys[1] = MathLibrary.scalarMultiply(x, 2);
		ys[2] = new ComplexNumber(1,0);
		
		ComplexNumber ret = new ComplexNumber(0,0);
		for(ComplexNumber y: ys){
			ret = MathLibrary.add(ret, y);
		}
		
		return ret;
		*/
		return MathLibrary.scalarMultiply(MathLibrary.power(x, 3), 4);
		/*
		ComplexNumber y = MathLibrary.scalarMultiply(MathLibrary.power(x, 2), 3);
		ComplexNumber one = new ComplexNumber(1,0);
		return MathLibrary.subtract(y, one);
		*/
	}
}
