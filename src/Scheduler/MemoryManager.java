package Scheduler;
public class MemoryManager {
    private int totalMemory = 2048;
    private int usedMemory = 0;

    public synchronized boolean allocateMemory(int size) {
        if (usedMemory + size <= totalMemory) {
            usedMemory += size;
            return true;
        }
        return false;
    }

    public synchronized void releaseMemory(int size) {
        usedMemory -= size;
    }

    public int getAvailableMemory() {
        return totalMemory - usedMemory;
    }
}
