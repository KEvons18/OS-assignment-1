public class Patient extends Thread {
    final int id;
    private final WaitingRoom waitingRoom;
    volatile boolean treated = false;
    final Semaphore done = new Semaphore(0);

    public Patient(int id, WaitingRoom waitingRoom) {
        this.id = id;
        this.waitingRoom = waitingRoom;
    }

    @Override
    public void run() {
        try {
            System.out.println(this + " arrived.");
            do {
                waitingRoom.enter(this);   // may block if the room is full
                done.acquire();            // wait for the doctor's verdict
            } while (!treated);            // not treated = sent back, so try again
            System.out.println(this + " left.");
        } catch (InterruptedException e) {
        }
    }

    @Override
    public String toString() {
        return "P" + id;
    }
}