package Scheduler;
public class PCB {
    public int id;
    public int burstTime;
    public int priority;
    public int memoryRequired;
    public String state;
    public int waitingTime = 0;
    public int turnaroundTime = 0;

    public PCB(int id, int burstTime, int priority, int memoryRequired) {
        this.id = id;
        this.burstTime = burstTime;
        this.priority = priority;
        this.memoryRequired = memoryRequired;
        this.state = "NEW";
    }
}