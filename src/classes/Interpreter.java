package classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Interpreter {
    public static List<Process> commandInterpreter(String fileNameAndExtension)
            throws FileNotFoundException, IOException, NumberFormatException {
        String filePath = "src/process_files/" + fileNameAndExtension;

        File file = new File(filePath);

        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        }

        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;
        int processCount = 0;

        while ((line = reader.readLine()) != null) {
            processCount++;
        }

        int processCountDesconsideringFirstLine = processCount - 1;

        Process[] processes = new Process[processCountDesconsideringFirstLine];

        reader.close();

        reader = new BufferedReader(new FileReader(file));
        int index = 0;

        boolean isFirstLine = true;

        while ((line = reader.readLine()) != null) {
            if (isFirstLine) {
                isFirstLine = false;
                continue;
            }

            String[] parts = line.split(",");

            int PID = Integer.parseInt(parts[0].trim());
            int arrive_time = Integer.parseInt(parts[1].trim());
            int burst = Integer.parseInt(parts[2].trim());
            int priority = Integer.parseInt(parts[3].trim());

            processes[index] = new Process(PID, arrive_time, burst, priority);
            index++;
        }

        reader.close();

        List<Process> processList = List.of(processes);
        return processList;
    }
}
