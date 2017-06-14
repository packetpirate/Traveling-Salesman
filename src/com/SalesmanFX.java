package com;

import com.ui.SalesmanUI;

import javafx.application.Application;
import javafx.stage.Stage;

public class SalesmanFX extends Application {
	public static void main(String[] args) {
		try {
			launch(args);
		} catch(Exception e) {
			System.err.println("ERROR: An error occurred trying to launch application.");
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Traveling Salesman FX");
		new SalesmanUI(stage);
	}
}
