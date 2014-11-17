package csci201.shapes;

public class Triangle extends Shape {
	private float base, height;
	public Triangle(String name, float base, float height) {
		super(name);
		this.base = base;
		this.height = height;
	}
	public float getArea() {
		return .5f * this.base * this.height;
	}
}
