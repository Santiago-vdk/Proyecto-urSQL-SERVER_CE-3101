/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread.management;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;


/**
 *
 * @author Shagy
 */
public class RuntimeDBProcessor implements Callable {
    private String local_query = "";
    SystemCatalog catalog = new SystemCatalog();

    @Override
    public String call() throws Exception {
        System.out.println("HUbo get");
        Future futureCatalog = ThreadManager.pool.submit(catalog);
        
        catalog.sendQuery(local_query);
 
        return futureCatalog.get() + "Pase por el runtimedatabaseprocessor";
    }
    
 
    
    public void sendQuery(String query) {
        
        
        local_query = query;
        
    }

    
    
    
}
