package com.ui;

import com.GeneticManager;
import com.population.City;
import com.population.Population;
import com.population.Tour;
import com.population.TourManager;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SalesmanUI {
	private static final int CELLS_WIDE = 50;
	private static final int CELLS_HIGH = 50;
	private static final int CELL_SIZE = 10;
	
	private Stage mStage;
	private Scene mScene;
	
	private Canvas canvas = new Canvas((CELLS_WIDE * CELL_SIZE), (CELLS_HIGH * CELL_SIZE));
	private GraphicsContext gc = canvas.getGraphicsContext2D();
	
	private VBox properties;
	private TextField popSize = new TextField();
	private TextField generations = new TextField();
	
	private Tour fittest = null;
	
	public SalesmanUI(Stage stage) {
		mStage = stage;
		mStage.setResizable(false);
		mStage.centerOnScreen();
		
		BorderPane pane = new BorderPane();
		mScene = new Scene(pane, mStage.getWidth(), mStage.getHeight());
		
		canvas.setOnMouseClicked(mouseHandler);
		
		mStage.setScene(mScene);
		
		initializeUI();
		pane.setLeft(properties);
		pane.setCenter(canvas);
		
		new AnimationTimer() {
			@Override
			public void handle(long now) {
				render();
			}
		}.start();
		
		mStage.show();
	}
	
	private void render() {
		// Clear the previous rendering.
		gc.setStroke(Color.BLACK);
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.strokeRect(0, 0, (canvas.getWidth() - 1), (canvas.getHeight() - 1));
		
		// Draw circles to represent cities.
		gc.setFill(Color.RED);
		for(int r = 0; r < CELLS_HIGH; r++) {
			for(int c = 0; c < CELLS_WIDE; c++) {
				if(TourManager.cityExists(c, r)) {
					gc.fillOval((c * CELL_SIZE), (r * CELL_SIZE), 10, 10);
				}
			}
		}
		
		// If the fittest has been found, draw the path.
		if(fittest != null) {
			gc.setStroke(Color.GREEN);
			for(int i = 0; i < fittest.size(); i++) {
				City from = fittest.getCity(i);
				City to = ((i == (fittest.size() - 1))? fittest.getCity(0) : 
														fittest.getCity(i + 1));
				int x1 = (from.getX() * CELL_SIZE) + (CELL_SIZE / 2);
				int y1 = (from.getY() * CELL_SIZE) + (CELL_SIZE / 2);
				int x2 = (to.getX() * CELL_SIZE) + (CELL_SIZE / 2);
				int y2 = (to.getY() * CELL_SIZE) + (CELL_SIZE / 2);
				
				gc.strokeLine(x1, y1, x2, y2);
			}
		} else {
			// Draw the grid lines.
			for(int r = 0; r < CELLS_HIGH; r++) {
				gc.strokeLine(0, (r * CELL_SIZE), canvas.getWidth(), (r * CELL_SIZE));
			}
			for(int c = 0; c < CELLS_WIDE; c++) {
				gc.strokeLine((c * CELL_SIZE), 0, (c * CELL_SIZE), canvas.getHeight());
			}
		}
	}
	
	private void initializeUI() {
		properties = new VBox(5);
		
		{ // Begin setup of properties bar.
			Label popSizeLabel = new Label("Population Size: ");
			VBox.setMargin(popSizeLabel, new Insets(5, 0, 5, 10));
			popSize.setText(Integer.toString(50));
			popSize.setMaxWidth(100);
			VBox.setMargin(popSize, new Insets(5, 10, 5, 10));
			properties.getChildren().addAll(popSizeLabel, popSize);
			
			Label generationsLabel = new Label("Generations: ");
			VBox.setMargin(generationsLabel, new Insets(5, 0, 5, 10));
			generations.setText(Integer.toString(100));
			generations.setMaxWidth(100);
			VBox.setMargin(generations, new Insets(5, 10, 5, 10));
			properties.getChildren().addAll(generationsLabel, generations);
			
			CheckBox elitism = new CheckBox("Elitism?");
			elitism.setSelected(true);
			VBox.setMargin(elitism, new Insets(5, 10, 5, 10));
			CheckBox graph = new CheckBox("Graph Results?");
			VBox.setMargin(graph, new Insets(5, 10, 5, 10));
			properties.getChildren().addAll(elitism, graph);
			
			Button runButton = new Button("Run");
			VBox.setMargin(runButton, new Insets(15, 10, 5, 10));
			runButton.setOnMouseClicked(runButtonHandler);
			properties.getChildren().add(runButton);
		} // End setup of properties bar.
	}
	
	private double getScaleFactor() {
		double wScale = (canvas.getWidth() / SalesmanUI.CELLS_WIDE);
		double hScale = (canvas.getHeight() / SalesmanUI.CELLS_HIGH);
		return Math.min(wScale, hScale);
	}
	
	EventHandler<MouseEvent> runButtonHandler = event -> {
		Population pop = new Population(Integer.parseInt(popSize.getText()), true);
		int initDistance = pop.getFittest().getDistance();
		
		for(int i = 0; i < Integer.parseInt(generations.getText()); i++) {
        	pop = GeneticManager.evolve(pop);
        }
		
		fittest = pop.getFittest();
		int endDistance = fittest.getDistance();
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Finished!");
		alert.setHeaderText("Initial solution optimized.");
		String content = String.format("Initial Distance: %d\nFinal Distance: %d",
									   initDistance, endDistance);
		alert.setContentText(content);
		alert.showAndWait();
	};
	
	EventHandler<MouseEvent> mouseHandler = event -> {
		double scale = getScaleFactor();
		int sX = (int)(event.getX() / scale),
	        sY = (int)(event.getY() / scale);
		
		// Toggle the city.
		if(TourManager.cityExists(sX, sY)) {
			TourManager.removeCity(sX, sY);
			fittest = null;
		} else TourManager.addCity(new City(sX, sY));
	};
}
