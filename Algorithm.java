import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CyclicBarrier;


public abstract class Algorithm implements Runnable {
	protected static Map<State, Boolean> visited;
	protected static BlockingQueue<State> queue;
	protected static boolean DEBUG = false;
	protected static CyclicBarrier barrier;
	
	protected void addNeighbors(List<State> list) {
		Iterator<State> iter = list.iterator();
		
		while(iter.hasNext()) {
			State next = iter.next();
			visited.put(next, true);
			queue.offer(next);
		}
	}

	protected synchronized void result(State state, int result) {
		if(state == null)
			System.out.println("Problem unsolvable.");
		else {
			System.out.println("Final state:");
			System.out.println(state);
			System.out.printf("Steps = %d\n", result);
		}
		System.exit(0);
	}
	
	public abstract void run();
}
