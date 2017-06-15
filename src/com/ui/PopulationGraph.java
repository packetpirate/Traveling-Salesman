package com.ui;

import java.util.ArrayList;

import com.population.Population;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class PopulationGraph {
	public PopulationGraph(ArrayList<Population> populations) {
		Stage stage = new Stage();
		stage.setTitle("Results of Population Evolution");
		stage.setResizable(false);
		stage.centerOnScreen();
		
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Generations");
		yAxis.setLabel("Distance");
		final LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
		chart.setTitle("Fittest Distance");
		
		XYChart.Series<Number, Number> series = new XYChart.Series<>();
		for(int i = 0; i < populations.size(); i++) {
			Population pop = populations.get(i);
			series.getData().add(new XYChart.Data<Number, Number>(i, pop.getFittest().getDistance()));
		}
		
		Scene scene = new Scene(chart);
		chart.getData().add(series);
		
		stage.setScene(scene);
		stage.show();
	}
}
