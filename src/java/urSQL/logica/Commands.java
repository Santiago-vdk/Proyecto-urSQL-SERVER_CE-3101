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
import org.json.JSONObject;
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
        
            Table a= new Table();
            a.setTable("kkk", "Persons");
            a.charge_Table();
            List<String> n = new ArrayList<String>();
            n.add("a");
            a.setColumns(n);
            a.set_PK("a");
            
            List<String> o = new ArrayList<String>();
            o.add("Puta");
            //a.add(o);
            
            
           
            
            

            a.printTable();
            /*
            JSONObject obj = new JSONObject();
            List<List> C = new ArrayList<List>();
            List<String> D = new ArrayList<String>();
            D.add("a");
            D.add("b");
            D.add("c");
            C.add(D);
            String[][] A = new String[C.size()][C.get(0).size()];
            A= C.toArray(A);
            obj.put("asa",A );
            String[][] B =(String[][])obj.get("asa");
            System.out.println(B[0][2]); */
        }
}
