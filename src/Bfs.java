import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


public class Bfs extends Algorithm {
	public Bfs(State initial, Heuristic heuristic, int nthreads) {
		visited = Collections.synchronizedMap(new HashMap<State, State>());
		queue = new LinkedBlockingQueue<State>();
		barrier = new CyclicBarrier(nthreads);
		
		initial.setHeuristic(heuristic);
		queue.offer(initial);
		visited.put(initial, initial);
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
				addNeighbors(State.findNeighbors(visited, state));
			}
		} catch(InterruptedException e) {
			return;
		} catch(BrokenBarrierException e) {
			System.out.println("Broken barrier");
			return;
		}
	}
}
