package algo;

public class ComputationHandler4 {

	public static void main(String[] args) {
		int from = 134792;
		int to = 675810;
		int currentNum = 134792;
		int[]numArray = new int[] {1,3,4,7,9,2};
		int passwordCount = 0;
		int[] criteriaCheck = null;
		while(currentNum <= 675810) {
			for(int index = 0 ; index < numArray.length-1; index++) {
				if(numArray[index] > numArray[index+1]) {
					numArray[index+1] = numArray[index];
				}
			}
			criteriaCheck = meetPasswordCriteria(numArray);
			currentNum = criteriaCheck[1];
			numArray = generateNumArray(currentNum);
			if(criteriaCheck[0]==0)
				passwordCount++;
			
		}
		/**
		while(currentNum <= 675810) {
			int currentDigit = currentNum % 10;
			int processedNum = currentNum / 10;
			int nextDigit = 0;
			boolean meetPasswordCriteria = true;
			while(processedNum >0) {
				nextDigit = processedNum % 10;
				if(nextDigit!=0) {
					if(nextDigit > currentDigit) {
						meetPasswordCriteria = false;
						break;
					}
					else {
						processedNum/=10;
						currentDigit = nextDigit;
					}
				}
			}
			if(meetPasswordCriteria)
				passwordCount++;
			currentNum++;
			
		}**/
		
		System.out.print("Password count:" + passwordCount);
		
	}

	
	public static int[] generateNumArray(int password) {
		int[]passwordArray = new int[6];
		int index = 5;
		while(password >0) {
			passwordArray[index]= password % 10;
			password/=10;
			index--;
		}
		return passwordArray;
	}
	public static int[] meetPasswordCriteria(int[]password) {
		int firstNum = password[0];
		int[] criteriaCheck = new int[2];
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(firstNum);
		int pairCount = 0;
		int matchNum = -1;
		int matchLen = 0;
		for(int index =1; index < password.length; index++) {
			sBuilder.append(password[index]);
			if(firstNum > password[index]) {
				criteriaCheck[0] = -1;
			} 
			if(firstNum==password[index]) {
				matchLen++;
				if(index==password.length-1 && matchLen==1) {
					pairCount++;
				}
			} else {
				if(matchLen == 1) pairCount++;
				matchLen = 0;
			}
			firstNum = password[index];
		}
		int currentNum = Integer.parseInt(sBuilder.toString());
		
		criteriaCheck[0] = currentNum>675810 || pairCount==0 ?-1:criteriaCheck[0];
		System.out.println("current number : " + currentNum + " fits criteria: " + criteriaCheck[0]);
		criteriaCheck[1] = currentNum+1;
		
		System.out.println("next number : " + criteriaCheck[1]);
		return criteriaCheck;
	}
}
