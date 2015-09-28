package urSQL.servicios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
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
import org.json.simple.parser.JSONParser;
import urSQL.logica.logHandler;
import urSQL.logica.Facade;
import urSQL.threads.Response;

/**
 * REST Web Service
 *
 * @author Shagy
 */
@Path("webapp")
public class WebAppResource {

    private boolean _flagStarted = false;
    
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
    public String postQuery(String msg) throws JSONException, InterruptedException, IOException, Exception {
    /*    if(!_flagStarted){
            contextChecker();
            _flagStarted = true;
        }*/
        /* No borrar
         long startTime = System.nanoTime();
         Thread.sleep(1000);
        
         long endTime = System.nanoTime();
         long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
         String path = ctx.getRealPath("/");
         */

        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
        Object obj = parser.parse(msg);
        org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) obj;
        String query = (String) jsonObject.get("source-code");

        long startTime = System.nanoTime();

        Facade.getInstance().processQuerry(query);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;  //divide by 1000000 to get milliseconds.
        
        Response rs = Facade.getInstance().getResponse();
        
        logHandler.getInstance().logEvent("not needed",rs.get_ErrorFlag() , rs.getConsulta(), rs.getState(), String.valueOf(duration) + " ms");
        
        JSONObject obj2 = new JSONObject();
        
        System.out.println(rs.get_TableFlag());
        
        obj2.put("table", rs.get_TableFlag()); //OJO
        
        //Si no hay datos no los devuelvo
        if(rs.get_TableFlag()){
            obj2.put("Valores", rs.getTabla());
            obj2.put("Columnas", rs.getColumnas());
        }
        else {
        
        obj2.put("Columnas","none");
        obj2.put("Valores","none");
        }

        return obj2.toString();
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
    public String queryLog(String msg) throws JSONException {
        // Object serverName = new InitialContext().lookup("serverName");

        String path = ctx.getRealPath("/");
        String JSONResponse = logHandler.getInstance().getLastEventJSON("not-used");

        return JSONResponse;

        /* logHandler.getInstance().logEvent("dp", false, "asdf", "executed", "Test");
        
         logHandler.getInstance().logExecution_Plan("asdfasdfasdf\nasdfa");*/
    }

    @GET
    @Path("/server_state")
    @Produces("application/json")
    public String getServerState() throws JSONException {
        String state = String.valueOf(Facade.getInstance().getOn());
        JSONObject obj = new JSONObject();
        obj.put("state", state);
        return obj.toString();

    }

    @GET
    @Path("/data/executionPlan")
    @Produces("application/json")
    public String getExecPlan() throws JSONException, FileNotFoundException {
        // Object serverName = new InitialContext().lookup("serverName");

        String plan = logHandler.getInstance().getExecutionPlan();

        return plan;

        /* logHandler.getInstance().logEvent("dp", false, "asdf", "executed", "Test");
        
         logHandler.getInstance().logExecution_Plan("asdfasdfasdf\nasdfa");*/
    }

    @GET
    @Path("/initialSetup")
    @Produces("text/html")
    public String contextChecker() {
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

    @GET
    @Path("/data/status")
    @Produces("application/json")
    public String getStatus() throws JSONException {
        String realPath = ctx.getRealPath("/");

            //Si el server esta online:
        //realPath = realPath.substring(0, realPath.indexOf("/apps"));
        //Si el server esta offline
        realPath = realPath.substring(0, realPath.indexOf("build") - 1);
        realPath = realPath + "\\urSQL\\";

        /* JSONObject obj = new JSONObject();

         obj.put("RuntimeDBProcessor", "Running...");
         obj.put("SystemCatalog", "Running...");
         obj.put("StoredDataManager", "Running...");
         obj.put("StoredData", "Running...");*/
        return logHandler.getInstance().systemState(realPath).toString();
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
       /* JSONArray container2 = new JSONArray();

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

         System.out.println(globalarray.toString());*/
        return globalarray.toString();
    }

}
