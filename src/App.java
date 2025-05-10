import java.io.FileNotFoundException;
import java.io.IOException;

public class App {

    public static void main(String[] args) {
        String fileName = "file_1.txt";

        Process[] processes = new Process[0];

        try {
            processes = Interpreter.commandInterpreter(fileName);
        } catch (FileNotFoundException error) {
            System.out.println("File not found: " + error.getMessage());
        } catch (IOException error) {
            System.out.println("Error reading lines: " + error.getMessage());
        } catch (Exception error) {
            System.out.println("An unexpected error occurred: " + error.getMessage());
        }

        for (Process process : processes) {
            System.out.println("PID: " + process.getPID() + ", Arrival Time: " + process.getArrive_time() +
                    ", Burst Time: " + process.getBurst() + ", Priority: " + process.getPriority());
        }
    }
}
