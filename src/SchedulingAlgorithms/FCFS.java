package SchedulingAlgorithms;
import Scheduler.PCB;
import java.util.*;

public class FCFS {
	public void run(List<PCB> jobs) {
		int currentTime = 0;
		for (PCB job : jobs) {
			job.waitingTime = currentTime;
			currentTime += job.burstTime;
			job.turnaroundTime = currentTime;
			job.state = "TERMINATED";
			System.out.printf("Job %d completed. Waiting: %d ms, Turnaround: %d ms\n", job.id, job.waitingTime,
					job.turnaroundTime);
		}
	}
}
