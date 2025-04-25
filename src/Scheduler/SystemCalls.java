package Scheduler;

public class SystemCalls {
    public static void createProcess(PCB pcb) {
        System.out.printf("[SysCall] Created Process %d\n", pcb.id);
    }

    public static void terminateProcess(PCB pcb) {
        System.out.printf("[SysCall] Terminated Process %d\n", pcb.id);
    }

    public static void allocateMemory(MemoryManager mm, int size) {
        System.out.printf("[SysCall] Attempting to allocate %d MB\n", size);
        mm.allocateMemory(size);
    }

    public static void deallocateMemory(MemoryManager mm, int size) {
        System.out.printf("[SysCall] Deallocating %d MB\n", size);
        mm.releaseMemory(size);
    }

    public static void getProcessInfo(PCB pcb) {
        System.out.printf("[SysCall] Process Info -> ID: %d, State: %s, Priority: %d\n",
            pcb.id, pcb.state, pcb.priority);
    }
}

