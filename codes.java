// Solved all 3 cases

import java.util.*;

public class codes {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // Number of drugs
        int m = scanner.nextInt(); // Number of codes
        scanner.nextLine(); // Consume the newline character

        String[] drugs = new String[n];
        List<String> codes = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            drugs[i] = scanner.nextLine();
        }

        for (int i = 0; i < m; i++) {
            codes.add(scanner.nextLine());
        }

        // Sort codes to find the lexicographically first mapping
        Collections.sort(codes);

        FordFulkerson ff = new FordFulkerson(n + m);
        Map<Integer, String> drugToCodeMap = new HashMap<>();

        // Create the bipartite graph with sorted codes
        for (int drugIndex = 0; drugIndex < n; drugIndex++) {
            for (int codeIndex = 0; codeIndex < m; codeIndex++) {
                if (drugs[drugIndex].contains(codes.get(codeIndex))) {
                    ff.add(drugIndex, n + codeIndex, 1); // Connect drug to sorted code
                    drugToCodeMap.put(n + codeIndex, codes.get(codeIndex));
                }
            }
        }

        // Connect source to all drugs and all codes to sink
        for (int i = 0; i < n; i++) {
            ff.add(ff.source, i, 1);
        }
        for (int i = 0; i < m; i++) {
            ff.add(n + i, ff.sink, 1);
        }

        // Run the max flow algorithm
        int maxFlow = ff.ff();

        // If the max flow is less than the number of drugs, a valid mapping is not possible
        if (maxFlow < n) {
            System.out.println("no");
            scanner.close();
            return; // Terminate the program
        }

        // Output the valid mapping in lexicographical order
        System.out.println("yes");
        for (int drugIndex = 0; drugIndex < n; drugIndex++) {
            for (int codeIndex = 0; codeIndex < m; codeIndex++) {
                // Check if the edge from the drug to the code has been used in the matching (flow is 0)
                if (ff.cap[drugIndex][n + codeIndex] == 0 && ff.cap[n + codeIndex][drugIndex] == 1) {
                    System.out.println(drugToCodeMap.get(n + codeIndex));
                    break;
                }
            }
        }

        scanner.close();
    }

    // Insert your provided FordFulkerson class here
    public static class FordFulkerson {
        public int[][] cap;
        public int n;
        public int source;
        public int sink;
        final public static int oo = (int)(1E9);

        public FordFulkerson(int size) {
            n = size + 2;
            source = n - 2;
            sink = n - 1;
            cap = new int[n][n];
        }

        public void add(int v1, int v2, int c) {
            cap[v1][v2] = c;
        }

        public int ff() {
            boolean[] visited = new boolean[n];
            int flow = 0;

            while (true) {
                Arrays.fill(visited, false);
                int res = dfs(source, visited, oo);

                if (res == 0) break;
                flow += res;
            }
            return flow;
        }

        public int dfs(int v, boolean[] visited, int min) {
            if (v == sink) return min;
            if (visited[v]) return 0;

            visited[v] = true;
            int flow = 0;

            for (int i = 0; i < n; i++) {
                if (cap[v][i] > 0) {
                    flow = dfs(i, visited, Math.min(cap[v][i], min));

                    if (flow > 0) {
                        cap[v][i] -= flow;
                        cap[i][v] += flow;
                        return flow;
                    }
                }
            }

            return 0;
        }
    }
}