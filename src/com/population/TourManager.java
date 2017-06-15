package com.population;

import java.util.ArrayList;

public class TourManager {
	private static ArrayList<City> cities = new ArrayList<>();
	
	public static void addCity(City city) {
		cities.add(city);
	}
	
	public static void removeCity(int x, int y) {
		City city = new City(x, y);
		cities.remove(city);
	}
	
	public static boolean cityExists(int x, int y) {
		City city = new City(x, y);
		return cities.contains(city);
	}
	
	public static City getCity(int pos) {
		return cities.get(pos);
	}
	
	public static void clearCities() {
		cities.clear();
	}
	
	public static int numberOfCities() {
		return cities.size();
	}
}
