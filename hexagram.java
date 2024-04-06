import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class hexagram {
    final static int[][] SLOTS = {
        {1, 2, 3, 4}, {0, 2, 5, 7}, {0, 3, 6, 10}, 
        {7, 8, 9, 10}, {1, 5, 8, 11}, {4, 6, 9, 11}
    };

    public static int[] vals;
    public static int sum;
    public static int magicSum;

    public static void main(String[] args) {

        // Input and output file paths
        String inputFilePath = "/Users/nicholas_klos/Desktop/COP3503/input1.txt"; // Replace with your input file path
        String outputFilePath = "/Users/nicholas_klos/Desktop/COP3503/output1.txt"; // Replace with your output file path

        try {
            Scanner stdin = new Scanner(new File(inputFilePath));
            PrintWriter output = new PrintWriter(outputFilePath);

            while (stdin.hasNextInt()) {
                vals = new int[12];
                sum = 0;

                for (int i = 0; i < 12; i++) { 
                    vals[i] = stdin.nextInt();
                    sum += vals[i];
                }

                if (vals[0] == 0) {
                    break;
                }

                magicSum = sum / 3;

                if (sum % 3 == 0) {
                    int res = hex();
                    output.println(res / 12);
                } else {
                    output.println(0);
                }
            }

            stdin.close();
            output.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public static int hex() {
        if (sum % 3 != 0) return 0;
        return hexstart(new int[12], new boolean[12], 0);
    }
    
// Recursive method to count the number of solutions, given a partial permutation perm
    public static int hexstart(int[] perm, boolean[] used, int k) {
        if (k == perm.length) return 1;

        int res = 0;
        for (int i = 0; i < perm.length; i++) {
            if (used[i]) continue;
            if (!hexdec(k, vals[i], perm)) continue;
            perm[k] = i;
            used[i] = true;
            res += hexstart(perm, used, k + 1);
            used[i] = false;
        }
        return res;
    }
// Method to determine if it's safe to place a given number at a position in the hexagram
    public static boolean hexdec(int pos, int num, int[] perm) {
        for (int i = 0; i < SLOTS.length; i++) {
            if (pos != SLOTS[i][3]) continue;
            int mysum = num;
            for (int j = 0; j < SLOTS[i].length - 1; j++)
                mysum += vals[perm[SLOTS[i][j]]];
            if (mysum != magicSum) return false;
        }
        return true;
    }
}
