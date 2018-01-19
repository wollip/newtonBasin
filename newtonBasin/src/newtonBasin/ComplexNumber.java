package newtonBasin;

public class ComplexNumber {
	private double Real;
	private double Imaginary;
		
	public final static ComplexNumber origin = new ComplexNumber(0,0);
	public final static ComplexNumber one = new ComplexNumber(1,0);
	
	public ComplexNumber(double real, double imaginary){
		Real = real;
		Imaginary = imaginary;
	}
	
	public ComplexNumber(double a, double b, boolean rtheta) {		
		double real = a;
		double imaginary = b;
		if (rtheta) {
			real = a * Math.cos(b);
			imaginary = a * Math.sin(b);
		} 
		Real = real;
		Imaginary = imaginary;
	}

	public double getReal(){
		return Real;
	}
	
	public double getImaginary(){
		return Imaginary;
	}
	
	public String toString(){
		return String.format("%f %+fi", Real, Imaginary);
	}
}
