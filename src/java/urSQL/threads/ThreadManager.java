package urSQL.threads;


import NET.sourceforge.BplusJ.BplusJ.hBplusTree;
import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import urSQL.logica.Facade;
import urSQL.logica.logHandler;

/**
 *
 * @author Shagy
 */
public class ThreadManager {
    
    public static String[] ListDB= null;
    /**
     *
     */
    public static ExecutorService _Pool;

    /**
     *
     */
    public static Response _DATA;

    /**
     *
     */
    public static String Current_Schema;

    /**
     *
     */
    public static SystemCatalog _SYCT;

    /**
     *
     */
    public static RuntimeDBProcessor _RDBM;

    /**
     *
     */
    public static StoredDataManager _STDM;

    /**
     *
     */
    public static StoredData _SD;

    /**
     *
     */
    public static FutureTask futureSystem;

    /**
     *
     */
    public static FutureTask futureRDBM;

    /**
     *
     */
    public static FutureTask futureSTDM;

    /**
     *
     */
    public static FutureTask futureSD;
    private String _Dir = logHandler.getInstance().getRootPath()+"/urSQL/";
  
    public ThreadManager() throws Exception{
        
        Start();
    }
    
    /**
     *
     * @throws Exception
     */
    public void Start() throws Exception{
        _Pool = Executors.newFixedThreadPool(40);
        _SYCT= new SystemCatalog();
        _RDBM = new RuntimeDBProcessor();
       _STDM = new StoredDataManager();
       _SD =  new StoredData();
       futureSystem= new FutureTask(_SYCT);
       futureRDBM = new FutureTask(_RDBM);
       futureSTDM = new FutureTask(_STDM);
       futureSD = new FutureTask(_SD);
       
    }
    
    /**
     *
     */
    public void stop(){
        _Pool.shutdown();
        
       
    }
    
    /**
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public String sendQuery( String pQuery) throws InterruptedException, ExecutionException, Exception{
        Start();
        _RDBM.set_Query(pQuery);
        _Pool.execute(futureRDBM);
        waitRDBM();
        if (futureRDBM.get().equals("true")){
            System.out.println("Entre");
            _Pool.execute(ThreadManager.futureSTDM);
            waitSTDM();
        
        }
        _Pool.shutdown();
        System.gc();
        return (String)futureSTDM.get();
        
        
        
        
        
    }

    /**
     *
     * @param args
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws Exception
     */
    public static synchronized void main(String[] args) throws InterruptedException, ExecutionException, Exception {
       
        //ThreadManager A = new ThreadManager();
        //A.iniciar_Directorios();
        
        Facade A = new Facade();
        A.processQuerry("START");
        //A.processQuerry("CREATE DATABASE jajaja");
        A.processQuerry("set DATABASE kkk");
        //A.processQuerry("DROP TABLE Persons");
        /*A.processQuerry("CREATE TABLE Persons\n" +
            "(\n" +
            "A int NOT NULL,\n" +
            "B varchar(255) NOT NULL,\n" +
            "C varchar,\n" +
            "PRIMARY KEY (P_Id)\n" +
            ")");*/
        
      //a.stop();
        
        
        
       
        
        //pool.shutdown();
    }
    
    /**
     *
     */
    public void waitRDBM(){
        while(futureRDBM.isDone()){
            
        }
    }

    /**
     *
     */
    public static void waitSC(){
        while(futureSystem.isDone()){
            
        }
    }

    /**
     *
     */
    public  void waitSTDM(){
        while(futureSTDM.isDone()){
            
        }
    }

    /**
     *
     */
    public void waitSD(){
        while(futureSD.isDone()){
            
        }
    }
    
    /**
     *
     * @throws Exception
     */
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
        new File(_Dir+"DataBases"+"/"+"System_Catalog"+"/"+"Table.txt").createNewFile();
        new File(_Dir+"DataBases"+"/"+"System_Catalog"+"/"+"List.txt").createNewFile();
    }
}

