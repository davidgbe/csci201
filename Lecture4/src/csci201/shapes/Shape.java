package csci201.shapes;

public abstract class Shape {
	private String name;
	Shape(String name) { 
		this.name = name;	
	}
	public String getName() {
		return this.name;
	}
	public abstract float getArea();
}
