/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.servicios;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import org.json.JSONException;
import org.json.JSONObject;
import urSQL.logica.Facade;
import urSQL.logica.logHandler;
import urSQL.threads.Response;

/**
 * REST Web Service
 *
 * @author Shagy
 */
@Path("API")
public class APIResource {

    @Context
    private UriInfo context;

    private boolean _flagStarted = false;
    
    
    @Context
    ServletContext ctx;

    /**
     * Creates a new instance of APIResource
     */
    public APIResource() {
    }

    @POST
    @Path("/ExecuteQuery")
    @Produces("application/json")
    @Consumes("application/json")
    public String postQuery(String msg) throws JSONException, InterruptedException, IOException, Exception {
        try {
            if (!_flagStarted) {
                contextCheckerAPI();
                Thread.sleep(1500);
                Facade.getInstance().processQuerry("START");
                _flagStarted = true;
            }

            org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
            Object obj = parser.parse(msg);
            org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) obj;
            String query = (String) jsonObject.get("source-code");

            System.out.println(query);

            Facade.getInstance().processQuerry(query);

            Response rs = Facade.getInstance().getResponse();

            JSONObject obj2 = new JSONObject();

            //Si hay datos no los devuelvo
            if (rs.get_TableFlag()) {
                obj2.put("table", String.valueOf(rs.get_TableFlag())); 
                obj2.put("data", rs.getTabla());
                
            }
            else {
                obj2.put("table", String.valueOf(rs.get_TableFlag())); 
                obj2.put("datas", "none");
            }


            return obj2.toString();
        } catch (Exception e) {
            return "Server Exception, could be malformed JSON";
        }

    }
    
    public String contextCheckerAPI() {
        try {
            String realPath = ctx.getRealPath("/");

            //Si el server esta online:
           // realPath = realPath.substring(0, realPath.indexOf("urSQL/apps"));
            
            //Si el server esta offline
            
           realPath = realPath.substring(0, realPath.indexOf("build") - 1);
   
            logHandler.getInstance().verifyStructure(realPath);
            logHandler.getInstance().logEvent("not-used", false, "START SERVER", "executed", "-");

            //Pongo datos en el log para probar
            //
            //Pruebo el plan
            //logHandler.getInstance().logExecution_Plan("asdfasdfasdf\nasdfa");
            
            
            //_flagStarted = true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating urSQL Folder Structure.";
        }

        return "true";
    }
    
    
}
