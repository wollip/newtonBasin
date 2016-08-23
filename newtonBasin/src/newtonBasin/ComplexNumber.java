package newtonBasin;

public class ComplexNumber {
	private double Real;
	private double Imaginary;
	
	public ComplexNumber(double real, double imaginary){
		Real = real;
		Imaginary = imaginary;
	}
	
	public double getReal(){
		return Real;
	}
	
	public double getImaginary(){
		return Imaginary;
	}
	
	public String getString(){
		return String.format("%f %+fi", Real, Imaginary);
	}
}
