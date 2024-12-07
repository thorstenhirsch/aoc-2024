import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        // read from input.txt
        var rules = new HashMap<Integer, List<Integer>>();
        var pages = new ArrayList<List<Integer>>();

        Files.lines(Paths.get("input.txt"))
                // if line contains "|", then it is a rule, if line contains "," then it is a page
                .forEach(line -> {
                    if (line.contains("|")) {
                        var parts = line.split("\\|");
                        var key = Integer.parseInt(parts[0]);
                        var value = Integer.parseInt(parts[1]);
                        if (rules.containsKey(key)) {
                            rules.get(key).add(value);
                        } else {
                            var initialList = new ArrayList<Integer>();
                            initialList.add(value);
                            rules.put(key, initialList);
                        }
                    } else if (line.contains(",")) {
                        var pageList = Arrays.stream(line.split(","))
                                .mapToInt(Integer::parseInt)
                                .boxed()
                                .toList();
                        //System.out.println("Page list: " + pageList);
                        pages.add(pageList);
                    } else if (line.isEmpty()) {
                        // ignore empty lines
                    } else {
                        throw new IllegalArgumentException("Invalid input: " + line);
                    }
                });

        // so far, so good, but the next part is ugly
        var sumOfMiddle = 0;
        for (var pageList : pages) {
            try {
                System.out.print("Checking pages... ");
                pageList.forEach(page -> {
                    System.out.print(page + " ");
                    if (rules.containsKey(page)) {
                        var mustComeAfter = rules.get(page);
                        var beforePage = true;
                        for (var p : pageList) {
                            if (p.equals(page)) {
                                beforePage = false;
                                continue;
                            }
                            if (beforePage && mustComeAfter.contains(p))
                                throw new IllegalStateException("-> Invalid order: " + page + " must come before " + p);
                        }
                    }
                });
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
                continue;
            }
            System.out.println("-> OK");
            sumOfMiddle += pageList.get((pageList.size() - 1) / 2);
        }
        System.out.println("Sum of middle elements: " + sumOfMiddle);
    }
}
