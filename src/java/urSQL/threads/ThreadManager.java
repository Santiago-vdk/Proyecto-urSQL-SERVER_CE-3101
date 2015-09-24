package urSQL.threads;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;



public class ThreadManager {
    public static ExecutorService _Pool;
    public static Response _DATA;
    private long _TInicio=0;
    private long _TFinal=0;
    RuntimeDBProcessor _RDBM;
    FutureTask _FutureRdbm;
    
    
    ThreadManager() throws Exception{
        _Pool = Executors.newFixedThreadPool(4);
        _RDBM = new RuntimeDBProcessor();
        _FutureRdbm= new FutureTask(_RDBM);
        
        
        
    }
    
    
    public void stop(){
        _Pool.shutdown();
        System.out.println("Procesos termiandos");
    }
    
    
    public void sendQuery(String query) throws InterruptedException, ExecutionException{
        _TInicio=System.currentTimeMillis();
        
        //_RDBM.setQuery("HOLA");
        _Pool.execute(_FutureRdbm);
        waitRDBM();
        String final_query = (String) _FutureRdbm.get();
        _TFinal=System.currentTimeMillis();
        
        float Time= (_TFinal - _TInicio)/1000000;
        System.out.println(final_query);
        System.out.println("Task is completed, Time: " + Time+ " s"); 
        
    }

    
    
    
    
    public static synchronized void main(String[] args) throws InterruptedException, ExecutionException, Exception {
        String query = "Select * from Estudiantes;";
        
        
        ThreadManager a = new ThreadManager();
        a.sendQuery(query);
        a.stop();
        
        
        
       
        
        //pool.shutdown();
    }
    
    
    public void waitRDBM(){
        while(_FutureRdbm.isDone()){
            
        }
    }
}

