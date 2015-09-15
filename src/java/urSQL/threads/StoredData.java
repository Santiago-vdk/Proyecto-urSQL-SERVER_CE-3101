/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.threads;

import java.util.concurrent.Callable;

/**
 *
 * @author Shagy
 */
class StoredData implements Callable{
    String Data="";
    @Override
    public String call() {
        System.out.println("Initialized by RDBP SD.");
        return Data + " Stored Data Ready";
        
    }
    
    public void recieveData(String Data){
        this.Data=Data;
    }
    
}
