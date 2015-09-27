/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.logica;

import NET.sourceforge.BplusJ.BplusJ.BplusTree;
import NET.sourceforge.BplusJ.BplusJ.hBplusTree;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import urSQL.Objects.Condition;
import urSQL.Objects.Table;


/**
 *
 * @author David
 */
public class Commands {
    private final String _Dir= "c:\\tmp/DataBases/";
    
    
    void create_System_Catalog() throws Exception{
        boolean mkdir = new File(_Dir+"System_Catalog").mkdir();
        hBplusTree.Initialize(_Dir+"System_Catalog/"+"Sys_Schemas"+".tree",
                _Dir+"System_Catalog/"+"Sys_Schemas"+".table", 6);
        hBplusTree.Initialize(_Dir+"System_Catalog/"+"Sys_Tables"+".tree",
                _Dir+"System_Catalog/"+"Sys_Tables"+".table", 6);
        hBplusTree.Initialize(_Dir+"System_Catalog/"+"Sys_Columns"+".tree",
                _Dir+"System_Catalog/"+"Sys_Columns"+".table", 6);
    }
    
    private void create_Schema(String pSchema){
        boolean mkdir = new File(_Dir+pSchema).mkdir();        
    }
    
    /**
     *
     * @param pSchema
     */
    public void drop_Schema(String pSchema){
        boolean mkdir = new File(_Dir+pSchema).delete();  
    }
    
    /**
     *
     * @param name
     * @param schema
     * @throws Exception
     */
    public void create_Table(String name,String schema) throws Exception{
        BplusTree myTree = hBplusTree.Initialize(_Dir+schema+"/"+name+".tree",
                _Dir+schema+"/"+name+".table", 6);
        
    }
    
    /**
     *
     * @param pSchema
     * @param pTable
     */
    public void drop_Table(String pSchema, String pTable){
       boolean tree= new File(_Dir+pSchema+"/"+pTable+".tree").delete(); 
       boolean table= new File(_Dir+pSchema+"/"+pTable+".table").delete(); 
    }
    
    /**
     *
     * @param pSchema
     * @param pTable
     * @param pData
     * @throws Exception
     */
    public void insert_In_Table(String pSchema, String pTable,String pData) throws Exception{
        BplusTree myTree = hBplusTree.Initialize(_Dir+pSchema+"/"+pTable+".tree",
                _Dir+pSchema+"/"+pTable+".table", 6);
        
    }
    
    /**
     *
     * @param pTable
     * @param pColumns
     * @return
     */
    public Table select(Table pTable,List<String> pColumns){
       pTable.table_get_colums(pColumns);
       return pTable;
    }
    
    /**
     *
     * @param pTable
     * @param pCond
     * @return
     */
    public Table where(Table pTable, Condition pCond){
        return pTable;
    }
    
    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String [] args) throws Exception
	{
           /* BplusTree myTree = hBplusTree.Initialize("c:\\tmp/DataBases/Esq/Tabla_A.tree","c:\\tmp/DataBases/Esq/Tabla_A.table",6);
            //BplusTree myTree = hBplusTree.Initialize("c:\\tmp/DataBases/Esq/Tabla_B.tree","c:\\tmp/DataBases/Esq/Tabla_B.table",6);
            myTree.set("500125", "500125,10000,2003/05/12");
            myTree.set("500126", "500126,10001,2003/05/13");
            myTree.set("500127", "500127,10004,2003/05/14");
            
            //myTree.set("10000", "10000,IBM");
            //myTree.set("10001", "10001,Hewlett Packard");
            //myTree.set("10002", "10002,Microsoft");
            //myTree.set("10004", "10004,NVIDIA");
            myTree.Commit();
            myTree.Shutdown();*/
            //BplusTree my = hBplusTree.ReadOnly("c:\\tmp/b.tree","c:\\tmp/b.table");
            Table D= new Table();
            Table E= new Table();
            D.setTable("Esq", "Tabla_A");
            E.setTable("Esq", "Tabla_B");
            E.charge_Table();
            D.charge_Table();
            //E.printTable();
            //D.printTable();
            //D.join_Table(E);
            List<String> G= new ArrayList<String>();
            List<String> H= new ArrayList<String>();
            G.add("a");
            G.add("b");
            G.add("c");
            H.add("b");
            H.add("c");
            D.set_PK("a");
            D.setColumns(G);
            Condition v=new Condition("a",null,"500125","=");
            //Condition r=new Condition("b",null,"10000","=");
            List<Condition> X = new ArrayList<Condition>();
            X.add(v);
            //X.add(r);
            //D.act_fila(X,"c", "PUTA RAFA");
            
            //D.borrar_fila(X);
            //D.table_get_colums(H);
            
            D.printTable();
            //D.commit_Table();
            
            //Commands A= new Commands();
            //A.create_System_Catalog();
            //Table D= new Table();
            //D.setTable("System_Catalog", "Sys_Columns");
            //D.charge_Table();
            //D.printTable();
            
        }
}
