package fcfs;

import fcfs.algorithm.FCFSAlgorithm;

public class App {
    /** Starts the FCFS simulation application. */
    public static void main(String[] args) {
        FCFSAlgorithm fcfsAlgorithm = new FCFSAlgorithm();
        fcfsAlgorithm.runSimulation();
    }
}
