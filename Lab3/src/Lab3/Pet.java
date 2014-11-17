package Lab3;

public abstract class Pet implements Nameable{
	private String name;
	public void setName(String newName) {
		this.name = newName;
	}
	public String getName() {
		return this.name;
	}
	abstract String speak();
}
