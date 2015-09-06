/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.servicios;

import javax.json.JsonArray;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import urSQL.logica.Parser;


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

    /**
     * Retrieves representation of an instance of urSQL.servicios.WebAppResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of WebAppResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
    
    
    @GET
    @Path("/data/QueryStatus")
    @Produces("application/json")
    public String getQueryStatus(){
        return "{status:success/fail,time:timestamp,action:accionejecutada,message:error/rowsreturned,duration:duration}";
        
    }
    
    @GET
    @Path("/data/query.json")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray getStatus() throws JSONException{
    JSONObject jo = new JSONObject();
    jo.put("id", 0);
    jo.put("name", "Santi");
    

    JSONArray ja = new JSONArray();
    ja.put(jo);
        
    return ja;
        
        //return "{status:success/fail,time:timestamp,action:accionejecutada,message:error/rowsreturned,duration:duration}";
        
    }
    
    @POST
    @Path("/Query")
    @Produces("application/json")
    @Consumes("application/json")
    public String postQuery(String msg){
        Parser parser = new Parser();
      //  parser.parseQuery(msg);
        return "{status:200}";
    }
    
    
    
    
}
