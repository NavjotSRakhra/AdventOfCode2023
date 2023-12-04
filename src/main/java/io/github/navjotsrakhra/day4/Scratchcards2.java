package io.github.navjotsrakhra.day4;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scratchcards2 {
    private static final int INPUT_SIZE = 201;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        var cards = input(sc);
        var numberOfCardsWon = calculateNumberOfCardsWon(cards);
        System.out.println(numberOfCardsWon);
    }

    private static long calculateNumberOfCardsWon(Card[] cards) {
        var cardsWon = 0L;
        HashMap<Integer, Integer> cardsCount = new HashMap<>(); // Maps card.id to number of times card is won
        List<Integer> openList = new LinkedList<>();
        Arrays.stream(cards).parallel().forEach(card -> cardsCount.put(card.id, 1));

        for (Card card : cards) {
            openList.add(card.id);
        }

        while (!openList.isEmpty()) {
            System.out.println(openList.size());
            var cardId = openList.removeFirst();

            var cardWinnings = computeCardWinnings(Objects.requireNonNull(Arrays.stream(cards).filter(card -> card.id == cardId).findFirst().orElse(null)));
            cardWinnings.parallelStream().forEach(wonId -> {
                var count = cardsCount.get(wonId);
                cardsCount.put(wonId, count + 1);
            });
            openList.addAll(cardWinnings);
        }
        for (var value : cardsCount.values()) {
            cardsWon += value;
        }
        return cardsWon;
    }

    private static List<Integer> computeCardWinnings(Card card) {
        List<Integer> cardWinnings = new ArrayList<>();

        var winningNumber = card.scratchNumbers().stream().mapToInt(scratchNumber -> card.winningNumbers().contains(scratchNumber) ? 1 : 0).sum();
        var id = card.id + 1;
        for (int i = 0; i < winningNumber; i++) {
            cardWinnings.add(id + i);
        }

        return cardWinnings;
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
