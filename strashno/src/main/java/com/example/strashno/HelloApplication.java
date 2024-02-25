package com.example.strashno;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }


    // Основной метод программы
    public static void main(String[] args) {
        int c = 0; // Счетчик для выходной строки
        String name = "(A+B)/(C*(D-A))"; // Входное инфиксное выражение
        String[] entrance = new String[name.length()]; // Массив для хранения символов входной строки
        Stack<String> stack = new Stack<>(); // Стек для операторов
        Map<Integer, String> exit = new HashMap<>(); // Словарь для хранения постфиксного выражения

        // Заполнение массива entrance символами из входной строки и их вывод
        for (int i = 0; i < entrance.length; i++) {
            entrance[i] = String.valueOf(name.charAt(i));
            System.out.print(entrance[i]);
        }

        // Инициализация мапа exit символами-заполнителями
        for (int i = 0; i < entrance.length; i++) {
            exit.put(i, "_");
        }

        // Обработка каждого символа входной строки
        for (String s : entrance) {
            if (s.matches("[+\\-/*]")) { // Если символ - оператор
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
