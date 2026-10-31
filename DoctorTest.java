public class DoctorTest {
    public static void main(String[] args) throws Exception {
        WaitingRoom room = new WaitingRoom(2);
        Semaphore rooms = new Semaphore(2);

        Doctor[] docs = new Doctor[3];
        for (int i = 0; i < 3; i++) {
            docs[i] = new Doctor(i + 1, room, rooms);
            docs[i].start();
        }

        Patient[] pats = new Patient[5];
        for (int i = 0; i < 5; i++) {
            pats[i] = new Patient(i + 1, room);
            pats[i].start();
            Thread.sleep(100);
        }

        for (Patient p : pats) p.join();
        System.out.println("All patients have been treated.");
        for (Doctor d : docs) d.interrupt();   // wake idle doctors so they exit
    }
}