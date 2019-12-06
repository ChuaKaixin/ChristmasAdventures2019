package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ComputationHandler2 {

	public static void main(String[] args) throws Exception{
		InputStream in = ComputationHandler.class.getClass().getResourceAsStream("/Day2Input.txt");
		List<Integer> inputs = readFromInputStream(in);
		for(int item1 = 0; item1<=99; item1++ ) {
			for(int item2 = 0; item2 <= 99; item2++) {
				int[] preparedArray = prepareList(inputs, item1, item2);
				int answer = position2(preparedArray);
			    //System.out.println("Checking for item1:" + item1 + " item2: " + item2 + " ==> " + answer);
				if(answer == 19690720) {
					System.out.print("Answer found:" + item1 + "---" + item2);
					break;
				}
			}
		}
		//int answer = position1(inputs);
	  // System.out.println("answer : " + answer);
		
	}
	
	private static int[] prepareList(List<Integer>inputs, int item1, int item2)
			 {
			int[]listing = new int[inputs.size()];
			listing[0] = inputs.get(0);
			listing[1] = item1;
			listing[2] = item2;
			
			for(int i = 3; i <listing.length; i++) {
				listing[i] = inputs.get(i);
			}
			return listing;
		}
	
	private static List<Integer> readFromInputStream(InputStream inputStream)
		  throws IOException {
		List<Integer> inputs = new ArrayList<>();
		    try (BufferedReader br
		      = new BufferedReader(new InputStreamReader(inputStream))) {
		        String line = br.readLine();
		        String[]numbers = line.split(",");
		        for(String number:numbers) {
		        	inputs.add(Integer.parseInt(number));
		        }
		    }
		    System.out.println("Input count: " + inputs.size());
		  return inputs;
	}
	
	private static int position2(int[] inputs) {
		int input = inputs[0];
		int currentIndex = 0;
		while(input!=99) {
		    //System.out.println("current Index:" + currentIndex + " input: " + input);
			switch(input) {
			case 1:
				inputs[inputs[currentIndex+3]]= inputs[inputs[currentIndex+1]] + inputs[inputs[currentIndex+2]];
				break;
			case 2:
				inputs[inputs[currentIndex+3]]= inputs[inputs[currentIndex+1]] * inputs[inputs[currentIndex+2]];
				break;
			default:
				input = 99;
			}
			currentIndex +=4;
			input = inputs[currentIndex];
		}
		return inputs[0];
	}
	
	private static int position1(List<Integer> inputs) {
		int input = inputs.get(0);
		int currentIndex = 0;
		while(input!=99) {
		    System.out.println("current Index:" + currentIndex + " input: " + input);
			switch(input) {
			case 1:
				inputs.set(inputs.get(currentIndex+3), inputs.get(inputs.get(currentIndex+1)) + inputs.get(inputs.get(currentIndex+2)));
				break;
			case 2:
				inputs.set(inputs.get(currentIndex+3), inputs.get(inputs.get(currentIndex+1)) * inputs.get(inputs.get(currentIndex+2)));
				break;
			default:
				input = 99;
			}
			currentIndex +=4;
			input = inputs.get(currentIndex);
		}
		return inputs.get(0);
	}

}
