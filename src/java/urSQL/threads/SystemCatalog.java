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
public class SystemCatalog implements Callable{
    String local = "";
    
    @Override
    public String call() throws InterruptedException {

       
        System.out.println("Entre a System Catalog");
        
        
        return local + "pase por el catalog";
    }
    

    public void sendQuery(String local_query) {
        local = local_query;
    }
    

    
    
    
}
