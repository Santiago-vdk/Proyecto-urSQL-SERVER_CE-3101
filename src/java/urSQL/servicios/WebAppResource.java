/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.servicios;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import org.json.JSONException;
import org.json.JSONObject;
import urSQL.logica.logHandler;

/**
 * REST Web Service
 *
 * @author Shagy
 */
@Path("webapp")
public class WebAppResource {

    @Context
    private UriInfo context;

    @Context
    ServletContext ctx;

    /**
     * Creates a new instance of WebAppResource
     */
    public WebAppResource() {
    }

    /**
     *
     * @param msg
     * @return
     * @throws JSONException
     * @throws InterruptedException
     * @throws IOException
     */
    @POST
    @Path("/ExecuteQuery")
    @Produces("application/json")
    @Consumes("application/json")
    public String postQuery(String msg) throws JSONException, InterruptedException, IOException {

        /* No borrar
        long startTime = System.nanoTime();
        Thread.sleep(1000);
        
        
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.

        String path = ctx.getRealPath("/");
        logHandler.getInstance().logEvent(path, false, msg, "executed", String.valueOf(duration));
        */
        
        JSONObject obj = new JSONObject();
        obj.put("dmata", "daca");
        
        Thread.sleep(1000);
        
        return obj.toString();
    }

    /**
     *
     * @param msg
     * @return
     * @throws JSONException
     * @throws java.io.IOException
     * @throws javax.naming.NamingException
     */
    @GET
    @Path("/data/log")
    @Produces("application/json")
    public String queryLog(String msg) throws JSONException, IOException, NamingException  {
        Object serverName = new InitialContext().lookup("serverName");
        
        
       // String path = ctx.getRealPath("/");
        String JSONResponse = logHandler.getInstance().getLastEventJSON(serverName.toString());
        //logHandler.getInstance().logExecution_Plan("asdfasdfasdfasdfa");
        //logHandler.getInstance().getExecutionPlan();
        return JSONResponse;
    }

}
