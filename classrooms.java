import java.io.*;
import java.util.*;

// Class to represent an interval with a start and end time
class Event implements Comparable<Event> {
    int start;
    int end;

    Event(int start, int end) {
        this.start = start;
        this.end = end;
    }

    // Compare events primarily by end time, then by start time
    @Override
    public int compareTo(Event other) {
        if (this.end == other.end) {
            return this.start - other.start;
        }
        return this.end - other.end;
    }
}

// Class to represent a classroom's availability with a time and an identifier
class Classroom implements Comparable<Classroom> {
    int endTime;
    int id;

    Classroom(int endTime, int id) {
        this.endTime = endTime;
        this.id = id;
    }

    // Compare classrooms by availability time, then by id
    @Override
    public int compareTo(Classroom other) {
        if (this.endTime == other.endTime) {
            return this.id - other.id;
        }
        return this.endTime - other.endTime;
    }
}

public class classrooms {

    // Custom Reader class for fast input
    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ') {
                c = read();
            }
            boolean neg = (c == '-');
            if (neg) c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (neg) return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1) buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead) fillBuffer();
            return buffer[bufferPointer++];
        }
    }

    public static void main(String[] args) throws IOException {
        Reader inputReader = new Reader();
        PrintWriter outputWriter = new PrintWriter(System.out);

        // Read the number of events and classrooms
        int numEvents = inputReader.nextInt();
        int numClassrooms = inputReader.nextInt();
        TreeSet<Classroom> availableClassrooms = new TreeSet<>();
        List<Event> eventList = new ArrayList<>();

        // Read events and add them to the list
        for (int i = 0; i < numEvents; i++) {
            eventList.add(new Event(inputReader.nextInt(), inputReader.nextInt()));
        }
        // Sort events by their end time
        Collections.sort(eventList);

        // Initialize classrooms with an identifier
        for (int i = 0; i < numClassrooms; i++) {
            availableClassrooms.add(new Classroom(0, i));
        }

        int maxScheduled = 0; // Counter for the maximum number of scheduled events
        for (Event event : eventList) {
            // Find the earliest available classroom that can be used for the event
            Classroom freeClassroom = availableClassrooms.floor(new Classroom(event.start, numClassrooms));
            if (freeClassroom != null) {
                // Remove the classroom and add it back with updated end time
                availableClassrooms.remove(freeClassroom);
                availableClassrooms.add(new Classroom(event.end + 1, freeClassroom.id));
                maxScheduled++;
            }
        }

        // Output the maximum number of events that can be scheduled
        outputWriter.println(maxScheduled);
        outputWriter.flush(); // Ensure all output is written
    }
}




