/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.logica;

import NET.sourceforge.BplusJ.BplusJ.hBplusTree;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Shagy
 */
public class logHandler {

    private static logHandler _singleton = new logHandler();
    private String _execPlanDir = "C:\\tmp\\DataBases\\System_Catalog\\";
    private String _rootDir = null;
    private boolean _treeFlag = false;

    private logHandler() {
    }

    /* Static 'instance' method */
    /**
     *
     * @return
     */
    public static logHandler getInstance() {
        return _singleton;
    }

    /**
     *
     * @param pPath
     */
    public void createLog(String pPath) {

    }

    /**
     *
     * @param pPath
     * @param pError
     * @param pAction
     * @param pStatus
     * @param pDuration
     * @throws IOException
     * @throws JSONException
     */
    public void logEvent(String pPath, boolean pError, String pAction, String pStatus, String pDuration) throws IOException, JSONException {
        //String path = pPath.substring(0, pPath.indexOf("build"));
        File fileDir = new File(_rootDir + "/urSQL/Log/log.txt");
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileDir, true)));
        String timeStamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        out.println(String.valueOf(pError) + "~" + timeStamp + "~" + "" + pAction + "" + "~" + pStatus + "~" + pDuration);
        out.close();

    }

    public static long folderSize(File directory) {
        long length = 0;
        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                length += file.length();
            } else {
                length += folderSize(file);
            }
        }
        return length;
    }

    public String structureSize(String pPath) {
        File fileDir = new File(pPath);
        //System.out.println(fileDir);
        long size = -1;
        if (fileDir.exists()) {
            size = folderSize(fileDir);
        }
        return String.valueOf(size);
    }

    
    public JSONObject systemState(String pPath) throws JSONException{
        JSONObject sysData = new JSONObject();
        
        Runtime runtime = Runtime.getRuntime();
        NumberFormat format = NumberFormat.getInstance();

        //StringBuilder sb = new StringBuilder();
        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        
       /* sb.append("free memory: " + format.format(freeMemory / 1024) + "<br/>");
        sb.append("allocated memory: " + format.format(allocatedMemory / 1024) + "<br/>");
        sb.append("max memory: " + format.format(maxMemory / 1024) + "<br/>");
        sb.append("total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024) + "<br/>");
        
        */
        sysData.put("free_memory", format.format(freeMemory / 1024) + " kb");
        sysData.put("allocated_memory", format.format(allocatedMemory / 1024) + " kb");
        sysData.put("max_memory", format.format(maxMemory / 1024) + " kb");
        sysData.put("total_free_memory", format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024) + " kb");
        sysData.put("folder_size", structureSize(pPath));
        sysData.put("server_status", Facade.getInstance().getOn());
        
        return sysData;
    }
    
    
    public void verifyStructure(String pPath) throws Exception {
        /* Estructura interna del SQL
         -defaultServer
         -urSQL
         -DataBases
         -db1
         *db1_files
         -db2
         *db2_files
         -db3
         *db3_files
         -System_Catalog
         *Execution_Plan.txt
         -Log
         *log.txt
         */

        String urSQLPath = pPath.replace("\\", "/") ;
        
        _rootDir = urSQLPath + "/"; //C:/Users/Shagy/Documents/NetBeansProjects/urSQL/
        System.out.println(_rootDir);

        //Check databases
        File db_root_folder = new File(_rootDir + "/urSQL/DataBases");
        if (!db_root_folder.exists()) {
            db_root_folder.mkdirs();
        }

        //Check sys_catalog
        File syscatalog_root_folder = new File(_rootDir + "/urSQL/DataBases/System_Catalog");
        if (!syscatalog_root_folder.exists()) {
            syscatalog_root_folder.mkdirs();
        }

        //Check log
        File log_root_folder = new File(_rootDir + "/urSQL/Log");
        if (!log_root_folder.exists()) {
            log_root_folder.mkdirs();
        }

        //Linea para online
        //String _Dir = logHandler.getInstance().getRootPath() + "/urSQL/";
        //Linea para offline
        String _Dir = _rootDir + "urSQL/"; //logHandler.getInstance().getRootPath() + "\\urSQL\\";
        try{
        if (!_treeFlag) {
            File Sys_Schemas_Tree_File = new File(_Dir + "DataBases/" + "System_Catalog/" + "Sys_Schemas" + ".tree");
            File Sys_Schemas_Table_File = new File(_Dir + "DataBases/" + "System_Catalog/" + "Sys_Schemas" + ".table");

            //Si el .tree no existe o el .table no existe
            if (!Sys_Schemas_Tree_File.exists() && !Sys_Schemas_Table_File.exists()) {
               // System.out.println("1");
                
                hBplusTree.Initialize(_Dir + "DataBases/" + "System_Catalog/" + "Sys_Schemas" + ".tree",
                        _Dir + "DataBases/" + "System_Catalog/" + "Sys_Schemas" + ".table", 6).Shutdown();
                
            } //Si el .tree existe y el .table no existe
            else if (Sys_Schemas_Tree_File.exists() && !Sys_Schemas_Table_File.exists()) {
                //System.out.println("2");
                //Borro el que si existe
                Sys_Schemas_Tree_File.delete();
                hBplusTree.Initialize(_Dir + "DataBases/" + "System_Catalog/" + "Sys_Schemas" + ".tree",
                        _Dir + "DataBases/" + "System_Catalog/" + "Sys_Schemas" + ".table", 6).Shutdown();
            } //Si el .tree no existe y el table existe
            else if (!Sys_Schemas_Tree_File.exists() && Sys_Schemas_Table_File.exists()) {
                //System.out.println("3");
                //Borro el que si existe
                Sys_Schemas_Table_File.delete();
                hBplusTree.Initialize(_Dir + "DataBases/" + "System_Catalog/" + "Sys_Schemas" + ".tree",
                        _Dir + "DataBases/" + "System_Catalog/" + "Sys_Schemas" + ".table", 6).Shutdown();
            } //Si los dos existen
            else {
            }
            

            File Sys_Tables_Tree_File = new File(_Dir + "DataBases/" + "System_Catalog/" + "Sys_Tables" + ".tree");
            File Sys_Tables_Table_File = new File(_Dir + "DataBases/" + "System_Catalog/" + "Sys_Tables" + ".table");
            //Si el .tree no existe o el .table no existe
            if (!Sys_Tables_Tree_File.exists() && !Sys_Tables_Table_File.exists()) {
               // System.out.println("4");
                hBplusTree.Initialize(_Dir + "DataBases/" + "System_Catalog/" + "Sys_Tables" + ".tree",
                        _Dir + "DataBases/" + "System_Catalog/" + "Sys_Tables" + ".table", 6).Shutdown();
               // System.out.println("4 asdf");
            } //Si el .tree existe y el .table no existe
            else if (Sys_Tables_Tree_File.exists() && !Sys_Tables_Table_File.exists()) {
               // System.out.println("5");
                //Borro el que si existe
                Sys_Tables_Tree_File.delete();
                hBplusTree.Initialize(_Dir + "DataBases/" + "System_Catalog/" + "Sys_Tables" + ".tree",
                        _Dir + "DataBases/" + "System_Catalog/" + "Sys_Tables" + ".table", 6).Shutdown();

            } //Si el .tree no existe y el table existe
            else if (!Sys_Tables_Tree_File.exists() && Sys_Tables_Table_File.exists()) {
                System.out.println("6");
                //Borro el que si existe
                Sys_Tables_Table_File.delete();
                hBplusTree.Initialize(_Dir + "DataBases/" + "System_Catalog/" + "Sys_Tables" + ".tree",
                        _Dir + "DataBases/" + "System_Catalog/" + "Sys_Tables" + ".table", 6).Shutdown();

            } //Si los dos existen
            else {
            }

            File Sys_Columns_Tree_File = new File(_Dir + "DataBases/" + "System_Catalog/" + "Sys_Columns" + ".tree");
            File Sys_Columns_Table_File = new File(_Dir + "DataBases/" + "System_Catalog/" + "Sys_Columns" + ".table");
            //Si el .tree no existe o el .table no existe
            if (!Sys_Columns_Tree_File.exists() && !Sys_Columns_Table_File.exists()) {
                //System.out.println("7");
                hBplusTree.Initialize(_Dir + "DataBases/" + "System_Catalog/" + "Sys_Columns" + ".tree",
                        _Dir + "DataBases/" + "System_Catalog/" + "Sys_Columns" + ".table", 6).Shutdown();
               // System.out.println("asdf");
            } //Si el .tree existe y el .table no existe
            else if (Sys_Columns_Tree_File.exists() && !Sys_Columns_Table_File.exists()) {
                System.out.println("8");
                //Borro el que si existe
                Sys_Columns_Tree_File.delete();
                hBplusTree.Initialize(_Dir + "DataBases/" + "System_Catalog/" + "Sys_Columns" + ".tree",
                        _Dir + "DataBases/" + "System_Catalog/" + "Sys_Columns" + ".table", 6).Shutdown();

            } //Si el .tree no existe y el table existe
            else if (!Sys_Columns_Tree_File.exists() && Sys_Columns_Table_File.exists()) {
                //System.out.println("9");
                //Borro el que si existe
                Sys_Columns_Table_File.delete();
                hBplusTree.Initialize(_Dir + "DataBases/" + "System_Catalog/" + "Sys_Columns" + ".tree",
                        _Dir + "DataBases/" + "System_Catalog/" + "Sys_Columns" + ".table", 6).Shutdown();

            } //Si los dos existen
            else {
            }

            /* hBplusTree.Initialize(_Dir + "DataBases/" + "System_Catalog/" + "Sys_Schemas" + ".tree",
             _Dir + "DataBases/" + "System_Catalog/" + "Sys_Schemas" + ".table", 6);
            
             hBplusTree.Initialize(_Dir + "DataBases/" + "System_Catalog/" + "Sys_Tables" + ".tree",
             _Dir + "DataBases/" + "System_Catalog/" + "Sys_Tables" + ".table", 6);
            
             hBplusTree.Initialize(_Dir + "DataBases/" + "System_Catalog/" + "Sys_Columns" + ".tree",
             _Dir + "DataBases/" + "System_Catalog/" + "Sys_Columns" + ".table", 6);*/
            _treeFlag = true;
        }

        new File(_Dir + "DataBases" + "/" + "System_Catalog" + "/" + "Table.txt").createNewFile();
        new File(_Dir + "DataBases" + "/" + "System_Catalog" + "/" + "List.txt").createNewFile();
        new File(_Dir + "DataBases" + "/" + "System_Catalog" + "/" + "Execution_Plan.txt").createNewFile();
        new File(_Dir + "Log" + "/" + "log.txt").createNewFile();
        
    
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Culpa SANTI");
        }

    }

    /**
     *
     * @param pExecutionPlan
     * @throws IOException
     * @throws JSONException
     */
    public void logExecution_Plan(String pExecutionPlan) throws IOException, JSONException {
        File fileDir = new File(logHandler.getInstance().getRootPath() + "/urSQL/" + "DataBases" + "/" + "System_Catalog" + "/" + "Execution_Plan.txt");
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileDir, false)));
        String[] string_arr = pExecutionPlan.split("\n");
        for (int i = 0; i < string_arr.length; i++) {
            out.println(string_arr[i]);
        }
        out.close();
    }

    /**
     *
     * @param pPath
     * @return
     * @throws JSONException
     */
    public String getLastEventJSON(String pPath) throws JSONException {
        //String path = pPath.substring(0, pPath.indexOf("build"));
        File fileDir = new File(_rootDir + "/urSQL/Log/log.txt");
        String logMessage = tail(fileDir);

        String[] parameters = logMessage.split("~");

        JSONObject object = new JSONObject();
        object.put("error", parameters[0]);
        object.put("time", parameters[1]);
        object.put("action", parameters[2]);
        object.put("status", parameters[3]);
        object.put("duration", parameters[4]);

        //System.out.println(object.toString());

        return object.toString();
    }

    /**
     *
     * @return @throws JSONException
     * @throws FileNotFoundException
     */
    public String getExecutionPlan() throws JSONException, FileNotFoundException {

        InputStream inputstream = new FileInputStream(logHandler.getInstance().getRootPath() + "/urSQL/" + "DataBases" + "/" + "System_Catalog" + "/" + "Execution_Plan.txt");
        String execution_plan = readStream(inputstream);

        JSONObject object = new JSONObject();
        object.put("execplan", execution_plan);
        //System.out.println(object.toString());

        return object.toString();
    }

    /**
     *
     * @param is
     * @return
     */
    public static String readStream(InputStream is) {
        StringBuilder sb = new StringBuilder(); //Fragil
        try {
            Reader r = new InputStreamReader(is, "UTF-8");
            int c = 0;
            while ((c = r.read()) != -1) {
                sb.append((char) c);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    /**
     *
     * @param file
     * @return
     */
    public String tail(File file) {
        RandomAccessFile fileHandler = null;
        try {
            fileHandler = new RandomAccessFile(file, "r");
            long fileLength = fileHandler.length() - 1;
            StringBuilder sb = new StringBuilder();

            for (long filePointer = fileLength; filePointer != -1; filePointer--) {
                fileHandler.seek(filePointer);
                int readByte = fileHandler.readByte();

                if (readByte == 0xA) {
                    if (filePointer == fileLength) {
                        continue;
                    }
                    break;

                } else if (readByte == 0xD) {
                    if (filePointer == fileLength - 1) {
                        continue;
                    }
                    break;
                }
                sb.append((char) readByte);
            }
            String lastLine = sb.reverse().toString();
            return lastLine;
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fileHandler != null) {
                try {
                    fileHandler.close();
                } catch (IOException e) {
                    /* ignore */
                }
            }
        }
    }

    public String getRootPath() {
        return _rootDir;
    }

}
