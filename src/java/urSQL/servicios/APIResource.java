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
import org.json.JSONArray;
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
    

@POST
    @Path("/ExecuteQuery")
    @Produces("application/json")
    @Consumes("application/json")
    public String postQuery(String msg) throws JSONException, InterruptedException, IOException, Exception {
        /* No borrar
         long startTime = System.nanoTime();
         Thread.sleep(1000);
        
        
         long endTime = System.nanoTime();
         long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.

         String path = ctx.getRealPath("/");
         logHandler.getInstance().logEvent(path, false, msg, "executed", String.valueOf(duration));
         */
        /*  JSONArray matrix = new JSONArray();

         JSONArray fila1 = new JSONArray();
         fila1.put("1");
         fila1.put("2");
         fila1.put("3");
        
        
         JSONArray fila2 = new JSONArray();
         fila2.put("4");
         fila2.put("5");
         fila2.put("6");
        
         JSONArray fila3 = new JSONArray();
         fila3.put("7");
         fila3.put("8");
         fila3.put("9");
        
         matrix.put(fila1);
         matrix.put(fila2);
         matrix.put(fila3);
        
         JSONArray col_names = new JSONArray();
        
         col_names.put("col1");
         col_names.put("col2");
         col_names.put("col3");
        
         JSONObject master = new JSONObject();
        
         master.put("Valores", matrix);
         master.put("Columnas", col_names);*/
        
        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
        Object obj = parser.parse(msg);
        org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) obj;
        String query = (String) jsonObject.get("source-code");

//        String result = Facade.getInstance().processQuerry(query);

      //Si en se debe devolver una tabla se cambia bandera en json
        //Hago loggin de las caracteristicas del objeto respuesta
      /*
         {
         "table":true/false,
         "data":{data}
         }
         */
        
        JSONArray matrix = new JSONArray();

         JSONArray fila1 = new JSONArray();
         fila1.put("1568");
         fila1.put("Leche");
         fila1.put("20");
         fila1.put("550");
        
        
         JSONArray fila2 = new JSONArray();
         fila2.put("7954");
         fila2.put("Pepino");
         fila2.put("10");
         fila2.put("50");

        
         JSONArray fila3 = new JSONArray();
         fila3.put("3649");
         fila3.put("Jabon");
         fila3.put("40");
         fila3.put("300");

         matrix.put(fila1);
         matrix.put(fila2);
         matrix.put(fila3);
        
         JSONArray col_names = new JSONArray();
        
         col_names.put("Codigo");
         col_names.put("Nombre");
         col_names.put("Cantidad");
         col_names.put("Precio");
         
        // String res = Facade.getInstance().processQuerry(msg);
        JSONObject obj2 = new JSONObject();
        obj2.put("table", "true"); //OJO
        obj2.put("data", matrix);
        
        
        
        return obj2.toString();
    }
}
