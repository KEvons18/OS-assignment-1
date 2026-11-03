import java.util.Scanner;

public class Hospital {

    public static void main(String[] args) throws InterruptedException {

        Scanner input = new Scanner(System.in);
        System.out.print("Waiting room capacity: ");
        int capacity = input.nextInt();
        System.out.print("Number of doctors: ");
        int doctorsCount = input.nextInt();
        System.out.print("Number of patients: ");
        int patientsCount = input.nextInt();

        WaitingRoom waitingRoom = new WaitingRoom(capacity);
        Semaphore rooms = new Semaphore(2);
        Doctor[] doctors = new Doctor[doctorsCount];

        for (int i = 0; i < doctorsCount; i++) {
            doctors[i] = new Doctor(i + 1, waitingRoom, rooms);
        }
        Patient[] patients = new Patient[patientsCount];

        for (int i = 0; i < patientsCount; i++) {
            patients[i] = new Patient(i + 1, waitingRoom);
        }
        System.out.println("\nPatients arriving:");

        for (Patient p : patients) {
            System.out.print(p + " ");
        }
        System.out.println("\n");

        for (Doctor d : doctors) {
            d.start();
        }

        for (Patient p : patients) {
            p.start();
            Thread.sleep(200);   // small gap between arrivals
        }

        try {
            for (Patient p : patients) {
                p.join();
            }
            System.out.println("\nAll patients have been treated.");

            for (Doctor d : doctors) {
                d.interrupt();
            }

            for (Doctor d : doctors) {
                d.join();
            }
            System.out.println("Simulation completed.");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        input.close();
    }
}

