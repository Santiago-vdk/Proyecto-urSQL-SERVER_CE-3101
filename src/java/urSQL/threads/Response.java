/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.threads;

import urSQL.Objects.Table;

/**
 *
 * @author David
 */
public class Response {
    
    private String _State;
    private Table _Tabla=null;
    
    Response(){
        _State="";
    }
    
    public void setTabla(Table pTabla){
        _Tabla=pTabla;
    }
    public Table getTabla(){
        return _Tabla;
    }
    
}
