public class SemaphoreTest {
    public static void main(String[] args) throws Exception {
        Semaphore s = new Semaphore(1);

        s.acquire();
        System.out.println("Acquired once");

        long t = System.currentTimeMillis();
        boolean ok = s.tryAcquire(1000);   // nobody releases: should fail after ~1s
        System.out.println("Try #1: " + ok + " after " + (System.currentTimeMillis() - t) + " ms");

        new Thread(() -> {
            try { Thread.sleep(500); } catch (InterruptedException e) {}
            s.release();                   // release after 0.5s
        }).start();

        t = System.currentTimeMillis();
        ok = s.tryAcquire(2000);           // should succeed after ~500ms
        System.out.println("Try #2: " + ok + " after " + (System.currentTimeMillis() - t) + " ms");

        System.out.println("Try #3 (non-blocking): " + s.tryAcquire(0));  // false, instantly
    }
}