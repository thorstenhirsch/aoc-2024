import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws IOException {

        var rules = new ArrayList<String>();
        var pages = new ArrayList<List<String>>();

        // comparator of strings is sufficient for task 1 and 2
        Comparator<String> comparator = (a, b) -> {
            if (rules.contains(a + "|" + b))
                return -1;
            if (rules.contains(b + "|" + a))
                return 1;
            return 0;
        };

        // read from input.txt
        Files.lines(Paths.get("input.txt"))
                // if line contains "|", then it is a rule, if line contains "," then it is a page
                .forEach(line -> {
                    if (line.contains("|")) {
                        rules.add(line);
                    } else if (line.contains(",")) {
                        pages.add(Arrays.stream(line.split(",")).toList());
                    } // ignore other lines
                });

        // task 1 & 2
        AtomicInteger sumCorrect = new AtomicInteger();
        AtomicInteger sumWeSorted = new AtomicInteger();
        pages.forEach(original -> {
            var sorted = new ArrayList<>(original);
            sorted.sort(comparator);

            if (original.equals(sorted)) {
                sumCorrect.addAndGet(Integer.parseInt(original.get((original.size() - 1) / 2)));
            } else {
                sumWeSorted.addAndGet(Integer.parseInt(sorted.get((sorted.size() - 1) / 2)));
            }
        });
        System.out.println("Sum of pages already sorted: " + sumCorrect);
        System.out.println("Sum of pages we had to sort: " + sumWeSorted);
    }
}
