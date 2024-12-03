import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        var file = new java.io.File("input.txt");
        var left = new ArrayList<Integer>();
        var right = new ArrayList<Integer>();

        // the following code only works if each line has a number in both columns
        try (var scanner = new java.util.Scanner(file)) {
            while (scanner.hasNextInt()) {
                left.add(scanner.nextInt());
                right.add(scanner.nextInt());
            }
        } catch (java.io.FileNotFoundException e) {
            System.out.println("File not found: " + file);
        }

        // diff: sum of absolute differences of each pair
        var orderedLeft = left.stream().sorted().toList();
        var orderedRight = right.stream().sorted().toList();

        var sumOfDiffs = IntStream.range(0, left.size())
                .map(i -> abs(orderedLeft.get(i) - orderedRight.get(i)))
                .sum();
        System.out.println(sumOfDiffs);

        // similarity: sum of all (i * occurence of each i)
        var occurences = new java.util.HashMap<Integer, Integer>();
        for (var i : right) {
            occurences.put(i, occurences.getOrDefault(i, 0) + 1);
        }
        var similarity = left.stream()
                .reduce(0, (acc, i) -> acc + i * occurences.getOrDefault(i, 0));
        System.out.println(similarity);
    }
}