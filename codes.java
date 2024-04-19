// Solved all 3 cases

import java.util.*;

class Edge {
    int v1, v2, cap, flow;
    Edge rev;

    Edge(int V1, int V2, int Cap, int Flow) {
        v1 = V1;
        v2 = V2;
        cap = Cap;
        flow = Flow;
    }
}

class Dinic {

    public ArrayDeque<Integer> q;
    public ArrayList<Edge>[] adj;
    public int n;
    public int s;
    public int t;
    public boolean[] blocked;
    public int[] dist;
    final public static int oo = (int)1E9;

    public Dinic (int N) {
        n = N;
        s = n++;
        t = n++;
        blocked = new boolean[n];
        dist = new int[n];
        q = new ArrayDeque<>();
        adj = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            adj[i] = new ArrayList<>();
        }
    }

    public void add(int v1, int v2, int cap, int flow) {
        Edge e = new Edge(v1, v2, cap, flow);
        Edge rev = new Edge(v2, v1, 0, 0);
        e.rev = rev;
        rev.rev = e;
        adj[v1].add(e);
        adj[v2].add(rev);
    }

    public boolean bfs() {
        q.clear();
        Arrays.fill(dist, -1);
        dist[t] = 0;
        q.add(t);

        while (!q.isEmpty()) {
            int node = q.poll();
            for (Edge e : adj[node]) {
                if (e.rev.cap > e.rev.flow && dist[e.v2] == -1) {
                    dist[e.v2] = dist[node] + 1;
                    q.add(e.v2);
                }
            }
        }

        return dist[s] != -1;
    }

    public int dfs(int pos, int min) {
        if (pos == t) {
            return min;
        }

        int flow = 0;
        for (Edge e : adj[pos]) {
            int cur = 0;
            if (!blocked[e.v2] && dist[e.v2] == dist[pos] - 1 && e.cap - e.flow > 0) {
                cur = dfs(e.v2, Math.min(min - flow, e.cap - e.flow));
                e.flow += cur;
                e.rev.flow = -e.flow;
                flow += cur;
            }

            if (flow == min) {
                return flow;
            }
        }

        blocked[pos] = flow != min;
        return flow;
    }

    public int flow() {
        clear();
        int ret = 0;

        while (bfs()) {
            Arrays.fill(blocked, false);
            ret += dfs(s, oo);
        }
        return ret;
    }

    public void clear() {
        for (ArrayList<Edge> edges : adj) {
            for (Edge e : edges) {
                e.flow = 0;
            }
        }
    }
}

public class codes {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        scanner.nextLine();

        String[] drugs = new String[n];
        ArrayList<String> codes = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            drugs[i] = scanner.nextLine();
        }

        for (int i = 0; i < m; i++) {
            codes.add(scanner.nextLine());
        }

        Collections.sort(codes); // Sort the codes lexicographically

        Dinic dinic = new Dinic(n + m);

        for (int drugIndex = 0; drugIndex < n; drugIndex++) {
            for (String code : codes) {
                if (drugs[drugIndex].contains(code)) {
                    dinic.add(drugIndex, n + codes.indexOf(code), 1, 0);
                }
            }
        }

        for (int i = 0; i < n; i++) {
            dinic.add(dinic.s, i, 1, 0);
        }
        for (int i = 0; i < m; i++) {
            dinic.add(n + i, dinic.t, 1, 0);
        }

        int maxFlow = dinic.flow();

        if (maxFlow < n) {
            System.out.println("no");
        } else {
            System.out.println("yes");
            // Iterate over each drug
            for (int drugIndex = 0; drugIndex < n; drugIndex++) {
                // Find all matched codes for the current drug
                List<String> matchedCodes = new ArrayList<>();
                for (Edge edge : dinic.adj[drugIndex]) {
                    // Check if there is a flow and if the drug contains the code
                    if (edge.flow == 1 && drugs[drugIndex].contains(codes.get(edge.v2 - n))) {
                        matchedCodes.add(codes.get(edge.v2 - n));
                    }
                }
        
                // Sort the matched codes to ensure lexicographical order
                Collections.sort(matchedCodes);
        
                // Print the first (smallest) code if any match is found, else print "no"
                if (!matchedCodes.isEmpty()) {
                    System.out.println(matchedCodes.get(0));
                } else {
                    System.out.println("no");
                }
            }
        }
        scanner.close();
    }
}


