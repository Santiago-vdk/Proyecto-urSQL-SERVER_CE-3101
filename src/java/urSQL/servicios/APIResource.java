/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.servicios;

import java.io.IOException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author Shagy
 */
@Path("API")
public class APIResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of APIResource
     */
    public APIResource() {
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
        obj.put("API", "true");
        
        Thread.sleep(1000);
        
        return obj.toString();
    }
}
