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
 * @author David Badilla
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
    private String _Dir = "c:\\tmp/";////////////////////////////////PATH
 
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
       _DATA = new Response();
       
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
        if (futureRDBM.get().equals("")){
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
       
       // Facade A = new Facade();
        //A.processQuerry("START");
       // A.processQuerry("SET DATABASE Pp");
       // System.out.println(ThreadManager._DATA.getState());
        //A.processQuerry("UPDATE test5\n"+"SET C3 = Herpes\n"+"WHERE A3 = sifilis; ");
        //A.processQuerry("Delete\n"+"FROM test5;");
        //A.processQuerry("SELECT A2,C2,C3 \n"+ "FROM test4\n"+"JOIN test5\n"+"WHERE C2 = iii;");
        //A.processQuerry("")
        //A.processQuerry("INSERT INTO test5 (A3,B3,C3)\n"+
        //    "VALUES ('cancer2','putas','drogas2')");
        //A.processQuerry("set DATABASE kkk");
        //A.processQuerry("DROP TABLE Persons");
        /*A.processQuerry("CREATE TABLE test5\n" +
            "(\n" +
            "A3 int NOT NULL,\n" +
            "B3 varchar(255) NOT NULL,\n" +
            "C3 varchar,\n" +
            "PRIMARY KEY (A3)\n" +
            ")");
        */
           
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
