
public class Run {
	public static void main(String[] args) {
		ArgParser parser = new ArgParser();
		
		int nth = parser.parseInt();
		State initialState = parser.parseState();
	    Algorithm algorithm = new Astar(initialState, new SimpleHeuristic(), nth);
	    System.out.println("Initial state:");
	    System.out.println(initialState);
	    
	    for(int i = 0; i < nth; i++) {
		    (new Thread(algorithm)).start();
	    }
	}
}
