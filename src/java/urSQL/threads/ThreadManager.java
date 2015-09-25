package urSQL.threads;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;



public class ThreadManager {
    public static ExecutorService _Pool;
    public static Response _DATA;
    public static String Current_Schema;
    public static SystemCatalog _SYCT;
    public static FutureTask futureSystem;
  
    ThreadManager() throws Exception{
        _Pool = Executors.newFixedThreadPool(4);
        _SYCT= new SystemCatalog();
       futureSystem= new FutureTask(_SYCT);
        
    }
    
    
    public void stop(){
        _Pool.shutdown();
        System.out.println("Procesos termiandos");
    }
    
    
    public void sendQuery() throws InterruptedException, ExecutionException{
        _SYCT.set_Plan("P" ,"P", null, null, "not null", "varchar(12)", "no", "R_PK");
        _Pool.execute(futureSystem);
        waitS();
        System.out.println(futureSystem.get());
        
        
        
    }

    
    
    
    
    public static synchronized void main(String[] args) throws InterruptedException, ExecutionException, Exception {
        String query = "Select * from Estudiantes;";
        
        
        ThreadManager a = new ThreadManager();
        a.sendQuery();
        a.stop();
        
        
        
       
        
        //pool.shutdown();
    }
    
    
    public void waitS(){
        while(futureSystem.isDone()){
            
        }
    }
}

