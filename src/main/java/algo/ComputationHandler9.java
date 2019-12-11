package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import algo.dao.Amplifier2;

public class ComputationHandler9 {

	public static void main(String[] args) throws Exception{
		InputStream in = ComputationHandler.class.getClass().getResourceAsStream("/Day9Input.txt");
		List<BigInteger> inputsOriginal = readFromInputStream(in);

		Amplifier2[]amplifiers = prepareAmplifiers(inputsOriginal, new int[] {1});
		runAmplifier(amplifiers[0]);
		System.out.println("FINAL value is :" + amplifiers[0].getLastOutput().toString());
	}
	
	public static Amplifier2[] prepareAmplifiers(List<BigInteger>inputsOriginal, int[]permutation) {
		Amplifier2[]amplifiers = new Amplifier2[1];
		for(int i = 0; i < permutation.length; i++) {
			amplifiers[i] = new Amplifier2(inputsOriginal, permutation[i]);
		}
		return amplifiers;
	}
	
	private static List<BigInteger> readFromInputStream(InputStream inputStream)
		  throws IOException {
		List<BigInteger> inputs = new ArrayList<>();
		    try (BufferedReader br
		      = new BufferedReader(new InputStreamReader(inputStream))) {
		        String line = br.readLine();
		        String[]numbers = line.split(",");
		        for(String number:numbers) {
		        	inputs.add(new BigInteger(number));
		        }
		    }
		    System.out.println("Input count: " + inputs.size());
		  return inputs;
	}
	
	private static void runAmplifier(Amplifier2 amp) {
		List<BigInteger> inputs = amp.getInstructions();
		int currentIndex = amp.getCurrentIndex();
		BigInteger input = inputs.get(currentIndex);
		int[] instructions = null;
		BigInteger value = BigInteger.ZERO;
		BigInteger output = BigInteger.ZERO;
		BigInteger exitCode = new BigInteger("99");
		int relativeBase = 0;
		while(!input.equals(exitCode)) {
		    System.out.println("current Index:" + currentIndex + " input: " + input);
		    if(currentIndex == 353) {
		    	System.out.println("current Index ---" + currentIndex );
		    }
		    instructions = interpretCode(input.intValue());
		    int indexToMove = 0;
			switch(instructions[3]) {
			case 1:
				value = (determineValue(inputs, instructions[2], currentIndex+1, relativeBase)).add(
						(determineValue(inputs, instructions[1], currentIndex+2, relativeBase)));
				System.out.println("Op [1] Set index" 
						+ determineValue(inputs, instructions[0], currentIndex+3, relativeBase).intValue() +
						" to value : " + value);
				setValueToInputs(inputs, currentIndex+3, value, relativeBase, instructions[0]);
				indexToMove = 4;
				break;
			case 2:
				value = (determineValue(inputs, instructions[2], currentIndex+1, relativeBase)).multiply(
						(determineValue(inputs, instructions[1], currentIndex+2, relativeBase)));
				System.out.println("Op [2] Set index" + inputs.get(currentIndex+3) +
						" to value : " + value);
				setValueToInputs(inputs, currentIndex+3, value, relativeBase, instructions[0]);
				indexToMove = 4;
						;
				break;
			case 3:
				value = determineValue(inputs, instructions[2], currentIndex+1, relativeBase);
				System.out.println("Op [3] Set index" + value +
						" to value : " + new BigInteger("2"));
				setValueToInputs(inputs, currentIndex+1, new BigInteger("2"), relativeBase, instructions[2]);
				indexToMove = 2;
				break;
			case 4:
				output = determineValue(inputs, instructions[2], currentIndex+1,relativeBase);
				amp.addOutput(output);
				System.out.println("OUTPUT: " + 
						output
						);
				indexToMove = 2;
				break;
			case 5:
				value = determineValue(inputs, instructions[2], currentIndex+1, relativeBase);
				if(!value.equals(BigInteger.ZERO)) {
					currentIndex = determineValue(inputs, instructions[1], currentIndex+2, relativeBase).intValue();
					input = getValueFromInputs(inputs, currentIndex);
					continue;
				} else {
					indexToMove = 3;
				}
				break;
			case 6:
				value = determineValue(inputs, instructions[2], currentIndex+1, relativeBase);
				if(value.equals(BigInteger.ZERO)) {
					currentIndex = determineValue(inputs, instructions[1], currentIndex+2, relativeBase).intValue();
					input = getValueFromInputs(inputs, currentIndex);
					continue;
				} else {
					indexToMove = 3;
				}
				break;
			case 7:
				if(determineValue(inputs, instructions[2], currentIndex+1, relativeBase).compareTo(determineValue(inputs, instructions[1], currentIndex+2, relativeBase))==-1) {
					setValueToInputs(inputs, currentIndex+3, BigInteger.ONE, relativeBase, instructions[0]);
				} else {
					setValueToInputs(inputs, currentIndex+3, BigInteger.ZERO, relativeBase, instructions[0]);
				}
				indexToMove = 4;
				break;
			case 8:
				if(determineValue(inputs, instructions[2], currentIndex+1, relativeBase).
						equals(determineValue(inputs, instructions[1], currentIndex+2, relativeBase))) {
					setValueToInputs(inputs, currentIndex+3, BigInteger.ONE, relativeBase, instructions[0]);
				} else {
					setValueToInputs(inputs, currentIndex+3, BigInteger.ZERO, relativeBase, instructions[0]);
				}
				indexToMove = 4;
				break;
			case 9:
				value = determineValue(inputs, instructions[2], currentIndex+1, relativeBase);
				relativeBase+=value.intValue();
				System.out.println("RELATIVE BASE changed to : " + 
						relativeBase
						);
				indexToMove = 2;
				break;
			default:
				input = exitCode;
				break;
			}
			currentIndex +=indexToMove;
			input = inputs.get(currentIndex);
		}
		amp.setExited();
	}
	
	private static BigInteger getValueFromInputs(List<BigInteger> inputs, int index) {
		if(index >= inputs.size()) {
			return BigInteger.ZERO;
		}
		return inputs.get(index) == null ? BigInteger.ZERO : inputs.get(index);
	}
	
	private static void setValueToInputs(List<BigInteger> inputs, int index, BigInteger value, int relativePosition, int mode) {
		int position = inputs.get(index).intValue();
		if(mode==2) 
			position+=relativePosition;
		if(position >= inputs.size()) {
			System.out.println("Increase size:::" + inputs.size());
			inputs.addAll(Arrays.asList( new BigInteger[position+1-inputs.size()] ));
			System.out.println("TO size:::" + inputs.size());
		}
		inputs.set(position, value);
	}
	
	private static BigInteger determineValue(List<BigInteger>inputs, int mode, int index, int relativeBase) {
		if(mode==0) {
			return getValueFromInputs(inputs, getValueFromInputs(inputs, index).intValue());
		}
		else if(mode==2) {
			System.out.println("check------------->"+ getValueFromInputs(inputs, index).intValue());
			return getValueFromInputs(inputs, (getValueFromInputs(inputs, index).intValue() + relativeBase));
		}
		return getValueFromInputs(inputs,index);
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
		if(currentNum == 9 && (code/10)%10 == 9) {
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
