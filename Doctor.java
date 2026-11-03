import java.util.Random;
public class Doctor extends Thread {
    private final int id;
    private final WaitingRoom waitingRoom;
    private final Semaphore rooms;
    private final Random random = new Random();

    public Doctor (int id , WaitingRoom waitingRoom, Semaphore rooms){
        this.id = id;
        this.waitingRoom = waitingRoom;
        this.rooms = rooms;
    }

    public void run (){
        try{
            while(true){
                Patient p = waitingRoom.take();
                System.out.println("Doctor " + id + " is assigned to " + p + ".");
                System.out.println("Doctor " + id + " is trying to acquire a treatment room.");
                if(rooms.tryAcquire(3000)){
                    System.out.println("Doctor " + id + " acquired a tretment room for " + p + ".");
                    System.out.println("Doctor " + id + " begins treating " +p + "." );

                    Thread.sleep(1000 + random.nextInt(2000)); 

                    System.out.println("Doctor " + id + " finished treating " + p + ".");
                    rooms.release();
                    
                    System.out.println("Doctor " + id + " released the treatment room.");
                    System.out.println("Doctor " + id + " is now available.");
                    p.treated = true;
                    p.done.release();
                }else{
                    System.out.println("Doctor " + id + " couldnt acquire room.");
                    System.out.println("Doctor " + id + " is sending" + p + " back to the waiting room.");
                    System.out.println("Doctor " + id + " is now available.");
                    p.done.release();
                }
            }
        }catch ( InterruptedException e){

        }
    }

}
