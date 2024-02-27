package com.example.strashno;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Calculator implements Initializable {
    @FXML
    TextField outputTF;
    private Controller mainController; // Добавьте поле для хранения ссылки на Controller

    // Добавьте метод для установки ссылки на Controller
    public void setMainController(Controller controller) {
        this.mainController = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @FXML
    private void onButtonClick(ActionEvent event) {
        if(event.getSource() instanceof Button btn) {
            outputTF.setText(outputTF.getText().trim() + btn.getText().trim());
        }
    }
    @FXML
    private void onDELClick(ActionEvent event) {
        if(outputTF.getText().length() > 0) {
            outputTF.setText(outputTF.getText(0, outputTF.getText().length() - 1));
        }
    }
    @FXML
    private void onOKClick(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Controller controller = (Controller) stage.getUserData();
        controller.setText(outputTF.getText());
        stage.close();
    }
}
