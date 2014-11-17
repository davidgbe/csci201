package Lab3;

public class Main {

	public static void main(String[] args) {
		Pet elephant = new Elephant();
		Pet pig = new Pig();
		elephant.setName("Bessy");
		pig.setName("Piggy");
		System.out.println("The elephant's name is " + elephant.getName() );
		System.out.println("The pig's name is " + pig.getName() );
		System.out.println(pig instanceof Pig );
		System.out.println(elephant instanceof Elephant );
	}

}
