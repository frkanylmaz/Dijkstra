import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Dijkstra {
	private Edge[] edgeTo;
	private double[] disTo;
	private boolean[] visited;
	
	private List<Edge> graph_list;		//edge list
	private String s,d; 		// source vertex
	private double velocity;
	private double switchtime;
	private ArrayList<Edge> path;
	public  Dijkstra(Graph g, String s,double velocity,double switchtime,String d) {
		
		this.graph_list = g.getEdgeList();
		this.edgeTo= new Edge[g.totalNode()];
		this.disTo = new double[g.totalNode()];
		this.visited = new boolean[g.totalNode()];
		
		this.s = s;
		this.d=d;
		this.switchtime = switchtime; 
		this.velocity = velocity;
		this.path=path;
	
		progress(g);
	}
	
	public void progress (Graph g) {
		for(int i=0; i<disTo.length; i++)
			disTo[i] = Double.POSITIVE_INFINITY;
		
		ArrayList<Edge>SwitchEdges=new ArrayList<Edge>();
		path=new ArrayList<Edge>();
		for(int i=0; i<visited.length; i++)
			visited[i] = false;
		
		
		HashMap<String, Integer> coding = new HashMap<String, Integer>();
		int counter=0;
		for(Edge e : graph_list) 
			if(!coding.containsKey(e.getStart().getStr())) 
				coding.put(e.getStart().getStr(), counter++);
		
		
		disTo[coding.get(s)] = 0;
		edgeTo[coding.get(s)] = new Edge(new Node(s), new Node(s), 0);
		visited[coding.get(s)] = true;
		
		for(Edge edge:graph_list) {
			if(!visited[coding.get(edge.getDestination().getStr())]) {
				for(Edge e : graph_list) {
					if(e.isBroken() ) {	
						continue;
					}
					
					if(g.findNode(e.getStart().getStr()).isMaintenance()) {
						
						continue;
					}
					
					double ek_vel = switchtime * velocity / 60;
					
	
					if(e.isActive()) {
						if(e.getWeight() + disTo[coding.get(e.getStart().getStr())] < disTo[coding.get(e.getDestination().getStr())]) {
							disTo[coding.get(e.getDestination().getStr())] = e.getWeight() + disTo[coding.get(e.getStart().getStr())];
							edgeTo[coding.get(e.getDestination().getStr())] = e;
							
							SwitchEdges.add(e);
							
						}
					}
					else {
						if(e.getWeight() + disTo[coding.get(e.getStart().getStr())] + ek_vel < disTo[coding.get(e.getDestination().getStr())]) {
							disTo[coding.get(e.getDestination().getStr())] = e.getWeight() + disTo[coding.get(e.getStart().getStr())] + ek_vel;
							edgeTo[coding.get(e.getDestination().getStr())] = e;
							
						}								
					}
					
				}
				visited[coding.get(edge.getStart().getStr())] = true;
			}
			if(edgeTo[coding.get(edge.getDestination().getStr())] == edge) {
				SwitchEdges.add(edge);
			}
		
		}
		
		String source=s;
		String dstination=d;
		
		int cnt = 0;
		while(!source.equals(dstination))
			
		{
			for(Edge e:SwitchEdges) {
				if(e.getDestination().getStr().equals(dstination))
				{
					dstination=e.getStart().getStr();
					path.add(e);
					//continue;
				}
			}
			
			if(cnt++ == 1000) break;
		
		}
		

	}
	public void Printpath(Graph g) {
		
		for(int i=0; i<disTo.length; i++) {
			if(edgeTo[i] != null) {
				if(d.equals(edgeTo[i].getDestination().getStr())) {
					//System.out.println("\t"+"Time (in min): "+ (disTo[(int) i] * 60 / velocity));
					System.out.printf("\t"+"Time (in min): %.3f",(disTo[(int) i] * 60 / velocity));
					System.out.println();
					
				}
			}
		}
		int totalswitch=0;
		for(Edge e :path) {
			if(!e.isActive())
				totalswitch++;
			
		}
		Crosstimes(g);
		for(Edge e:path)
		{
			if(!e.isActive()) {
			for(Edge ed: graph_list) {
				if(ed.getStart().getStr().equals(e.getStart().getStr()))
					ed.setActive(false);
				}
			}
			e.setActive(true);
		}
		System.out.println("\t"+"Total # of switch changes: "+totalswitch);
		System.out.print("\t"+"Route from "+s+" to "+ d+":" );
		for(int i=path.size()-1;i>=0;i--) {
			System.out.print(path.get(i).getStart().getStr()+" ");
			if(i==0) {
				System.out.print(d);
				System.out.println();
			}
		}
		
		
	}
	public void Crosstimes(Graph g) {
		ArrayList<Node> nodes=new ArrayList<Node>();
		for(Edge e: path) {
			if(!nodes.contains(e.getStart())) {
				nodes.add(e.getStart());
			}
			if(!nodes.contains(e.getDestination())) {
				nodes.add(e.getDestination());
			}
			
		}
		
		for(Node n: nodes) {
			n.setPassed_train(n.getPassed_train()+1);
		}
	}
	
	public String totalTime(String time) {
		return time;
	}

	public List<Edge> getGraph_list() {
		return graph_list;
	}

	public void setGraph_list(List<Edge> graph_list) {
		this.graph_list = graph_list;
	}

	public Edge[] getEdgeTo() {
		return edgeTo;
	}

	public void setEdgeTo(Edge[] edgeTo) {
		this.edgeTo = edgeTo;
	}

	public double[] getDisTo() {
		return disTo;
	}

	public void setDisTo(double[] disTo) {
		this.disTo = disTo;
	}

	public boolean[] getVisited() {
		return visited;
	}

	public void setVisited(boolean[] visited) {
		this.visited = visited;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public double getVelocity() {
		return velocity;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	public double getSwitchtime() {
		return switchtime;
	}

	public void setSwitchtime(double switchtime) {
		this.switchtime = switchtime;
	}
	
}
