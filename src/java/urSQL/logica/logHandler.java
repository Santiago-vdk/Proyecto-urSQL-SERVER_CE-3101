/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.logica;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
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

    private logHandler() {
    }

    /* Static 'instance' method */
    public static logHandler getInstance() {
        return _singleton;
    }

    public void createLog(String pPath){

    }

    public void logEvent(String pPath, boolean pError , String pAction, String pStatus, String pDuration ) throws IOException, JSONException {
        String path = pPath.substring(0, pPath.indexOf("build"));
        File fileDir = new File(path + "log.txt");
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileDir, true)));
        String timeStamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        out.println(String.valueOf(pError) + "~" + timeStamp + "~" + "'" + pAction + "'" + "~" + pStatus + "~" + pDuration);
        out.close();
    }

    public String getLastEventJSON(String pPath) throws JSONException {
        String path = pPath.substring(0, pPath.indexOf("build"));
        File fileDir = new File(path + "log.txt");
        String logMessage = tail(fileDir);
        
        String[] parameters = logMessage.split("~");
        
        
        JSONObject object = new JSONObject();
        object.put("error", parameters[0]);
        object.put("time", parameters[1]);
        object.put("action", parameters[2]);
        object.put("status", parameters[3]);
        object.put("duration", parameters[4]);
        
        System.out.println( object.toString());
        
        return object.toString();
    }
    
    public String tail( File file ) {
    RandomAccessFile fileHandler = null;
    try {
        fileHandler = new RandomAccessFile( file, "r" );
        long fileLength = fileHandler.length() - 1;
        StringBuilder sb = new StringBuilder();

        for(long filePointer = fileLength; filePointer != -1; filePointer--){
            fileHandler.seek( filePointer );
            int readByte = fileHandler.readByte();

            if( readByte == 0xA ) {
                if( filePointer == fileLength ) {
                    continue;
                }
                break;

            } else if( readByte == 0xD ) {
                if( filePointer == fileLength - 1 ) {
                    continue;
                }
                break;
            }

            sb.append( ( char ) readByte );
        }

        String lastLine = sb.reverse().toString();
        return lastLine;
    } catch( java.io.FileNotFoundException e ) {
        e.printStackTrace();
        return null;
    } catch( java.io.IOException e ) {
        e.printStackTrace();
        return null;
    } finally {
        if (fileHandler != null )
            try {
                fileHandler.close();
            } catch (IOException e) {
                /* ignore */
            }
    }
}

}
