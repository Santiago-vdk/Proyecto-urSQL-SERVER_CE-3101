/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.threads;

import java.util.concurrent.Callable;
import urSQL.Objects.Table;
import urSQL.logica.Commands;

/**
 *
 * @author Shagy
 */
class StoredData implements Callable{
    private Table _Data;
    private String _Schema;
    private String _Table;
    private String _Mode;
    private Commands _Commands;
    
    public void set_Plan(Table pData , String pSchema, String pTable, String pMode){
        _Data=pData;
        _Schema=pSchema;
        _Table=pTable;
        _Mode=pMode;
    }
    
    private Table retrieve_Table() throws Exception{
        Table Tmp = new Table();
        Tmp.setTable(_Schema, _Table);
        Tmp.charge_Table();
        return Tmp;
    }
    
    private Table commit_Table() throws Exception{
        _Data.commit_Table();
        return _Data;
    }
    
    
    @Override
    public Table call() throws Exception {
        Table Tmp = null;
        if (_Mode.equals("R_T")){
           Tmp = retrieve_Table();
        }
        else  if (_Mode.equals("S_T")){
               commit_Table();
               Tmp = _Data;
               
        }
          
       return Tmp;
    }
    
    public void recieveData(Table Data){
        this._Data=Data;
    }
    
}
