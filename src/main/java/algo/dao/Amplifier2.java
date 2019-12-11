package algo.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Amplifier2 {
	public int getCurrentIndex() {
		return currentIndex;
	}
	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}
	public List<Integer> getInputs() {
		return inputs;
	}
	public void setInputs(List<Integer> inputs) {
		this.inputs = inputs;
	}
	public List<BigInteger> getInstructions() {
		return instructions;
	}
	public void setInstructions(List<BigInteger> instructions) {
		this.instructions = instructions;
	}
	private List<Integer>inputs;
	private List<BigInteger>instructions;
	private List<BigInteger>outputs = new ArrayList<>();
	private Boolean exited = false;
	private int currentIndex = 0;
	
	public void addOutput(BigInteger output) {
		outputs.add(output);
	}
	
	public void addInput(List<Integer>inputs) {
		this.inputs.addAll(inputs);
	}
	
	public BigInteger getLastOutput() {
		if(outputs!=null && outputs.size()>0) {
			return outputs.get(outputs.size()-1);
		}
		return BigInteger.ZERO;
	}
	
	public boolean hasInput() {
		return inputs!=null && inputs.size()>0;
	}
	
	public Integer getInput() {
		return inputs.remove(0);
	}
	
	public Integer getInputNoRemove() {
		return inputs.get(0);
	}
	
	public void setExited() {
		exited = true;
	}
	
	public boolean isExited() {
		return exited;
	}
	
	public Amplifier2(List<BigInteger>instructions, Integer...inputs) {
		this.instructions = new ArrayList<>(instructions);
		this.inputs = new ArrayList<>(Arrays.asList(inputs));
	}
	
	public boolean canRun() {
		return !((inputs==null || inputs.size()<=0) || exited);
	}
	
}
