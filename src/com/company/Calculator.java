package com.company;


class Calculator {

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

    String calculate(String inputExpression) throws Exception {
        Expression expression = new Parser().parse(inputExpression);
        return expression.calculate();
    }

    private class Parser {

        Expression parse(String expression) throws Exception {
            expression = expression.replaceAll("\\s", "");
            ArithmeticOperation operation = parseOperation(expression);
            String[] numbers = parseNumbers(expression, operation);
            NumbersType type = parseNumbersType(numbers);
            int[] arabicNumbers = parseArabicNumbers(numbers, type);
            int left = arabicNumbers[0];
            int right = arabicNumbers[1];

            return new Expression(operation, type, left, right);
        }

        private ArithmeticOperation parseOperation(String expression) throws Exception {
            for (ArithmeticOperation operation : ArithmeticOperation.values()) {
                if (expression.contains(operation.value))
                    return operation;
            }
            throw new Exception("Wrong expression format");
        }

        private String[] parseNumbers(String expression, ArithmeticOperation operation) throws Exception {
            String[] numbers = expression.split(operation.screenedValue());
            if (numbers.length != 2)
                throw new Exception("Something went wrong during parsing");
            return numbers;

        }

        private NumbersType parseNumbersType(String[] numbers) throws Exception {
            int arabicType = 0;
            int romanType = 0;

            for (String str : numbers) {
                if (isArabic(str))
                    arabicType++;
                else if (isRoman(str))
                    romanType++;
            }
            if (arabicType == numbers.length)
                return NumbersType.Arabic;
            else if (romanType == numbers.length)
                return NumbersType.Roman;
            throw new Exception("Wrong number types");
        }

        private boolean isArabic(String str) {
            try {
                Integer.parseInt(str);
                return true;
            }
            catch (NumberFormatException e) {
                return false;
            }
        }

        private boolean isRoman(String str) {
            for (String romanNumber : roman)
                if (str.equalsIgnoreCase(romanNumber))
                    return true;
            return false;
        }

        private int[] parseArabicNumbers(String[] numbers, NumbersType type) throws Exception {
            int[] result = new int[numbers.length];
            for (int i = 0; i < numbers.length; i++) {
                switch (type) {
                    case Arabic:
                        result[i] = Integer.parseInt(numbers[i]);
                        if (!isInInterval(result[i]))
                            throw new Exception("Too big or too small");
                        break;
                    case Roman:
                        result[i] = getRomanIndex(numbers[i]);
                        if (!isInInterval(result[i]))
                            throw new Exception("Too big or too small");
                        break;
                }
            }
            return result;
        }

        private boolean isInInterval(int i) {
            return i >= 0 && i <= 9;
        }

        private int getRomanIndex(String number) {
            for (int i = 0; i < roman.length; i++)
                if (number.equalsIgnoreCase(roman[i]))
                    return i;
            return 0;
        }
    }

    private class Expression {
        final ArithmeticOperation operation;
        final NumbersType type;
        final int left, right;

        Expression(ArithmeticOperation operation, NumbersType type, int left, int right) {
            this.operation = operation;
            this.type = type;
            this.left = left;
            this.right = right;
        }

        String calculate() throws Exception {
            switch (type) {
                case Arabic:
                    return String.valueOf(calculateArabic());
                case Roman:
                    return calculateRoman();
            }
            throw new Exception("Calculation mistake");
        }
        private int calculateArabic() {
            switch (operation) {
                case Plus:
                    return left + right;
                case Minus:
                    return left - right;
                case Div:
                    return left / right;
                case Mult:
                    return left * right;
            }
            return 0;
        }

        private String calculateRoman() {
            int i = calculateArabic();
            if (i >= 0)
                return roman[i];
            else
                return "-" + roman[-i];
        }
    }

    enum ArithmeticOperation {
        Plus("+"),
        Div("/"),
        Mult("*"),
        Minus("-");

        final String value;

        ArithmeticOperation(String value) {
            this.value = value;
        }

        String screenedValue() {
            switch (this) {
                case Plus:
                    return "\\+";
                case Mult:
                    return "\\*";
                default:
                    return value;
            }
        }
    }

    enum NumbersType {
        Arabic,
        Roman
    }

}
