import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Edge {
	
	private boolean active=false;
	private boolean broken = false;
	private Node start;
	private Node destination;
	private double weight;

public Edge(Node start,Node destination,double weight) {
		
		this.setWeight(weight);
		
		this.start=start;
		this.destination=destination;
	}
	
	public String toString() {
		return start.getStr() + "->" + destination.getStr();
	}
	

	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public Node getDestination() {
		return destination;
	}
	public void setDestination(Node destination) {
		this.destination = destination;
	}
	public Node getStart() {
		return start;
	}
	public void setStart(Node start) {
		this.start = start;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean isBroken() {
		return broken;
	}
	public void setBroken(boolean broken) {
		this.broken = broken;
	}
}
