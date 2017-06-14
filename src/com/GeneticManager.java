package com;

import com.population.City;
import com.population.Population;
import com.population.Tour;

public class GeneticManager {
	private static final double mutationRate = 0.015;
    private static final int candidatesSize = 5;
    private static final boolean elitism = true;
    
    public static Population evolve(Population pop) {
    	Population newPop = new Population(pop.size(), false);
    	
    	int elitismOff = ((elitism)? 1 : 0);
    	if(elitism) newPop.saveTour(0, pop.getFittest());
    	
    	for(int i = elitismOff; i < newPop.size(); i++) {
    		Tour parent1 = selectCandidate(pop);
    		Tour parent2 = selectCandidate(pop);
    		
    		Tour child = crossover(parent1, parent2);
    		mutate(child);
    		
    		newPop.saveTour(i, child);
    	}
    	
    	return newPop;
    }
    
    public static Tour crossover(Tour parent1, Tour parent2) {
    	Tour child = new Tour();
    	
    	int a = (int)(Math.random() * parent1.size());
    	int b = (int)(Math.random() * parent2.size());
    	int start = Math.min(a, b);
    	int end = Math.max(a, b);
    	
    	for(int i = 0; i < child.size(); i++) {
    		if((i > start) && (i < end)) {
    			child.setCity(i, parent1.getCity(i));
    		}
    	}
    	
    	for(int i = 0; i < parent2.size(); i++) {
    		if(!child.contains(parent2.getCity(i))) {
    			for(int j = 0; j < child.size(); j++) {
    				if(child.getCity(j) == null) {
    					child.setCity(j, parent2.getCity(i));
    					break;
    				}
    			}
    		}
    	}
    	
    	return child;
    }
    
    public static void mutate(Tour tour) {
    	for(int i = 0; i < tour.size(); i++) {
    		if(Math.random() < mutationRate) {
    			int j = (int)(Math.random() * tour.size());
    			
    			City city1 = tour.getCity(i);
    			City city2 = tour.getCity(j);
    			
    			tour.setCity(i, city2);
    			tour.setCity(j, city1);
    		}
    	}
    }
    
    public static Tour selectCandidate(Population pop) {
    	Population candidates = new Population(candidatesSize, false);
    	
    	for(int i = 0; i < candidatesSize; i++) {
    		int pos = (int)(Math.random() * pop.size());
    		candidates.saveTour(i, pop.getTour(pos));
    	}
    	
    	return candidates.getFittest();
    }
}
