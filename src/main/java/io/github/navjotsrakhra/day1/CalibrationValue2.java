package io.github.navjotsrakhra.day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CalibrationValue2 {
    private static final String[] digits = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        long sum = 0;
        int c = 0;
        sum = in.lines().map(CalibrationValue2::processString).mapToLong(Integer::longValue).sum();
        System.out.println(sum);
    }

    private static int processString(String s) {
        int n = 0;

        int x = Integer.MAX_VALUE;
        int x_val = -1;
        for (int i = 0; i < digits.length; i++) {
            String digit = digits[i];
            int index = s.indexOf(digit);
            if (index != -1 && index < x) {
                x = index;
                x_val = i;
            }
        }

        int y = Integer.MIN_VALUE;
        int y_val = -1;

        for (int i = 0; i < digits.length; i++) {
            String digit = digits[i];
            int index = s.lastIndexOf(digit);
            if (index != -1 && index > y) {
                y = index;
                y_val = i;
            }
        }

        for (int i = 0; i < x; i++) {
            char ch = s.charAt(i);
            if (Character.isDigit(ch)) {
                x_val = Character.getNumericValue(ch);
                break;
            }
        }

        for (int i = s.length() - 1; i > y; i--) {
            char ch = s.charAt(i);
            if (Character.isDigit(ch)) {
                y_val = Character.getNumericValue(ch);
                break;
            }
        }
        n = x_val * 10 + y_val;
        return n;
    }
}
