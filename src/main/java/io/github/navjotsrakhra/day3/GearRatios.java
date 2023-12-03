package io.github.navjotsrakhra.day3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class GearRatios {
    private static final int INPUT_LINES = 140;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        parseInputAndOutputResult(scanner);
    }

    public static void parseInputAndOutputResult(Scanner scanner) {
        final Line[] lines = new Line[INPUT_LINES];
        final String[] linesString = new String[INPUT_LINES];
        for (int i = 0; i < INPUT_LINES; i++) {
            lines[i] = new Line();
            String line = scanner.nextLine();
            linesString[i] = line;
            int[] intArray = convertStringToIntArray(line);
            for (int gear : intArray) {
                lines[i].addGear(gear, line.indexOf(String.valueOf(gear), lines[i].indexStream().max(Integer::compareTo).orElse(0) + lines[i].lastElementSize()));
            }
        }
        long sumOfValidNeighbours = 0L;
        for (int i = 0; i < INPUT_LINES; i++) {
            System.out.println(linesString[i] + " " + i + " " + checkIfNeighboursIsACharacterOtherThanPeriod(i, lines, linesString) + " " + lines[i].stream().mapToInt(Integer::intValue).sum());
            sumOfValidNeighbours += checkIfNeighboursIsACharacterOtherThanPeriod(i, lines, linesString);
        }
        System.out.println(sumOfValidNeighbours);
    }

    public static int[] convertStringToIntArray(String inputString) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(inputString);
        List<Integer> intList = new ArrayList<>();
        int index = 0;
        while (matcher.find()) {
            intList.add(Integer.parseInt(matcher.group()));
            index++;
        }
        return intList.stream().mapToInt(i -> i).toArray();
    }

    private static long checkIfNeighboursIsACharacterOtherThanPeriod(int i, Line[] lines, String[] linesString) {
        if (i == 49) {
            var a = 5;
        }

        long sum = 0L;
        Line line = lines[i];
        String lineString = linesString[i];

        var gears = line.stream().toList();

        for (int j = 0; j < gears.size(); j++) {
            var gear = gears.get(j);
            var gearIndex = line.getIndex(j);
            var gearEndIndex = gearIndex + line.getGearSize(j);

            if (gearIndex > 0 && lineString.charAt(gearIndex - 1) != '.') {
                sum += gear;
            } else if (gearEndIndex < lineString.length() && lineString.charAt(gearEndIndex) != '.') {
                sum += gear;
            } else {
                int sp = Math.max(0, gearIndex - 1);
                int ep = Math.min(lineString.length(), gearEndIndex + 1);

                String prevLine = null;
                if (i - 1 >= 0)
                    prevLine = linesString[i - 1];
                String nextLine = null;
                if (i + 1 < INPUT_LINES)
                    nextLine = linesString[i + 1];

                for (int k = sp; k < ep; k++) {
                    if (prevLine != null && prevLine.charAt(k) != '.' && !Character.isDigit(prevLine.charAt(k))) {
                        sum += gear;
                        break;
                    } else if (nextLine != null && nextLine.charAt(k) != '.' && !Character.isDigit(nextLine.charAt(k))) {
                        sum += gear;
                        break;
                    }
                }
            }
        }
        return sum;
    }

    private static class Line {
        private final List<Integer> gears;
        private final List<Integer> gearsIndex;

        public Line() {
            gears = new ArrayList<>();
            gearsIndex = new ArrayList<>();
        }

        public void addGear(int gear, int index) {
            gears.add(gear);
            gearsIndex.add(index);
        }

        public int getGear(int index) {
            return gears.get(index);
        }

        public int getGearSize(int index) {
            return (int) (Math.log(gears.get(index)) / Math.log(10) + 1);
        }

        public int getIndex(int index) {
            return gearsIndex.get(index);
        }

        public int getGearIndex(int gear) {
            return gearsIndex.get(gears.indexOf(gear));
        }

        public int size() {
            return gears.size();
        }

        public Stream<Integer> stream() {
            return gears.stream();
        }

        public Stream<Integer> indexStream() {
            return gearsIndex.stream();
        }

        @Override
        public String toString() {
            return "Line{" +
                    "gears=" + gears +
                    ", gearsIndex=" + gearsIndex +
                    '}';
        }

        public int lastElementSize() {
            if (gears.isEmpty()) return 0;
            int lastElement = gears.getLast();
            return (int) (Math.log(lastElement) / Math.log(10) + 1);
        }
    }

}
