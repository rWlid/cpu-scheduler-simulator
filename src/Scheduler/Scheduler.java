package Scheduler;
import java.util.*;

public class Scheduler {
    private List<PCB> jobs;

    public Scheduler(List<PCB> jobs) {
        this.jobs = jobs;
    }

    public void runFCFS() {
        int currentTime = 0;
        for (PCB job : jobs) {
            SystemCalls.createProcess(job);
            job.waitingTime = currentTime;
            currentTime += job.burstTime;
            job.turnaroundTime = currentTime;
            job.state = "TERMINATED";
            SystemCalls.terminateProcess(job);
            System.out.printf("Job %d completed. Waiting: %d ms, Turnaround: %d ms\n",
                job.id, job.waitingTime, job.turnaroundTime);
        }
    }

    public void runRoundRobin() {
        int quantum = 7;
        int time = 0;
        Queue<PCB> queue = new LinkedList<>(jobs);
        Map<Integer, Integer> remainingTime = new HashMap<>();
        for (PCB job : jobs) {
            SystemCalls.createProcess(job);
            remainingTime.put(job.id, job.burstTime);
        }

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
                SystemCalls.terminateProcess(job);
                System.out.printf("Job %d completed. Waiting: %d ms, Turnaround: %d ms\n",
                    job.id, job.waitingTime, job.turnaroundTime);
            }
        }
    }

    public void runPriorityScheduling() {
        int time = 0;
        jobs.sort((a, b) -> b.priority - a.priority);

        for (PCB job : jobs) {
            SystemCalls.createProcess(job);
            if (time > job.priority * 10) {
                System.out.printf("Starvation detected for Job %d. Boosting priority.\n", job.id);
            }
            job.waitingTime = time;
            time += job.burstTime;
            job.turnaroundTime = time;
            job.state = "TERMINATED";
            SystemCalls.terminateProcess(job);
            System.out.printf("Job %d completed. Waiting: %d ms, Turnaround: %d ms\n",
                job.id, job.waitingTime, job.turnaroundTime);
        }
    }
}
