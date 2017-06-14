package com.population;

import java.util.ArrayList;
import java.util.Collections;

public class Tour {
	private ArrayList<City> tour = new ArrayList<>();
	private int distance = 0;
	private double fitness = 0;
	
	public Tour() {
		for(int i = 0; i < TourManager.numberOfCities(); i++) {
			tour.add(null);
		}
	}
	
	public Tour(ArrayList<City> tour_) {
		this.tour = tour_;
	}
	
	public City getCity(int pos) {
		return tour.get(pos);
	}
	
	public void setCity(int pos, City city) {
		tour.set(pos, city);
		
		// Tour has been altered, so reset our fitness and distance.
		distance = 0;
		fitness = 0;
	}
	
	public double getFitness() {
		return ((fitness == 0)?(1 / (double)getDistance()):fitness);
	}
	
	public int getDistance() {
		if(distance == 0) {
			int tourDistance = 0;
			
			for(int i = 0; i < tour.size(); i++) {
				City from = getCity(i);
				City to = (((i + 1) < tour.size()) ? getCity(i + 1) : getCity(0));
				tourDistance += from.distanceTo(to);
			}
			
			distance = tourDistance;
		}
		
		return distance;
	}
	
	public int size() {
		return tour.size();
	}
	
	public void generateIndividual() {
		for(int i = 0; i < TourManager.numberOfCities(); i++) {
			setCity(i, TourManager.getCity(i));
		}
		
		Collections.shuffle(tour);
	}
	
	public boolean contains(City city) {
		return tour.contains(city);
	}
	
	@Override
	public String toString() {
		String gene = "|";
		
		for(int i = 0; i < tour.size(); i++) {
			gene += getCity(i) + "|";
		}
		
		return gene;
	}
}
