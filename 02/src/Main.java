import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try (var lines = Files.lines(Paths.get("input.txt"))) {
            var safeLevels = lines.parallel()
                    .map(line -> Arrays.stream(line.trim().split("\\s+"))
                            .mapToInt(Integer::parseInt)
                            .toArray())
                    //.filter(intArr -> slightlyIncreasing(intArr) || slightlyDecreasing(intArr))
                    .filter(intArr -> slightlyIncreasingWithDampener(intArr, false) || slightlyDecreasingWithDampener(intArr, false))
                    .count();
            System.out.println(safeLevels);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean slightlyIncreasing(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] - arr[i - 1] < 1 || arr[i] - arr[i - 1] > 3) {
                return false;
            }
        }
        return true;
    }

    private static boolean slightlyDecreasing(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] - arr[i] < 1 || arr[i - 1] - arr[i] > 3) {
                return false;
            }
        }
        return true;
    }

    private static boolean slightlyIncreasingWithDampener(int[] arr, boolean secondChance) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] - arr[i - 1] < 1 || arr[i] - arr[i - 1] > 3) {
                if (!secondChance) {
                    return slightlyIncreasingWithDampener(duplicateWithout(arr, i - 1), true)
                            || slightlyIncreasingWithDampener(duplicateWithout(arr, i), true);
                }
                return false;
            }
        }
        return true;
    }

    private static boolean slightlyDecreasingWithDampener(int[] arr, boolean secondChance) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] - arr[i] < 1 || arr[i - 1] - arr[i] > 3) {
                if (!secondChance) {
                    return slightlyDecreasingWithDampener(duplicateWithout(arr, i - 1), true)
                            || slightlyDecreasingWithDampener(duplicateWithout(arr, i), true);
                }
                return false;
            }
        }
        return true;
    }

    private static int[] duplicateWithout(int[] arr, int index) {
        var newArr = new int[arr.length - 1];
        for (int i = 0, j = 0; i < arr.length; i++) {
            if (i == index) {
                continue;
            }
            newArr[j++] = arr[i];
        }
        return newArr;
    }
}