package SchedulingAlgorithms;

import Scheduler.PCB;
import Scheduler.SystemCalls;

import java.util.*;

public class RoundRobin {
	public void run(List<PCB> jobs) {
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
				System.out.printf("Job %d completed. Waiting: %d ms, Turnaround: %d ms\n", job.id, job.waitingTime,
						job.turnaroundTime);
			}
		}
		printAverages(jobs);
	}
	private static void printAverages(List<PCB> list) {
        double totalWait = 0, totalTurn = 0;
        for (PCB p : list) {
            totalWait += p.waitingTime;
            totalTurn += p.turnaroundTime;
        }
        System.out.println("Average Waiting Time: " + totalWait / list.size());
        System.out.println("Average Turnaround Time: " + totalTurn / list.size());
    }
}
