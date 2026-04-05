# FCFS Project 1

Java implementation of FCFS (First Come, First Served) CPU scheduling.

## Java Version

- Java 8 or higher

## Run the JAR

```powershell
java -jar FCFSProject.jar
```

## Compile and Run from Source

```powershell
mkdir bin
javac -d bin src\App.java src\fcfs\algorithm\FCFSAlgorithm.java src\fcfs\model\Process.java src\fcfs\queue\QueueInterface.java src\fcfs\queue\QueueImplementation.java
java -cp bin App
```

## Program Interaction

- The program uses `JOptionPane` dialogs.
- Enter number of processes, then each process's arrival time and burst time.
- The program displays waiting time, turnaround time, and averages.

## Sample Run (Quick Check)

For 5 processes with results:

- Waiting time: `0, 3, 5, 6, 11`
- Turnaround time: `4, 6, 7, 12, 16`
- Average waiting time: `5.00`
- Average turnaround time: `9.00`
