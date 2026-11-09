package tiny;

import java.util.ArrayList;

/** Minimal smoke test: runs the sample TINY program from the original screenshots and checks token count. */
public class ScannerSmokeTest {
    public static void main(String[] args) {
        String sample =
                "{ Sample program in TINY language\n" +
                "  - computes factorial\n" +
                "}\n" +
                "read x; {input an integer }\n" +
                "if 0 < x then { don't compute if x <= 0 }\n" +
                "fact := 1;\n" +
                "repeat\n" +
                "fact := fact * x;\n" +
                "x := x - 1;\n" +
                "until x = 0;\n" +
                "write fact { output factorial of x };\n" +
                "end";

        Scanner scanner = new Scanner(sample);
        ArrayList<Token> tokens = scanner.analyze();
        System.out.println(scanner);

        if (tokens.size() != 34) {
            throw new AssertionError("Expected 34 tokens, got " + tokens.size());
        }
        if (tokens.get(0).getToken() != TokenType.READ) {
            throw new AssertionError("First token should be READ");
        }
        System.out.println("SMOKE TEST PASSED");
    }
}
