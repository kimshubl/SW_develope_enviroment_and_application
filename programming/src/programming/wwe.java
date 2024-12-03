package programming;

public class wwe {
	abstract class Shape{
		int x, y;
		Shape(){
			this.x = 1;
			this.y = 1;
		}
		Shape(int x, int y){
			this.x = x;
			this.y = y;
			
		}
		abstract double area();
		abstract double length();
	}
	public class Circle extends Shape{
		double r;
		Circle(){
			this.r = 1;
		}
		Circle(int r){
			this.r = r;
			
		}
		@Override
		double area() {
			return (r*2)*Math.PI;
		}
	}
	/*public class Rectangle extends Shape{
		double w, h;
		Rectangle(){
			this(1,1);
		}
	}*/
	public static void main(String[] args) {
		
	}
}
