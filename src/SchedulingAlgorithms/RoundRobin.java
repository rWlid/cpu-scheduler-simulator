package SchedulingAlgorithms;
import Scheduler.PCB;
import java.util.*;

public class RoundRobin {
    public void run(List<PCB> jobs) {
        int quantum = 7;
        int time = 0;
        Queue<PCB> queue = new LinkedList<>(jobs);
        Map<Integer, Integer> remainingTime = new HashMap<>();
        for (PCB job : jobs) remainingTime.put(job.id, job.burstTime);

        while (!queue.isEmpty()) {
            PCB job = queue.poll();
            int burstLeft = remainingTime.get(job.id);
            int execTime = Math.min(quantum, burstLeft);
            time += execTime;
            remainingTime.put(job.id, burstLeft - execTime);

            if (remainingTime.get(job.id) > 0) {
                queue.offer(job);
            } else {
                job.turnaroundTime = time;
                job.waitingTime = time - job.burstTime;
                job.state = "TERMINATED";
                System.out.printf("Job %d completed. Waiting: %d ms, Turnaround: %d ms\n",
                    job.id, job.waitingTime, job.turnaroundTime);
            }
        }
    }
}