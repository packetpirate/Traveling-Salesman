package com.population;

public class City {
	private int x, y;
	public int getX() { return x; }
	public int getY() { return y; }
	public void setX(int x_) { this.x = x_; }
	public void setY(int y_) { this.y = y_; }
	
	public City() {
		this.x = (int)(Math.random() * 200);
		this.y = (int)(Math.random() * 200);
	}
	
	public City(int x_, int y_) {
		this.x = x_;
		this.y = y_;
	}
	
	public double distanceTo(City city) {
		int a = Math.abs(city.getX() - this.x);
		int b = Math.abs(city.getY() - this.y);
		return Math.sqrt((a * a) + (b * b));
	}
	
	@Override
	public String toString() {
		return ("(" + this.x + ", " + this.y + ")");
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(this == obj) return true;
		if(this.getClass() != obj.getClass()) return false;
		
		City city = (City) obj;
		return ((this.x == city.getX()) && (this.y == city.getY()));
	}
}
