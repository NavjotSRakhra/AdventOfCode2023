package io.github.navjotsrakhra.day3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class WRONG_GearRatio2Approach2 {
    private static final int INPUT_SIZE = 140;

    public static void main(String[] args) {
        var sc = new Scanner(System.in);
        Line[] input = getInput(sc);
        System.out.println(gearRatioSum(input));
    }

    private static long gearRatioSum(Line[] lines) {
        var sum = 0L;
        for (var i = 0; i < lines.length; i++) {
            sum += gearRatio(i, lines);
        }
        return sum;
    }

    private static long gearRatio(int i, Line[] lines) {
        var gearRatioInLineSum = 0L;
        var line = lines[i];
        var lineString = line.getLine();

        for (int j = 0; j < lineString.length(); j++) {
            var ch = lineString.charAt(j);
            if (ch == '*')
                gearRatioInLineSum += gearRatio(i, j, lines);
        }

        return gearRatioInLineSum;
    }

    private static long gearRatio(int i, int j, Line[] lines) {
        var gearRatio = 1;
        var neighbourCount = 0;
        var line = lines[i];
        var lineString = line.getLine();

        String currLine = null;
        String prevLine = null;
        String nextLine = null;
        if (i - 1 >= 0)
            prevLine = lines[i - 1].getLine();
        if (i + 1 < INPUT_SIZE)
            nextLine = lines[i + 1].getLine();

        // If prev != null
        if (prevLine != null) {
            currLine = prevLine;
            var sp = Math.max(0, j - 1);
            var ep = Math.min(currLine.length(), j + 2);
            if (Arrays.stream(currLine.substring(sp, ep).split("\\D+")).filter(a -> !a.isEmpty()).count() > 1) {
                sp = j - 1;
                while (sp >= 0 && Character.isDigit(currLine.charAt(sp))) {
                    sp--;
                    if (sp == 0) break;
                }
                if (!Character.isDigit(currLine.charAt(sp))) sp++;
                var num = 0;
                while (Character.isDigit(currLine.charAt(sp))) {
                    num = num * 10 + (currLine.charAt(sp) - '0');
                    sp++;
                    if (sp >= currLine.length()) break;
                }
                gearRatio *= num;
                neighbourCount++;

                sp = j + 1;
                num = 0;
                while (Character.isDigit(currLine.charAt(sp))) {
                    num = num * 10 + (currLine.charAt(sp) - '0');
                    sp++;
                    if (sp >= currLine.length()) break;
                }
                gearRatio *= num;
                neighbourCount++;
            } else {
                for (int k = sp; k < ep; k++) {
                    var ch = currLine.charAt(k);
                    if (Character.isDigit(ch)) {
                        sp = k;
                        while (Character.isDigit(currLine.charAt(sp))) {
                            sp--;
                            if (sp == 0) break;
                        }
                        if (!Character.isDigit(currLine.charAt(sp))) sp++;
                        var num = 0;
                        while (Character.isDigit(currLine.charAt(sp))) {
                            num = num * 10 + (currLine.charAt(sp) - '0');
                            sp++;
                            if (sp >= currLine.length()) break;
                        }
                        gearRatio *= num;
                        neighbourCount++;
                        break;
                    }
                }
            }
        }
        if (nextLine != null) {
            currLine = nextLine;
            var sp = Math.max(0, j - 1);
            var ep = Math.min(currLine.length(), j + 2);

            if (currLine.substring(sp, ep).split("\\D+").length > 1) {
                sp = j - 1;
                while (sp >= 0 && Character.isDigit(currLine.charAt(sp))) {
                    sp--;
                    if (sp == 0) break;
                }
                if (!Character.isDigit(currLine.charAt(sp))) sp++;
                var num = 0;
                while (Character.isDigit(currLine.charAt(sp))) {
                    num = num * 10 + (currLine.charAt(sp) - '0');
                    sp++;
                    if (sp >= currLine.length()) break;
                }
                gearRatio *= num;
                neighbourCount++;

                sp = j + 1;
                num = 0;
                while (Character.isDigit(currLine.charAt(sp))) {
                    num = num * 10 + (currLine.charAt(sp) - '0');
                    sp++;
                    if (sp >= currLine.length()) break;
                }
                gearRatio *= num;
                neighbourCount++;
            } else {
                for (int k = sp; k < ep; k++) {
                    var ch = currLine.charAt(k);
                    if (Character.isDigit(ch)) {
                        sp = k;
                        while (Character.isDigit(currLine.charAt(sp))) {
                            sp--;
                            if (sp == 0) break;
                        }
                        if (!Character.isDigit(currLine.charAt(sp))) sp++;
                        var num = 0;
                        while (Character.isDigit(currLine.charAt(sp))) {
                            num = num * 10 + (currLine.charAt(sp) - '0');
                            sp++;
                            if (sp >= currLine.length()) break;
                        }
                        gearRatio *= num;
                        neighbourCount++;
                        break;
                    }
                }
            }
        }

        if (j - 1 >= 0 && Character.isDigit(lineString.charAt(j - 1))) {
            neighbourCount++;
            var num = 0;
            int k = j - 1;
            char ch = lineString.charAt(k);
            while (Character.isDigit(ch)) {
                num = num * 10 + (ch - '0');
                k--;
                if (k < 0) break;
                ch = lineString.charAt(k);
            }
            num = Integer.parseInt(new StringBuilder(num + "").reverse().toString());
            gearRatio *= num;
        }
        if (j + 1 < lineString.length() && Character.isDigit(lineString.charAt(j + 1))) {
            neighbourCount++;
            var num = 0;
            int k = j + 1;
            char ch = lineString.charAt(k);
            while (Character.isDigit(ch)) {
                num = num * 10 + (ch - '0');
                k++;
                if (k >= lineString.length()) break;
                ch = lineString.charAt(k);
            }
            gearRatio *= num;
        }

        if (neighbourCount == 2)
            return gearRatio == 1 ? 0 : gearRatio;
        return 0;
    }

    private static Line[] getInput(Scanner scanner) {
        var input = new Line[INPUT_SIZE];

        for (int i = 0; i < INPUT_SIZE; i++) {
            input[i] = new Line(scanner.nextLine());
        }
        return input;
    }

    private static class Line {
        private final String line;
        private final List<Integer> numbers;
        private final List<Integer> numbersIndex;

        public Line(String line) {
            this.line = line;
            this.numbers = convertStringToNumbers(line);
            this.numbersIndex = new ArrayList<>();

            for (var number : numbers) {
                var lastIndex = numbersIndex.isEmpty() ? 0 : numbersIndex.getLast();
                numbersIndex.add(line.indexOf(number, lastIndex));
            }
        }

        private List<Integer> convertStringToNumbers(String line) {
            var numbers = new ArrayList<Integer>();

            var pattern = Pattern.compile("\\d+");
            var matcher = pattern.matcher(line);

            while (matcher.find()) {
                numbers.add(Integer.parseInt(matcher.group()));
            }
            return numbers;
        }

        public String getLine() {
            return line;
        }

        public List<Integer> getNumbers() {
            return numbers.stream().toList();
        }

        public List<Integer> getNumbersIndex() {
            return numbersIndex.stream().toList();
        }

        public int getNumber(int index) {
            return numbers.get(index);
        }

        public int getNumberIndex(int index) {
            return numbersIndex.get(index);
        }

        public int getNumberSize(int index) {
            return (int) (Math.log10(getNumber(index)) + 1);
        }
    }
}
