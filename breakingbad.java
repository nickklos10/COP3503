import java.io.*;
import java.util.*;

// Class name changed to better reflect its functionality
public class breakingbad {

    InputReader inputReader;

    // Entry method for the program
    public void execute() {
        inputReader = new InputReader(System.in);
        int itemCount = inputReader.nextInt();
        Item[] items = new Item[itemCount];
        HashMap<String, Item> itemMap = new HashMap<>();

        // Reading items and storing them in a map
        for (int i = 0; i < itemCount; i++) {
            String itemName = inputReader.next();
            items[i] = new Item(itemName);
            itemMap.put(itemName, items[i]);
        }

        int pairCount = inputReader.nextInt();

        // Linking items based on pairs
        for (int i = 0; i < pairCount; i++) {
            Item item1 = itemMap.get(inputReader.next());
            Item item2 = itemMap.get(inputReader.next());
            item1.link(item2);
        }

        boolean classificationPossible = true;

        // Attempting to classify each item
        for (Item item : items) {
            if (item.type == -1) {
                classificationPossible &= classifyItem(item, 0);
            }
        }

        // Output based on classification success or failure
        if (classificationPossible) {
            boolean isFirst = true;
            for (Item item : items) {
                if (item.type == 0) {
                    if (!isFirst) {
                        inputReader.print(" ");
                    }
                    inputReader.print(item.name);
                    isFirst = false;
                }
            }
            inputReader.println();
            isFirst = true;
            for (Item item : items) {
                if (item.type == 1) {
                    if (!isFirst) {
                        inputReader.print(" ");
                    }
                    inputReader.print(item.name);
                    isFirst = false;
                }
            }
        } else {
            inputReader.println("impossible");
        }

        inputReader.flush();
        inputReader.close();
    }

    // Method to classify items using BFS
    public boolean classifyItem(Item currentItem, int currentType) {
        currentItem.type = currentType;
        for (Item linkedItem : currentItem.linkedItems) {
            if (linkedItem.type != -1 && linkedItem.type != (currentType ^ 1)) {
                return false;
            } else if (linkedItem.type == -1) {
                classifyItem(linkedItem, currentType ^ 1);
            }
        }
        return true;
    }

    // Main method to trigger the program
    public static void main(String[] args) {
        new breakingbad().execute();
    }

    // Inner class to represent items
    private class Item {

        String name;
        int type = -1;
        ArrayList<Item> linkedItems = new ArrayList<>();

        public Item(String name) {
            this.name = name;
        }

        // Link this item with another item
        public void link(Item other) {
            linkedItems.add(other);
            other.linkedItems.add(this);
        }

        @Override
        public String toString() {
            return name + " " + type;
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }

    // Inner class to handle input and output efficiently
    private class InputReader extends PrintWriter {

        private BufferedReader reader;
        private String line;
        private StringTokenizer tokenizer;
        private String token;

        public InputReader(InputStream inputStream) {
            super(new BufferedOutputStream(System.out));
            reader = new BufferedReader(new InputStreamReader(inputStream));
        }

        public InputReader(InputStream inputStream, OutputStream outputStream) {
            super(new BufferedOutputStream(outputStream));
            reader = new BufferedReader(new InputStreamReader(inputStream));
        }

        public int nextInt() {
            return Integer.parseInt(nextToken());
        }

        public String next() {
            return nextToken();
        }

        private String peekToken() {
            if (token == null)
                try {
                    while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                        line = reader.readLine();
                        if (line == null) return null;
                        tokenizer = new StringTokenizer(line);
                    }
                    token = tokenizer.nextToken();
                } catch (IOException e) {
                }
            return token;
        }

        private String nextToken() {
            String answer = peekToken();
            token = null;
            return answer;
        }
    }
}
