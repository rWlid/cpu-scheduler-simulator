package SchedulingAlgorithms;
import Scheduler.PCB;
import java.util.*;

public class PriorityScheduling {
	public void run(List<PCB> jobs) {
		int time = 0;
		jobs.sort((a, b) -> b.priority - a.priority);

		for (PCB job : jobs) {
			if (time > job.priority * 10) {
				System.out.printf("Starvation detected for Job %d. Boosting priority.\n", job.id);
			}
			job.waitingTime = time;
			time += job.burstTime;
			job.turnaroundTime = time;
			job.state = "TERMINATED";
			System.out.printf("Job %d completed. Waiting: %d ms, Turnaround: %d ms\n", job.id, job.waitingTime,
					job.turnaroundTime);
		}
	}
}
