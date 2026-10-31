import java.util.LinkedList;
import java.util.Queue;

public class WaitingRoom {
    private final Queue<Patient> queue = new LinkedList<>();
    private final Semaphore empty;
    private Semaphore full = new Semaphore(0);
    private final Semaphore mutex = new Semaphore(1);

    public WaitingRoom(int capacity){
        this.empty = new Semaphore(capacity);
    }

    public void enter (Patient p)throws InterruptedException{  //producer
        if(!empty.tryAcquire(0)){
            System.out.println("The waiting room is full.");
            System.out.println(p + " is waiting outside for an available seat.");
            empty.acquire();
        }
        mutex.acquire();
        queue.add(p);             //cant reverse order beccause it would casue a deadlock so the order is empty -> mutex
        System.out.println(p + " enetered the waiting room.");    
        mutex.release();

        full.release();
    }

    public Patient take() throws InterruptedException{  //consumer
        full.acquire();

        mutex.acquire(); 
        Patient p = queue.remove();
        mutex.release();

        empty.release();
        return p;   
    }
}
