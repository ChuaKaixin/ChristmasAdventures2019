package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ComputationHandler3 {

	public static void main(String[] args) throws Exception{
		InputStream in = ComputationHandler.class.getClass().getResourceAsStream("/Day3Input.txt");
		List<String[]> inputs = readFromInputStream(in);
		Set<String> coordinates1 = new HashSet<>();
		generateMap(inputs.get(0), coordinates1);
		System.out.print("Set 1:" + coordinates1.size());
		Set<String> coordinates2 = new HashSet<>();
		generateMap(inputs.get(1), coordinates2);
		System.out.print("Set 2:" + coordinates2.size());
		coordinates1.retainAll(coordinates2);
		System.out.print("intersections:" + coordinates1.size());
		System.out.print("shortest Walk:" + findShortestRouteIntersection(coordinates1, inputs));
		//System.out.print("min Dist:" + findShortestDistance(coordinates1));
	}
	
	
	private static int findShortestRouteIntersection(Set<String> intersections, List<String[]>inputs) {
		int minSteps = 1000000;
		for(String intersection : intersections) {
			String[]points = intersection.split("\\|");
			System.out.println("Intersection x: " + points[0] + "Intersection y: " + points[1] );
			int currentX = Integer.parseInt(points[0]); 
			int currentY = Integer.parseInt(points[1]);
			int walk1 = countSteps(currentX, currentY, inputs.get(0));
			int walk2 = countSteps(currentX, currentY, inputs.get(1));
			if(walk1 + walk2 < minSteps) {
				minSteps = walk1 + walk2;
			}
		}
		return minSteps;
	}
	
	private static int countSteps(int currentX, int currentY, String[] input) {
		int walkX = 0;
		int walkY = 0;
		int steps = 0;
		for(String coordinate : input) {
			System.out.print("coordinate:" + coordinate);
			String firstLetter = coordinate.substring(0, 1);
			System.out.print("--first letter:" + firstLetter);
			int len = Integer.parseInt(coordinate.substring(1));
			System.out.println("--len:" + len);
			switch (firstLetter) {
				case "L":
					for(int i = 1; i <= len; i++) {
						walkX--;
						steps++;
						if(walkX == currentX && walkY== currentY) {
							return steps;
						}
					}
					break;
				case "R":
					for(int i = 1; i <= len; i++) {
						walkX++;
						steps++;
						if(walkX == currentX && walkY== currentY) {
							return steps;
						}
					}
					break;
				case "U":
					for(int i = 1; i <= len; i++) {
						walkY++;
						steps++;
						if(walkX == currentX && walkY== currentY) {
							return steps;
						}
					}
					break;
				case "D":
					for(int i = 1; i <= len; i++) {
						walkY--;
						steps++;
						if(walkX == currentX && walkY== currentY) {
							return steps;
						}
					}
					break;			
				default:
					break;
				}
		}
		return 0;
	}
	private static int findShortestDistance(Set<String> coordinates) {
		int minDist = 1000000;
		for(String coordinate:coordinates) {
			String[]points = coordinate.split("\\|");
			System.out.println("Intersection x: " + points[0] + "Intersection y: " + points[1] );
			int current = Math.abs(Integer.parseInt(points[0])) + Math.abs(Integer.parseInt(points[1]));
			if(current < minDist) {
				minDist = current;
			}
		}
		return minDist;
	}
	private static void generateMap(String[] input, Set<String>coordinates) {
		int currentX = 0;
		int currentY = 0;
		for(String coordinate : input) {
			System.out.print("coordinate:" + coordinate);
			String firstLetter = coordinate.substring(0, 1);
			System.out.print("--first letter:" + firstLetter);
			int len = Integer.parseInt(coordinate.substring(1));
			System.out.println("--len:" + len);
			switch (firstLetter) {
				case "L":
					for(int i = 1; i <= len; i++) {
						coordinates.add("" + (currentX -i) + "|" + (currentY));
					}
					currentX-=len;
					break;
				case "R":
					for(int i = 1; i <= len; i++) {
						coordinates.add("" + (currentX +i) + "|" + (currentY));
					}
					currentX+=len;
					break;
				case "U":
					for(int i = 1; i <= len; i++) {
						coordinates.add("" + (currentX) + "|" + (currentY+i));
					}
					currentY+=len;
					break;
				case "D":
					for(int i = 1; i <= len; i++) {
						coordinates.add("" + (currentX) + "|" + (currentY-i));
					}
					currentY-=len;
					break;			
				default:
					break;
				}
		}
	}
	
	private static List<String[]> readFromInputStream(InputStream inputStream)
			  throws IOException {
			List<String[]> wires = new ArrayList<>();
			    try (BufferedReader br
			      = new BufferedReader(new InputStreamReader(inputStream))) {
			        String line = br.readLine();
			        String[]paths = line.split(",");
			        wires.add(paths);
			        
			        line = br.readLine();
			        paths = line.split(",");
			        wires.add(paths);
			    }
			    System.out.println("Wires count: " + wires.size());
			  return wires;
		}

}
