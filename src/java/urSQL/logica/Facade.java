/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.logica;

import java.util.concurrent.ExecutionException;
import urSQL.threads.ThreadManager;

/**
 *
 * @author RafaelAngel
 */
public class Facade {
    boolean _On = false;
    ThreadManager Threads;

    public Facade() throws Exception {
        this.Threads = new ThreadManager();
    }
    
    /**
     *
     * @param pQuerry
     */
    public void processQuerry(String pQuerry) throws InterruptedException, ExecutionException, Exception{
        if(_On){
            
            if(pQuerry.toUpperCase().compareTo("STOP")==0){
                _On = false;
                 //finaliza los threads
            }
            else{
                Threads.sendQuery(pQuerry);
            }
           
        }
        else if(pQuerry.toUpperCase().compareTo("START")==0){
            _On = true;
            
        }
        
    }
    
    public String[] getList(){
        return ThreadManager.ListDB;
    }
    
    
    
}
