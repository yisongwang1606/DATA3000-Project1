# FCFS Project 1

A Java implementation of FCFS (First Come, First Served) CPU scheduling.

## Java Version

- Java 8 or higher

## Source Code Structure

- `src/App.java`: Entry point, starts the simulation.
- `src/fcfs/algorithm/FCFSAlgorithm.java`: Main FCFS workflow and result calculation.
- `src/fcfs/model/Process.java`: Process data model (`processId`, `arrivalTime`, `burstTime`, `waitingTime`, `turnaroundTime`).
- `src/fcfs/queue/QueueInterface.java`: Queue API.
- `src/fcfs/queue/QueueImplementation.java`: Linked-list queue implementation (`Node`, `enqueue`, `dequeue`, `isEmpty`, `clear`).

## How to Run

### Run JAR

```powershell
java -jar FCFSProject.jar
```

### Compile and Run from Source

```powershell
mkdir bin
javac -d bin src\App.java src\fcfs\algorithm\FCFSAlgorithm.java src\fcfs\model\Process.java src\fcfs\queue\QueueInterface.java src\fcfs\queue\QueueImplementation.java
java -cp bin App
```

## Runtime Logic

1. `App.main()` creates `FCFSAlgorithm` and calls `runSimulation()`.
2. `runSimulation()` shows a start dialog, reads process count, and collects each process's arrival/burst time.
3. Processes are sorted by arrival time.
4. All processes are enqueued into the custom queue.
5. FCFS execution loop (`while !queue.isEmpty()`):
   - Dequeue next process.
   - If CPU is idle (`currentTime < arrivalTime`), move `currentTime` to that arrival time.
   - Compute `waitingTime = currentTime - arrivalTime`.
   - Compute `turnaroundTime = waitingTime + burstTime`.
   - Update `currentTime += burstTime`.
6. Show per-process results and average waiting/turnaround times via `JOptionPane`.

## Input Validation

- Process count must be a positive integer.
- Arrival time must be a non-negative integer.
- Burst time must be a positive integer.
- Canceling input stops the simulation with a warning dialog.
