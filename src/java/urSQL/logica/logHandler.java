/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.logica;

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
        String path = pPath.substring(0, pPath.indexOf("build"));
        File fileDir = new File(path + "log.txt");
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileDir, true)));
        String timeStamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        out.println(String.valueOf(pError) + "~" + timeStamp + "~" + "'" + pAction + "'" + "~" + pStatus + "~" + pDuration);
        out.close();
    }

    /**
     *
     * @param pExecutionPlan
     * @throws IOException
     * @throws JSONException
     */
    public void logExecution_Plan(String pExecutionPlan) throws IOException, JSONException {
        File fileDir = new File(_execPlanDir + "Execution_Plan.txt");
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileDir, false)));
        out.println(String.valueOf(pExecutionPlan));
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
        File fileDir = new File(pPath + "log.txt");
        String logMessage = tail(fileDir);

        String[] parameters = logMessage.split("~");

        JSONObject object = new JSONObject();
        object.put("error", parameters[0]);
        object.put("time", parameters[1]);
        object.put("action", parameters[2]);
        object.put("status", parameters[3]);
        object.put("duration", parameters[4]);

        System.out.println(object.toString());

        return object.toString();
    }
    
    /**
     *
     * @return
     * @throws JSONException
     * @throws FileNotFoundException
     */
    public String getExecutionPlan() throws JSONException, FileNotFoundException {
        
        InputStream inputstream = new FileInputStream(_execPlanDir + "Execution_Plan.txt");
        String execution_plan = readStream(inputstream);

        JSONObject object = new JSONObject();
        object.put("ExecPLan", execution_plan);
        System.out.println(object.toString());

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
}
