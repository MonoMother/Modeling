package com.example.strashno;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.geometry.HPos; //для горизонтального выравнивания
import javafx.geometry.Insets;
import javafx.geometry.VPos; //для вертикальноого выравнивания
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class Controller {
    int index;
    private Stack<Character> eternalGreatStack = new Stack<>();
    Text letterFlag = new Text(String.valueOf('<'));
    private int currentRowIndex = 10; //текущий индекс строки
    int rowGridPane;
    Stack<Character> tempStack = new Stack<>();
    private Stack<Character> stack = new Stack<>();
    private Stack<Character> greatStack = new Stack<>();
    private StringBuilder postfix = new StringBuilder();
    private int currentIndex = 0;
    private boolean isConversionComplete = false;
    //в таблице в [8,8] исправила 7 на 2, потому что это не ошибка, такая ситуация корректна и может возникнуть в ходе
    //работы программы, пример: A+(B-C*sin(A))
    //в ходе выполнения программы в стеке остаётся sin, потому что мы удаляем оттуда открывающую и закрывающую скобку
    //а потом на вход поступает ')'
    char[][] table = {
            {' ', '|', '+', '-', '*', '/', '^', '(', ')', 'F', 'P'},
            {'|', '4', '1', '1', '1', '1', '1', '1', '5', '1', '6'},
            {'+', '2', '2', '2', '1', '1', '1', '1', '2', '1', '6'},
            {'-', '2', '2', '2', '1', '1', '1', '1', '2', '1', '6'},
            {'*', '2', '2', '2', '2', '2', '1', '1', '2', '1', '6'},
            {'/', '2', '2', '2', '2', '2', '1', '1', '2', '1', '6'},
            {'^', '2', '2', '2', '2', '2', '2', '1', '2', '1', '6'},
            {'(', '5', '1', '1', '1', '1', '1', '1', '3', '1', '6'},
            {'F', '2', '2', '2', '2', '2', '2', '1', '2', '7', '6'},
    };

    @FXML
    private GridPane stackGridPane;
    @FXML
    private Button button;
    @FXML
    private Button bigButton;
    @FXML
    private TextField inputText;
    @FXML
    private TextField outputText;
    @FXML
    private GridPane Deikstra;
    @FXML
    private GridPane DeikstraColor;
    @FXML
    private GridPane firstTrain;
    @FXML
    private GridPane secondTrain;
    @FXML
    private GridPane stackFlag;

    @FXML
    public void initialize() {
        createTable();
        stackFlag.getChildren().clear();
        DeikstraColor.getChildren().clear();
    }
/*
    public void click() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Label label = new Label(" ");
                label.setPrefSize(100, 100);
                int cellValue = i * 3 + j;
                label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("Clicked on cell: " + cellValue);
                        // Присвоить значение переменной в ячейке
                        label.setText("Clicked");
                    }
                });
                firstTrain.add(label,i,j);
            }
        }
    }

 */
    @FXML
    private void onButtonClick(ActionEvent event) {
        DeikstraColor.getChildren().clear();
        secondTrain.getChildren().removeIf(node -> node instanceof Text); //удаляем текст
        postfix.setLength(0);
        stack.clear();
        outputText.setText(convert(inputText.getText()));

        postfix.setLength(0);
        currentIndex = 0;
        currentRowIndex = 10;
        rowGridPane = stackGridPane.getRowCount() - 1; // Получаем текущий индекс строки
        while (!greatStack.isEmpty()) {
            tempStack.push(greatStack.pop());
        }
        while (!eternalGreatStack.isEmpty()) {
            tempStack.push(eternalGreatStack.pop());
        }
    }

    @FXML
    private void onBigButtonClick(ActionEvent event) {
        DeikstraColor.getChildren().clear();
        secondTrain.getChildren().removeIf(node -> node instanceof Text); // Удаляем текст
        if (!isConversionComplete) {
            setTextSecondTrain(convertNextStep(inputText.getText()));
        }

        if (isConversionComplete) {
            stackFlag.getChildren().removeIf(node -> node instanceof Text); // Удаляем текст
            stackFlag.add(letterFlag, 0, 9);
            while (!eternalGreatStack.isEmpty()) {
                tempStack.push(eternalGreatStack.pop());
            }
            DeikstraColor.getChildren().clear();
            DeikstraColor.add(createColoredPane(new Color(0, 1, 0, 0.5)), 1, 1);
            postfix.setLength(0);
            stack.clear();
            currentIndex = 0;
            rowGridPane = stackGridPane.getRowCount() - 1; // Получаем текущий индекс строки
            currentRowIndex = 10;
            isConversionComplete = false;
        }

    }

    public void setTextSecondTrain(String text) {
        for (int i = 0; i < text.length(); i++) {
            Text letter = new Text(String.valueOf(text.charAt(i)));
            secondTrain.add(letter, i, 0); // Добавляем символ в i-тую колонку
            GridPane.setHalignment(letter, HPos.CENTER); // Горизонтальное выравнивание
            GridPane.setValignment(letter, VPos.CENTER); // Вертикальное выравнивание
        }

    }

    public void handleCalculator(MouseEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InFixToPostFix.class.getResource("Calculator.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Калькулятор");
        Parent root = fxmlLoader.load();
        Calculator calculatorController = fxmlLoader.getController();
        calculatorController.setController(this); // Устанавливаем связь с текущим Controller
        Scene scene = new Scene(root, 440, 479);
        stage.setScene(scene);
        stage.initModality(Modality.NONE);
        stage.show();
    }

    public void setText(String text) {
        inputText.setText(text);
    }

    public void setTextFirstTrain(String text) {
        firstTrain.getChildren().removeIf(node -> node instanceof Text); //удаляем текст
        for (int i = 0; i < text.length(); i++) {
            Text letter = new Text(String.valueOf(text.charAt(i)));
            firstTrain.add(letter, i, 0); // Добавляем символ в i-тую колонку
            GridPane.setHalignment(letter, HPos.CENTER); // Горизонтальное выравнивание
            GridPane.setValignment(letter, VPos.CENTER); // Вертикальное выравнивание
        }
    }

    public void createTable() {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                Label label = new Label(String.valueOf(table[i][j]));
                if ((i==0)||(j==0)){
                    label.setFont(Font.font("Courier New", FontWeight.BOLD, 16));
                } else label.setFont(Font.font("Courier New", 18));
                Deikstra.add(label, j, i);
                GridPane.setHalignment(label, HPos.CENTER); // Горизонтальное выравнивание
                GridPane.setValignment(label, VPos.CENTER); // Вертикальное выравнивание
            }
        }
    }
    private Pane createColoredPane(Color color) {
        Pane pane = new Pane();
            pane.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
        return pane;
    }
    private String convertNextStep(String infix) {
        if ((currentIndex >= infix.length())&&(stack.isEmpty())) {
            isConversionComplete = true;
            return postfix.toString();
        }
        char c;
        infix = infix.replace("sin", "s");
        infix = infix.replace("cos", "c");
        infix = infix.replace("сtg", "с");
        infix = infix.replace("tg", "t");
        if (currentIndex >= infix.length()) c = ' ';
        else c = infix.charAt(currentIndex);

        int row = findRow(stack);
        int col = findCol(c);

        if (row == -1 || col == -1) {
            throw new IllegalArgumentException("Invalid character: " + c);
        }
        int action = Character.getNumericValue(table[row][col]);

    DeikstraColor.add(createColoredPane(new Color(0, 1, 0, 0.5)), col, row);

        switch (action) {
            case 1:
                stack.push(c);
                eternalGreatStack.push(c);
                Text letter = new Text(String.valueOf(c));
                stackGridPane.add(letter, 0, rowGridPane); // Добавляем символ в новую строку rowGridPane;
                rowGridPane--;
                GridPane.setHalignment(letter, HPos.CENTER);
                GridPane.setValignment(letter, VPos.CENTER);
                currentRowIndex--;
                stackFlag.getChildren().removeIf(node -> node instanceof Text); // Удаляем текст
                stackFlag.add(letterFlag, 0, 10-eternalGreatStack.size());
                currentIndex++;
                break;
            case 2:
                if (eternalGreatStack.get(0) == stack.get(0)) currentRowIndex = 10-stack.size();
                else currentRowIndex = 10-stack.size()-1;
                if (stack.peek() == 's'){
                    postfix.append(stack.pop());
                    currentRowIndex = 10-stack.size()-2;
                    postfix.append('i');
                    postfix.append('n');
                } else if (stack.peek() == 'c'){
                    postfix.append(stack.pop());
                    currentRowIndex = 10-stack.size()-1;
                    postfix.append('o');
                    postfix.append('s');
                } else if (stack.peek() == 'с'){
                    postfix.append(stack.pop());
                    currentRowIndex = 10-stack.size()-1;
                    postfix.append('t');
                    postfix.append('g');
                }
                else if (stack.peek() == 't'){
                    postfix.append(stack.pop());
                    currentRowIndex = 10-stack.size()-1;
                    postfix.append('g');
                }
                else {
                    postfix.append(stack.pop());
                }
                stackFlag.getChildren().removeIf(node -> node instanceof Text); // Удаляем текст
                stackFlag.add(letterFlag, 0, currentRowIndex);
                break;
            case 3:
                currentRowIndex = 10-stack.size();
                //находим, где в стеке '(' и заменяем её. Напротив ячейки со скобкой ставим указатель
                index = (eternalGreatStack.search('(')-1);
                eternalGreatStack.set(index, '_');
                stackFlag.getChildren().removeIf(node -> node instanceof Text); // Удаляем текст
                stackFlag.add(letterFlag, 0, (9-(eternalGreatStack.size()-index)));
                currentIndex++;
                while (!stack.isEmpty()) {
                    char popped = stack.pop();
                    if (popped == '(') {
                        break;
                    } else {
                        postfix.append(popped);
                    }
                }
                break;
            case 4:
                isConversionComplete = true;
                return postfix.toString();
            case 5:
                throw new IllegalArgumentException("Mismatched parentheses");
            case 6:
                currentIndex++;
                postfix.append(c);
                break;
            case 7:
                throw new IllegalArgumentException("Missing function argument");
        }
        return postfix.toString();
    }
    public String convert(String infix) {
        infix = infix.replace("sin", "s");
        infix = infix.replace("cos", "c");
        infix = infix.replace("сtg","с");
        infix = infix.replace("tg","t");

        int length = infix.length();

        for (int i = 0; i < length || !stack.isEmpty(); i++) {
            char c = (i < length) ? infix.charAt(i) : ' ';

            int row = findRow(stack);
            int col = findCol(c);

            if (row == -1 || col == -1) {
                throw new IllegalArgumentException("Invalid character: " + c);
            }

            int action = Character.getNumericValue(table[row][col]);

            switch (action) {
                case 1:
                    stack.push(c);
                    greatStack.push(c);
                    break;
                case 2:
                    if (stack.peek() == 's'){
                        postfix.append(stack.pop());
                        postfix.append('i');
                        postfix.append('n');
                    }
                else if (stack.peek() == 'c'){
                        postfix.append(stack.pop());
                        postfix.append('o');
                        postfix.append('s');
                    } else if (stack.peek() == 'с') {
                        postfix.append(stack.pop());
                        postfix.append('t');
                        postfix.append('g');
                    } else if(stack.peek() == 't'){
                        postfix.append(stack.pop());
                        postfix.append('g');
                    }
                    else {
                        postfix.append(stack.pop());
                    }
                    if (c != ')'&&(c!=' ')) {
                        stack.push(c);
                        greatStack.push(c);
                    }
                    if (c== ')') {
                        boolean foundOpeningBracket = false;

                    Stack<Character> tempStack = new Stack<>();

                    // Проходим по всем элементам в стеке
                    while (!stack.isEmpty()) {
                        char popped = stack.pop();
                        if (popped == '(' && !foundOpeningBracket) {
                            foundOpeningBracket = true; // Устанавливаем флаг, что открывающая скобка найдена и пропускаем удаление этой скобки
                        } else {
                            tempStack.push(popped); // Сохраняем элементы, кроме первой открывающей скобки
                        }
                    }

                    // Возвращаем элементы обратно в основной стек, кроме первой открывающей скобки
                    while (!tempStack.isEmpty()) {
                        stack.push(tempStack.pop());
                    }}
                    break;
                case 3:
                    while (!stack.isEmpty()) {
                        char popped = stack.pop();
                        if (popped == '(') {
                            break;
                        } else {
                            postfix.append(popped);
                        }
                    }
                    break;
                case 4:
                    return postfix.toString();
                case 5:
                    throw new IllegalArgumentException("Mismatched parentheses");
                case 6:
                    postfix.append(c);
                    break;
                case 7:
                    throw new IllegalArgumentException("Missing function argument");
            }
        }
        return postfix.toString();
    }

    private int findRow(Stack<Character> stack) {
        if (stack.isEmpty()) return 1;
        for (int i = 0; i < table.length; i++) {
            if (table[i][0] == stack.peek()) return i;
        }
        if ((stack.peek() == 's')||(stack.peek() == 'c')||(stack.peek() == 'с')||(stack.peek() == 't'))   return 8;
        return -1;
    }


    private int findCol(char c) {
        for (int j = 0; j < table[0].length; j++) {
            if (c == ' ') return 1;
            else if (table[0][j] == c) return j;
            else if ((c == 'A')||(c == 'B')||(c == 'C')||(c == 'D')) return 10;
            else if ((c == 's') || (c == 'c') || (c =='с') ||(c == 't')) return 9;
        }
        return -1;
    }
    }
