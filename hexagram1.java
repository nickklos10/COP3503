import java.util.*;

public class hexagram1 {

    // Define the slots for the lines in the hexagram that need to sum up to the magic sum
    final static int[][] SLOTS = {
        {1, 2, 3, 4}, {0, 2, 5, 7}, {0, 3, 6, 10}, 
        {7, 8, 9, 10}, {1, 5, 8, 11}, {4, 6, 9, 11}
    };

    // Array to hold the values of each vertex of the hexagram
    public static int[] vals;
    // Variable to hold the sum of all numbers inputted
    public static int sum;
    // Variable to hold the required magic sum for each line
    public static int magicSum;
    
    // Main method to handle input and initiate solving the hexagram puzzle
    public static void main(String[] args) {
    
        Scanner stdin = new Scanner(System.in);
        vals = new int[12];
        sum = 0;
        
        // Read initial data
        for (int i = 0; i < 12; i++) {
            vals[i] = stdin.nextInt();
            sum += vals[i];
        }
        
        // Calculate what the magic sum needs to be
        magicSum = sum / 3;
        
        // Loop until a line of zeros is encountered
        while (vals[0] != 0) {
        
            // Solve the puzzle and divide the result by 12 to account for rotation and reflection
            int res = solve();
            System.out.println(res / 12);
        
            // Read the next case
            sum = 0;
            for (int i = 0; i < 12; i++) {
                vals[i] = stdin.nextInt();
                sum += vals[i];
            }
            // Recalculate the magic sum for the new case
            magicSum = sum / 3;
        }
    
        stdin.close();
    }
    
    // Wrapper method to initiate the solving process, handling cases with impossible sums
    public static int solve() {
        
        // Return 0 if the magic sum is not an integer (impossible case)
        if (sum % 3 != 0) return 0;
        
        // Start the recursive backtracking with initial parameters
        return go(new int[12], new boolean[12], 0);
    }
    
    // Recursive method to count the number of solutions, given a partial permutation perm
    public static int go(int[] perm, boolean[] used, int k) {
        
        // Base case: if all slots are filled, we have a complete solution
        if (k == perm.length) return 1;
        
        int res = 0;
        
        // Attempt to place a number in slot k
        for (int i = 0; i < perm.length; i++) {
            
            // Skip if the number has already been used
            if (used[i]) continue;
            
            // Use backtracking to check if it's safe to place the number
            if (!canPlace(k, vals[i], perm)) continue;
            
            // Place the number in the permutation and mark it as used
            perm[k] = i;
            used[i] = true;
            
            // Recursively count solutions with the number placed
            res += go(perm, used, k + 1);
            
            // Backtrack: unmark the number as used
            used[i] = false;
        }
        
        // Return the total number of solutions found
        return res;
    }
    
    // Method to determine if it's safe to place a given number at a position in the hexagram
    public static boolean canPlace(int pos, int num, int[] perm) {
        
        // Check if placing the number completes any row
        for (int i = 0; i < SLOTS.length; i++) {
            
            // Skip if the current position does not complete this row
            if (pos != SLOTS[i][3]) continue;
            
            // Calculate the sum of the row with the new number included
            int mysum = num;
            for (int j = 0; j < SLOTS[i].length - 1; j++)
                mysum += vals[perm[SLOTS[i][j]]];
            
            // If the row sum does not equal the magic sum, the number cannot be placed
            if (mysum != magicSum) return false;
        }
        
        // If all checks pass, it's safe to place the number
        return true;
    }
}


