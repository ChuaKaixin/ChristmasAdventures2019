package algo.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Amplifier {
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
	public List<Integer> getInstructions() {
		return instructions;
	}
	public void setInstructions(List<Integer> instructions) {
		this.instructions = instructions;
	}
	private List<Integer>inputs;
	private List<Integer>instructions;
	private List<Integer>outputs = new ArrayList<>();
	private Boolean exited = false;
	private int currentIndex = 0;
	private List<Integer>thrustOutput = new ArrayList<>();
	
	public void addTrustOutput() {
		thrustOutput.add(getLastOutput());
	}
	
	public int getTrustOutput() {
		if(thrustOutput!=null && thrustOutput.size()>0) {
			return thrustOutput.get(thrustOutput.size()-1);
		}
		return 0;
	}
	
	public void addOutput(int output) {
		outputs.add(output);
	}
	
	public void addInput(List<Integer>inputs) {
		this.inputs.addAll(inputs);
	}
	
	public List<Integer> getOutput() {
		List<Integer> outputsAvailable = new ArrayList<>();
		outputsAvailable.addAll(this.outputs);
		this.outputs = new ArrayList<>();
		return outputsAvailable;
	}
	
	public Integer getLastOutput() {
		if(outputs!=null && outputs.size()>0) {
			return outputs.get(outputs.size()-1);
		}
		return 0;
	}
	
	public boolean hasInput() {
		return inputs!=null && inputs.size()>0;
	}
	
	public Integer getInput() {
		return inputs.remove(0);
	}
	
	public void setExited() {
		exited = true;
	}
	
	public boolean isExited() {
		return exited;
	}
	
	public Amplifier(List<Integer>instructions, Integer...inputs) {
		this.instructions = new ArrayList<>(instructions);
		this.inputs = new ArrayList<>(Arrays.asList(inputs));
	}
	
	public boolean canRun() {
		return !((inputs==null || inputs.size()<=0) || exited);
	}
	
}
