package io.github.navjotsrakhra.day4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scratchcards {

    private static final int INPUT_SIZE = 201;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        var cards = input(sc);
        var winningPoints = calculateWinningPoints(cards);
        System.out.println(winningPoints);
    }

    private static long calculateWinningPoints(Card[] cards) {
        long winningPoints = 0;

        for (Card card : cards) {
            var winningPoint = calculateWinningPoints(card);
            winningPoints += winningPoint;
        }
        return winningPoints;
    }

    private static long calculateWinningPoints(Card card) {
        var winningPoints = 0L;
        var winningNumbers = card.winningNumbers();
        var scratchNumbers = card.scratchNumbers();

        var numberOfMatches = scratchNumbers.stream().mapToInt(scratchNumber -> winningNumbers.contains(scratchNumber) ? 1 : 0).sum();
        if (numberOfMatches == 0)
            return 0L;
        winningPoints = (long) Math.pow(2, numberOfMatches - 1);
        return winningPoints;
    }

    private static Card[] input(Scanner sc) {
        Card[] cards = new Card[INPUT_SIZE];
        for (int i = 0; i < INPUT_SIZE; i++) {
            var card = sc.nextLine();
            var t1 = card.split(":");
            var t2 = t1[0].split(" ");
            var id = Integer.parseInt(t2[t2.length - 1]);
            var temp = card.substring(card.indexOf(':')).split("\\|");
            var winningNumbers = convertStringToIntArray(temp[0]);
            var scratchNumbers = convertStringToIntArray(temp[1]);

            cards[i] = new Card(id, winningNumbers, scratchNumbers);
        }
        return cards;
    }

    private static List<Integer> convertStringToIntArray(String s) {
        List<Integer> list = new ArrayList<>();

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(s);

        while (matcher.find()) {
            list.add(Integer.valueOf(matcher.group()));
        }
        return list;
    }

    private record Card(int id, List<Integer> winningNumbers, List<Integer> scratchNumbers) {
    }
}
