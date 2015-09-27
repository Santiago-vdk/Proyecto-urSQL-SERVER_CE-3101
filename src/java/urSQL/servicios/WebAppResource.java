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
import java.nio.file.Files;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import urSQL.logica.Facade;
import urSQL.logica.logHandler;
import urSQL.threads.ThreadManager;

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
        JSONArray matrix = new JSONArray();

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
        master.put("Columnas", col_names);
        
        return master.toString();
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
    public String queryLog(String msg) throws JSONException, IOException, NamingException {
        // Object serverName = new InitialContext().lookup("serverName");

        String path = ctx.getRealPath("/");
        String JSONResponse = logHandler.getInstance().getLastEventJSON("not-used");

        return JSONResponse;

        /* logHandler.getInstance().logEvent("dp", false, "asdf", "executed", "Test");
        
         logHandler.getInstance().logExecution_Plan("asdfasdfasdf\nasdfa");*/
    }

    @GET
    @Path("/data/executionPlan")
    @Produces("application/json")
    public String getExecPlan(String msg) throws JSONException, IOException, NamingException {
        // Object serverName = new InitialContext().lookup("serverName");

        String plan = logHandler.getInstance().getExecutionPlan();

        return plan;

        /* logHandler.getInstance().logEvent("dp", false, "asdf", "executed", "Test");
        
         logHandler.getInstance().logExecution_Plan("asdfasdfasdf\nasdfa");*/
    }

    @GET
    @Path("/initialSetup")
    @Produces("application/json")
    public String contextChecker() {
        try {
            String realPath = ctx.getRealPath("/");
            realPath = realPath.substring(0, realPath.indexOf("/apps"));
            logHandler.getInstance().verifyStructure(realPath);
            logHandler.getInstance().logEvent("not-used", false, "START SERVER", "executed", "-");

            //Pongo datos en el log para probar
            //
            //Pruebo el plan
            logHandler.getInstance().logExecution_Plan("asdfasdfasdf\nasdfa");

        } catch (Exception e) {
            return "Error generating urSQL Folder Structure.";
        }

        return "true";
    }

    @GET
    @Path("/data/status")
    @Produces("application/json")
    public String getStatus() throws JSONException {
        JSONObject obj = new JSONObject();

        obj.put("RuntimeDBProcessor", "Running...");
        obj.put("SystemCatalog", "Running...");
        obj.put("StoredDataManager", "Running...");
        obj.put("StoredData", "Running...");

        return obj.toString();
    }

    @GET
    @Path("/data/dataBases")
    @Produces("application/json")
    public String listDataBases() throws JSONException {
        JSONObject obj = new JSONObject();
        JSONArray dbs = new JSONArray();

        dbs.put("db1");
        dbs.put("db2");
        dbs.put("db3");

        obj.put("schemas", dbs);

        return obj.toString();
    }

    @GET
    @Path("/data/refreshSchemaTree")
    @Produces("application/json")
    public String getCurrentSchemaTree() throws JSONException {
        JSONArray globalarray = new JSONArray();

        JSONArray container = new JSONArray();

        JSONObject tables = new JSONObject();
        JSONArray tablasdedb1 = new JSONArray();

        tablasdedb1.put("Estudiantes");
        tablasdedb1.put("Profesores");

        tables.put("tables", tablasdedb1);

        JSONObject name = new JSONObject();
        name.put("name", "db1");

        container.put(name);
        container.put(tables);

        globalarray.put(container);

        /////////////////////////////////////////////
        JSONArray container2 = new JSONArray();

        JSONObject tables2 = new JSONObject();
        JSONArray tablasdedb2 = new JSONArray();

        tablasdedb2.put("Estudiantes2");
        tablasdedb2.put("Profesores2");

        tables2.put("tables", tablasdedb2);

        JSONObject name2 = new JSONObject();
        name2.put("name", "db2");

        container2.put(name2);
        container2.put(tables2);

        globalarray.put(container2);

        System.out.println(globalarray.toString());
        return globalarray.toString();
    }
    
    @GET
    @Path("/test")
    @Produces("application/json")
    public String test() throws JSONException, Exception {
        Facade facade = new Facade();
        String t = facade.processQuerry("Start");
        
        return t;
    }
    
    
    
}
