//solved both problems from assignment
import java.io.*;
import java.util.*;

public class gcdsum {
    // Global variables to store the input numbers, dynamic programming table, and segment starts
    private static int[] input;
    private static int[][] dp;
    private static int[][] nextSegmentStart;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out);

        // Read the input size n and number of partitions k
        String[] firstLine = reader.readLine().split(" ");
        int n = Integer.parseInt(firstLine[0]);
        int k = Integer.parseInt(firstLine[1]);

        // Read the list of numbers
        input = Arrays.stream(reader.readLine().split(" "))
                      .mapToInt(Integer::parseInt)
                      .toArray();

        // Initialize DP and tracking arrays with -1 (for uncomputed states)
        dp = new int[n + 1][k + 1];
        nextSegmentStart = new int[n + 1][k + 1];
        for (int[] row : dp) Arrays.fill(row, -1);

        // Find the maximum GCD sum for the entire list
        int maxGcdSum = findMaxGcdSum(0, k);

        // Output the maximum GCD sum
        writer.println(maxGcdSum);
        
        // Reconstruct and output the starting indices of each segment
        List<Integer> partitions = new ArrayList<>();
        reconstructPartitions(0, k, partitions);
        for (int index : partitions) {
            writer.print((index + 1) + " "); // Convert to 1-based indexing
        }
        writer.println();

        reader.close();
        writer.close();
    }

    // Recursively computes the maximum GCD sum of segments using dynamic programming
    private static int findMaxGcdSum(int index, int remainingSegments) {
        if (remainingSegments == 0) return index == input.length ? 0 : Integer.MIN_VALUE;
        if (index == input.length) return Integer.MIN_VALUE;
        if (dp[index][remainingSegments] != -1) return dp[index][remainingSegments];

        int currentGcd = 0;
        int maxSum = Integer.MIN_VALUE;
        for (int end = index; end < input.length; end++) {
            currentGcd = gcd(currentGcd, input[end]);
            int restMaxSum = findMaxGcdSum(end + 1, remainingSegments - 1);
            if (restMaxSum != Integer.MIN_VALUE) {
                int sum = currentGcd + restMaxSum;
                if (sum > maxSum) {
                    maxSum = sum;
                    nextSegmentStart[index][remainingSegments] = end + 1;
                }
            }
        }
        dp[index][remainingSegments] = maxSum;
        return maxSum;
    }

    // Recursively reconstructs the partition indices
    private static void reconstructPartitions(int index, int remainingSegments, List<Integer> partitions) {
        if (remainingSegments == 0) return;
        partitions.add(index);
        reconstructPartitions(nextSegmentStart[index][remainingSegments], remainingSegments - 1, partitions);
    }

    // Utility function to compute the GCD of two numbers
    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}




