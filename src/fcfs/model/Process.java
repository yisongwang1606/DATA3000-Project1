package fcfs.model;

/**
 * Represents one process in the FCFS scheduling simulation.
 */
public class Process {
    private String processId;
    private int arrivalTime;
    private int burstTime;
    private int waitingTime;
    private int turnaroundTime;

    /**
     * Builds a process with required scheduling input values.
     *
     * @param processId identifier of the process.
     * @param arrivalTime time when the process arrives.
     * @param burstTime CPU time required by the process.
     */
    public Process(String processId, int arrivalTime, int burstTime) {
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
    }

    /** Returns the process identifier. */
    public String getProcessId() {
        return processId;
    }

    /** Updates the process identifier. */
    public void setProcessId(String processId) {
        this.processId = processId;
    }

    /** Returns the arrival time. */
    public int getArrivalTime() {
        return arrivalTime;
    }

    /** Updates the arrival time. */
    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /** Returns the burst time. */
    public int getBurstTime() {
        return burstTime;
    }

    /** Updates the burst time. */
    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    /** Returns the waiting time. */
    public int getWaitingTime() {
        return waitingTime;
    }

    /** Updates the waiting time. */
    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    /** Returns the turnaround time. */
    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    /** Updates the turnaround time. */
    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }
}
