package it.polito.tdp.rivers.model;

public class TestSimulator {

	public static void main(String[] args) {
		
		River fiume = new River(1, "name");
		Model model = new Model();
		model.simula(3, fiume);
		
		System.out.println(model.getOccupazioneMedia());
		System.out.println(model.getInsoddisfatti());
		
//		Simulator sim = new Simulator();
//		sim.init(3, fiume);
//		System.out.println(sim.getQ());
		

	}

}
