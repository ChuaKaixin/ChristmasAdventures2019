package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import algo.dao.Vertex;

public class ComputationHandler6 {

	public static void main(String[] args) throws Exception{
		InputStream in = ComputationHandler.class.getClass().getResourceAsStream("/Day6Input.txt");
		Map<String, Vertex> graph = new HashMap<>();
		Map<String, String> parentMap = new HashMap<>();
		//PART 1 ----
		/**
		readFromInputStream(in, graph);
		int orbits = traverseGraph(graph, "COM", 0);
        System.out.println("Total orbit count :" + orbits);
        **/
		readFromInputStreamPopulateParentMap(in, parentMap);
		List<String>converge1Path = new ArrayList<>();
		List<String>converge2Path = new ArrayList<>();
		String converge1 = "YOU";
		String converge2 = "SAN";
		String convergedNode = "";
		int pathCount = 0;
		while (!converge1.equals(converge2)) {
	        System.out.print("Moved from " + converge1 + " ---" + converge2);
			converge1 = parentMap.get(converge1);
			converge2 = parentMap.get(converge2);
			converge1Path.add(converge1);
			converge2Path.add(converge2);
			if(converge1Path.contains(converge2)) {
				convergedNode = converge2;
				break;
			}
			if(converge2Path.contains(converge1)) {
				convergedNode = converge1;
				break;
			}
	        System.out.println(" TO " + converge1 + " ---" + converge2);
		}
		pathCount = converge1Path.indexOf(convergedNode) + converge2Path.indexOf(convergedNode);
        System.out.println("Hops :" + pathCount);
	}
	
	private static void readFromInputStreamPopulateParentMap(InputStream inputStream, Map<String, String>parentMap)
			  throws IOException {
	    try (BufferedReader br
	      = new BufferedReader(new InputStreamReader(inputStream))) {
	        String line;
	        int linecount=0;
	        while ((line = br.readLine()) != null) {
	        	String[]connection = line.split("\\)");
	        	System.out.println("ORBIT-" + connection[0] + "---" + connection[1]);
	        	parentMap.put(connection[1], connection[0]);
	        	linecount++;
	        }
	        
	    }
	}
	
	private static int traverseGraph(Map<String, Vertex> graph, String nodeName, int orbitCount) {
		Vertex node = graph.get(nodeName);
		int orbitSum = orbitCount;
		if(node.getConnections()!=null && node.getConnections().size()>0) {
			for(Vertex orbitter:node.getConnections()) {
				orbitSum+=traverseGraph(graph, orbitter.getName(), orbitCount+1);
			}
		} 
		return orbitSum;
	}

	private static void readFromInputStream(InputStream inputStream, Map<String, Vertex> graph)
			  throws IOException {
	    try (BufferedReader br
	      = new BufferedReader(new InputStreamReader(inputStream))) {
	        String line;
	        int linecount=0;
	        while ((line = br.readLine()) != null) {
	        	String[]connection = line.split("\\)");
	        	System.out.println("ORBIT-" + connection[0] + "---" + connection[1]);
	        	populateGraph(graph, connection[0], connection[1]);
	        	linecount++;
	        }
	        
	    }
	}
	
	private static void populateGraph(Map<String, Vertex> graph, String core, String orbiter) {
		Vertex coreNode = graph.get(core)==null?new Vertex(core):graph.get(core);
		Vertex orbitterNode = graph.get(orbiter)==null?new Vertex(orbiter):graph.get(orbiter);
		coreNode.addConnection(orbitterNode);
		graph.put(core, coreNode);
		graph.put(orbiter, orbitterNode);
	}
}
