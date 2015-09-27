package urSQL.threads;


import NET.sourceforge.BplusJ.BplusJ.hBplusTree;
import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;



public class ThreadManager {
    public static ExecutorService _Pool;
    public static Response _DATA;
    public static String Current_Schema;
    public static SystemCatalog _SYCT;
    public static RuntimeDBProcessor _RDBM;
    public static StoredDataManager _STDM;
    public static StoredData _SD;
    public static FutureTask futureSystem;
    public static FutureTask futureRDBM;
    public static FutureTask futureSTDM;
    public static FutureTask futureSD;
    private String _Dir = "c:\\tmp/";
  
    ThreadManager() throws Exception{
        
        
    }
    
    public void Start() throws Exception{
        _Pool = Executors.newFixedThreadPool(4);
        _SYCT= new SystemCatalog();
        _RDBM = new RuntimeDBProcessor();
       _STDM = new StoredDataManager();
       _SD =  new StoredData();
       futureSystem= new FutureTask(_SYCT);
       futureRDBM = new FutureTask(_RDBM);
       futureSTDM = new FutureTask(_STDM);
       futureSD = new FutureTask(_SD);
       
    }
    
    public void stop(){
        _Pool.shutdown();
       
    }
    
    
    public void sendQuery() throws InterruptedException, ExecutionException{
        
        
        
        
        
        
    }

    
    
    
    
    public static synchronized void main(String[] args) throws InterruptedException, ExecutionException, Exception {
       
        
        
        ThreadManager a = new ThreadManager();
        a.iniciar_Directorios();
      //a.stop();
        
        
        
       
        
        //pool.shutdown();
    }
    
    
    public void waitRDBM(){
        while(futureRDBM.isDone()){
            
        }
    }
    public  void waitSC(){
        while(futureSystem.isDone()){
            
        }
    }
    public  void waitSTDM(){
        while(futureSTDM.isDone()){
            
        }
    }
    public void waitSD(){
        while(futureSD.isDone()){
            
        }
    }
    
    
    public void iniciar_Directorios() throws Exception{
        boolean mkdir1 = new File(_Dir).mkdir();
        boolean mkdir2 = new File(_Dir+"DataBases").mkdir();
        boolean mkdir3 = new File(_Dir+"DataBases/"+"System_Catalog").mkdir();
        hBplusTree.Initialize(_Dir+"DataBases/"+"System_Catalog/"+"Sys_Schemas"+".tree",
                _Dir+"DataBases/"+"System_Catalog/"+"Sys_Schemas"+".table", 6);
        hBplusTree.Initialize(_Dir+"DataBases/"+"System_Catalog/"+"Sys_Tables"+".tree",
                _Dir+"DataBases/"+"System_Catalog/"+"Sys_Tables"+".table", 6);
        hBplusTree.Initialize(_Dir+"DataBases/"+"System_Catalog/"+"Sys_Columns"+".tree",
                _Dir+"DataBases/"+"System_Catalog/"+"Sys_Columns"+".table", 6);
        
        new File(_Dir+"DataBases"+"/"+"System_Catalog"+"/"+"Execution_Plan.txt").createNewFile();
    }
}

