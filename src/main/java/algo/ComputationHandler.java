package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ComputationHandler {

	public static void main(String[] args) throws Exception{
		InputStream in = ComputationHandler.class.getClass().getResourceAsStream("/Day1Input.txt");
		List<Integer> inputs = readFromInputStream(in);
		int requirement = getTotalFuelRequirements(inputs);
	    System.out.println("fuel : " + requirement);
		
	}
	
	private static List<Integer> readFromInputStream(InputStream inputStream)
		  throws IOException {
		List<Integer> inputs = new ArrayList<>();
		    try (BufferedReader br
		      = new BufferedReader(new InputStreamReader(inputStream))) {
		        String line;
		        while ((line = br.readLine()) != null) {
		        	inputs.add(Integer.parseInt(line));
		        }
		    }
		    System.out.println("Input count: " + inputs.size());
		  return inputs;
	}
	
	private static int getTotalFuelRequirements(List<Integer> inputs) {
		int requirement = 0;
		for(int input: inputs) {
			int inputProcessed = input;
			int inputSum = 0;
			while(inputProcessed > 0) {
				if(Math.floor(inputProcessed/3) - 2>0) {
					inputSum+=Math.floor(inputProcessed/3) - 2;
					inputProcessed = (int)(Math.floor(inputProcessed/3) - 2);
				} else {
					inputProcessed = 0;
				}
			}
			requirement += inputSum;
		}
		return requirement;
	}

}
