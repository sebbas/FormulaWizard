package com.ndroidstudios.android.helper;

import java.text.DecimalFormat;

public class FormulaHelper {
	
	private static final double PI = 3.141592653589793;
	private static double result;
	private static double[] results = new double[2];

	public static class InvalidInputException extends Exception {
		private static final long serialVersionUID = 1L;
	}
	
	public static double plus(double a, double b) {
		return a + b;
	}
	
	public static double minus(double a, double b) {
		return a - b;
	}
	
	public static double multiply(double a, double b) {
		return a * b;
	}
	
	public static double divide(double a, double b) {
		return a / b;
	}
	
	public static double squareRoot(double value) throws InvalidInputException {
		if (value >= 0) {
			return Math.sqrt(value);
		} else {
			throw new InvalidInputException();
		}
	}
	
	public static double circleArea(double radius) throws InvalidInputException {
		if (radius >= 0) {
			result = radius * radius * PI;
			return result;
		} else {
			throw new InvalidInputException();
		}
	}
	
	public static double circlePerimeter(double radius) throws InvalidInputException {
		if(radius >= 0) {
			result = 2 * radius * PI;
			return result;
		} else {
			throw new InvalidInputException();
		}
	}
	
	public static double coneSurface(double radius, double slant) throws InvalidInputException {
		if(radius >= 0 && slant >= 0) {
			result = (radius * radius * PI) + (radius * slant * PI);
			return result;
		} else {
			throw new InvalidInputException();
		}
	}
	
	public static double coneVolume(double radius, double height) throws InvalidInputException {
		if(radius >= 0 && height >= 0) {
			result = ((PI * radius * radius * height) / 3);
			return result;
		} else {
			throw new InvalidInputException();
		}
	}
	
	public static double cubeSurface(double side) throws InvalidInputException {
		if(side >= 0) {
			result = side * side * 6;
			return result;
		} else {
			throw new InvalidInputException();
		}
	}
	
	public static double cubeVolume(double side) throws InvalidInputException {
		if(side >= 0) {
			result = side * side * side;
			return result;
		} else {
			throw new InvalidInputException();
		}
	}
	
	public static double cylinderSurface(double radius, double height) throws InvalidInputException {
		if(radius >= 0 && height >= 0) {
			result = (2 * radius * radius * PI) + (2 * radius * height * PI);
			return result;
		} else {
			throw new InvalidInputException();
		}
	}
	
	public static double cylinderVolume(double radius, double height) throws InvalidInputException {
		if(radius >= 0 && height >= 0) {
			result = (radius * radius * PI * height);
			return result;
		} else {
			throw new InvalidInputException();
		}
	}
	
	public static double distance(double x1, double x2, double y1, double y2) {
		result = (Math.sqrt((x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1)));
		return result;
	}

	public static double ellipseArea(double radius1, double radius2) throws InvalidInputException {
		if(radius1 >= 0 && radius2 >= 0) {
			result = (radius1 * radius2 * PI);
			return result;
		} else {
			throw new InvalidInputException();
		}
	}
	
	public static double ellipsePerimeter(double radius1, double radius2) throws InvalidInputException {
		if(radius1 >= 0 && radius2 >= 0) {
			result = (2 * PI * Math.sqrt(0.5  * radius1 * radius1 * radius2 * radius2));
			return result;
		} else {
			throw new InvalidInputException();
		}
	}
	
	public static double ellipsoidVolume(double radius1, double radius2, double radius3) throws InvalidInputException {
		if(radius1 >= 0 && radius2 >= 0 && radius3 >= 0) {
			result = ((4 * PI * radius1 * radius2 * radius3) / 3);
			return result;
		} else {
			throw new InvalidInputException();
		}
	}
	
	public static double exponent2Negative(double a, double b) {
		result = ((a * a) + (b * b) - (2 * a * b));
		return result;
	}
	
	public static double exponent2Positive(double a, double b) {
		result = ((a * a) + (b * b) + (2 * a * b));
		return result;
	}
	
	public static double exponent3Negative(double a, double b) {
		result = ((a * a * a) - (3 * a * a * b) + (3 * a * b * b) - (b * b * b));
		return result;
	}
	
	public static double exponent3Positive(double a, double b) {
		result = ((a * a * a) + (3 * a * a * b) + (3 * a * b * b) + (b * b * b));
		return result;
	}
	
	public static double[] midpoint(double x1, double x2, double y1, double y2) {
		results[0] = (x2 + x1) / 2;
		results[1] = (y2 + y1) / 2;
		return results;
	}
	
	public static double prismVolume(double length, double width, double height) throws InvalidInputException {
		if(length >= 0 && width >= 0 && height >= 0) {
			result = (length * width * height);
			return result;
		} else {
			throw new InvalidInputException();
		}
	}
	
	public static double pyramidVolume(double baseArea, double height) throws InvalidInputException {
		if(baseArea >= 0 && height >= 0) {
			result = (baseArea * height) / 3;
			return result;
		} else {
			throw new InvalidInputException();
		}
	}
	
	public static double pythagoreanTheorem(double a, double b) {
		result = (Math.sqrt(a*a + b*b));
		return result;
	}
	
	public static double[] quadratic(double a, double b, double c) throws InvalidInputException {
		if(((b*b - (4 * a * c)) < 0) == false) {
			results[0] = (-b + (Math.sqrt(b*b - (4 * a * c))))/ (2 * a);
			results[1] = (-b - (Math.sqrt(b*b - (4 * a * c))))/ (2 * a);
			return results;
		} else {
			throw new InvalidInputException();
		}
	}
	
	public static double rectangleArea(double width, double height) throws InvalidInputException {
		if (width >= 0 && height >= 0) {
			result = (width * height);
			return result;
		} else {
			throw new InvalidInputException();
		}
	}
	
	public static double rectanglePerimeter(double width, double height) throws InvalidInputException {
		if (width >= 0 && height >= 0) {
			result = (2 * width + 2 * height);
			return result;
		} else {
			throw new InvalidInputException();
		}
	}
	
	public static double slope(double x1, double x2, double y1, double y2) throws InvalidInputException {
		if (x2 != x1) {
			result = ((y2 - y1) / (x2 - x1));
			return result;
		} else {
			throw new InvalidInputException();
		}
	}
	
	public static double sphereVolume(double radius) throws InvalidInputException {
		if (radius >= 0) {
			result = ((4 * PI * radius * radius * radius) / 3);
			return result;
		} else {
			throw new InvalidInputException();
		}
	}
	
	public static double squareArea(double side) throws InvalidInputException {
		if (side >= 0) {
			result = (side * side);
			return result;
		} else {
			throw new InvalidInputException();
		}
	}
	
	public static double squarePerimeter(double side) throws InvalidInputException {
		if (side >= 0) {
			result = (4 * side);
			return result;
		} else {
			throw new InvalidInputException();
		}
	}
	
	public static double trapezoidArea(double base1, double base2, double height) throws InvalidInputException {
		if (base1 >= 0 && base2 >= 0 && height >= 0) {
			result = (height * (base1 + base2) / 2);
			return result;
		} else {
			throw new InvalidInputException();
		}
	}
	
	public static double trapezoidPerimeter(double base1, double base2, double base3, double base4) throws InvalidInputException {
		if (base1 >= 0 && base2 >= 0 && base3 >= 0 && base4 >= 0) {
			result = (base1 + base2 + base3 + base4);
			return result;
		} else {
			throw new InvalidInputException();
		}
	}
	
	public static double triangleArea(double base, double height) throws InvalidInputException {
		if (base >= 0 && height >= 0) {
			result = (base * height / 2);
			return result;
		} else {
			throw new InvalidInputException();
		}
	}
	
	public static double trianglePerimeter(double base, double side1, double side2) throws InvalidInputException {
		if (base >= 0 && side1 >= 0 && side2 >= 0) {
			result = (base + side1 + side2);
			return result;
		} else {
			throw new InvalidInputException();
		}
	}
	
	public static String formatResult(double result) {
		DecimalFormat fourDForm = new DecimalFormat("#0.0000");
    	String resultFormated = fourDForm.format(result);
    	return resultFormated;
	}
}
