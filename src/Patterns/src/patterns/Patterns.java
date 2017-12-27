package patterns;
import java.util.LinkedList;

public class Patterns {

    public static void main(String[] args) throws InterruptedException
    {
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
        t1.start();
        t2.start();
    }
 
    public static class Resource
    {
        //creating the resource collector 
        LinkedList<Integer> list = new LinkedList<>();

        public void produce() throws InterruptedException
        {
            int value = 0;
            while (true)
            {
                //checking wether the resource is full or not
                if (list.size()==2){ 
                }   
                else{
                    //making sure that at every instance list is being handled serially  
                    synchronized(list){
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
                //checking the resource availability 
                if (list.size()==0){
                }  
                else{
                  synchronized(list){    
                    int val = list.removeFirst();
                    System.out.println("Consumer consumed:"+ val);
                  }
                }
            }
        }
    }
}
