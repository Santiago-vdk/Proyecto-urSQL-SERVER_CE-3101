/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import urSQL.Objects.Table;

/**
 *
 * @author Shagy
 */
public class SystemCatalog implements Callable{
    
    private final String _Dir="c:\\tmp/DataBases/System_Catalog/";
    private final String _TSchema= "Sys_Schemas";
    private final String _TTable= "Sys_Tables";
    private final String _TColumn= "Sys_Columns";
    private List<String> _Columns_Schema;
    private List<String> _Columns_Tables;
    private List<String> _Columns_Columns;
    private Table _Table_Schemas;
    private Table _Table_Tables;
    private Table _Table_Columns;
    public String local = "";
    public String _Schema="";
    public String _Table="";
    public String _Column="";
    public String _PK="";
    public String _Modifider="";
    public String _Type="";
    public String _Order="";
    public String _Mode="";
    
    
    SystemCatalog() throws Exception{
        
        setColumns();
        chargeTables();
        
    }
    
    
    public void set_Plan(String _Schema,String _Table,String _Column,String _PK,
            String _Modifider,String _Type,String _Order,String _Mode){
        
        
        this._Column=_Column;
        this._Mode=_Mode;
        this._Modifider=_Modifider;
        this._PK=_PK;
        this._Schema=_Schema;
        this._Table=_Table;
        this._Type=_Type;
        this._Order=_Order;
        
        
        
    }
    
    private void chargeTables() throws Exception{
        _Table_Schemas= new Table();
        _Table_Tables= new Table();
        _Table_Columns= new Table();
        _Table_Schemas.setTable(_Dir, _TSchema);
        _Table_Tables.setTable(_Dir, _TTable);
        _Table_Columns.setTable(_Dir, _TColumn);
        _Table_Schemas.charge_Table_Sys();
        _Table_Tables.charge_Table_Sys();
        _Table_Columns.charge_Table_Sys();
        _Table_Schemas.setColumns(_Columns_Schema);
        _Table_Tables.setColumns(_Columns_Tables);
        _Table_Columns.setColumns(_Columns_Columns);
        
    }
    
    private void setColumns(){
        _Columns_Schema= new ArrayList<String>();
        _Columns_Tables= new ArrayList<String>();
        _Columns_Columns= new ArrayList<String>();
        _Columns_Schema.add("Schema");
        _Columns_Tables.add("Schema");
        _Columns_Tables.add("Table");
        _Columns_Columns.add("Schema");
        _Columns_Columns.add("Table");
        _Columns_Columns.add("Column");
        _Columns_Columns.add("PK");
        _Columns_Columns.add("Modifider");
        _Columns_Columns.add("Type");
        _Columns_Columns.add("Order");
    }
    
    
    
    /////////////////////////// FUNCIONES DE VERIFICACION///////////////////////////
    public boolean verify_Schema(String pSchema){
        return _Table_Schemas.exists("Schema", pSchema);
    }
    public boolean verify_Pk(String pSchema,String pTable, String pColumn){
        Table Tmp= _Table_Columns;
        Tmp.elim_fila("Schema", null, pSchema, "=");
        Tmp.elim_fila("Table", null, pTable, "=");
        Tmp.elim_fila("Column", null, pColumn, "=");
        return Tmp.exists("PK", "true");
    }
    public boolean verify_Column_Type(String pSchema,String pTable, String pColumn, String pType){
        Table Tmp= _Table_Columns;
        Tmp.elim_fila("Schema", null, pSchema, "=");
        Tmp.elim_fila("Table", null, pTable, "=");
        Tmp.elim_fila("Column", null, pColumn, "=");
        return Tmp.exists("Type", pType);
        
    }
    public boolean verify_Column(String pSchema,String pTable, String pColumn){
        Table Tmp= _Table_Columns;
        Tmp.elim_fila("Schema", null, pSchema, "=");
        Tmp.elim_fila("Table", null, pTable, "=");
        return Tmp.exists("Column",pColumn);
        
    }
    public boolean verify_Table(String pSchema,String pTable){
        Table Tmp= _Table_Tables;
        Tmp.elim_fila("Schema", null, pSchema, "=");
        return Tmp.exists("Table", pTable);
    }
    
    public boolean verify_Column_Modifider(String pSchema,String pTable, String pColumn){
        Table Tmp= _Table_Columns;
        Tmp.elim_fila("Schema", null, pSchema, "=");
        Tmp.elim_fila("Table", null, pTable, "=");
        Tmp.elim_fila("Column", null, pColumn, "=");
        return Tmp.exists("Modifider", "true");
        
    }
    /////////////////////////////////////////////////////////////////////////////
    
    
    
    /////////////////// FUNCIONES DE INSERCION//////////////////////////////////
    
    public void insert_Schema(String pSchema) throws Exception{
        List<String> Tmp = new ArrayList<String>();
        Tmp.add(pSchema);
        _Table_Schemas.add(Tmp);
        _Table_Schemas.commit_Table();
        chargeTables();
    }
    
    public void insert_Table(String pSchema,String pTable) throws Exception{
        List<String> Tmp = new ArrayList<String>();
        Tmp.add(pSchema);
        Tmp.add(pTable);
        _Table_Tables.add(Tmp);
        _Table_Tables.commit_Table();
        chargeTables();
    }
    
    public void insert_Column(String pSchema,String pTable,String pColumn,String pPK,
            String pModifider,String pType,String pOrder ) throws Exception{
        List<String> Tmp = new ArrayList<String>();
        Tmp.add(pSchema);
        Tmp.add(pTable);
        Tmp.add(pColumn);
        Tmp.add(pPK);
        Tmp.add(pModifider);
        Tmp.add(pType);
        Tmp.add(pOrder);
        _Table_Columns.add(Tmp);
        _Table_Columns.commit_Table();
        chargeTables();
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    
    
    
    
    ////////FUNCIONES DE RECUPERACION DE LA METADATA DE LAS COLUMNAS////////////
    
    public String re_Columns(String pSchema,String pTable){
        List<String> Tmp1= new ArrayList<String>();
        Tmp1.add("Column");
        Table Tmp2 = _Table_Columns;
        Tmp2.elim_fila("Schema", null,pSchema ,"=");
        Tmp2.elim_fila("Table", null,pTable ,"=");
        Tmp2.setColumns(Tmp1);
        List<List> Tmp3= new ArrayList<List>();
        Tmp3.addAll(Tmp2.get_Values());
        String Tmp4="";
        for(int i=0;i<Tmp3.size();i++){
            if(Tmp3.size()==i+1){
                Tmp4= Tmp4+ Tmp3.get(i).get(0).toString();
            }
            else{
                Tmp4= Tmp4+ Tmp3.get(i).get(0).toString()+",";
            }
        }
        return Tmp4;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    
    
    @Override
    public String call() throws InterruptedException, Exception {

        String Result="";
        switch(_Mode){
        
            case "V_TY":
                Result = Boolean.toString(verify_Column_Type(_Schema,_Table, _Column, _Type));
            case "V_PK":
                Result = Boolean.toString(verify_Pk(_Schema,_Table,_Column));
            case "V_S":
                Result = Boolean.toString(verify_Schema(_Schema));
            case "V_T":
                Result = Boolean.toString(verify_Table(_Schema,_Table));
            case "V_C":
                Result = Boolean.toString(verify_Column(_Schema,_Table,_Column));
            case "V_M":
                Result = Boolean.toString(verify_Column_Modifider(_Schema,_Table,_Column));
            case "I_S":
               insert_Schema(_Schema);
                Result= "true";
            case "I_T":
                insert_Table(_Schema,_Table);
                Result= "true";
            case "I_C":
                insert_Column(_Schema,_Table,_Column,_PK,_Modifider,_Type,_Order);
                Result= "true";
            case "R_C":
                Result = re_Columns(_Schema,_Table);    
        }
        
        
        return Result;
    }
    

    public void sendQuery(String local_query) {
        local = local_query;
    }
    

    
    
    
}
