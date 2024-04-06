import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class destroy {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfStudents = Integer.parseInt(scanner.nextLine());
        HashMap<Character, HashMap<String, Integer>> nameCounts = new HashMap<>();

        // Reading student names and counting unique names with the same first letter
        for (int i = 0; i < numberOfStudents; i++) {
            String name = scanner.nextLine();
            char firstLetter = name.charAt(0);
            nameCounts.putIfAbsent(firstLetter, new HashMap<>());
            HashMap<String, Integer> namesWithSameFirstLetter = nameCounts.get(firstLetter);
            namesWithSameFirstLetter.put(name, namesWithSameFirstLetter.getOrDefault(name, 0) + 1);
        }

        long totalPairs = 0;

        // Calculating the number of pairs for each first letter, taking duplicates into account
        for (HashMap<String, Integer> names : nameCounts.values()) {
            long sum = 0; 
            long duplicates = 0; 

            for (int count : names.values()) {
                sum += count;
                duplicates += count > 1 ? count * (count - 1) : 0;
            }

            totalPairs += sum * (sum - 1) - duplicates;
        }

        System.out.println(totalPairs);
        scanner.close();
    }
}



