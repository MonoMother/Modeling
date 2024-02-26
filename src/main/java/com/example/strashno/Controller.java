package com.example.strashno;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    public GridPane Dijkstra;
    public GridPane firstTrain;
    public MenuBar menuForHelp;
    @FXML
    private TextField calculator;
    @FXML
    private Label label;
    public void handleCalculator(MouseEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InFixToPostFix.class.getResource("Calculator.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Калькулятор");
        Scene scene = new Scene(fxmlLoader.load(), 379, 423);
        stage.setScene(scene);
        stage.initModality(Modality.NONE);
        stage.show();
    }

    public void setText(String text){
        calculator = new TextField(text);
        System.out.println(calculator.getText());
    }

}
