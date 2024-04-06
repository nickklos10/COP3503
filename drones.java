import java.util.*;
import java.io.*;

public class drones {
    private static final int GRID_SIZE = 8;
    private static boolean[][] noFlyZones = new boolean[GRID_SIZE][GRID_SIZE];
    private static int[][] destinations;
    private static int numDrones;

    private static class State {
        int[] dronePositions;
        int stepCount;

        State(int[] dronePositions, int stepCount) {
            this.dronePositions = Arrays.copyOf(dronePositions, dronePositions.length);
            this.stepCount = stepCount;
        }

        // Convert drone positions to an integer for hashing in a set
        int encode() {
            int code = 0;
            for (int i = 0; i < dronePositions.length; i++) {
                code |= dronePositions[i] << (6 * i);
            }
            return code;
        }
    }

    private static boolean isValid(int droneId, int x, int y, int[] dronePositions) {
        if (x < 0 || x >= GRID_SIZE || y < 0 || y >= GRID_SIZE || noFlyZones[x][y]) {
            return false; // Check bounds and no-fly zones
        }
        for (int i = 0; i < numDrones; i++) {
            if (i != droneId && dronePositions[i] == x * GRID_SIZE + y) {
                return false; // Another drone is in the way
            }
            if (i != droneId && destinations[i][0] == x && destinations[i][1] == y) {
                return false; // It's another drone's destination
            }
        }
        return true; // The position is valid for this drone
    }
    

    public static int bfs(int[][] drones) {
        Queue<State> queue = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();
        int[] startPositions = new int[numDrones];
        for (int i = 0; i < numDrones; i++) {
            startPositions[i] = drones[i][0] * GRID_SIZE + drones[i][1];
        }
        State startState = new State(startPositions, 0);
        queue.offer(startState);
        visited.add(startState.encode());

        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            if (allDronesDelivered(currentState.dronePositions)) {
                return currentState.stepCount;
            }

            for (int[] moves : new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}) { // N, S, W, E
                int[] newPositions = Arrays.copyOf(currentState.dronePositions, currentState.dronePositions.length);
                boolean validMove = false;
                for (int i = 0; i < numDrones; i++) {
                    int x = newPositions[i] / GRID_SIZE;
                    int y = newPositions[i] % GRID_SIZE;
                    int newX = x + moves[0];
                    int newY = y + moves[1];
            
                    if (x == destinations[i][0] && y == destinations[i][1]) {
                        // Drone has reached its destination, don't move it
                        continue;
                    }
            
                    if (isValid(i, newX, newY, newPositions)) {
                        newPositions[i] = newX * GRID_SIZE + newY;
                        validMove = true;
                    }
                }
            
                if (validMove) {
                    State newState = new State(newPositions, currentState.stepCount + 1);
                    int encodedNewState = newState.encode();
                    if (!visited.contains(encodedNewState)) {
                        visited.add(encodedNewState);
                        queue.offer(newState);
                    }
                }
            }

    }
    return -1; // Not possible to deliver all foods
}

    private static boolean allDronesDelivered(int[] dronePositions) {
        for (int i = 0; i < numDrones; i++) {
            int x = dronePositions[i] / GRID_SIZE;
            int y = dronePositions[i] % GRID_SIZE;
            if (x != destinations[i][0] || y != destinations[i][1]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("inputdrones.txt"));
        numDrones = sc.nextInt();
        destinations = new int[numDrones][2];
        int[][] drones = new int[numDrones][2];

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                String cell = sc.next();
                if (cell.charAt(0) == 'D') {
                    int droneNumber = cell.charAt(1) - '1';
                    drones[droneNumber][0] = i;
                    drones[droneNumber][1] = j;
                } else if (cell.charAt(0) == 'G') {
                    int groupNumber = cell.charAt(1) - '1';
                    destinations[groupNumber][0] = i;
                    destinations[groupNumber][1] = j;
                } else if (cell.equals("XX")) {
                    noFlyZones[i][j] = true;
                }
            }
        }
        sc.close();

        int result = bfs(drones);
        System.out.println(result);
    }
}



