
public class Test {
	public static void main(String args[]) {
		int arg, nthreads;
		if(args.length > 0)
			arg= Integer.parseInt(args[0]);
		else
			arg = 7;
		if(args.length > 1)
			nthreads = Integer.parseInt(args[1]);
		else
			nthreads = 4;

		State state = state1();
		switch(arg) {
		case 1:
			state = state1();
			break;
		case 2:
			state = state2();
			break;
		case 3:
			state = state3();
			break;
		case 4:
			state = state4();
			break;
		case 5:
			state = state5();
			break;
		case 6:
			state = state6();
			break;
		case 7:
			state = state7();
			break;
		case 8:
			state = state8();
			break;
		}
	    
	    Astar a = new Astar(state, new SimpleHeuristic(), nthreads);
	    System.out.println("Initial state:");
	    System.out.println(state);

	    for(int i = 0; i < nthreads; i++) {
		    (new Thread(a)).start();
	    }
	}
	
	private static State state1() {
		Car[] cars = new Car[8];
	    cars[0] = new Car(0, new Point(1, 2), new Point(2, 2));
	    cars[1] = new Car(1, new Point(0, 0), new Point(1, 0));
	    cars[2] = new Car(2, new Point(0, 1), new Point(0, 3));
	    cars[3] = new Car(3, new Point(0, 4), new Point(0, 5));
	    cars[4] = new Car(4, new Point(3, 1), new Point(3, 3));
	    cars[5] = new Car(5, new Point(5, 0), new Point(5, 2));
	    cars[6] = new Car(6, new Point(4, 4), new Point(5, 4));
	    cars[7] = new Car(7, new Point(2, 5), new Point(4, 5));
	    
	    return new State(new Point(6, 6), cars, new Point(5, 2));
	}

	private static State state2() {
		Car[] cars = new Car[10];
	    cars[0] = new Car(0, new Point(1, 2), new Point(2, 2));
	    cars[1] = new Car(1, new Point(0, 0), new Point(0, 2));
	    cars[2] = new Car(2, new Point(2, 0), new Point(2, 1));
	    cars[3] = new Car(3, new Point(3, 0), new Point(5, 0));
	    cars[4] = new Car(4, new Point(3, 1), new Point(3, 2));
	    cars[5] = new Car(5, new Point(0, 3), new Point(1, 3));
	    cars[6] = new Car(6, new Point(2, 3), new Point(3, 3));
	    cars[7] = new Car(7, new Point(0, 5), new Point(1, 5));
	    cars[8] = new Car(8, new Point(2, 5), new Point(3, 5));
	    cars[9] = new Car(9, new Point(5, 3), new Point(5, 5));
	    
	    return new State(new Point(6, 6), cars, new Point(5, 2));
	}
	
	private static State state3() {
		Car[] cars = new Car[13];
	    cars[0] = new Car(0, new Point(0, 2), new Point(1, 2));
	    cars[1] = new Car(1, new Point(4, 0), new Point(5, 0));
	    cars[2] = new Car(2, new Point(3, 0), new Point(3, 1));
	    cars[3] = new Car(3, new Point(1, 1), new Point(2, 1));
	    cars[4] = new Car(4, new Point(0, 3), new Point(1, 3));
	    cars[5] = new Car(5, new Point(0, 4), new Point(0, 5));
	    cars[6] = new Car(6, new Point(2, 2), new Point(2, 3));
	    cars[7] = new Car(7, new Point(1, 4), new Point(3, 4));
	    cars[8] = new Car(8, new Point(2, 5), new Point(3, 5));
	    cars[9] = new Car(9, new Point(5, 4), new Point(5, 5));
	    cars[10] = new Car(10, new Point(3, 2), new Point(3, 3));
	    cars[11] = new Car(11, new Point(4, 1), new Point(4, 2));
	    cars[12] = new Car(12, new Point(4, 3), new Point(5, 3));

	    
	    return new State(new Point(6, 6), cars, new Point(5, 2));
	}
	
	private static State state4() {
		Car[] cars = new Car[11];
	    cars[0] = new Car(0, new Point(1, 2), new Point(2, 2));
	    cars[1] = new Car(1, new Point(0, 0), new Point(1, 0));
	    cars[2] = new Car(2, new Point(3, 1), new Point(3, 2));
	    cars[3] = new Car(3, new Point(3, 0), new Point(5, 0));
	    cars[4] = new Car(4, new Point(0, 2), new Point(0, 3));
	    cars[5] = new Car(5, new Point(4, 1), new Point(5, 1));
	    cars[6] = new Car(6, new Point(2, 3), new Point(2, 5));
	    cars[7] = new Car(7, new Point(0, 4), new Point(1, 4));
	    cars[8] = new Car(8, new Point(3, 3), new Point(4, 3));
	    cars[9] = new Car(9, new Point(5, 2), new Point(5, 4));
	    cars[10] = new Car(10, new Point(3, 5), new Point(5, 5));

	    
	    return new State(new Point(6, 6), cars, new Point(5, 2));
	}
	
	private static State state5() {
		Car[] cars = new Car[11];
	    cars[0] = new Car(0, new Point(0, 2), new Point(1, 2));
	    cars[1] = new Car(1, new Point(0, 0), new Point(1, 0));
	    cars[2] = new Car(2, new Point(2, 0), new Point(2, 2));
	    cars[3] = new Car(3, new Point(3, 0), new Point(3, 1));
	    cars[4] = new Car(4, new Point(4, 0), new Point(5, 0));
	    cars[5] = new Car(5, new Point(0, 3), new Point(0, 4));
	    cars[6] = new Car(6, new Point(1, 3), new Point(2, 3));
	    cars[7] = new Car(7, new Point(3, 3), new Point(4, 3));
	    cars[8] = new Car(8, new Point(0, 5), new Point(1, 5));
	    cars[9] = new Car(9, new Point(3, 4), new Point(3, 5));
	    cars[10] = new Car(10, new Point(5, 3), new Point(5, 5));

	    
	    return new State(new Point(6, 6), cars, new Point(5, 2));
	}

	private static State state6() {
		Car[] cars = new Car[13];
	    cars[0] = new Car(0, new Point(3, 2), new Point(4, 2));
	    cars[1] = new Car(1, new Point(0, 0), new Point(0, 2));
	    cars[2] = new Car(2, new Point(1, 0), new Point(2, 0));
	    cars[3] = new Car(3, new Point(4, 0), new Point(4, 1));
	    cars[4] = new Car(4, new Point(1, 1), new Point(1, 2));
	    cars[5] = new Car(5, new Point(2, 1), new Point(2, 2));
	    cars[6] = new Car(6, new Point(5, 1), new Point(5, 3));
	    cars[7] = new Car(7, new Point(0, 3), new Point(2, 3));
	    cars[8] = new Car(8, new Point(3, 3), new Point(3, 4));
	    cars[9] = new Car(9, new Point(4, 4), new Point(5, 4));
	    cars[10] = new Car(10, new Point(0, 5), new Point(1, 5));
	    cars[11] = new Car(11, new Point(2, 4), new Point(2, 5));
	    cars[12] = new Car(12, new Point(3, 5), new Point(4, 5));

	    
	    return new State(new Point(6, 6), cars, new Point(5, 2));
	}

	/* Hardest 6x6 */
	private static State state7() {
		Car[] cars = new Car[13];
		cars[0] = new Car(0, new Point(2, 2), new Point(3, 2));
		cars[1] = new Car(1, new Point(0, 1), new Point(0, 2));
		cars[2] = new Car(2, new Point(1, 4), new Point(1, 5));
		cars[3] = new Car(3, new Point(2, 3), new Point(2, 4));
		cars[4] = new Car(4, new Point(3, 0), new Point(3, 1));
		cars[5] = new Car(5, new Point(4, 0), new Point(4, 2));
		cars[6] = new Car(6, new Point(5, 0), new Point(5, 2));
		cars[7] = new Car(7, new Point(0, 0), new Point(2, 0));
		cars[8] = new Car(8, new Point(1, 1), new Point(2, 1));
		cars[9] = new Car(9, new Point(0, 3), new Point(1, 3));
		cars[10] = new Car(10, new Point(4, 4), new Point(5, 4));
		cars[11] = new Car(11, new Point(2, 5), new Point(3, 5));
		cars[12] = new Car(12, new Point(4, 5), new Point(5, 5));
	   
		return new State(new Point(6, 6), cars, new Point(5, 2));
	}
	
	/* Big one by Manolis Liosis. */
	private static State state8() {
		Car[] cars = new Car[15];
		
		cars[0] = new Car(0, new Point(1,5), new Point(3,5));
		cars[1] = new Car(1, new Point(0,5), new Point(0,11));
		cars[2] = new Car(2, new Point(5,3), new Point(5,6));
		cars[3] = new Car(3, new Point(3,2), new Point(5,2));
		cars[4] = new Car(4, new Point(2,1), new Point(2,4));
		cars[5] = new Car(5, new Point(1,0), new Point(2,0));
		cars[6] = new Car(6, new Point(3,7), new Point(3,9));
		cars[7] = new Car(7,new Point(4,8), new Point(7,8));
		cars[8] = new Car(8, new Point(8,7), new Point(8,10));
		cars[9] = new Car(9, new Point(7,0), new Point(7,4));
		cars[10] = new Car(10, new Point(8,4), new Point(11,4));
		cars[11] = new Car(11, new Point(1,10), new Point(4,10));
		cars[12] = new Car(12, new Point(5,9), new Point(5,11));
		cars[13] = new Car(13, new Point(7,11), new Point(10,11));
		cars[14] = new Car(14, new Point(9,5), new Point(9,10));

		return new State(new Point(12, 12), cars, new Point(11, 5));
	}
}
