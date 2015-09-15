/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.threads;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;


/**
 *
 * @author Shagy
 */
public class RuntimeDBProcessor implements Callable {
    private String _Local_Query = "";
    SystemCatalog _Catalog;
    StoredDataManager _DataM;
    FutureTask futureCatalog;
    FutureTask futureDataM;
            
    RuntimeDBProcessor(){
        _Catalog = new SystemCatalog();
        _DataM= new StoredDataManager();
        futureCatalog = new FutureTask(_Catalog);
        futureDataM = new FutureTask(_DataM);
    }

    @Override
    public String call() throws Exception {
        System.out.println("Entre a RDBM");
        sendQuery();
        
        return "hola";
    }
    
 
    
    public void sendQuery() {
        ThreadManager._Pool.execute(futureCatalog);
        waitCatalog();
        ThreadManager._Pool.execute(futureDataM);
        waitDataM();
    }
    
    public void setQuery(String pQuery){
       _Local_Query=pQuery;
    }
    public void waitCatalog(){
        while(futureCatalog.isDone()){
            
        }
    }
    public void waitDataM(){
        while(futureDataM.isDone()){
            
        }
    }
    
    
    
}
