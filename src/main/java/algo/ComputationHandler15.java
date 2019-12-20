package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import algo.dao.Amplifier2;

public class ComputationHandler15 {

	public static void main(String[] args) throws Exception{
		InputStream in = ComputationHandler15.class.getClass().getResourceAsStream("/Day15Input.txt");
		List<BigInteger> inputsOriginal = readFromInputStream(in);

		Amplifier2[]amplifiers = prepareAmplifiers(inputsOriginal, new int[] {1});
		runAmplifier(amplifiers[0]);
		System.out.println("PROGRAM ENDED");
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
		
		int[][] map = new int[50][50];
		
		int currentX = 25;
		int currentY = 25;
		int nextMove = 1;
		boolean backtrack = false;
		int[]moveResult = new int[3];
		Stack<Integer>moveStack = new Stack<>();
		moveStack.push(nextMove);
		
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
						" to value : " + BigInteger.valueOf(nextMove));
				setValueToInputs(inputs, currentIndex+1, BigInteger.valueOf(nextMove), relativeBase, instructions[2]);
				indexToMove = 2;
				break;
			case 4:
				output = determineValue(inputs, instructions[2], currentIndex+1,relativeBase);
				moveResult = plotRobot(map, currentX, currentY, nextMove, output.intValue(), moveStack, backtrack);
				System.out.println("OUTPUT: " + 
						output
						);
				if(output.intValue()==2) {
					System.out.println("REACHED GOAL!!! STOP");
					input = exitCode;
					printMap(map);
					continue;
				} else if(moveResult[2]==0) {
					System.out.println("ERROR!! No valid move");
					printMap(map);
					input = exitCode;
					continue;
				} else {
					currentX = moveResult[0];
					currentY = moveResult[1];
					nextMove = moveResult[2];
					backtrack = moveResult[3]==1;
				}
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
	
	private static void printMap(int[][] map) {
		for(int y=0; y<map.length; y++) {
			for(int x=0; x<map[0].length; x++) {
				String toprint = null;
				if(x==25 && y==25) toprint = "!";
				else if (map[y][x]==9) toprint = "#";
				else if(map[y][x]==8 || map[y][x]==0) toprint = ".";
				else toprint = "*";
				System.out.print(toprint);
			}
			System.out.println("-");
		}
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
	
	private static int[] plotRobot(int[][]map, int currentX, int currentY, int previousMove, int moveResult, Stack<Integer>moveStack, boolean isBacktrack) {
		if(currentX == 12 && currentY==41 && previousMove==2 && moveResult==0) {
			System.out.println("CHECKPOINT--");
		}
		if(currentX == 11 && currentY==40 && previousMove==3 && moveResult==0) {
			System.out.println("CHECKPOINT--");
		}
		/**
		 * 0:new X
		 * 1:new Y
		 * 2:next move
		 * 
		 * map legend:
		 * 9: wall
		 * 8: covered before
		 * 1: goal
		 */
		int[]newLocation = recordMove(map, currentX, currentY, previousMove, moveResult, moveStack, isBacktrack);
		int decidedMove = 0;
		int backtrack = 0;
		for (int move =1; move <=4; move++) {
			if(checkMove(map, newLocation[0], newLocation[1], move, 0)) {
				decidedMove = move;
				break;
			}
		}
		//move to some location you already visited if it has unreached nodes 
		if(decidedMove ==0) {
			//check for already visited nodes
			for (int move =1; move <=4; move++) {
				if(checkMove(map, newLocation[0], newLocation[1], move, 1)) {
					decidedMove = move;
					break;
				}
			}
		}
		//back track if no other choice
		if(decidedMove == 0) {
			if(!moveStack.empty()) {
				int previousDoneMove = moveStack.pop();
				switch(previousDoneMove) {
					case 1:
						decidedMove = 2;
						break;
					case 2:
						decidedMove = 1;
						break;
					case 3:
						decidedMove = 4;
						break;
					case 4: 
						decidedMove = 3;
						break;
				}
				backtrack = 1;
			}
		}
		System.out.println("currentX:" + currentX + " currentY:" + currentY + " previousMove:" + previousMove + " moveResult:" + moveResult);

		System.out.println("newX:" + newLocation[0] + " newY:" + newLocation[1] + " decided:" + decidedMove);
		return new int[] {newLocation[0], newLocation[1], decidedMove, backtrack};
	}
	
	private static boolean checkMove(int[][]map, int x, int y, int moveTry, int moveType) {
		/**
		 * moveType
		 * 0: uncovered area
		 * 1: covered area
		 */
		boolean goodMove = true;
		int attemptedX = x;
		int attemptedY = y;
		switch(moveTry) {
		case 1://north
			attemptedY+=1;
			break;
		case 2://south
			attemptedY-=1;
			break;
		case 4://east
			attemptedX+=1;
			break;
		case 3://west
			attemptedX-=1;
			break;
		}
		if(moveType == 0) {
			if(map[attemptedY][attemptedX] == 0) {return goodMove;}
			else 
				return false;
		}
		else if(map[attemptedY][attemptedX] == 8) {
			if(
				map[attemptedY+1][attemptedX] ==0 ||
				map[attemptedY-1][attemptedX] ==0 ||
				map[attemptedY][attemptedX+1] ==0 ||
				map[attemptedY][attemptedX-1] ==0 
					)
				return goodMove;
		}
		return false;
	}
	
	private static int[] recordMove(int[][]map, int x, int y, int previousMove, int moveResult, Stack<Integer>moveStack, boolean isBackTrack) {
		int[] newLocation = new int[] {x,y};
		int coveredX = x;
		int coveredY = y;
		switch(previousMove) {
			case 1://north
				coveredY+=1;
				break;
			case 2://south
				coveredY-=1;
				break;
			case 4://east
				coveredX+=1;
				break;
			case 3://west
				coveredX-=1;
				break;
		}
		int locationMarker = 0;
		switch(moveResult) {
			case 0:
				locationMarker = 9;
				break;
			case 1:
				locationMarker = 8;
				newLocation = new int[] {coveredX, coveredY};
				if(!isBackTrack)
					moveStack.push(previousMove);
				break;
			case 2:
				locationMarker = 1;
				newLocation = new int[] {coveredX, coveredY};
				break;
		}
		map[coveredY][coveredX] = locationMarker;
		
		return newLocation;
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
