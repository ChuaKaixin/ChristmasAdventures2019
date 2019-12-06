package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ComputationHandler5 {

	public static void main(String[] args) throws Exception{
		InputStream in = ComputationHandler.class.getClass().getResourceAsStream("/Day5Input.txt");
		List<Integer> inputs = readFromInputStream(in);
		int answer = position1(inputs);
		System.out.println("answer : " + answer);
		
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
	
	
	private static int position1(List<Integer> inputs) {
		int input = inputs.get(0);
		int currentIndex = 0;
		int[] instructions = null;
		int value = 0;
		while(input!=99) {
		    System.out.println("current Index:" + currentIndex + " input: " + input);
		    if(currentIndex == 247) {
		    	System.out.println("current Index ---" + currentIndex );
		    }
		    instructions = interpretCode(input);
		    int indexToMove = 0;
			switch(instructions[3]) {
			case 1:
				value = (determineValue(inputs, instructions[2], currentIndex+1)) + 
						(determineValue(inputs, instructions[1], currentIndex+2));
				System.out.println("Op [1] Set index" + inputs.get(currentIndex+3) +
						" to value : " + value);
				inputs.set(inputs.get(currentIndex+3), value);
				indexToMove = 4;
				break;
			case 2:
				value = (determineValue(inputs, instructions[2], currentIndex+1)) *
						(determineValue(inputs, instructions[1], currentIndex+2));
				System.out.println("Op [2] Set index" + inputs.get(currentIndex+3) +
						" to value : " + value);
				inputs.set(inputs.get(currentIndex+3), value);
				indexToMove = 4;
						;
				break;
			case 3:
				System.out.println("Op [3] Set index" + inputs.get(currentIndex+1) +
						" to value : 5");
				inputs.set(inputs.get(currentIndex+1), 5);
				indexToMove = 2;
				break;
			case 4:
				System.out.println("OUTPUT: " + 
						(determineValue(inputs, instructions[2], currentIndex+1))
						);
				indexToMove = 2;
				break;
			case 5:
				value = determineValue(inputs, instructions[2], currentIndex+1);
				if(value!=0) {
					currentIndex = determineValue(inputs, instructions[1], currentIndex+2);
					input = inputs.get(currentIndex);
					continue;
				} else {
					indexToMove = 3;
				}
				break;
			case 6:
				value = determineValue(inputs, instructions[2], currentIndex+1);
				if(value==0) {
					currentIndex = determineValue(inputs, instructions[1], currentIndex+2);
					input = inputs.get(currentIndex);
					continue;
				} else {
					indexToMove = 3;
				}
				break;
			case 7:
				if(determineValue(inputs, instructions[2], currentIndex+1)<determineValue(inputs, instructions[1], currentIndex+2)) {
					inputs.set(inputs.get(currentIndex+3), 1);
				} else {
					inputs.set(inputs.get(currentIndex+3), 0);
				}
				indexToMove = 4;
				break;
			case 8:
				if(determineValue(inputs, instructions[2], currentIndex+1)==determineValue(inputs, instructions[1], currentIndex+2)) {
					inputs.set(inputs.get(currentIndex+3), 1);
				} else {
					inputs.set(inputs.get(currentIndex+3), 0);
				}
				indexToMove = 4;
				break;
			default:
				input = 99;
				break;
			}
			currentIndex +=indexToMove;
			input = inputs.get(currentIndex);
		}
		return inputs.get(0);
	}
	
	private static int determineValue(List<Integer>inputs, int mode, int index) {
		if(mode==0) {
			return inputs.get(inputs.get(index));
		} return 
				inputs.get(index);
	}
	
	private static int[] interpretCode(int code) {
		int [] instructions = new int[4];
		/**
		 * 1 parameter 3 mode
		 * 2 parameter 2 mode
		 * 3 parameter 1 mode
		 * 4 instruction - 1,2,3,4,99
		 */
		int currentNum = code%10;
		if(currentNum == 9) {
			instructions[3] = 99;
			return instructions;
		} else {
			instructions[3] = currentNum;
			code/=100;
		}
		int index = 2;
		while(code>0) {
			currentNum = code%10;
			instructions[index] = currentNum;
			code/=10;
			index-=1;
		}
		return instructions;
	}

}
