import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ArgParser {
	private Scanner scanner;
	
	public ArgParser() {
		scanner = new Scanner(System.in);
		scanner.useDelimiter("[ \\n\\r\\t,)(\\[\\];]+");
	}
	
	public int parseInt() {
		return Integer.parseInt(scanner.next());
	}
	
	public State parseState() {
		int sizex = parseInt();
		int sizey = parseInt();
		
		int exitx = parseInt();
		int exity = parseInt();

		int i = 0;
		List<Car> cars = new ArrayList<Car>();
		while(scanner.hasNext()) {
			int id = parseInt();
			int startx = parseInt();
			int starty = parseInt();
			int endx = parseInt();
			int endy = parseInt();
			cars.add(new Car(id, new Point(startx, starty), new Point(endx, endy)));
		}
		
		return new State(new Point(sizex, sizey), cars.toArray(new Car[i]), new Point(exitx, exity));
	}
}
