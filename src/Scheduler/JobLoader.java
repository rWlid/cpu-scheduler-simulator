package Scheduler;
import java.util.concurrent.*;
import java.io.*;
public class JobLoader extends Thread {
    private BlockingQueue<PCB> jobQueue;

    public JobLoader(BlockingQueue<PCB> jobQueue) {
        this.jobQueue = jobQueue;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader("job.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("[:;]");
                int id = Integer.parseInt(parts[0]);
                int burst = Integer.parseInt(parts[1]);
                int priority = Integer.parseInt(parts[2]);
                int memory = Integer.parseInt(parts[3]);
                jobQueue.put(new PCB(id, burst, priority, memory));
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
