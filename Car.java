
public class Car {
	private int id;
	private Point start;
	private Point end;
	
	public Car(int id, Point start, Point end) {
		this.id = id;
		if(start.getX() > end.getX() || start.getY() > end.getY()) {
			this.start = end;
			this.end = start;
		}
		else {
			this.start = start;
			this.end = end;
		}
	}
	
	public Car clone() {
		return new Car(id, new Point(start.getX(), start.getY()),
				           new Point(end.getX(), end.getY()));
	}
	
	/* True if car crosses a horizontal line (equation y = line). */
	public boolean crossesHorizontal(int line) {
		return (start.getY() <= line) && (end.getY() >= line);
	}

	/* True if car crosses a vertical line (equation x = line). */
	public boolean crossesVertical(int line) {
		return (start.getX() <= line) && (end.getX() >= line);
	}
	
	public int getId() {
		return id;
	}
	
	public Point getStart() {
		return start;
	}
	
	public Point getEnd() {
		return end;
	}

	public boolean equals(Object o) {
		return equals((Car)o);
	}
	
	public boolean equals(Car car) {
		return id == car.id &&
		       start.equals(car.start) &&
		       end.equals(car.end);
	}
	
	public int hashCode() {
		int hash = 1;
		hash = 31 * hash + id;
		hash = 31 * hash + start.getX();
		hash = 31 * hash + start.getY();
		hash = 31 * hash + end.getX();
		hash = 31 * hash + end.getY();
		return hash;
	}
	
	public boolean isHorizontal() {
		return start.getY() == end.getY();
	}
	
	public String toString() {
		return "Car (" + id + "): (" + start.getX() + "," + start.getY() + ")" +
		                  ", " + "(" + end.getX() + "," + end.getY() + ")";
	}
}