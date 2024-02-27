package com.example.strashno;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Controller {

    public GridPane Dijkstra;
    public GridPane firstTrain;
    public MenuBar menuForHelp;
    public GridPane secondTrain;
    public GridPane stack;
    public TextField exit;
    @FXML
    private TextField calculator;


    private List<Step> steps; // Добавляем поле steps для хранения списка шагов
    private int currentStepIndex = 0; // Добавляем индекс текущего шага

    public void updateGridPaneText(String text) {

        int row = 0;
        int col = 0;

        for (int i = 0; i < text.length(); i++) {
            Text characterText = new Text(String.valueOf(text.charAt(i)));
            firstTrain.add(characterText, col, row);

            col++;
            if (col == 15) {
                col = 0;
                row++;
            }
        }
    }
    public void updateSecondGridPaneText(String text) {
        int row = 0;
        int col = 0;

        for (int i = 0; i < text.length(); i++) {
            Text characterText = new Text(String.valueOf(text.charAt(i)));
            secondTrain.add(characterText, col, row);

            col++;
            if (col == 15) {
                col = 0;
                row++;
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
    public void updateInterface(Step step) {
        // Очистка содержимого стека на интерфейсе
        stack.getChildren().clear();

        // Добавление содержимого стека на интерфейс (снизу вверх)
        List<String> stackContent = step.getStackContent();
        int rowIndex = 9; // Начинаем с последней строки
        for (int i = stackContent.size() - 1; i >= 0; i--) {
            Text valueText = new Text(stackContent.get(i));
            stack.add(valueText, 0, rowIndex); // Добавляем в первый столбец и текущую строку
            rowIndex--;
            if (rowIndex < 0) break; // Прерываем цикл, если достигнут конец стека
        }

        // Очистка содержимого выходного массива на интерфейсе
        secondTrain.getChildren().clear();

        // Добавление содержимого выходного массива на интерфейс
        int row = 0;
        int col = 0;
        for (Map.Entry<Integer, String> entry : step.getExitContent().entrySet()) {
            Integer key = entry.getKey();
            String value = entry.getValue();

            Text keyText = new Text(key.toString());
            Text valueText = new Text(value);

            secondTrain.add(keyText, col, row);
            secondTrain.add(valueText, col + 1, row);

            row++;
        }
    }
    @FXML
    private void handleButtonAction(ActionEvent event) {
        String input = calculator.getText(); // Получаем текст из TextField
        steps = InFixToPostFix.convertToPostfixStepByStep(input); // Получаем список шагов
        currentStepIndex = 0; // Сбрасываем индекс текущего шага
        updateInterface(steps.get(currentStepIndex)); // Обновляем интерфейс для первого шага
    }
    @FXML
    private void onNextStepClick(ActionEvent event) {
        if (steps != null && currentStepIndex < steps.size() - 1) {
            currentStepIndex++;
            updateInterface(steps.get(currentStepIndex)); // Обновляем интерфейс для следующего шага
        }
    }

}
