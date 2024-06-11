package com.example.financetracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private FinanceDataStorage storage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        storage = new FinanceDataStorage();
        storage.loadExpenses();
        storage.loadIncomes();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/financetracker/finance_tracker.fxml"));
        Parent root = loader.load();
        
        FinanceTrackerController controller = loader.getController();
        controller.setFinanceDataStorage(storage);

        primaryStage.setTitle("Finance Tracker");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
