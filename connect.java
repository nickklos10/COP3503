import java.util.Scanner;
import java.math.BigInteger;

public class connect {

    // A helper class to manage the union-find data structure
    private static class UnionFind {
        private int[] parent; // Parent links for each element
        private int[] size;   // Size of each component

        // Constructor initializes each computer as a separate component
        public UnionFind(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        // Find the root of the component that element x belongs to
        public int find(int x) {
            if (x != parent[x]) {
                parent[x] = find(parent[x]); // Path compression
            }
            return parent[x];
        }

        // Merge the sets that x and y belong to
        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) {
                if (size[rootX] < size[rootY]) {
                    int temp = rootX;
                    rootX = rootY;
                    rootY = temp;
                }
                parent[rootY] = rootX; // Union by size
                size[rootX] += size[rootY];
            }
        }

        // Calculate the average connectivity of the network
        public String calculateAverageConnectivity(int n) {
            BigInteger total = BigInteger.ZERO;
            int components = 0;
            for (int i = 0; i < n; i++) {
                if (i == parent[i]) {
                    // Add the square of the component's size to the total
                    total = total.add(BigInteger.valueOf(size[i]).multiply(BigInteger.valueOf(size[i])));
                    components++; // Increment the number of components
                }
            }
            BigInteger numerator = total; // Total of sizes squared
            BigInteger denominator = BigInteger.valueOf(components); // Number of components
            BigInteger gcd = numerator.gcd(denominator); // Greatest common divisor for reduction
            // Return the fraction in lowest terms
            return numerator.divide(gcd).toString() + "/" + denominator.divide(gcd).toString();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // Number of computers
        int m = scanner.nextInt(); // Number of operations
        UnionFind uf = new UnionFind(n);

        for (int i = 0; i < m; i++) {
            int operation = scanner.nextInt();
            if (operation == 1) {
                // Connect operation: union two computers
                int a = scanner.nextInt();
                int b = scanner.nextInt();
                uf.union(a - 1, b - 1);
            } else if (operation == 2) {
                // Query operation: print the average connectivity
                System.out.println(uf.calculateAverageConnectivity(n));
            }
        }
        scanner.close(); // Close the scanner
    }
}





