import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {
	private ArrayList<Edge>edgeList=new ArrayList<Edge>();
	
	private int V=0;
	private ArrayList<Node>[] adj;
	public Graph()
	{
		
		init();
	
	}
	public void addEdge(Node v, Node w)
	{
		for(int i=0;i<V;i++) {
			if(adj[i].get(0).getStr().equals(v.getStr())) {
				adj[i].add(w);
				Edge e= new Edge(v,w,0);
				edgeList.add(e);
				
			}
			
		}
	
	}
	public void addNode(Node v) {
		adj[V]=new ArrayList<Node>();
		adj[V].add(v);
		//System.out.println(adj[V].get(0).getStr());
		V++;
	}
	public Node findNode(String v) {
		for(int i=0;i<V;i++) {
			if(adj[i].get(0).getStr().equals(v)) {
				return adj[i].get(0);
			}
			
		}
		return null;
		
	}
	public Edge FindEdge(String s,String t) {
		for(Edge e:edgeList) {
			if(e.getStart().getStr().equals(s)&&e.getDestination().getStr().equals(t)) {
				return e;
			}
		}
		return null;
	
	}
	@SuppressWarnings("unchecked")
	public void init() {
		this.adj = (ArrayList<Node>[]) new ArrayList[10000];
	}
	public Edge addDistance(String s,String t,double distance) {
		for(Edge e:edgeList) {	
			if(e.getStart().getStr().equals(s)&&e.getDestination().getStr().equals(t)) {	
				e.setWeight(distance);	
				return e;
			}
			
		}
		
		return null;
	}
	public void maintain(String s,boolean m) {
		Node n=findNode(s);
		if(n!=null) {
			n.setMaintenance(m);
		}
		
	}
	
	public void Print() {
		for(int i=0;i<V;i++) {
			System.out.print(adj[i].get(0).getStr()+": " );
			for(int j=1;j<adj[i].size();j++) {
				System.out.print(adj[i].get(j).getStr()+" ");
			}
			System.out.println();
		}
		
	}
	public int totalNode() {
		return V;
	}
	public ArrayList<Node>[] adj(){
		return adj;
	}
	public int totalEdge() {
		int i=edgeList.size();
		return i;
	}
	public void listRoutesFrom(String s) {
		
		for(int i=0;i<V;i++) {
			if(s.equals(adj[i].get(0).getStr())) {
				for(int j=1;j<adj[i].size();j++) {
					System.out.print(adj[i].get(j).getStr()+" ");
				}	
			}
		}
		
	}
	public ArrayList<Edge> getEdgeList() {
		return edgeList;
	}
	public void setEdgeList(ArrayList<Edge> edgeList) {
		this.edgeList = edgeList;
	}
	
	

}
