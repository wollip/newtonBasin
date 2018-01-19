package newtonBasin;

public class MathLibrary {
	
	public static ComplexNumber add(ComplexNumber a, ComplexNumber b){
		return new ComplexNumber(a.getReal() + b.getReal(), a.getImaginary() + b.getImaginary());
	}
	
	public static ComplexNumber subtract(ComplexNumber a, ComplexNumber b){
		return new ComplexNumber(a.getReal() - b.getReal(), a.getImaginary() - b.getImaginary());
	}
	
	public static ComplexNumber multiply(ComplexNumber a, ComplexNumber b){
		double real = a.getReal() * b.getReal() - a.getImaginary() * b.getImaginary();
		double imaginary = a.getReal() * b.getImaginary() + a.getImaginary() * b.getReal();
		return new ComplexNumber(real, imaginary);
	}
	
	public static ComplexNumber getConjugate(ComplexNumber a){
		return new ComplexNumber(a.getReal(), - a.getImaginary());
	}
	
	public static ComplexNumber divide(ComplexNumber a, ComplexNumber b){
		ComplexNumber numerator = multiply(a, getConjugate(b));
		double denominator = Math.pow(b.getReal(), 2) + Math.pow(b.getImaginary(), 2);
		return new ComplexNumber(numerator.getReal()/denominator, numerator.getImaginary()/denominator);
	}
	
	public static ComplexNumber scalarMultiply(ComplexNumber a, double b){
		return new ComplexNumber(a.getReal()*b, a.getImaginary() * b);
	}
	
	public static ComplexNumber power(ComplexNumber a, int b){
		double scalar = Math.sqrt( Math.pow(a.getReal(), 2) + Math.pow(a.getImaginary(), 2));
		double angle = Math.atan2(a.getImaginary()/scalar, a.getReal()/scalar);
		ComplexNumber ret = new ComplexNumber(Math.cos(b*angle), Math.sin(b*angle));
		return scalarMultiply(ret, Math.pow(scalar, b));
	}
	
	public static ComplexNumber power(double a, ComplexNumber b) {
		double theta = b.getImaginary() * Math.log(a);
		double scalar = Math.pow(a, b.getReal());
		return new ComplexNumber(scalar * Math.cos(theta), scalar * Math.sin(theta));
	}
	
	public static boolean distance(ComplexNumber a, ComplexNumber b, double d){
		double real = a.getReal() - b.getReal();
		double imaginary = a.getImaginary() - b.getImaginary();
		double c = Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
		return c < d;
	}
}
