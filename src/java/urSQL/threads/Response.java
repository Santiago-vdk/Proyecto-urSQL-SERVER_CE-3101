/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.threads;
 
import org.json.JSONArray;
import org.json.JSONObject;
import urSQL.Objects.Table;
 
/**
 *
 * @author David
 */
public class Response {
   
    private String _State= "sucessful";
    private JSONArray _Tabla=null;
    private JSONArray Columnas= null;
    private String Consulta = "";
    private String Error = "";
    private boolean Tabla_Flag = false;
    private boolean Error_Flag = false;
   
    Response(){
       
    }
     public void setError(String pError){
         Error = pError;
     }
     
     public String getError(){
         return Error;
     }
    public void setConsulta(String pConsulta){
        Consulta = pConsulta;
    }
   
    public String getConsulta(){
        return Consulta;
    }
    /**
     *
     * @param pTabla
     */
    public void setColumnas(JSONArray pColumnas){
        Columnas=pColumnas;
    }
 
    /**
     *
     * @return
     */
    public JSONArray getColumnas(){
        return Columnas;
    }
    /**
     *
     * @param pTabla
     */
    public void setTabla(JSONArray pTabla){
        _Tabla=pTabla;
    }
 
    /**
     *
     * @return
     */
    public JSONArray getTabla(){
        return _Tabla;
    }
   
    /**
     * @return the _State
     */
    public String getState() {
        return _State;
    }
 
    /**
     * @param _State the _State to set
     */
    public void setState(String _State) {
        this._State = _State;
    }
   
    public void set_TableFlag(boolean pFlag){
        Tabla_Flag = pFlag;
    }
    public void set_ErrorFlag(boolean pFlag){
        Error_Flag = pFlag;
    }
   
    public boolean get_TableFlag(){
        return Tabla_Flag;
    }
    public boolean get_ErrorFlag(){
        return Error_Flag;
    }
   
}