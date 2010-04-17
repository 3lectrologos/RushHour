import java.util.HashMap;
import java.util.PriorityQueue;


public class Astar {
	private HashMap<State, Boolean> visited;
	private PriorityQueue<State> queue;
	private boolean DEBUG;
	
	public Astar(State initial, Heuristic heuristic, boolean debug) {
		visited = new HashMap<State, Boolean>();
		queue = new PriorityQueue<State>();
		
		DEBUG = debug;
		
		initial.setHeuristic(heuristic);
		queue.offer(initial);
		visited.put(initial, true);
	}
	
	public int run() {
		while(!queue.isEmpty()) {
			State state = queue.poll();
			if(DEBUG) {
				System.out.printf("---- Loop (visited size = %d) Queue poll:\n", visited.size());
				System.out.println(state);
			}
			if(state.isFinal()) {
				System.out.println(state);
				return state.getSteps() + 1;
			}
			
			addNeighbors(state);
		}
		
		return -1;
	}
	
	private void addNeighbors(State conststate) {
		State state = conststate.clone();
		state.setSteps(state.getSteps() + 1);
		int sizex = state.getDimensions().getX(),
		    sizey = state.getDimensions().getY();
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
						queue.offer(clone);
						visited.put(clone, true);
						if(DEBUG) {
							System.out.printf("Hor1:\n");
							System.out.println(state);
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
						queue.offer(clone);
						visited.put(clone, true);
						if(DEBUG) {
							System.out.printf("Hor2:\n");
							System.out.println(state);
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
						queue.offer(clone);
						visited.put(clone, true);
						if(DEBUG) {
							System.out.printf("Ver1:\n");
							System.out.println(state);
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
						queue.offer(clone);
						visited.put(clone, true);
						if(DEBUG) {
							System.out.printf("Ver2:\n");
							System.out.println(state);
						}
					}
					c.getStart().setY(inity1);
					c.getEnd().setY(inity2);
					y1++; y2++;
				}
			}
		}
	}
}
