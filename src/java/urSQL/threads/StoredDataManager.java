/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.threads;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 *
 * @author Shagy
 */
public class StoredDataManager implements Callable{
    String Data="";
    StoredData DataS;
    FutureTask _FutureStoredData;
    
    StoredDataManager(){
      DataS= new StoredData();
      _FutureStoredData=new FutureTask(DataS);  
    }
    
    
    @Override
    public String call() throws InterruptedException, ExecutionException {
        System.out.println("Initialized by RDBP SDM.");
        ThreadManager._Pool.execute(_FutureStoredData);
        waitSD();
        
        
        return Data;
    }
    
   
    public void waitSD(){
        while(_FutureStoredData.isDone()){
            
        }
    }
}
