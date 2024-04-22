//solved both problems from assignment
import java.io.*;
import java.util.*;

public class subseqsum {
    private static int[] input; 
    private static long[] dp; 
    private static int[] predecessor; 

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out);

        // Read the input size and the smoothness factor k
        String[] firstLine = reader.readLine().split(" ");
        int n = Integer.parseInt(firstLine[0]);
        int k = Integer.parseInt(firstLine[1]);

        // Read the sequence
        input = Arrays.stream(reader.readLine().split(" "))
                      .mapToInt(Integer::parseInt)
                      .toArray();

        // Initialize arrays
        dp = new long[n];
        predecessor = new int[n];
        Arrays.fill(predecessor, -1); 

        long maxSum = input[0];
        int lastIndex = 0; 

        // Fill the dp array with the input values for the base cases
        for (int i = 0; i < n; i++) {
            dp[i] = input[i];
        }

        // Compute the dp values and track the predecessors
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (Math.abs(input[i] - input[j]) <= k && dp[j] + input[i] > dp[i]) {
                    dp[i] = dp[j] + input[i];
                    predecessor[i] = j; 
                }
            }
            // Update maxSum and lastIndex if a new maximum is found
            if (dp[i] > maxSum) {
                maxSum = dp[i];
                lastIndex = i;
            }
        }


        writer.println(maxSum);

        // Reconstruct and print the k-smooth subsequence
        List<Integer> sequence = new ArrayList<>();
        for (int i = lastIndex; i != -1; i = predecessor[i]) {
            sequence.add(i + 1); 
        }
        Collections.reverse(sequence); 

        // Print the sequence indices
        for (int index : sequence) {
            writer.print(index + " ");
        }
        writer.println();

        writer.flush();
        reader.close();
        writer.close();
    }
}



