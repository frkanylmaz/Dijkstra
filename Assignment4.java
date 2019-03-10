import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Assignment4 {

	public static void main(String[] args) {
		ArrayList<String> lines=new ArrayList<String>();
		ArrayList<String> lines_distance=new ArrayList<String>();
		Graph g=new Graph();
		
		ArrayList<Edge> brokenEdge=new ArrayList<Edge>();
		ArrayList<String> linkedEgde=new ArrayList<String>();
		ArrayList<Double> linkedEgdedistance=new ArrayList<Double>();
		double switchtime= Double.parseDouble(args[3]);
		try {
			Scanner scanner = new Scanner(new File(args[0]));
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				lines.add(line);	
		}	
	}
		catch(FileNotFoundException ex) {
			System.out.println("No File Found!");
			return;	
		}
		for(String line:lines)
		{
			String nodeName=line.split(":")[0];
			Node n =new Node(nodeName);
			g.addNode(n);
		}
		for(String line:lines)
		{
			String[] nodesName=line.split(":")[1].split(",");
			for(String l:nodesName) {
				String parentnode=line.split(":")[0];
				//
				Node parnt=g.findNode(parentnode);
				if(l.contains(">")) {
					
					String[] inp=l.split(">");
					String sNode=inp[0];			
					Node n =g.findNode(sNode);
					g.addEdge(parnt, n);
					
					Edge e=g.FindEdge(parentnode, inp[1]);
					if(e!=null) {
						e.setActive(true);
					}
				}
				else {
					Node n=g.findNode(l);
					g.addEdge(parnt,n);	
				}
			}	
		}		
			
		try {
			Scanner scanner = new Scanner(new File(args[1]));
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				lines_distance.add(line);
						
		}
			for(String s:lines_distance) {
				String startNode=s.split("-")[0];
				String[] destNode=s.split("-")[1].split(" ");
				String desttnode=destNode[0];
				double distance=Double.parseDouble(destNode[1]);		
				g.addDistance(startNode, desttnode, distance);
				
				}
			for(String s:lines_distance) {
				String destNode=s.split("-")[0];
				String[] startNode=s.split("-")[1].split(" ");
				String desttnode=startNode[0];
				
				double distance=Double.parseDouble(startNode[1]);
				g.addDistance(desttnode, destNode, distance);
				}
	}
		catch(FileNotFoundException ex) {
			System.out.println("No File Found!");
			return;
			
		}
		try {
			Scanner scanner = new Scanner(new File(args[2]));
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				String[]input=line.split(" ");
				if(input[0].equals("MAINTAIN")) {
					System.out.println("COMMAND IN PROCESS >> "+input[0]+" "+input[1]);
					System.out.println("\t"+"Command \""+input[0]+" "+input[1]+"\""+" has been executed successfully!");
					g.maintain(input[1],true);
					
					
				}
				else if(input[0].equals("SERVICE")) {
					System.out.println("COMMAND IN PROCESS >> "+input[0]+" "+input[1]);
					System.out.println("\t"+"Command \""+input[0]+" "+input[1]+"\""+" has been executed successfully!");
					g.maintain(input[1],false);
				}
				else if(input[0].equals("BREAK")) {
					System.out.println("COMMAND IN PROCESS >> "+input[0]+" "+input[1]);
					System.out.println("\t"+"Command \""+input[0]+" "+input[1]+"\""+" has been executed successfully!");
					String[] s=input[1].split(">");
					String a=s[0];
					String b=s[1];
					if(g.FindEdge(a, b)!=null) {		
						g.FindEdge(a, b).setBroken(true);
						
						brokenEdge.add(g.FindEdge(a, b));
					}
					
				}
				else if(input[0].equals("REPAIR")) {
					System.out.println("COMMAND IN PROCESS >> "+input[0]+" "+input[1]);
					String[] s=input[1].split(">");
					String a=s[0];
					String b=s[1];
					g.FindEdge(a, b).setBroken(false);
					for(int i=0;i<brokenEdge.size();i++) {
						if(g.FindEdge(a, b).equals(brokenEdge.get(i))) {
							brokenEdge.remove(i);
						}	
					}
					System.out.println("\t"+"Command \""+input[0]+" "+input[1]+"\""+" has been executed successfully!");
	
				}
				else if(input[0].equals("ADD")) {
					System.out.println("COMMAND IN PROCESS >> "+input[0]+" "+input[1]);
					System.out.println("\t"+"Command \""+input[0]+" "+input[1]+"\""+" has been executed successfully!");
					Node v=new Node(input[1]);
					g.addNode(v);
					
				}
				else if(input[0].equals("LINK")) {
					
					String[] s=input[1].split(":");	
					String[] a=s[1].split(">");	
					String[]b =a[0].split(",");

					for(int i=0;i<b.length;i++) {
						String[]c= b[i].split("-");
						linkedEgde.add(c[0]);
						linkedEgdedistance.add(Double.parseDouble(c[1]));
					}
					
					for(int i=0;i<linkedEgde.size();i++) {
						Node n=g.findNode(s[0]);
						Node m=g.findNode(linkedEgde.get(i));
						g.addEdge(n, m);
						g.addEdge(m, n);
						g.addDistance(s[0], linkedEgde.get(i), linkedEgdedistance.get(i));
						g.addDistance(linkedEgde.get(i), s[0], linkedEgdedistance.get(i));
					}
					
					linkedEgde.clear();
					linkedEgdedistance.clear();
					System.out.println("COMMAND IN PROCESS >> "+input[0]+" "+input[1]);
					System.out.println("\t"+"Command \""+input[0]+" "+input[1]+"\""+" has been executed successfully!");

				}
				else if(input[0].equals("ROUTE")) {
					System.out.println("COMMAND IN PROCESS >> "+input[0]+" "+input[1]+" " +input[2]);
					String[] a=input[1].split(">");
					double velocity= Double.parseDouble(input[2]);
					Dijkstra d= new Dijkstra(g, input[1].split(">")[0],velocity,switchtime,input[1].split(">")[1]);
					if(g.findNode(a[0]).isMaintenance()||g.findNode(a[1]).isMaintenance())
						System.out.println("\t"+"No route from "+ a[0]+" to "+ a[1]+" found currently!");
					else {
						
						d.Printpath(g);
					}
					
					System.out.println("\t"+"Command \""+input[0]+" "+input[1]+"\""+" has been executed successfully!");
					
				}
				else if(input[0].equals("LISTROUTESFROM")) {
					System.out.println("COMMAND IN PROCESS >> "+input[0]+" "+input[1]);
					System.out.print("\t"+"Routes from "+input[1]+": ");
					g.listRoutesFrom(input[1]);
					System.out.println();
					System.out.print   ("\t"+"Command \""+input[0]+" "+input[1]+ "\""+" has been executed successfully!");
					System.out.println();
		
				}
				else if(input[0].equals("LISTMAINTAINS")) {
					System.out.println("COMMAND IN PROCESS >> "+input[0]);
					System.out.print("\t"+"Intersections under maintenance: ");
					for(int i=0;i<g.totalNode();i++) {
						if(g.adj()[i].get(0).isMaintenance()) {
							System.out.print(g.adj()[i].get(0).getStr()+" ");		
						}
					}
					System.out.println();
					System.out.print   ("\t"+"Command \""+input[0]+"\""+" has been executed successfully!"+"\n");
	
				}
				else if(input[0].equals("LISTACTIVERAILS")) {
					System.out.println("COMMAND IN PROCESS >> "+input[0]);
					System.out.print("\t"+"Active Rails: ");
					for(Edge e: g.getEdgeList()) {
						if(e.isActive()) {
							System.out.print(e.getStart().getStr()+">"+e.getDestination().getStr()+" ");
						
						}
					}
					System.out.println();
					System.out.println("\t"+"Command \""+input[0]+"\""+" has been executed successfully!");
					
					
				}
				else if(input[0].equals("LISTBROKENRAILS")) {
					System.out.println("COMMAND IN PROCESS >> "+input[0]);
					System.out.print("\t"+"Broken rails: ");
					for(Edge e:brokenEdge) {
						System.out.print(e.getStart().getStr()+">"+e.getDestination().getStr()+" ");
						
					}
					System.out.println();
					System.out.print   ("\t"+"Command \""+input[0]+"\""+" has been executed successfully!"+"\n");
					
				}
				else if(input[0].equals("LISTCROSSTIMES")) {
					System.out.println("COMMAND IN PROCESS >> "+input[0]);
					System.out.print("\t"+"# of cross times: ");
					for(int i=0;i<g.totalNode();i++)
					{
						if(g.adj()[i].get(0).getPassed_train()>0)
						System.out.print(g.adj()[i].get(0).getStr()+":"+g.adj()[i].get(0).getPassed_train()+" ");
					}
					System.out.println();
					
				}
				else if(input[0].equals("TOTALNUMBEROFJUNCTIONS")) {
					System.out.println("COMMAND IN PROCESS >> "+input[0]);
					int a=g.totalNode();
					System.out.println("\t"+"Total # of junctions: "+a);
					System.out.print   ("\t"+"Command \""+input[0]+"\""+" has been executed successfully!"+"\n");
					
					
				}
				else if(input[0].equals("TOTALNUMBEROFRAILS")) {
					System.out.println("COMMAND IN PROCESS >> "+input[0]);
					System.out.println("\t"+"Total # of rails: "+g.totalEdge());
					System.out.print   ("\t"+"Command \""+input[0]+"\""+" has been executed successfully!"+"\n");
					
				}
				else {
					System.out.println("COMMAND IN PROCESS >> "+input[0]);
					System.out.println("\t"+"Unrecognized command \""+input[0]+"\"!");
				}
	
			}
		}
		catch(FileNotFoundException ex) {
			System.out.println("No File Found!");
			return;
			
		}
		
	}
	

}
