import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        var XMAS = new char[]{'X', 'M', 'A', 'S'};

        // read from input.txt into matrix of chars
        char[][] matrix = Files.lines(Paths.get("input.txt"))
                .map(String::toCharArray)
                .toList()
                .toArray(new char[0][]);

        // check if chars in matrix equal [ "X", "M", "A", "S" ] in any direction
        var counter = 0;
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                if (Arrays.equals(xmasHorizontal(matrix, x, y), XMAS))
                    counter++;
                if (Arrays.equals(xmasDiagonalDown(matrix, x, y), XMAS))
                    counter++;
                if (Arrays.equals(xmasVerticalDown(matrix, x, y), XMAS))
                    counter++;
                if (Arrays.equals(xmasDiagonalDownBack(matrix, x, y), XMAS))
                    counter++;
                if (Arrays.equals(xmasHorizontalBack(matrix, x, y), XMAS))
                    counter++;
                if (Arrays.equals(xmasDiagonalUpBack(matrix, x, y), XMAS))
                    counter++;
                if (Arrays.equals(xmasVerticalUp(matrix, x, y), XMAS))
                    counter++;
                if (Arrays.equals(xmasDiagonalUp(matrix, x, y), XMAS))
                    counter++;
            }
        }
        System.out.println(counter);
        
        // part 2
        var MAS = new char[]{'M', 'A', 'S'};
        counter = 0;
        for (int y = 1; y < matrix.length - 1; y++) {
            for (int x = 1; x < matrix[y].length - 1; x++) {
                var match = Arrays.stream(masAllDiagonal(matrix, x, y))
                        .filter(arr -> Arrays.equals(arr, MAS))
                        .count() == 2;
                if (match) counter++;
            }
        }
        System.out.println(counter);
    }

    // part 1 - coordinates are the begin ("X")
    private static char[] xmasHorizontal(char[][] m, int x, int y) {
        if (x + 3 > m[y].length - 1) { return new char[0]; }
        return new char[]{ m[y][x], m[y][x + 1], m[y][x + 2], m[y][x + 3] };
    }

    private static char[] xmasDiagonalDown(char[][] m, int x, int y) {
        if (x + 3 > m[y].length - 1 || y + 3 > m.length - 1) { return new char[0]; }
        return new char[]{ m[y][x], m[y + 1][x + 1], m[y + 2][x + 2], m[y + 3][x + 3] };
    }

    private static char[] xmasVerticalDown(char[][] m, int x, int y) {
        if (y + 3 > m.length - 1) { return new char[0]; }
        return new char[]{ m[y][x], m[y + 1][x], m[y + 2][x], m[y + 3][x] };
    }

    private static char[] xmasDiagonalDownBack(char[][] m, int x, int y) {
        if (x - 3 < 0 || y + 3 > m.length - 1) { return new char[0]; }
        return new char[]{ m[y][x], m[y + 1][x - 1], m[y + 2][x - 2], m[y + 3][x - 3] };
    }

    private static char[] xmasHorizontalBack(char[][] m, int x, int y) {
        if (x - 3 < 0) { return new char[0]; }
        return new char[]{ m[y][x], m[y][x - 1], m[y][x - 2], m[y][x - 3] };
    }

    private static char[] xmasDiagonalUpBack(char[][] m, int x, int y) {
        if (x - 3 < 0 || y - 3 < 0) { return new char[0]; }
        return new char[]{ m[y][x], m[y - 1][x - 1], m[y - 2][x - 2], m[y - 3][x - 3] };
    }

    private static char[] xmasVerticalUp(char[][] m, int x, int y) {
        if (y - 3 < 0) { return new char[0]; }
        return new char[]{ m[y][x], m[y - 1][x], m[y - 2][x], m[y - 3][x] };
    }

    private static char[] xmasDiagonalUp(char[][] m, int x, int y) {
        if (x + 3 > m[y].length - 1 || y - 3 < 0) { return new char[0]; }
        return new char[]{ m[y][x], m[y - 1][x + 1], m[y - 2][x + 2], m[y - 3][x + 3] };
    }

    // part 2 - coordinates are the middle ("A")
    private static char[][] masAllDiagonal(char[][] m, int x, int y) {
        return new char[][]{
                { m[y - 1][x - 1], m[y][x], m[y + 1][x + 1] },
                { m[y - 1][x + 1], m[y][x], m[y + 1][x - 1] },
                { m[y + 1][x + 1], m[y][x], m[y - 1][x - 1] },
                { m[y + 1][x - 1], m[y][x], m[y - 1][x + 1] }
        };
    }
}
