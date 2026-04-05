package fcfs.algorithm;

import javax.swing.JOptionPane;

import fcfs.model.Process;
import fcfs.queue.QueueImplementation;

/**
 * Simulates the FCFS scheduling algorithm using a linked-list queue.
 */
public class FCFSAlgorithm {
    /** Runs the full dialog-based simulation flow. */
    public void runSimulation() {
        try {
            JOptionPane.showMessageDialog(null, "Press OK to initiate the simulation!", "FCFS Simulation",
                    JOptionPane.INFORMATION_MESSAGE);

            int numberOfProcesses = requestPositiveInteger("Enter the number of processes ?");
            Process[] processes = collectProcesses(numberOfProcesses);
            Process[] executedProcesses = simulateProcesses(processes);

            showProcessResults(executedProcesses);
            showAverageResults(executedProcesses);
        } catch (IllegalStateException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Simulation Stopped", JOptionPane.WARNING_MESSAGE);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Unexpected error: " + exception.getMessage(), "Simulation Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Simulates FCFS scheduling for a supplied process list. */
    public Process[] simulateProcesses(Process[] inputProcesses) {
        validateProcessInput(inputProcesses);
        Process[] workingProcesses = copyProcesses(inputProcesses);
        sortByArrivalTime(workingProcesses);
        return executeFCFS(workingProcesses);
    }

    /** Calculates average waiting and turnaround metrics. */
    public double[] calculateAverageMetrics(Process[] executedProcesses) {
        validateProcessInput(executedProcesses);

        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;
        for (Process process : executedProcesses) {
            totalWaitingTime += process.getWaitingTime();
            totalTurnaroundTime += process.getTurnaroundTime();
        }

        double averageWaitingTime = totalWaitingTime / executedProcesses.length;
        double averageTurnaroundTime = totalTurnaroundTime / executedProcesses.length;
        return new double[] { averageWaitingTime, averageTurnaroundTime };
    }

    /** Collects process inputs from the user. */
    private Process[] collectProcesses(int numberOfProcesses) {
        Process[] processes = new Process[numberOfProcesses];
        for (int i = 0; i < numberOfProcesses; i++) {
            String processId = "P" + (i + 1);
            int arrivalTime = requestNonNegativeInteger("Enter arrival time for process " + processId + " ?");
            int burstTime = requestPositiveInteger("Enter burst time for process " + processId + " ?");
            processes[i] = new Process(processId, arrivalTime, burstTime);
        }
        return processes;
    }

    /** Copies process inputs so caller data is not mutated. */
    private Process[] copyProcesses(Process[] sourceProcesses) {
        Process[] copiedProcesses = new Process[sourceProcesses.length];
        for (int i = 0; i < sourceProcesses.length; i++) {
            copiedProcesses[i] = new Process(sourceProcesses[i].getProcessId(), sourceProcesses[i].getArrivalTime(),
                    sourceProcesses[i].getBurstTime());
        }
        return copiedProcesses;
    }

    /** Validates process arrays for simulation and aggregate calculations. */
    private void validateProcessInput(Process[] processes) {
        if (processes == null || processes.length == 0) {
            throw new IllegalArgumentException("Process list must contain at least one process.");
        }

        for (int i = 0; i < processes.length; i++) {
            if (processes[i] == null) {
                throw new IllegalArgumentException("Process at index " + i + " is null.");
            }
            if (processes[i].getArrivalTime() < 0) {
                throw new IllegalArgumentException("Arrival time must be >= 0 for process "
                        + processes[i].getProcessId() + ".");
            }
            if (processes[i].getBurstTime() <= 0) {
                throw new IllegalArgumentException("Burst time must be > 0 for process "
                        + processes[i].getProcessId() + ".");
            }
        }
    }

    /** Sorts processes by arrival time using insertion sort. */
    private void sortByArrivalTime(Process[] processes) {
        for (int i = 1; i < processes.length; i++) {
            Process currentProcess = processes[i];
            int j = i - 1;
            // Shift later arrivals right until the insertion point is found.
            while (j >= 0 && processes[j].getArrivalTime() > currentProcess.getArrivalTime()) {
                processes[j + 1] = processes[j];
                j--;
            }
            processes[j + 1] = currentProcess;
        }
    }

    /** Executes FCFS and writes waiting/turnaround values onto each process. */
    private Process[] executeFCFS(Process[] processes) {
        QueueImplementation<Process> processQueue = new QueueImplementation<>();
        Process[] executedProcesses = new Process[processes.length];
        int currentTime = 0;
        int executedIndex = 0;

        for (Process process : processes) {
            processQueue.enqueue(process);
        }

        while (!processQueue.isEmpty()) {
            Process currentProcess = processQueue.dequeue();

            // If CPU is idle, jump to the next process arrival time.
            if (currentTime < currentProcess.getArrivalTime()) {
                currentTime = currentProcess.getArrivalTime();
            }

            // FCFS metric formulas.
            int waitingTime = currentTime - currentProcess.getArrivalTime();
            int turnaroundTime = waitingTime + currentProcess.getBurstTime();

            currentProcess.setWaitingTime(waitingTime);
            currentProcess.setTurnaroundTime(turnaroundTime);

            currentTime += currentProcess.getBurstTime();
            executedProcesses[executedIndex] = currentProcess;
            executedIndex++;
        }

        return executedProcesses;
    }

    /** Displays per-process results in chained format. */
    private void showProcessResults(Process[] processes) {
        StringBuilder resultBuilder = new StringBuilder();
        for (int i = 0; i < processes.length; i++) {
            resultBuilder.append("Process ").append(processes[i].getProcessId()).append(" - Arrival Time - ")
                    .append(processes[i].getArrivalTime()).append(" - Waiting Time - ")
                    .append(processes[i].getWaitingTime()).append(" - Turnaround Time - ")
                    .append(processes[i].getTurnaroundTime());
            if (i < processes.length - 1) {
                resultBuilder.append("\n\n");
            }
        }
        JOptionPane.showMessageDialog(null, resultBuilder.toString(), "Results", JOptionPane.INFORMATION_MESSAGE);
    }

    /** Displays average waiting and turnaround times. */
    private void showAverageResults(Process[] processes) {
        double[] averages = calculateAverageMetrics(processes);
        String averageMessage = "Average Waiting Time: " + formatTwoDecimals(averages[0])
                + "\nAverage Turnaround Time: " + formatTwoDecimals(averages[1]);
        JOptionPane.showMessageDialog(null, averageMessage, "Average Results", JOptionPane.INFORMATION_MESSAGE);
    }

    /** Reads a positive integer from dialog input. */
    private int requestPositiveInteger(String prompt) {
        while (true) {
            String input = JOptionPane.showInputDialog(null, prompt, "FCFS Input", JOptionPane.QUESTION_MESSAGE);
            if (input == null) {
                throw new IllegalStateException("Simulation was canceled by the user.");
            }
            input = input.trim();
            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                }
                JOptionPane.showMessageDialog(null, "Please enter a whole number greater than 0.", "Invalid Input",
                        JOptionPane.WARNING_MESSAGE);
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(null, "Please enter a valid whole number.", "Invalid Input",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /** Reads a non-negative integer from dialog input. */
    private int requestNonNegativeInteger(String prompt) {
        while (true) {
            String input = JOptionPane.showInputDialog(null, prompt, "FCFS Input", JOptionPane.QUESTION_MESSAGE);
            if (input == null) {
                throw new IllegalStateException("Simulation was canceled by the user.");
            }
            input = input.trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= 0) {
                    return value;
                }
                JOptionPane.showMessageDialog(null, "Please enter a whole number greater than or equal to 0.",
                        "Invalid Input", JOptionPane.WARNING_MESSAGE);
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(null, "Please enter a valid whole number.", "Invalid Input",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /** Formats a decimal number to two places. */
    private String formatTwoDecimals(double value) {
        return String.format("%.2f", value);
    }
}
