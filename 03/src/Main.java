import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws IOException {
        var data = Files.readString(Paths.get("input.txt"));

        // part 2
        // remove matches of "don't().*do()" from data
        data = data.replaceAll("don't\\(\\)(?s).*?do\\(\\)", ""); // (?s) is for DOTALL -> including \n
        data = data.replaceAll("don't\\(\\)(?s).*?\n", ""); // if at the end

        // part 1
        var total = new AtomicInteger();
        var mulString = java.util.regex.Pattern.compile("mul\\((\\d+),(\\d+)\\)").matcher(data);
        while (mulString.find()) {
            //System.out.println("mul: " + mulString.group(1) + " * " + mulString.group(2));
            total.addAndGet(Integer.parseInt(mulString.group(1)) * Integer.parseInt(mulString.group(2)));
        }
        System.out.println("Sum: " + total.get());
    }
}