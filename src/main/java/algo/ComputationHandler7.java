package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComputationHandler7 {

	public static void main(String[] args) throws Exception{
		InputStream in = ComputationHandler.class.getClass().getResourceAsStream("/Day7Input.txt");
		List<Integer> inputsOriginal = readFromInputStream(in);
		//int answer = position1(inputs);
		//System.out.println("answer : " + answer);
		List<int[]> permutations = generatePermutations();
		System.out.println("Permutation count: " + permutations.size());
		int maxValue = 0;
		int amplifierInput = 0;
		for(int[]permutation:permutations) {
			amplifierInput = 0;
			for(int phase : permutation) {
				amplifierInput =  position1(copyList(inputsOriginal), phase, amplifierInput);
			}
			maxValue = maxValue < amplifierInput?amplifierInput:maxValue;
		}
		System.out.println("MAX value is :" + maxValue);
	}
	
	public static List<Integer> copyList(List<Integer> original) {
		return new ArrayList<>(original);
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
	
	private static List<int[]> generatePermutations() {
		List<int[]>permutations = new ArrayList<>();
		int shift = 5;
		for(int digit1 = 0; digit1 <=4; digit1++) {
			int[] combination = new int[5];
			combination[0] = digit1 + shift;
			for(int digit2 = 0; digit2 <=4; digit2++) {
				if(numNotUsedBefore(1, digit2 + shift, combination)) {
					combination[1] = digit2 + shift;
					for(int digit3 = 0; digit3 <=4; digit3++) {
						if(numNotUsedBefore(2, digit3 + shift, combination)) {
							combination[2] = digit3 + shift;
							for(int digit4 = 0; digit4 <=4; digit4++) {
								if(numNotUsedBefore(3, digit4 + shift, combination)) {
									combination[3] = digit4 + shift;
									for(int digit5 = 0; digit5 <=4; digit5++) {
										if(numNotUsedBefore(4, digit5 + shift, combination)) {
											combination[4] = digit5 + shift;
											permutations.add(
												Arrays.copyOf(combination, 5)
											);
										}
									}
								}
							}
						}
						
					}
				}
			}
		}
		return permutations;
	}
	
	private static boolean numNotUsedBefore(int currentIndex, int numToCheck, int[]combination) {
		for(int i = 0; i<currentIndex; i++) {
			if(combination[i] == numToCheck) {
				return false;
			}
		}
		return true;
	}
	private static int position1(List<Integer> inputs, int phase, int amplifierInput) {
		int input = inputs.get(0);
		int currentIndex = 0;
		int[] instructions = null;
		int value = 0;
		int inputCount = 0;
		int output = 0;
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
				value = inputCount==0?phase:amplifierInput;
				System.out.println("Op [3] Set index" + inputs.get(currentIndex+1) +
						" to value : " + value);
				inputs.set(inputs.get(currentIndex+1), value);
				inputCount++;
				indexToMove = 2;
				break;
			case 4:
				output = determineValue(inputs, instructions[2], currentIndex+1);
				System.out.println("OUTPUT: " + 
						output
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
		return output;
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
