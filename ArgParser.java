import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class ArgParser {
	public ArgParser() {
	}
	
	public State parse(String sx, String sy, String ex, String carlist) {
		int sizex = Integer.parseInt(sx);
		int sizey = Integer.parseInt(sy);
		
		StringTokenizer tok = new StringTokenizer(ex, "(),");
		int exitx = Integer.parseInt(tok.nextToken());
		int exity = Integer.parseInt(tok.nextToken());

		tok = new StringTokenizer(carlist, "[](), \n\t\r;");
		int i = 0;
		List<Car> cars = new ArrayList<Car>();
		while(tok.hasMoreTokens()) {
			int id = Integer.parseInt(tok.nextToken());
			int startx = Integer.parseInt(tok.nextToken());
			int starty = Integer.parseInt(tok.nextToken());
			int endx = Integer.parseInt(tok.nextToken());
			int endy = Integer.parseInt(tok.nextToken());
			
			cars.add(new Car(id, new Point(startx, starty), new Point(endx, endy)));
		}
		
		return new State(new Point(sizex, sizey), cars.toArray(new Car[i]), new Point(exitx, exity));
	}
}
