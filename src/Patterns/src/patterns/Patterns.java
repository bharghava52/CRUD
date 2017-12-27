package patterns;
import java.util.LinkedList;

public class Patterns {

    public static void main(String[] args) throws InterruptedException
    {
        //TODO : This should be running indefinitely                        Done 
        //TODO : Shouldn't stop until I exit from app.                      Done    
        //TODO : Change this to two producers and four consumers.           Done
        //TODO : Comment what the removed code does : join , wait , notify etc
        /* 
        join()
        is used to make sure that one thread runs after the task of the other
        thread is completed.
        wiat()
        this statement is to state that its job is done and waiting for new task
        or resources to be given by other threads.
        notify()
        this method is to tell the other thread which is waiting for this 
        threads action stating that other thread can now execute.
        */
        final Resource resource = new Resource();
        Thread t1 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    resource.produce();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    resource.consume();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });
        new Thread(t1).start();
        new Thread(t1).start();
        new Thread(t2).start();
        new Thread(t2).start();
        new Thread(t2).start();
    }
 
    public static class Resource
    {
        int value = 0;
        //creating the resource collector 
        //TODO : Why you used linked list rather than other data structure,Reason?
        /*
        it will make it easy to remove the first element from the list 
        we can also use queue.  
        */
        final LinkedList<Integer> list = new LinkedList<>();

        public void produce() throws InterruptedException
        {
            
            while (true)
            {
                synchronized(list){
                    //TODO : This one is called busy loop.
                    /*
                    as both the methods are using the list the producer is 
                    reading the value of the list in the if() statement and then
                    the consumer consumes resource making the producer think that
                    the resources are full and producer is not producing them
                    as the resources are full.But they are already consumed the 
                    consumer is waiting for the producer to produce resources.
                    now producer is waiting for the resource to be consumed 
                    consumer is waiting for the resource to be produced.
                    */
                    //TODO : Remove busy loop. And update your findings here.
                    /*
                    making the if() statement also synchronized ensures that 
                    at any particular instance only one process is holding 
                    the value list.
                    */
                    if (list.size()==2){ 
                    }   
                    else{
                        System.out.println("Producer produced:"+ value);
                        list.add(value++);
                    }
                }
            }
        }
        
        public void consume() throws InterruptedException
        {
            while (true)
            {
                synchronized(list){
                    if (list.isEmpty()){
                    }  
                    else{  
                        int val = list.removeFirst();
                        System.out.println("Consumer consumed:"+ val);
                    }
                }
            }
        }
    }
}
