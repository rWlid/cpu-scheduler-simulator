package Scheduler;

import SchedulingAlgorithms.*;
import java.util.*;

public class Main {
	public static void main(String[] args) throws InterruptedException {
        Queue<PCB> jobQueue = new LinkedList<>();
        Queue<PCB> readyQueue = new LinkedList<>();
        MemoryManager memoryManager = new MemoryManager();

        JobLoader loader = new JobLoader(jobQueue);
        loader.start();

        Thread readyLoader = new Thread(() -> {
            while (true) {
                try {
                    // Take jobs from the jobQueue and add them to readyQueue if memory is available
                    PCB job = jobQueue.poll();
                    if (job != null && memoryManager.allocateMemory(job.memoryRequired)) {
                        job.state = "READY";
                        readyQueue.offer(job);
                    }
                } catch (Exception e) {
                    break;
                }
            }
        });
        readyLoader.start();
    
        Thread.sleep(2000); // Wait for jobs to populate
        
        List<PCB> jobs = new ArrayList<>();
        while (!readyQueue.isEmpty()) {
            jobs.add(readyQueue.poll());  // Poll jobs from readyQueue and add to List
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose Scheduling Algorithm:\n1. FCFS\n2. Round Robin\n3. Priority");
        int choice = scanner.nextInt();
        scanner.close();

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
