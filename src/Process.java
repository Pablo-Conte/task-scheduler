public class Process {
    private int PID;
    private int arrive_time;
    private int burst;
    private int priority;

    public int getPID() {
        return PID;
    }

    public void setPID(int pID) {
        PID = pID;
    }

    public int getArrive_time() {
        return arrive_time;
    }

    public void setArrive_time(int arrive_time) {
        this.arrive_time = arrive_time;
    }

    public int getBurst() {
        return burst;
    }

    public void setBurst(int burst) {
        this.burst = burst;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
