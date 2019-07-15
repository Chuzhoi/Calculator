package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String expression = reader.readLine();

        Calculator calculator = new Calculator();
        try {
            System.out.println(calculator.calculate(expression));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
