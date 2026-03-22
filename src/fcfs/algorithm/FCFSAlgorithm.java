package fcfs.algorithm;

import javax.swing.JOptionPane;

import fcfs.model.Process;
import fcfs.queue.QueueImplementation;

/**
 * Simulates the FCFS scheduling algorithm using a linked-list queue.
 */
public class FCFSAlgorithm {
    /**
     * Starts the simulation and coordinates input, processing, and output.
     */
    public void runSimulation() {
        try {
            // We display the start prompt so the user knows the simulation is ready to begin.
            JOptionPane.showMessageDialog(null, "Press OK to initiate the simulation!", "FCFS Simulation",
                    JOptionPane.INFORMATION_MESSAGE);
            // We read the number of processes so we can allocate storage for all process entries.
            int numberOfProcesses = requestPositiveInteger("Enter the number of processes ?");
            // We gather process details from user input so each process has arrival and burst values.
            Process[] processes = collectProcesses(numberOfProcesses);
            // We sort by arrival time so FCFS execution order follows process arrival sequence.
            sortByArrivalTime(processes);
            // We run the FCFS scheduling simulation so waiting and turnaround times are calculated.
            Process[] executedProcesses = executeFCFS(processes);
            // We show per-process results so the user can review each process metrics.
            showProcessResults(executedProcesses);
            // We show average metrics so the user can evaluate total scheduling performance.
            showAverageResults(executedProcesses);
        } catch (IllegalStateException exception) {
            // We display a cancellation or state message so the user receives clear feedback.
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Simulation Stopped", JOptionPane.WARNING_MESSAGE);
        } catch (Exception exception) {
            // We display an unexpected error message so runtime failures are surfaced to the user clearly.
            JOptionPane.showMessageDialog(null, "Unexpected error: " + exception.getMessage(), "Simulation Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Collects process inputs from the user.
     *
     * @param numberOfProcesses total number of processes to collect.
     * @return array containing all created processes.
     */
    private Process[] collectProcesses(int numberOfProcesses) {
        // We create the process array so every entered process has a fixed storage slot.
        Process[] processes = new Process[numberOfProcesses];
        // We iterate by index so each process can be requested and created in order.
        for (int i = 0; i < numberOfProcesses; i++) {
            // We build the process label so prompts identify each process clearly.
            String processId = "P" + (i + 1);
            // We ask for arrival time so each process includes its FCFS arrival moment.
            int arrivalTime = requestNonNegativeInteger("Enter arrival time for process " + processId + " ?");
            // We ask for burst time so each process includes its required CPU execution time.
            int burstTime = requestPositiveInteger("Enter burst time for process " + processId + " ?");
            // We create the process object so the collected input is encapsulated in one entity.
            processes[i] = new Process(processId, arrivalTime, burstTime);
        }
        // We return the completed process array so scheduling can proceed.
        return processes;
    }

    /**
     * Sorts processes by arrival time in ascending order using insertion sort.
     *
     * @param processes process array to be sorted in place.
     */
    private void sortByArrivalTime(Process[] processes) {
        // We start from the second element because a single-element prefix is always sorted.
        for (int i = 1; i < processes.length; i++) {
            // We hold the current process so it can be inserted into the sorted left side.
            Process currentProcess = processes[i];
            // We start comparing from the element before i to shift larger arrival times right.
            int j = i - 1;
            // We continue shifting while indices are valid and left arrival time is larger than current.
            while (j >= 0 && processes[j].getArrivalTime() > currentProcess.getArrivalTime()) {
                // We shift the larger process one step right to make room for insertion.
                processes[j + 1] = processes[j];
                // We move left to keep checking earlier positions for insertion.
                j--;
            }
            // We place the current process at the discovered insertion location.
            processes[j + 1] = currentProcess;
        }
    }

    /**
     * Runs FCFS scheduling and computes waiting and turnaround times.
     *
     * @param processes sorted process array.
     * @return processes in execution order with computed metrics.
     */
    private Process[] executeFCFS(Process[] processes) {
        // We create the queue so processes are handled in first-come, first-served order.
        QueueImplementation<Process> processQueue = new QueueImplementation<>();
        // We create an output array so execution order and computed metrics can be returned.
        Process[] executedProcesses = new Process[processes.length];
        // We initialize current time to zero because simulation starts at time zero.
        int currentTime = 0;
        // We initialize the output index so each executed process can be stored sequentially.
        int executedIndex = 0;
        // We enqueue each process so the queue holds all work in arrival-sorted order.
        for (int i = 0; i < processes.length; i++) {
            // We append this process to the queue tail to preserve FCFS sequence.
            processQueue.enqueue(processes[i]);
        }
        // We process until queue is empty so all processes receive computed scheduling metrics.
        while (!processQueue.isEmpty()) {
            // We remove the next process from the front because FCFS always executes the earliest queued process.
            Process currentProcess = processQueue.dequeue();
            // We check CPU idle time so execution can start at the process arrival when needed.
            if (currentTime < currentProcess.getArrivalTime()) {
                // We jump current time to arrival time because CPU waits until this process arrives.
                currentTime = currentProcess.getArrivalTime();
            }
            // We calculate waiting time as current start time minus process arrival time.
            int waitingTime = currentTime - currentProcess.getArrivalTime();
            // We store waiting time in the process so it can be displayed in final results.
            currentProcess.setWaitingTime(waitingTime);
            // We calculate turnaround time as waiting time plus burst time.
            int turnaroundTime = waitingTime + currentProcess.getBurstTime();
            // We store turnaround time in the process so it can be displayed in final results.
            currentProcess.setTurnaroundTime(turnaroundTime);
            // We advance current time by burst time because CPU finishes after executing this process.
            currentTime = currentTime + currentProcess.getBurstTime();
            // We place this process in output order so callers receive final execution sequence.
            executedProcesses[executedIndex] = currentProcess;
            // We increment output index so next executed process is stored at the next slot.
            executedIndex++;
        }
        // We return the executed process array so UI methods can present results and averages.
        return executedProcesses;
    }

    /**
     * Shows per-process FCFS results.
     *
     * @param processes executed process array.
     */
    private void showProcessResults(Process[] processes) {
        // We create a string builder so result lines can be assembled efficiently.
        StringBuilder resultBuilder = new StringBuilder();
        // We add a title so the first dialog clearly identifies what is being displayed.
        resultBuilder.append("FCFS Scheduling Results\n\n");
        // We add a header row so each column meaning is clear to the user.
        resultBuilder.append("Process\tArrival\tBurst\tWaiting\tTurnaround\n");
        // We add a separator row so table data is easier to read.
        resultBuilder.append("-----------------------------------------------------\n");
        // We iterate through all processes so every process result appears in the table.
        for (int i = 0; i < processes.length; i++) {
            // We append process ID so each row is traceable to the correct process.
            resultBuilder.append(processes[i].getProcessId()).append("\t");
            // We append arrival time so the user can compare entered and scheduled timing.
            resultBuilder.append(processes[i].getArrivalTime()).append("\t");
            // We append burst time so the CPU duration per process is visible.
            resultBuilder.append(processes[i].getBurstTime()).append("\t");
            // We append waiting time so per-process delay is visible.
            resultBuilder.append(processes[i].getWaitingTime()).append("\t");
            // We append turnaround time so total completion time is visible.
            resultBuilder.append(processes[i].getTurnaroundTime()).append("\n");
        }
        // We show the process results dialog so the user can inspect per-process metrics.
        JOptionPane.showMessageDialog(null, resultBuilder.toString(), "Process Results", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Shows average waiting and turnaround times.
     *
     * @param processes executed process array.
     */
    private void showAverageResults(Process[] processes) {
        // We initialize waiting sum so average waiting time can be calculated.
        double totalWaitingTime = 0;
        // We initialize turnaround sum so average turnaround time can be calculated.
        double totalTurnaroundTime = 0;
        // We iterate through all processes so totals include every process metric.
        for (int i = 0; i < processes.length; i++) {
            // We add this process waiting time so the waiting total is accumulated correctly.
            totalWaitingTime = totalWaitingTime + processes[i].getWaitingTime();
            // We add this process turnaround time so the turnaround total is accumulated correctly.
            totalTurnaroundTime = totalTurnaroundTime + processes[i].getTurnaroundTime();
        }
        // We divide by process count so waiting total becomes average waiting time.
        double averageWaitingTime = totalWaitingTime / processes.length;
        // We divide by process count so turnaround total becomes average turnaround time.
        double averageTurnaroundTime = totalTurnaroundTime / processes.length;
        // We build the average output string so both aggregate values are displayed cleanly.
        String averageMessage = "Average Waiting Time: " + formatTwoDecimals(averageWaitingTime)
                + "\nAverage Turnaround Time: " + formatTwoDecimals(averageTurnaroundTime);
        // We display average metrics so the user can evaluate FCFS overall performance quickly.
        JOptionPane.showMessageDialog(null, averageMessage, "Average Results", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Reads a positive integer from dialog input.
     *
     * @param prompt input prompt.
     * @return integer greater than zero.
     */
    private int requestPositiveInteger(String prompt) {
        // We loop until valid input is provided so the method guarantees a positive integer.
        while (true) {
            // We request text input so user can enter numeric data for simulation setup.
            String input = JOptionPane.showInputDialog(null, prompt, "FCFS Input", JOptionPane.QUESTION_MESSAGE);
            // We detect cancel action so simulation can stop gracefully.
            if (input == null) {
                // We throw an exception so caller can show a clear stop message to the user.
                throw new IllegalStateException("Simulation was canceled by the user.");
            }
            // We remove surrounding spaces so accidental whitespace does not break numeric parsing.
            input = input.trim();
            try {
                // We parse integer value so the text input can be validated numerically.
                int value = Integer.parseInt(input);
                // We verify positive rule so zero and negative values are rejected.
                if (value > 0) {
                    // We return the value because it satisfies the positive integer requirement.
                    return value;
                }
                // We show validation feedback so user knows the accepted input range.
                JOptionPane.showMessageDialog(null, "Please enter a whole number greater than 0.", "Invalid Input",
                        JOptionPane.WARNING_MESSAGE);
            } catch (NumberFormatException exception) {
                // We show parse-error feedback so user knows the input must be an integer.
                JOptionPane.showMessageDialog(null, "Please enter a valid whole number.", "Invalid Input",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /**
     * Reads a non-negative integer from dialog input.
     *
     * @param prompt input prompt.
     * @return integer greater than or equal to zero.
     */
    private int requestNonNegativeInteger(String prompt) {
        // We loop until valid input is provided so the method guarantees a non-negative integer.
        while (true) {
            // We request text input so user can enter arrival time values.
            String input = JOptionPane.showInputDialog(null, prompt, "FCFS Input", JOptionPane.QUESTION_MESSAGE);
            // We detect cancel action so simulation can stop gracefully.
            if (input == null) {
                // We throw an exception so caller can show a clear stop message to the user.
                throw new IllegalStateException("Simulation was canceled by the user.");
            }
            // We remove surrounding spaces so accidental whitespace does not break numeric parsing.
            input = input.trim();
            try {
                // We parse integer value so the text input can be validated numerically.
                int value = Integer.parseInt(input);
                // We verify non-negative rule so negative arrival times are rejected.
                if (value >= 0) {
                    // We return the value because it satisfies the non-negative requirement.
                    return value;
                }
                // We show validation feedback so user knows negative values are not accepted.
                JOptionPane.showMessageDialog(null, "Please enter a whole number greater than or equal to 0.",
                        "Invalid Input", JOptionPane.WARNING_MESSAGE);
            } catch (NumberFormatException exception) {
                // We show parse-error feedback so user knows the input must be an integer.
                JOptionPane.showMessageDialog(null, "Please enter a valid whole number.", "Invalid Input",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /**
     * Formats a decimal number to two decimal places.
     *
     * @param value decimal value to format.
     * @return string with two decimal places.
     */
    private String formatTwoDecimals(double value) {
        // We format with two decimal places so average values are displayed consistently.
        return String.format("%.2f", value);
    }
}
