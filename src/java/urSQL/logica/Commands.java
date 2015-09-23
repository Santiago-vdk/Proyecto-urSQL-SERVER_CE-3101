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
    
    
    public void create_System_Catalog(String schema) throws Exception{
        boolean mkdir = new File(_Dir+"System_Catalog").mkdir();
        hBplusTree.Initialize(_Dir+"System_Catalog/"+"Sys_Schemas"+".tree",
                _Dir+"System_Catalog/"+"Sys_Schemas"+".table", 6);
        hBplusTree.Initialize(_Dir+"System_Catalog/"+"Sys_Tables"+".tree",
                _Dir+"System_Catalog/"+"Sys_Tables"+".table", 6);
        hBplusTree.Initialize(_Dir+"System_Catalog/"+"Sys_Columns"+".tree",
                _Dir+"System_Catalog/"+"Sys_Columns"+".table", 6);
        hBplusTree.Initialize(_Dir+"System_Catalog/"+"Sys_Constraints"+".tree",
                _Dir+"System_Catalog/"+"Sys_Constraints"+".table", 6);
    }
    
    public void create_Schema(String pSchema){
        boolean mkdir = new File(_Dir+pSchema).mkdir();        
    }
    
    public void drop_Schema(String pSchema){
        boolean mkdir = new File(_Dir+pSchema).delete();  
    }
    
    public void create_Table(String name,String schema) throws Exception{
        BplusTree myTree = hBplusTree.Initialize(_Dir+schema+"/"+name+".tree",
                _Dir+schema+"/"+name+".table", 6);
        
    }
    
    public void drop_Table(String pSchema, String pTable){
       boolean tree= new File(_Dir+pSchema+"/"+pTable+".tree").delete(); 
       boolean table= new File(_Dir+pSchema+"/"+pTable+".table").delete(); 
    }
    
    public void insert_In_Table(String pSchema, String pTable,String pData) throws Exception{
        BplusTree myTree = hBplusTree.Initialize(_Dir+pSchema+"/"+pTable+".tree",
                _Dir+pSchema+"/"+pTable+".table", 6);
        
    }
    
    public Table select(Table pTable,List<String> pColumns){
       pTable.table_get_colums(pColumns);
       return pTable;
    }
    
    public Table where(Table pTable, Condition pCond){
        return pTable;
    }
    
    
    
    public static void main(String [] args) throws Exception
	{
            //BplusTree myTree = hBplusTree.Initialize("c:\\tmp/DataBases/Esq/Tabla_A.tree","c:\\tmp/DataBases/Esq/Tabla_A.table",6);
            //BplusTree myTree = hBplusTree.Initialize("c:\\tmp/DataBases/Esq/Tabla_B.tree","c:\\tmp/DataBases/Esq/Tabla_B.table",6);
            //myTree.set("500125", "500125,10000,2003/05/12");
            //myTree.set("500126", "500126,10001,2003/05/13");
            //myTree.set("500127", "500127,10004,2003/05/14");
            
            //myTree.set("10000", "10000,IBM");
            //myTree.set("10001", "10001,Hewlett Packard");
            //myTree.set("10002", "10002,Microsoft");
            //myTree.set("10004", "10004,NVIDIA");
            //myTree.Commit();
            //myTree.Shutdown();
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
            
            D.setColumns(G);
            D.elim_fila("a", null, "500126","=");
            D.table_get_colums(H);
            
            D.printTable();
        }
}
