package com.example.strashno;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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

    public void updateGridPaneText(String text) {
        // Предположим, что у вас есть Text элемент в GridPane firstTrain с id "textElement"
        for (Node node : firstTrain.getChildren()) {
            if (node instanceof Text && GridPane.getColumnIndex(node) == 0 && GridPane.getRowIndex(node) == 0) {
                ((Text) node).setText(text);
                break; // Выходим из цикла после обновления текста
            }
        }
    }
    public void handleCalculator(MouseEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InFixToPostFix.class.getResource("Calculator.fxml"));
        Parent root = fxmlLoader.load();
        Controller controller = new Controller();
        Stage stage = new Stage();
        stage.setTitle("Калькулятор");
        Scene scene = new Scene(root, 379, 423);
        stage.setScene(scene);
        stage.initModality(Modality.NONE);

        stage.setUserData(this); // Set the Controller object associated with the existing GUI

        stage.show();
    }
    public void setText(String text){
        calculator.setText(text);
        System.out.println(calculator.getText());
    }
}
