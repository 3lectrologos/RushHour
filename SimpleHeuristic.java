
public class SimpleHeuristic extends Heuristic {

	public SimpleHeuristic() {
	}
	
	public int eval(State state) {
		return State.blockingCars(state);
	}

}