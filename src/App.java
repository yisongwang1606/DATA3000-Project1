import fcfs.algorithm.FCFSAlgorithm;

public class App {
    public static void main(String[] args) {
        // We create the FCFS algorithm runner so the program can execute the full simulation workflow.
        FCFSAlgorithm fcfsAlgorithm = new FCFSAlgorithm();
        // We trigger the simulation process so the user can input process data and see scheduling results.
        fcfsAlgorithm.runSimulation();
    }
}
