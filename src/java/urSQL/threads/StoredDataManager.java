/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.threads;

import NET.sourceforge.BplusJ.BplusJ.BplusTree;
import NET.sourceforge.BplusJ.BplusJ.hBplusTree;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import urSQL.Objects.Table;
import urSQL.logica.logHandler;

/**
 *
 * @author Shagy
 */
public class StoredDataManager implements Callable{
    
    
    private final String _ExecutionPlan = logHandler.getInstance().getRootPath()+"urSQL/DataBases/System_Catalog/Execution_Plan.txt";
    private final String _Dir=logHandler.getInstance().getRootPath()+"/urSQL/DataBases/";
    private Table TMP1 = new Table();
    
    
    
    
    StoredDataManager() throws Exception{
      
      
    }
    
        @Override
    public String call() throws InterruptedException, ExecutionException, IOException, Exception {
        executePlan();
        
            
        return "Plan executed";
    }
    
    private void executePlan() throws IOException, Exception{
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(_ExecutionPlan));
            String line;
            
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                process(line);
                
                
            }
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StoredDataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void process(String pCommand) throws Exception{
        
        List<String> CommandList = new ArrayList<String>(Arrays.asList(pCommand.split("~")));
            
            if (CommandList.get(0).equals("CREATE_DB")){
                
                create_Schema(CommandList.get(1));
            }   
                
            if (CommandList.get(0).equals("DELETE_DB")){
                drop_Schema(CommandList.get(1));
            }
                
            
            if (CommandList.get(0).equals("CREATE_TABLE")){
                
                create_Table(CommandList.get(1),ThreadManager.Current_Schema);
            }
                
                
            if (CommandList.get(0).equals("DELETE_TABLE")){
                
                drop_Table(ThreadManager.Current_Schema,CommandList.get(1));
            }
                
            
            if (CommandList.get(0).equals("DISPLAY_DB")){
               
                writeTable(display_DB(CommandList.get(1)));
            }
                
            
            if (CommandList.get(0).equals("LIST_DB")){
                list_DB();
            }
                
                
            if (CommandList.get(0).equals("SET_DB")){
              
                set_DB(CommandList.get(1));
            }
                
             
            if (CommandList.get(0).equals("GET_STATUS")){
               get_Status(); 
            }
                
                
            if (CommandList.get(0).equals("NEW_COLUMN")){
               
               new_Col(ThreadManager.Current_Schema,CommandList.get(1),CommandList.get(2),"false",CommandList.get(3),
                       CommandList.get(4));
            }
                
            if (CommandList.get(0).equals("PRIMARY_KEY")){
                set_PK(ThreadManager.Current_Schema,CommandList.get(1),CommandList.get(2));
            }
                
            if(CommandList.get(0).equals("OPEN_TABLE")){
              charge(ThreadManager.Current_Schema,CommandList.get(1));
            }
            if(CommandList.get(0).equals("INSERT")){
                insert(CommandList.get(1),CommandList.get(2));
            }
            if(CommandList.get(0).equals("JOIN")){
                join(ThreadManager.Current_Schema,CommandList.get(1));
            }
            if(CommandList.get(0).equals("WHERE")){
                where(CommandList.get(1),CommandList.get(2), CommandList.get(3));
            }
            if(CommandList.get(0).equals("SELECT")){
                select(CommandList.get(1));
            }
    }
    
    public void join(String pSchema, String pTable) throws Exception{
        ThreadManager._SYCT = new SystemCatalog();
        ThreadManager.futureSystem= new FutureTask(ThreadManager._SYCT);
        Table tmp = new Table();
        tmp.setTable(pSchema, pTable);
        tmp.setColumns(new ArrayList<String>(Arrays.asList(ThreadManager._SYCT.re_Columns(pSchema, pTable).split(","))));
        tmp.set_PK(ThreadManager._SYCT.re_PK(pSchema, pTable));
        tmp.charge_Table();
        TMP1.join_Table(tmp);
    }
    public void where(String a, String b, String c){
        TMP1.elim_fila(a, null, b, c);
    }
    public void select(String R) throws IOException, JSONException{
        List<String> myList = new ArrayList<String>(Arrays.asList(R.split(",")));
        TMP1.setColumns(myList);
        writeTable(TMP1);
    }
    public void charge(String pSchema, String pTable) throws Exception{
        ThreadManager._SYCT = new SystemCatalog();
        ThreadManager.futureSystem= new FutureTask(ThreadManager._SYCT);
        Table tmp = new Table();
        TMP1.setTable(pSchema, pTable);
        TMP1.setColumns(new ArrayList<String>(Arrays.asList(ThreadManager._SYCT.re_Columns(pSchema, pTable).split(","))));
        TMP1.set_PK(ThreadManager._SYCT.re_PK(pSchema, pTable));
        TMP1.charge_Table();
        
    }
    
    public void insert(String cols,String vals) throws Exception{

        TMP1.add(new ArrayList<String>(Arrays.asList(vals.split(","))));
        TMP1.commit_Table();
        TMP1.charge_Table();
        
    }
    
    public int getI(List<String> L,String V){
        int x=0;
        for(int i=0;i<L.size();i++){
            if  (L.get(i).equals(V)){
                x=i;
            }
        }
        return x;
    }
    

    
    
    
    
    
    
    private void create_Schema(String pSchema) throws Exception{
        boolean mkdir = new File(_Dir+pSchema).mkdir();  
        ThreadManager._SYCT = new SystemCatalog();
        ThreadManager.futureSystem= new FutureTask(ThreadManager._SYCT);
        ThreadManager._SYCT.insert_Schema(pSchema);
    }
    
    private void drop_Schema(String pSchema) throws Exception{
        
        boolean mkdir = new File(_Dir+pSchema).delete(); 
        ThreadManager._SYCT = new SystemCatalog();
        ThreadManager.futureSystem= new FutureTask(ThreadManager._SYCT);
        ThreadManager._SYCT.set_Plan(pSchema, null, null, null, null, null, null, "E_S");
        ThreadManager._SYCT.elim_Schema();
    }
    
    private void create_Table(String name,String schema) throws Exception{
        
        BplusTree myTree = hBplusTree.Initialize(_Dir+schema+"/"+name+".tree",
                _Dir+schema+"/"+name+".table", 6);
        myTree.Shutdown();
        
        ThreadManager._SYCT = new SystemCatalog();
        ThreadManager.futureSystem= new FutureTask(ThreadManager._SYCT);
        
        ThreadManager._SYCT.insert_Table(schema, name);
        
    }
    
    private void drop_Table(String pSchema, String pTable) throws Exception{
        
       boolean tree= new File(_Dir+pSchema+"/"+pTable+".tree").delete(); 
       boolean table= new File(_Dir+pSchema+"/"+pTable+".table").delete(); 
       
       ThreadManager._SYCT = new SystemCatalog();
       ThreadManager.futureSystem= new FutureTask(ThreadManager._SYCT);
       
       
       ThreadManager._SYCT.elim_Table(pSchema,pTable);
       
    }
    
    private Table display_DB(String pSchema) throws Exception{
       ThreadManager._SYCT = new SystemCatalog();
       ThreadManager.futureSystem= new FutureTask(ThreadManager._SYCT);
       
       
        Table Tmp=ThreadManager._SYCT.getDBS();
         
        Tmp.setTable("System_Catalog", "Sys_Columns");
        Tmp.elim_fila("Schema", null, pSchema, "=");
        Tmp.printTable();
        
        return Tmp;
        
    }
    
    private void list_DB() throws Exception{
        ThreadManager._SYCT = new SystemCatalog();
        ThreadManager.futureSystem= new FutureTask(ThreadManager._SYCT);
       
       
        Table Tmp=ThreadManager._SYCT.getDBD();
        String[] A = new String[Tmp.get_Values().size()];
        for(int i=0;i<Tmp.get_Values().size();i++){
            A[i] = (String) Tmp.get_Values().get(i).get(0);
        }
        
        JSONObject a= new JSONObject();
        a.put("Schemas", A);
        File f;
        f = new File(_Dir+"System_Catalog/"+"List.txt");
        FileWriter w = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(w);
        PrintWriter wr = new PrintWriter(bw);  
        wr.write(a.toString());
         
         bw.close();
         wr.close();
        
    }
    private void set_DB(String pSchema){
        
        ThreadManager.Current_Schema= pSchema;
      
    }
    
    private void get_Status(){
        
    }
      
    
    private void new_Col(String pSchema,String pTable,String pColumn,String pPK,String pModifider,String pType) throws Exception{
       ThreadManager._SYCT = new SystemCatalog();
       ThreadManager.futureSystem= new FutureTask(ThreadManager._SYCT);
       ThreadManager._SYCT.insert_Column(pSchema, pTable, pColumn, "false", pModifider, pType, "null");
    }
    
    private void set_PK(String pSchema,String pTable,String pColumn) throws Exception{
       ThreadManager._SYCT = new SystemCatalog();
       ThreadManager.futureSystem= new FutureTask(ThreadManager._SYCT);
       ThreadManager._SYCT.set_PK(pSchema, pTable, pColumn); 
       
    }
    /**
     *
     */
     private void writeTable(Table pTable) throws IOException, JSONException{
         
         JSONObject a= new JSONObject();
         String[][] A = new String[pTable.get_Values().size()][pTable.get_Values().get(0).size()];
         for (int i=0;i<pTable.get_Values().size();i++){
             for (int j=0;j<pTable.get_Values().size();j++){
                 A[i][j] = (String) pTable.get_Values().get(i).get(j);
             }
         }
         
         String[] B = new String[pTable.getColumns().size()];
         for (int i=0;i<pTable.getColumns().size();i++){
             B[i] = (String) pTable.getColumns().get(i);
         }
         
         a.put("Columnas",B);
         a.put("Valores",A);
         a.put("PK", pTable.getPK());
         System.out.println(a);
         File f;
        f = new File(_Dir+"System_Catalog/"+"Table.txt");
        FileWriter w = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(w);
        PrintWriter wr = new PrintWriter(bw);  
        wr.write(a.toString());
         
         bw.close();
         wr.close();

     }
     
     public void createTree() throws Exception{
       ThreadManager._SYCT = new SystemCatalog();
       ThreadManager.futureSystem= new FutureTask(ThreadManager._SYCT);
       Table tmp = null;
       tmp= ThreadManager._SYCT.getDBD();
       for(int i=0;i<ThreadManager.ListDB.length;i++){
           
       }
     }
    
  
    
}
