import java.util.Arrays;
import java.util.Formatter;


public class State implements Comparable<State> {
	private Point dimensions;
	private Point exit;
	private Car[] cars;
	private int steps;			// Steps taken from initial state
	Heuristic heuristic;
	
	public State(Point dimensions, Car[] cars, Point exit) {
		this(dimensions, cars, exit, 0);
	}

	public State(Point dimensions, Car[] cars, Point exit, int steps) {
		this.dimensions = dimensions;
		this.cars = cars;
		this.exit = exit;
		this.steps = steps;
	}
	
	public void setHeuristic(Heuristic heuristic) {
		this.heuristic = heuristic;
	}
	
	public State clone() {
		Car[] carsClone = new Car[cars.length];
		for(int i = 0; i < cars.length; i++) {
			carsClone[i] = cars[i].clone();
		}
		State result = new State(dimensions,
				                 carsClone,
				                 new Point(exit.getX(), exit.getY()),
				                 steps);
		result.setHeuristic(heuristic);
		return result;
	}
	
	public Car getCar(int i) {
		return cars[i];
	}
	
	public Car[] getCars() {
		return cars;
	}
	
	public Point getExit() {
		return exit;
	}
	
	public int getSteps() {
		return steps;
	}
	
	public void setSteps(int steps) {
		this.steps = steps;
	}
	
	public Point getDimensions() {
		return dimensions;
	}

	public int compareTo(State state) {
		return ((Integer)(this.steps + this.heuristic.eval(this))).
				compareTo(state.steps + state.heuristic.eval(state));
	}
	
	public int hashCode() {
		int hash = 31;
		
		for(Car c : cars) {
			hash = 31 * hash + c.hashCode();
		}
		
		return hash;
	}
	
	public boolean equals(Object o) {
		return equals((State)o);
	}
	
	public boolean equals(State state) {
		return dimensions.equals(state.dimensions) &&
		       exit.equals(state.exit) &&
		       Arrays.equals(cars, state.cars);
	}
	
	public boolean exitIsHorizontal() {
		return (exit.getX() == 0) || (exit.getX() == dimensions.getX() - 1);
	}

	private int blockingCarsHorizontal() {
		int result = 0, x1, x2;
		int line = exit.getY();
		
		if(exit.getX() == 0) {
			x1 = 0;
			x2 = cars[0].getStart().getX();
		}
		else {
			x1 = cars[0].getEnd().getX();
			x2 = dimensions.getX() - 1;
		}
		
		for(int i = 1; i < cars.length; i++) {
			if(cars[i].crossesHorizontal(line) &&
			   cars[i].getStart().getX() >= x1 &&
			   cars[i].getStart().getX() <= x2)
				result++;
		}
		
		return result;
	}
	
	private int blockingCarsVertical() {
		int result = 0, y1, y2;
		int line = exit.getX();
		
		if(exit.getY() == 0) {
			y1 = 0;
			y2 = cars[0].getStart().getY();
		}
		else {
			y1 = cars[0].getEnd().getY();
			y2 = dimensions.getY() - 1;
		}
		
		for(int i = 1; i < cars.length; i++) {
			if(cars[i].crossesVertical(line) &&
			   cars[i].getStart().getY() >= y1 &&
			   cars[i].getStart().getY() <= y2)
				result++;
		}
		
		return result;
	}
	
	public int blockingCars() {
		if(exitIsHorizontal())
			return blockingCarsHorizontal();
		else
			return blockingCarsVertical();
	}
	
	public boolean isFinal() {
		return blockingCars() == 0;
	}
	
	public String toString() {
		int sizex = dimensions.getX(),
	        sizey = dimensions.getY();
		int[][] map = new int[sizex][sizey];
		Car[] cars = getCars();
		
		for(int i = 0; i < sizex; i++) {
			for(int j = 0; j < sizey; j++) {
				map[i][j] = -1;
			}
		}
		
		/* 2-D map, true if occupied by car, false otherwise */
		for(int k = 0; k < cars.length; k++) {
			Car c = cars[k];
			if(c.isHorizontal()) {
				for(int i = c.getStart().getX(); i <= c.getEnd().getX(); i++)
					map[i][c.getStart().getY()] = k;
			}
			else {
				for(int i = c.getStart().getY(); i <= c.getEnd().getY(); i++)
					map[c.getStart().getX()][i] = k;
			}
		}
		
		StringBuilder sb = new StringBuilder("Steps = " + steps +
				                             ", Heuristic = " +
				                             heuristic.eval(this) + "\n");
		Formatter formatter = new Formatter(sb);
		for(int y = 0; y < sizey; y++) {
			for(int x = 0; x < sizex; x++) {
				if(map[x][y] >= 0)
					formatter.format("%3d", map[x][y]);
				else
					formatter.format("%3s", "*");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}