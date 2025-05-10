package classes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Scheduler {
    public static void proemptivePriorityScheduling(List<Process> processes) {
        int time = 0;
        int processCount = processes.size();
        System.out.println("Total number of processes: " + processCount);
        int completed = 0;

        List<Process> readyQueue = new ArrayList<>();

        while (completed < processCount) {
            for (Process process : processes) {
                if (process.getArrivalTime() == time) {
                    readyQueue.add(process);
                }
            }

            Process currentProcess = readyQueue.stream()
                    .filter(process -> process.getRemainingTime() > 0)
                    .min(Comparator.comparingInt((Process process) -> process.getPriority())
                            .thenComparingInt(process -> process.getArrivalTime()))
                    .orElse(null);

            if (currentProcess != null) {
                if (currentProcess.getStartTime() == -1)
                    currentProcess.setStartTime(time);

                currentProcess.setRemainingTime(currentProcess.getRemainingTime() - 1);

                System.out.printf(" %2d  |     P%d\n", time + 1, currentProcess.getPID());

                if (currentProcess.getRemainingTime() == 0) {
                    currentProcess.setCompletionTime(time + 1);
                    completed++;
                }
            } else {
                System.out.printf(" %2d  |     Idle\n", time + 1);
            }

            time++;
        }

        System.out.println("\nFinal Summary:");
        System.out.println("PID | Arrival | Burst | Priority | Start | Completion");
        for (Process p : processes) {
            System.out.printf(" P%d |   %2d    |   %2d  |    %2d    |  %2d   |     %2d\n",
                    p.getPID(), p.getArrivalTime(), p.getBurst(), p.getPriority(), p.getStartTime(),
                    p.getCompletionTime());
        }

        int totalWaitingTime = 0;

        for (Process p : processes) {
            int waitingTime = p.getStartTime() - p.getArrivalTime();
            totalWaitingTime += waitingTime;
        }

        double averageWaitingTime = (double) totalWaitingTime / processCount;

        System.out.printf("\nAverage waiting time: %.2f unit time\n", averageWaitingTime);
    }
}
