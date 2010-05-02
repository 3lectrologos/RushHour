
public class Run {

	public static void main(String[] args) {
		if(args.length != 5) {
			System.err.printf("Wrong number of arguments (%d).\nExpected:nthreads sizex sizey (exitx, exity) [cars]", args.length);
			System.exit(1);
		}
		
		int nth = Integer.parseInt(args[0]);
		ArgParser parser = new ArgParser();
		State initialState = parser.parse(args[1], args[2], args[3], args[4]);
	    Astar astar = new Astar(initialState, new SimpleHeuristic(), nth);
	    System.out.println("Initial state:");
	    System.out.println(initialState);

	    for(int i = 0; i < nth; i++) {
		    (new Thread(astar)).start();
	    }
	}

}
