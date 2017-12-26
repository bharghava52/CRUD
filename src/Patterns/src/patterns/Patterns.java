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
        t1.join();
        t2.join();
    }
 
    public static class Resource
    {
        LinkedList<Integer> list = new LinkedList<>();

        public void produce() throws InterruptedException
        {
            int value = 0;
            while (true)
            {
                //synchronized (this)      //as we only need the producer to produce he need not to worry about the synchronized
                //{
                    if (list.size()==2)
                        //wait();
                        Thread.sleep(1000);
                    else{
                    System.out.println("Producer produced:"+ value);
                    list.add(value++);
                    //notify();
                    Thread.sleep(1000);
                    }
                //}
            }
        }
        
        public void consume() throws InterruptedException
        {
            while (true)
            {
                //synchronized (this)
                //{
                  if (list.size()==0)
                      //wait();
                      Thread.sleep(1000);
                  else{
                  int val = list.removeFirst();
                  System.out.println("Consumer consumed:"+ val);
                  //notify();
                  Thread.sleep(1000);
                  }
                //}
            }
        }
    }
}
