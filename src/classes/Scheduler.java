package classes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Scheduler {
    public static void preemptivePriorityWithQuantum(List<Process> processes, int quantum) {
        int time = 0;
        int processCount = processes.size();
        int completed = 0;

        List<Process> readyQueue = new ArrayList<>();
        Process currentProcess = null;
        int timeSlice = 0;

        while (completed < processCount) {
            for (Process process : processes) {
                if (process.getArrivalTime() == time) {
                    boolean alreadyInQueue = readyQueue.stream()
                            .anyMatch(p -> p.getPID() == process.getPID());
                    if (!alreadyInQueue) {
                        readyQueue.add(process);
                    }
                }
            }

            if (readyQueue.isEmpty()) {
                System.out.printf(" %2d  |   Idle\n", time);
                time++;
                continue;
            }

            List<Process> allNotFinishedProcesses = readyQueue.stream()
                    .filter(process -> process.getRemainingTime() > 0)
                    .toList();

            List<Process> allNotFinishedProcessesSorted = allNotFinishedProcesses.stream()
                    .sorted(Comparator.comparingInt((Process process) -> process.getPriority()))
                    .toList();

            int higherPriority = allNotFinishedProcessesSorted.get(0).getPriority();

            List<Process> allNotFinishedProcessesWithTheHigherPriority = allNotFinishedProcessesSorted.stream()
                    .filter(process -> process.getPriority() == higherPriority)
                    .sorted(Comparator.comparingInt((Process process) -> process.getQuantityOfQuantum()))
                    .toList();

            boolean isTheSameProcess = currentProcess != null
                    && currentProcess.getPID() == allNotFinishedProcessesWithTheHigherPriority.get(0).getPID();

            if (!isTheSameProcess) {
                timeSlice = 0;
            }

            currentProcess = allNotFinishedProcessesWithTheHigherPriority.get(0);

            if (currentProcess != null) {
                if (currentProcess.getStartTime() == -1) {
                    currentProcess.setStartTime(time);
                }
            }

            if (currentProcess != null) {
                if (timeSlice < quantum) {
                    currentProcess.setRemainingTime(currentProcess.getRemainingTime() - 1);
                    System.out.printf(" %2d  |   P%d\n", time, currentProcess.getPID());
                    timeSlice++;
                } else {
                    currentProcess.setQuantityOfQuantum(currentProcess.getQuantityOfQuantum() + 1);
                    timeSlice = 0;

                    currentProcess = null;

                    continue;
                }

                boolean hasFinished = currentProcess.getRemainingTime() == 0;

                if (hasFinished) {
                    currentProcess.setCompletionTime(time + 1);
                    completed++;
                    currentProcess = null;
                    timeSlice = 0;
                }

                if (timeSlice == quantum) {
                    currentProcess.setQuantityOfQuantum(currentProcess.getQuantityOfQuantum() + 1);
                    timeSlice = 0;
                }
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

        List<Integer> waitingTimes = new ArrayList<>();

        for (Process process : processes) {
            int waitingTime = process.getCompletionTime() - process.getBurst() - process.getArrivalTime();
            waitingTimes.add(waitingTime);
        }

        double totalWaitingTime = waitingTimes.stream().mapToInt(Integer::intValue).sum();
        double averageWaitingTime = totalWaitingTime / processCount;
        System.out.printf("\nAverage waiting time: %.2f unit time\n", averageWaitingTime);
    }
}
