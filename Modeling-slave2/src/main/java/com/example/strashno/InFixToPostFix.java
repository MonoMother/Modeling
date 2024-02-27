package com.example.strashno;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class InFixToPostFix extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InFixToPostFix.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 700);
        stage.setTitle("Перевод из инфиксной формы в постфиксную");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
    public static List<Step> convertToPostfixStepByStep(String userInput) {
        List<Step> steps = new ArrayList<>();
        int c = 0;
        String[] entrance = new String[userInput.length()];
        Deque<String> stack = new LinkedList<>();
        Map<Integer, String> exit = new HashMap<>();

        for (int i = 0; i < entrance.length; i++) {
            entrance[i] = String.valueOf(userInput.charAt(i));
        }

        for (int i = 0; i < entrance.length; i++) {
            exit.put(i, "_");
        }

        for (String s : entrance) {
            if (s.matches("[+\\-/*]")) {
                while (!stack.isEmpty() && precedence(s) <= precedence(stack.peek())) {
                    exit.put(c, stack.pop());
                    c++;
                    steps.add(new Step(s, stack, exit));
                }
                stack.push(s);
            } else if (s.equals("(")) {
                stack.push(s);
            } else if (s.equals(")")) {
                while (!stack.peek().equals("(")) {
                    exit.put(c, stack.pop());
                    c++;
                    steps.add(new Step(s, stack, exit));
                }
                stack.pop();
            } else {
                exit.put(c, s);
                c++;
                steps.add(new Step(s, stack, exit));
            }
        }

        while (!stack.isEmpty()) {
            exit.put(c, stack.pop());
            c++;
            steps.add(new Step("", stack, exit));
        }

        return steps;
    }
    public static String convertToPostfix(String userInput) {
        int c = 0; // Счетчик для выходной строки
        String[] entrance = new String[userInput.length()]; // Массив для хранения символов входной строки
        Stack<String> stack = new Stack<>(); // Стек для операторов
        Map<Integer, String> exit = new HashMap<>(); // Словарь для хранения постфиксного выражения

        // Заполнение массива entrance символами из входной строки
        for (int i = 0; i < entrance.length; i++) {
            entrance[i] = String.valueOf(userInput.charAt(i));
        }

        // Инициализация мапа exit символами-заполнителями
        for (int i = 0; i < entrance.length; i++) {
            exit.put(i, "_");
        }

        // Обработка каждого символа входной строки
        for (String s : entrance) {
            if (s.matches("[+\\-/*]")) { // Исправлено: проверка на операторы +, -, *, /
                // Пока стек не пуст и приоритет текущего оператора меньше или равен приоритету оператора на вершине стека
                while (!stack.empty() && precedence(s) <= precedence(stack.peek())) {
                    exit.put(c, stack.pop()); // Выталкиваем оператор из стека в выходную строку
                    c++;
                }
                stack.push(s); // Помещаем текущий оператор в стек
            } else if (s.equals("(")) { // Если символ - открывающая скобка
                stack.push(s); // Помещаем ее в стек
            } else if (s.equals(")")) { // Если символ - закрывающая скобка
                // Пока не встретим открывающую скобку, выталкиваем операторы из стека в выходную строку
                while (!stack.peek().equals("(")) {
                    exit.put(c, stack.pop());
                    c++;
                }
                stack.pop(); // Удаляем открывающую скобку из стека
            } else { // Если символ - операнд
                exit.put(c, s); // Помещаем операнд в выходную строку
                c++;
            }
        }

        // Выталкиваем оставшиеся операторы из стека в выходную строку
        while (!stack.empty()) {
            exit.put(c, stack.pop());
            c++;
        }

        System.out.println(); // Переход на новую строку
        for (int i = 0; i < entrance.length; i++) {
            System.out.print(exit.get(i)); // Выводим постфиксное выражение
        }

        // Формирование строки с постфиксным выражением
        StringBuilder postfixExpression = new StringBuilder();
        for (int i = 0; i < entrance.length; i++) {
            postfixExpression.append(exit.get(i));
        }

        return postfixExpression.toString();
    }

    // Метод для определения приоритета оператора
    private static int precedence(String op) {
        return switch (op) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            default -> 0;
        };
    }
}