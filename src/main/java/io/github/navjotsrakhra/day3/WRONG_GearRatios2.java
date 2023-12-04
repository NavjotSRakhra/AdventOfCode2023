package io.github.navjotsrakhra.day3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

//51100179 < X < 78846023
public class WRONG_GearRatios2 {
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
        List<List<Integer>> partGears = new ArrayList<>();
        for (int i = 0; i < INPUT_LINES; i++) {
            var valid = checkIfNeighboursIsACharacterOtherThanPeriod(i, lines, linesString);
            partGears.add(valid);
        }

        long gearRatio = 0;

        for (int i = 0; i < INPUT_LINES; i++) {
            long ratio = checkGears(i, lines, partGears, linesString);
            gearRatio += ratio;
        }
        System.out.println(gearRatio);
    }

    private static long checkGears(int i, Line[] lines, List<List<Integer>> partGears, String[] linesString) {
        long gearRatio = 0L;


        String lineString = linesString[i];
        for (int j = 0; j < lineString.length(); j++) {
            char c = lineString.charAt(j);

            if (c == '*') {
                int surroundingGears = 0;
                long n = 1;

                if (j - 1 >= 0 && Character.isDigit(lineString.charAt(j - 1))) {
                    surroundingGears++;
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
                    n *= num;
                }
                if (j + 1 < lineString.length() && Character.isDigit(lineString.charAt(j + 1))) {
                    surroundingGears++;
                    var num = 0;
                    int k = j + 1;
                    char ch = lineString.charAt(k);
                    while (Character.isDigit(ch)) {
                        num = num * 10 + (ch - '0');
                        k++;
                        if (k >= lineString.length()) break;
                        ch = lineString.charAt(k);
                    }
                    n *= num;
                }
                int sp = Math.max(0, j - 1);
                int ep = Math.min(lineString.length(), j + 1);

                if (i - 1 >= 0) {
                    Line line = lines[i - 1];
                    List<Integer> validNeighbours = partGears.get(i - 1);

                    for (Integer validNeighbour : validNeighbours) {
                        int gearIndex = line.getIndex(validNeighbour);
                        int gearEndIndex = gearIndex + line.getGearSize(validNeighbour) - 1;

//                        if (isGearAdjacentToPart(sp, gearEndIndex, ep, gearIndex)) {
                        if (isRangeIntersecting(sp, ep, gearIndex, gearEndIndex)) {
                            surroundingGears++;
                            n *= line.getGear(validNeighbour);
                        }
                    }
                }
                if (i + 1 < INPUT_LINES) {
                    Line line = lines[i + 1];
                    List<Integer> validNeighbours = partGears.get(i + 1);

                    for (Integer validNeighbour : validNeighbours) {
                        int gearIndex = line.getIndex(validNeighbour);
                        int gearEndIndex = gearIndex + line.getGearSize(validNeighbour) - 1;

//                        if (isGearAdjacentToPart(sp, gearEndIndex , ep, gearIndex)) {
                        if (isRangeIntersecting(sp, ep, gearIndex, gearEndIndex)) {
                            surroundingGears++;
                            n *= line.getGear(validNeighbour);
                        }
                    }
                }
                if (surroundingGears == 2) {
                    gearRatio += n;
                } else
                    System.out.println("surroundingGears = " + surroundingGears + " " + i + " " + j);
            }
        }
        return gearRatio;
    }

    public static boolean isRangeIntersecting(int a, int b, int x, int y) {
        return !(b < x || y < a);
    }

    private static boolean isGearAdjacentToPart(int sp, int gearEndIndex, int ep, int gearIndex) {
        return (gearIndex >= sp && gearIndex <= ep) || (gearEndIndex >= sp && gearEndIndex <= ep);
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

    private static ArrayList<Integer> checkIfNeighboursIsACharacterOtherThanPeriod(int i, Line[] lines, String[] linesString) {
        var listOfValidNeighbours = new ArrayList<Integer>();

        Line line = lines[i];
        String lineString = linesString[i];

        var gears = line.stream().toList();

        for (int j = 0; j < gears.size(); j++) {
            var gearIndex = line.getIndex(j);
            var gearEndIndex = gearIndex + line.getGearSize(j);

            if (gearIndex > 0 && lineString.charAt(gearIndex - 1) != '.') {
                listOfValidNeighbours.add(j);
            } else if (gearEndIndex < lineString.length() && lineString.charAt(gearEndIndex) != '.') {
                listOfValidNeighbours.add(j);
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
                        listOfValidNeighbours.add(j);
                        break;
                    } else if (nextLine != null && nextLine.charAt(k) != '.' && !Character.isDigit(nextLine.charAt(k))) {
                        listOfValidNeighbours.add(j);
                        break;
                    }
                }
            }
        }
        return listOfValidNeighbours;
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
            return (int) (Math.log10(gears.get(index)) + 1);
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
            return (int) (Math.log10(lastElement) + 1);
        }
    }

}
