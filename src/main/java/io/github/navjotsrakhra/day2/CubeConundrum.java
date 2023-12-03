package io.github.navjotsrakhra.day2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CubeConundrum {
    private static final Set BASE_SET = new Set(12, 14, 13);

    private static Game inputGame(Scanner scanner) {
        String game = scanner.nextLine();
        String gameName = game.split(":")[0].trim();
        String[] sets = game.split(":")[1].split(";");
        List<Set> subsets = new ArrayList<>(sets.length);

        for (String set : sets) {
            String[] colors = set.trim().split(" ");
            int red = 0, blue = 0, green = 0;
            for (int i = 0; i < colors.length; i++) {
                try {
                    int count = Integer.parseInt(colors[i]);
                    switch (colors[i + 1].charAt(0)) {
                        case 'r':
                            red = count;
                            break;
                        case 'b':
                            blue = count;
                            break;
                        case 'g':
                            green = count;
                            break;
                    }
                } catch (NumberFormatException e) {
                    // ignore
                }
            }
            subsets.add(new Set(red, blue, green));
        }
        System.out.println(Arrays.toString(gameName.split(" ")));
        System.out.println(subsets);
        return new Game(Integer.parseInt(gameName.split(" ")[1].trim()), subsets);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numberOfGames = Integer.parseInt(sc.nextLine());
        long sumOfValidIds = 0L;
        Set maxSet = new Set(0, 0, 0);
        for (int i = 0; i < numberOfGames; i++) {
            Game game = inputGame(sc);
            if (game.isValid(BASE_SET)) sumOfValidIds += game.id();
        }
        System.out.println(sumOfValidIds);
    }

    public record Set(int red, int blue, int green) {
        boolean isValid(Set set) {
            return red() >= set.red() && blue() >= set.blue() && green() >= set.green();
        }

        Set maximize(Set set) {
            return new Set(Math.max(red(), set.red()), Math.max(blue(), set.blue()), Math.max(green(), set.green()));
        }
    }

    public record Game(int id, List<Set> subsets) {
        public boolean isValid(Set baseSet) {
            return subsets.stream().allMatch(baseSet::isValid);
        }

        Set maximizeGameSet() {
            return subsets().stream()
                    .reduce(new Set(0, 0, 0), Set::maximize);
        }
    }
}
