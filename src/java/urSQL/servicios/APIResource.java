/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.servicios;

import java.io.IOException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import org.json.JSONException;
import org.json.JSONObject;
import urSQL.logica.Facade;
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

            //Si no hay datos no los devuelvo
            if (rs.get_TableFlag()) {
                obj2.put("Valores", rs.getTabla());
            }

            obj2.put("table", rs.get_TableFlag()); //OJO
            obj2.put("Valores", "none");

            return obj2.toString();
        } catch (Exception e) {
            return "Server Exception, could be malformed JSON";
        }

    }
    
    
}
