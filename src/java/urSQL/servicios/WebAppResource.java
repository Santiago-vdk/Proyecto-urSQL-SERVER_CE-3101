/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.servicios;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * REST Web Service
 *
 * @author Shagy
 */
@Path("webapp")
public class WebAppResource  {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of WebAppResource
     */
    public WebAppResource() {
    }
    
    @POST
    @Path("/ExecuteQuery")
    @Produces("application/json")
    @Consumes("application/json")
    public String postQuery(String msg) throws JSONException{
        System.out.println(msg);
        JSONObject obj = new JSONObject();
        obj.put("data", "daca");
        return obj.toString();
    }
    
    
    @GET
    @Path("/data/queryLog")
    @Produces("application/json")
    public String queryLog(String msg){
        return null;
    }
   
}
