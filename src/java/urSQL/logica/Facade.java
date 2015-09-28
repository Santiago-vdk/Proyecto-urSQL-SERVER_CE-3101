/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.logica;

import java.util.concurrent.ExecutionException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import urSQL.threads.Response;
import urSQL.threads.ThreadManager;

/**
 *
 * @author RafaelAngel
 */
public class Facade {

    boolean _On = false;
    ThreadManager Threads;

    private static Facade _singleton = new Facade();

    private Facade() {

    }
    
    public static Facade getInstance(){
        return _singleton;
    }

    public boolean getOn(){
        return _On;
    }
    
    /**
     *
     * @param pQuery
     * @throws java.lang.InterruptedException
     * @throws java.util.concurrent.ExecutionException
     */
    public void processQuerry(String pQuery) throws InterruptedException, ExecutionException, Exception {
        if (_On) {
            if (pQuery.toUpperCase().compareTo("STOP") == 0) {
                _On = false;
                Threads.stop(); //fragil
            } else {
                 Threads.sendQuery(pQuery);
            }
        } else if (pQuery.toUpperCase().compareTo("START") == 0) {
            this.Threads = new ThreadManager();
            _On = true;

        }
    }

    public String[] getList() {
        return ThreadManager.ListDB;
    }
    public Response getResponse(){
        return ThreadManager.getResponse();
    }
}