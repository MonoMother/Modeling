package com.example.strashno;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


    public class Calculator implements Initializable {
        @FXML
        TextField outputTF;
        private List<Step> steps;
        private int currentStepIndex = 0;

        public String name = "";

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
            controller.setText(outputTF.getText()); // Обновление текста в TextField
            controller.updateGridPaneText(outputTF.getText()); // Обновление текста посимвольно в первом GridPane
            name = outputTF.getText();
            String postfixExpression = InFixToPostFix.convertToPostfix(name);
            System.out.println("Postfix: " + postfixExpression);

            // Обновление текста во втором GridPane
            controller.updateSecondGridPaneText(postfixExpression);

            steps = InFixToPostFix.convertToPostfixStepByStep(name);

            stage.close();
        }

        @FXML
        private void onNextStepClick(ActionEvent event) {
            if (steps != null && currentStepIndex < steps.size()) {
                Step currentStep = steps.get(currentStepIndex);
                Controller controller = (Controller) ((Button) event.getSource()).getScene().getWindow().getUserData();
                controller.updateInterface(currentStep); // Обновляем интерфейс с текущим шагом
                currentStepIndex++;
            }
        }

    }

