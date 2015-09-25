/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.threads;

import NET.sourceforge.BplusJ.BplusJ.BplusTree;
import NET.sourceforge.BplusJ.BplusJ.hBplusTree;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import urSQL.Objects.Table;

/**
 *
 * @author Shagy
 */
public class StoredDataManager implements Callable{
    private StoredData DataS;
    private FutureTask _FutureStoredData;

    
    private final String _ExecutionPlan = "c:\\tmp/DataBases/System_Catalog/Execution_Plan.txt";
    private final String _Dir="c:\\tmp/DataBases/";
    
    
    
    
    
    private Table TMP1 = null;
    
    
    
    StoredDataManager() throws Exception{
      DataS= new StoredData();
      _FutureStoredData=new FutureTask(DataS); 
      
    }
    
    
    private void executePlan() throws IOException{
        try {
            BufferedReader br = new BufferedReader(new FileReader(_ExecutionPlan));
            String line = br.readLine();
            while (line != null) {
                System.out.println(line);
                line = br.readLine();
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
                drop_Table(CommandList.get(1),ThreadManager.Current_Schema);
            }
                
            
            if (CommandList.get(0).equals("DISPLAY_DB")){
                ThreadManager._DATA.setTabla(display_DB(CommandList.get(1)));
            }
                
            
            if (CommandList.get(0).equals("LIST_DB")){
                ThreadManager._DATA.setTabla(list_DB());
            }
                
                
            if (CommandList.get(0).equals("SET_DB")){
                set_DB(CommandList.get(1));
            }
                
             
            if (CommandList.get(0).equals("GET_STATUS")){
               get_Status(); 
            }
                
                
            if (CommandList.get(0).equals("NEW_COLUMN")){
                
            }
                
            
                
            
        
    }
    
    
    
    
    @Override
    public String call() throws InterruptedException, ExecutionException, IOException {
        executePlan();
        
        
        return "Plan executed";
    }
    
    
    
    
    
    
    
    private void create_Schema(String pSchema){
        boolean mkdir = new File(_Dir+pSchema).mkdir();  
        ThreadManager._SYCT.set_Plan(pSchema, null, null, null, null, null, null, "I_S");
        ThreadManager._Pool.execute(ThreadManager.futureSystem);
        waitSC();
    }
    
    private void drop_Schema(String pSchema){
        boolean mkdir = new File(_Dir+pSchema).delete(); 
        ThreadManager._SYCT.set_Plan(pSchema, null, null, null, null, null, null, "E_S");
        waitSC();
    }
    
    private void create_Table(String name,String schema) throws Exception{
        BplusTree myTree = hBplusTree.Initialize(_Dir+schema+"/"+name+".tree",
                _Dir+schema+"/"+name+".table", 6);
        ThreadManager._SYCT.set_Plan(schema, name, null, null, null, null, null, "I_T");
        waitSC();
    }
    
    private void drop_Table(String pSchema, String pTable){
       boolean tree= new File(_Dir+pSchema+"/"+pTable+".tree").delete(); 
       boolean table= new File(_Dir+pSchema+"/"+pTable+".table").delete(); 
       ThreadManager._SYCT.set_Plan(pSchema, pTable, null, null, null, null, null, "E_T");
       waitSC();
    }
    
    private Table display_DB(String pSchema){
        Table Tmp= new Table();
        Tmp.setTable("System_Catalog", "Sys_Columns");
        Tmp.elim_fila("Schema", null, pSchema, "=");
        return Tmp;
        
    }
    
    private Table list_DB(){
        Table Tmp= new Table();
        Tmp.setTable("System_Catalog", "Sys_Schemas");
        return Tmp;
    }
    private void set_DB(String pSchema){
        ThreadManager.Current_Schema= pSchema;
    }
    
    private void get_Status(){
        
    }
            
    /**
     *
     */
    private void waitSD(){
        while(_FutureStoredData.isDone()){
            
        }
    }
    
    private void waitSC(){
        while(ThreadManager.futureSystem.isDone()){
            
        }
    }
    
}
