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
        // We save the process identifier so each process can be shown clearly in the final output.
        this.processId = processId;
        // We save the arrival time because FCFS ordering depends on arrival sequence.
        this.arrivalTime = arrivalTime;
        // We save the burst time because execution duration drives waiting and turnaround calculations.
        this.burstTime = burstTime;
        // We initialize waiting time to zero because it will be calculated during simulation.
        this.waitingTime = 0;
        // We initialize turnaround time to zero because it will be calculated during simulation.
        this.turnaroundTime = 0;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        // We update the process identifier so external callers can rename a process when needed.
        this.processId = processId;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        // We update the arrival time so this object can reflect corrected scheduling input.
        this.arrivalTime = arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        // We update the burst time so this object can reflect corrected execution duration.
        this.burstTime = burstTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        // We store the calculated waiting time so we can display process-specific FCFS results.
        this.waitingTime = waitingTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        // We store the calculated turnaround time so we can display process-specific FCFS results.
        this.turnaroundTime = turnaroundTime;
    }
}
