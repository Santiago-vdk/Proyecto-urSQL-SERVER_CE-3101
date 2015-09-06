/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread.management;

import java.util.concurrent.Callable;

/**
 *
 * @author Shagy
 */
class StoredData implements Callable{

    @Override
    public String call() {
        System.out.println("Initialized by RDBP SD.");
        return "Stored Data Ready";
    }
    
}
