public class WaitingRoomTest {
     public static void main(String[] args) throws Exception {
        WaitingRoom room = new WaitingRoom(2);

        // 3 patients arrive, but only 2 seats and no doctor yet
        for (int i = 1; i <= 3; i++) {
            Patient p = new Patient(i , room);
            new Thread(() -> {
                try { room.enter(p); } catch (InterruptedException e) {}
            }).start();
            Thread.sleep(100);        // keep the arrival order predictable
        }

        Thread.sleep(1500);
        System.out.println("--- a doctor arrives ---");

        for (int i = 0; i < 3; i++) {
            System.out.println("Doctor took " + room.take());
            Thread.sleep(500);
        }
    }
}
