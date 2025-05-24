import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import classes.Interpreter;
import classes.Process;
import classes.Scheduler;

public class App {
    public static void main(String[] args) {
        String fileName = "file_1.txt";

        List<Process> processes = null;

        try {
            processes = Interpreter.commandInterpreter(fileName);
        } catch (FileNotFoundException error) {
            System.out.println("File not found: " + error.getMessage());
        } catch (IOException error) {
            System.out.println("Error reading lines: " + error.getMessage());
        } catch (NumberFormatException error) {
            System.out.println("Your process file have a invalid pattern: " + error.getMessage());
        } catch (Exception error) {
            System.out.println("An unexpected error occurred: " + error.getMessage());
        }

        Scheduler.preemptivePriorityWithQuantum(processes, 4);

    }
}
