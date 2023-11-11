package instrumentation;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class Instrumentation {
    private static Instrumentation instance = new Instrumentation(); // Create singleton object
    private boolean isActive;           // State of singleton obj
    private Map<String, Long> timings;  // key: comment, value: time
    private String logs;                // To create structured logs
    private int tabs;                   // Determines how many tabs per line
    private Long totalStart;            // Used to calculate total time
    private Long totalEnd;

    // Constructor is private so class cannot be instantiated
    private Instrumentation() {
        this.timings = new LinkedHashMap<>();   
        this.logs = "";  
        this.isActive = false;
        this.tabs = 0;
        this.totalStart = Long.MAX_VALUE;
        this.totalEnd = Long.MIN_VALUE;

    }

    // Get the only object available
    public static Instrumentation getInstance(){
        return instance;
    }

    public void activate(boolean onoff) {
        isActive = onoff;
    }

    public void startTiming(String comment) {
        if (!isActive) return;

        Long currentTime = System.currentTimeMillis();
        
        if (currentTime < totalStart) totalStart = currentTime;

        for (int i = 0; i < tabs; i++) {
            logs += "|\t";
        }

        logs += String.format("STARTTIMING: %s\n", comment);
        timings.put(comment, currentTime);

        tabs++;
    }

    public void stopTiming(String comment) {
        if (!isActive) return;

        tabs--;
        for (int i = 0; i < tabs; i++) {
            logs += "|\t";
        }

        Long startTime = timings.remove(comment);
        if (startTime == null) {
            logs += String.format("ERROR: Timing for '%s' was not started.\n", comment);
            return;
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        logs += String.format("STOPTIMING: %s %dms\n", comment, duration);

        if (endTime > totalEnd) totalEnd = endTime;
    }

    public void comment(String comment) {
        if (!isActive) return;
        
        for (int i = 0; i < tabs; i++) {
            logs += "|\t";
        }

        logs += String.format("COMMENT: %s\n", comment);
    }

    public void dump() {
        if (!isActive) return;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
        LocalDateTime now = LocalDateTime.now();
        String filename = "instrumentation" + dtf.format(now) + ".log";
        dump(filename);
    }
    public void dump(String filename) {
        if (!isActive) return;

        Long totalTime = totalEnd - totalStart;

        logs += String.format("TOTAL TIME: %dms\n", totalTime);

        try {
            FileWriter file = new FileWriter(filename);
            file.write(logs);
            file.close();
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}

