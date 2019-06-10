package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static String[] roman = new String []{"N", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX",
            "X", "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX",
            "XX", "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI", "XXVII", "XXVIII", "XXIX",
            "XXX", "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI", "XXXVII", "XXXVIII", "XXXIX",
            "XL", "XLI", "XLII", "XLIII", "XLIV", "XLV", "XLVI", "XLVII", "XLVIII", "XLIX",
            "L", "LI", "LII", "LIII", "LIV", "LV", "LVI", "LVII", "LVIII", "LIX",
            "LX", "LXI", "LXII", "LXIII", "LXIV", "LXV", "LXVI", "LXVII", "LXVIII", "LXIX",
            "LXX", "LXXI", "LXXII", "LXXIII", "LXXIV", "LXXV", "LXXVI", "LXXVII", "LXXVIII", "LXXIX",
            "LXXX", "LXXXI", "LXXXII", "LXXXIII", "LXXXIV", "LXXXV", "LXXXVI", "LXXXVII", "LXXXVIII", "LXXXIX",
            "XC", "XCI", "XCII", "XCIII", "XCIV", "XCV", "XCVI", "XCVII", "XCVIII", "XCIX", "C"};
    private static boolean isRoman = false;


    public static void main(String[] args) throws IOException {
        int[] numbers;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String expression = reader.readLine();

        expression = expression.replaceAll("\\s", "");

        try {
            String type = expressionSplit(expression);

            numbers = getNumbers(expression, type);

            System.out.println(implementation(numbers, type));
        }
        catch (WrongExpressionFormat e) {
            System.out.println(e.getMessage());
        }

    }

    private static String expressionSplit(String expression) throws WrongExpressionFormat {
        String[] subStr;
        String type = null;
        if (expression.contains("/")) {
            subStr = expression.split("/");
            if(isExpression(subStr)){
                type = "/";
            }
        }
        if (expression.contains("*")) {
            subStr = expression.split("\\*");
            if(isExpression(subStr)){
                type = "\\*";
            }
        }
        if (expression.contains("-")) {
            subStr = expression.split("-");
            if(isExpression(subStr)){
                type = "-";
            }
        }
        if (expression.contains("+")) {
            subStr = expression.split("\\+");
            if(isExpression(subStr)){
                type = "\\+";
            }
        }
        if (type != null)
            return type;
        else throw new WrongExpressionFormat("Wrong expression!");
    }

    private static boolean isExpression(String[] subStr) throws WrongExpressionFormat  {

        int compCount = 0;
        if (subStr.length == 2) {
            String comp;
            for (int n = 0; n < 2; n++) {
                for (int i = 1; i < 11; i++) {
                    comp = String.valueOf(i);
                    if (subStr[n].equals(comp))
                        compCount++;
                }
            }
            if (compCount == 0) {
                for (int n = 0; n < 2; n++) {
                    for (int i = 1; i < 11; i++) {
                        if (subStr[n].equalsIgnoreCase(roman[i]))
                            compCount++;
                    }
                }
                isRoman = true;
            }
        }
        else
            throw new WrongExpressionFormat("Wrong expression format!");

        return  compCount == 2;
    }

    private static int[] getNumbers(String expression, String type) {
        int[] result = new int[2];
        String[] subStr = expression.split(type);

        try {
            for (int i = 0; i < 2; i++)
                result[i] = Integer.parseInt(subStr[i]);
        }
        catch (NumberFormatException ex) {
            for (int i = 0; i < 2; i++)
                for (int n = 1; n < 11; n++) {
                    if (subStr[i].equalsIgnoreCase(roman[n]))
                    result[i] = n;
                }
        }

        return result;
    }

    private static int arabicImplementation(int[] numbers, String type) {
        int result = 0;
        switch (type) {
            case "\\+":
                result =  numbers[0] + numbers[1];
                break;
            case "-":
                result = numbers[0] - numbers[1];
                break;
            case "\\*":
                result = numbers[0] * numbers[1];
                break;
            case "/":
                result = numbers[0] / numbers[1];
                break;
        }
        return result;
    }

    private static String romanImplementation(int[] numbers, String type) {
        int auxResult = arabicImplementation(numbers, type);
        if (auxResult < 0)
            return "-" + roman[-auxResult];
        else return roman[auxResult];
    }

    private static String implementation(int[] numbers, String type) {
        if (isRoman)
            return String.valueOf(romanImplementation(numbers,type));
        else
            return romanImplementation(numbers, type);
    }


    public static class WrongExpressionFormat extends Exception {
        WrongExpressionFormat(String message) {
            super(message);
        }
    }


}
