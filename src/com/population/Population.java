package com.population;

public class Population {
	private Tour [] tours;
	
	public Population(int size, boolean init) {
		tours = new Tour[size];
		
		if(init) {
			for(int i = 0; i < size; i++) {
				Tour tour = new Tour();
				tour.generateIndividual();
				saveTour(i, tour);
			}
		}
	}
	
	public Tour getTour(int pos) {
		return tours[pos];
	}
	
	public void saveTour(int pos, Tour tour) {
		tours[pos] = tour;
	}
	
	public Tour getFittest() {
		Tour fittest = tours[0];
		
		for(int i = 0; i < tours.length; i++) {
			if(fittest.getFitness() <= getTour(i).getFitness()) {
				fittest = getTour(i);
			}
		}
		
		return fittest;
	}
	
	public int size() {
		return tours.length;
	}
}
