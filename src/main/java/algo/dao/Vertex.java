package algo.dao;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
	public Vertex(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Vertex> getConnections() {
		return connections;
	}
	public void setConnections(List<Vertex> connections) {
		this.connections = connections;
	}
	
	public void addConnection(Vertex orbitter) {
		if(connections==null)
			connections = new ArrayList<>();
		connections.add(orbitter);
	}
	private String name;
	private List<Vertex>connections;
}
