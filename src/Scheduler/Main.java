package Scheduler;
import SchedulingAlgorithms.*;
import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<PCB> jobQueue = new LinkedBlockingQueue<>();
        BlockingQueue<PCB> readyQueue = new LinkedBlockingQueue<>();
        MemoryManager memoryManager = new MemoryManager();

        JobLoader loader = new JobLoader(jobQueue);
        loader.start();

        Thread readyLoader = new Thread(() -> {
            while (true) {
                try {
                    PCB job = jobQueue.take();
                    if (memoryManager.allocateMemory(job.memoryRequired)) {
                        job.state = "READY";
                        readyQueue.put(job);
                    } else {
                        jobQueue.put(job);
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        readyLoader.start();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose Scheduling Algorithm:\n1. FCFS\n2. Round Robin\n3. Priority");
        int choice = scanner.nextInt();
        scanner.close();

        Thread.sleep(2000); // Allow queues to populate

        List<PCB> jobs = new ArrayList<>();
        readyQueue.drainTo(jobs);

        switch (choice) {
            case 1:
                new FCFS().run(jobs);
                break;
            case 2:
                new RoundRobin().run(jobs);
                break;
            case 3:
                new PriorityScheduling().run(jobs);
                break;
            default:
                System.out.println("Invalid choice.");
        }

        loader.interrupt();
        readyLoader.interrupt();
    }
}