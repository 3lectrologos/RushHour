import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;


public class Astar implements Runnable {
	private static Map<State, Boolean> visited;
	private static BlockingQueue<State> queue;
	private static boolean DEBUG = false;
	private static CyclicBarrier barrier;
	
	public Astar(State initial, Heuristic heuristic, int nthreads) {
		visited = Collections.synchronizedMap(new HashMap<State, Boolean>());
		queue = new PriorityBlockingQueue<State>();
		barrier = new CyclicBarrier(nthreads);
		
		initial.setHeuristic(heuristic);
		queue.offer(initial);
		visited.put(initial, true);
	}
	
	public void run() {
		try {
			barrier.await();
			while(true) {
				State state;
					state = queue.poll(1000, TimeUnit.MILLISECONDS);
				if(DEBUG) {
					System.out.printf("---- Loop (visited size = %d) Queue poll:\n", visited.size());
					System.out.println(state);
				}
				if(state == null)
					result(state, -1);
				if(State.isFinal(state)) {
					result(state, state.getSteps() + 1);
				}
				
				addNeighbors(state);
			}
		} catch(InterruptedException e) {
			return;
		} catch(BrokenBarrierException e) {
			System.out.println("Broken barrier");
			return;
		}
	}
	
	private synchronized void result(State state, int result) {
		System.out.println(state);
		System.out.println(result);
		System.exit(0);
	}
	
	private boolean[][] createMap(State state, int sizex, int sizey) {
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
	
	private void addNeighbors(State conststate) {
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
