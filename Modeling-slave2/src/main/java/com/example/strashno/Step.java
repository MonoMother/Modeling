package com.example.strashno;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Step {
    private String currentSymbol;
    private Stack<String> stackContent;
    private Map<Integer, String> exitContent;

    public Step(String currentSymbol, Deque<String> stackContent, Map<Integer, String> exitContent) {
        this.currentSymbol = currentSymbol;
        this.stackContent = new Stack<>();
        this.stackContent.addAll(stackContent);
        this.exitContent = new HashMap<>(exitContent);
    }

    public String getCurrentSymbol() {
        return currentSymbol;
    }

    public Stack<String> getStackContent() {
        return stackContent;
    }

    public Map<Integer, String> getExitContent() {
        return exitContent;
    }
}