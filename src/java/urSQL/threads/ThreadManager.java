package thread.management;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;


public class ThreadManager {
    public static ExecutorService pool;
    


    public static synchronized void main(String[] args) throws InterruptedException, ExecutionException {
        String query = "Select * from Estudiantes;";
        pool = Executors.newFixedThreadPool(2);
        RuntimeDBProcessor rdbm = new RuntimeDBProcessor();
        Future futureRdbm = pool.submit(rdbm);
        
        rdbm.sendQuery(query);
        
        while(!futureRdbm.isDone()){
            System.out.println("Task is not completed yet...."); 
            Thread.sleep(1000); //sleep for 1 millisecond before checking again
        }
        
        System.out.println("Task is completed, let's check result"); 
        String final_query = (String) futureRdbm.get();
        System.out.println(final_query);
        
        
        FutureTask task = new FutureTask(rdbm);
        rdbm.sendQuery("sex");
        task.run();
        String final_query2 = (String) task.get();
        System.out.println(final_query2);
        
        //pool.shutdown();
    }

}

