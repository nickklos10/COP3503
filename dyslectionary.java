import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class dyslectionary {

    static class ReversedString implements Comparable<ReversedString> {
        String value;

        ReversedString(String value) {
            this.value = value;
        }

        @Override
        public int compareTo(ReversedString other) {
            return this.value.compareTo(other.value);
        }

        @Override
        public String toString() {
            return new StringBuilder(value).reverse().toString();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<ReversedString> dyslectionary = new ArrayList<>();
        int maxlength = 0;

        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            if (s.isEmpty()) {
                printOutput(dyslectionary, maxlength);
                System.out.println();
                maxlength = 0;
                dyslectionary.clear();
            } else {
                s = new StringBuilder(s).reverse().toString();
                dyslectionary.add(new ReversedString(s));
                maxlength = Math.max(maxlength, s.length());
            }
        }

        printOutput(dyslectionary, maxlength);
    }

    private static void printOutput(ArrayList<ReversedString> dyslectionary, int maxlength) {
        Collections.sort(dyslectionary);

        for (ReversedString rs : dyslectionary) {
            for (int i = 0; i < (maxlength - rs.value.length()); ++i) {
                System.out.print(" ");
            }
            System.out.println(rs);
        }
    }
}









