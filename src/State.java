import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;
import java.util.Map;


public class State implements Comparable<State> {
	private Point dimensions;
	private Point exit;
	private Car[] cars;
	private int steps;
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
				                 exit,
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
	
	public static boolean exitIsHorizontal(State state) {
		return (state.exit.getX() == 0) || (state.exit.getX() == state.dimensions.getX() - 1);
	}

	private static int blockingCarsHorizontal(State state) {
		int result = 0, x1, x2;
		int line = state.exit.getY();
		
		if(state.exit.getX() == 0) {
			x1 = 0;
			x2 = state.cars[0].getStart().getX();
		}
		else {
			x1 = state.cars[0].getEnd().getX();
			x2 = state.dimensions.getX() - 1;
		}
		
		for(int i = 1; i < state.cars.length; i++) {
			if(state.cars[i].crossesHorizontal(line) &&
			   state.cars[i].getStart().getX() >= x1 &&
			   state.cars[i].getStart().getX() <= x2)
				result++;
		}
		
		return result;
	}
	
	private static int blockingCarsVertical(State state) {
		int result = 0, y1, y2;
		int line = state.exit.getX();
		
		if(state.exit.getY() == 0) {
			y1 = 0;
			y2 = state.cars[0].getStart().getY();
		}
		else {
			y1 = state.cars[0].getEnd().getY();
			y2 = state.dimensions.getY() - 1;
		}
		
		for(int i = 1; i < state.cars.length; i++) {
			if(state.cars[i].crossesVertical(line) &&
			   state.cars[i].getStart().getY() >= y1 &&
			   state.cars[i].getStart().getY() <= y2)
				result++;
		}
		
		return result;
	}
	
	public static int blockingCars(State state) {
		if(exitIsHorizontal(state))
			return blockingCarsHorizontal(state);
		else
			return blockingCarsVertical(state);
	}
	
	public static boolean isFinal(State state) {
		return blockingCars(state) == 0;
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
					map[i][c.getStart().getY()] = cars[k].getId();
			}
			else {
				for(int i = c.getStart().getY(); i <= c.getEnd().getY(); i++)
					map[c.getStart().getX()][i] = cars[k].getId();
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
	
	private static boolean[][] createMap(State state, int sizex, int sizey) {
		boolean[][] map = new boolean[sizex][sizey];
		Car[] cars = state.getCars();
		
		for(int i = 0; i < sizex; i++) {
			for(int j = 0; j < sizey; j++) {
				map[i][j] = false;
			}
		}
		
		/* 2-D map, true if occupied by car, false otherwise */
		for(Car c : cars) {
			if(c.isHorizontal()) {
				for(int i = c.getStart().getX(); i <= c.getEnd().getX(); i++)
					map[i][c.getStart().getY()] = true;
			}
			else {
				for(int i = c.getStart().getY(); i <= c.getEnd().getY(); i++)
					map[c.getStart().getX()][i] = true;
			}
		}
		
		return map;
	}
	
	public static synchronized List<State> findNeighbors(Map<State, State> visited, State conststate) {
		List<State> neighbors = new ArrayList<State>();
		State state = conststate.clone();
		state.setSteps(state.getSteps() + 1);
		int sizex = state.getDimensions().getX(),
		    sizey = state.getDimensions().getY();
		Car[] cars = state.getCars();
		boolean[][] map = createMap(state, sizex, sizey);
		
		for(int i = 0; i < cars.length; i++) {
			Car c = cars[i];
			if(c.isHorizontal()) {
				int initx1 = c.getStart().getX(),
				    initx2 = c.getEnd().getX(),
				    x1 = initx1 - 1,
			        x2 = initx2 - 1,
			        y = c.getStart().getY();
			
				while(x1 >= 0 && !map[x1][y]) {
					c.getStart().setX(x1);
					c.getEnd().setX(x2);
					if(!visited.containsKey(state)) {
						State clone = state.clone();
						neighbors.add(clone);
					}
					else {
						State vis = visited.get(state);
						if(vis.compareTo(state) == 1) {
							visited.remove(state);
							State clone = state.clone();
							neighbors.add(clone);
						}
					}
					c.getStart().setX(initx1);
					c.getEnd().setX(initx2);
					x1--; x2--;
				}
				
				x1 = initx1 + 1;
				x2 = initx2 + 1;
				while(x2 < state.getDimensions().getX() && !map[x2][y]) {
					c.getStart().setX(x1);
					c.getEnd().setX(x2);
					if(!visited.containsKey(state)) {
						State clone = state.clone();
						neighbors.add(clone);
					}
					else {
						State vis = visited.get(state);
						if(vis.compareTo(state) == 1) {
							visited.remove(state);
							State clone = state.clone();
							neighbors.add(clone);
						}
					}
					c.getStart().setX(initx1);
					c.getEnd().setX(initx2);
					x1++; x2++;
				}
			}
			else {
				int inity1 = c.getStart().getY(),
		        	inity2 = c.getEnd().getY(),
				    y1 = inity1 - 1,
			        y2 = inity2 - 1,
			        x = c.getStart().getX();
			
				while(y1 >= 0 && !map[x][y1]) {
					c.getStart().setY(y1);
					c.getEnd().setY(y2);
					if(!visited.containsKey(state)) {
						State clone = state.clone();
						neighbors.add(clone);
					}
					else {
						State vis = visited.get(state);
						if(vis.compareTo(state) == 1) {
							visited.remove(state);
							State clone = state.clone();
							neighbors.add(clone);
						}
					}
					c.getStart().setY(inity1);
					c.getEnd().setY(inity2);
					y1--; y2--;
				}
				
				y1 = inity1 + 1;
				y2 = inity2 + 1;
				while(y2 < state.getDimensions().getY() && !map[x][y2]) {
					c.getStart().setY(y1);
					c.getEnd().setY(y2);
					if(!visited.containsKey(state)) {
						State clone = state.clone();
						neighbors.add(clone);
					}
					else {
						State vis = visited.get(state);
						if(vis.compareTo(state) == 1) {
							visited.remove(state);
							State clone = state.clone();
							neighbors.add(clone);
						}
					}
					c.getStart().setY(inity1);
					c.getEnd().setY(inity2);
					y1++; y2++;
				}
			}
		}
		return neighbors;
	}
}
