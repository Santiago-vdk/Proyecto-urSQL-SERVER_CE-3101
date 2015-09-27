/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.Objects;

import NET.sourceforge.BplusJ.BplusJ.BplusTree;
import NET.sourceforge.BplusJ.BplusJ.hBplusTree;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import urSQL.logica.logHandler;

/**
 *
 * @author David
 */
public class Table {
    private List<List> _Values= new ArrayList<List>();
    private List<String> _Columns=new ArrayList<String>();
    private final String _Dir= logHandler.getInstance().getRootPath()+"/urSQL/DataBases/";
    //private final String _DirSys= "c:\\tmp/DataBases/System_Catalog/";
    private String _PK="";
    private String _Schema;
    private String _Table;
   

    
    
 ///////////////FUNCIONES DE CARGADO Y GUARDADO DE TABLAS///////////////////////   

    /**
     *
     * @param pSchema
     * @param pTable
     */
        public void setTable(String pSchema,String pTable){
        _Schema=pSchema;
        _Table=pTable;
    }

    /**
     *
     * @throws Exception
     */
    public void charge_Table() throws Exception{
        _Values.removeAll(_Values);
        
        BplusTree A= hBplusTree.ReadOnly(_Dir+_Schema+"/"+_Table+".tree",
                _Dir+_Schema+"/"+_Table+".table");
        
        String Key= A.FirstKey();
        while(Key!=null){
            String Data= A.get(Key);
            _Values.add( new ArrayList<>(Arrays.asList(Data.split(","))));
            Key= A.NextKey(Key);
            
                  
        }
        
        A.Shutdown();
        
        
    }
    public void insert(String key,String val) throws Exception{
       BplusTree myTree = hBplusTree.ReOpen(_Dir+_Schema+"/"+_Table+".tree",
                _Dir+_Schema+"/"+_Table+".table");
       myTree.Set(key, val);
       myTree.Commit();
       myTree.Shutdown();
    }
    /**
     *
     * @throws Exception
     */
    public void commit_Table() throws Exception{
        boolean tree= new File(_Dir+_Schema+"/"+_Table+".tree").delete(); 
        boolean table= new File(_Dir+_Schema+"/"+_Table+".table").delete(); 
        
        BplusTree myTree = hBplusTree.Initialize(_Dir+_Schema+"/"+_Table+".tree",
                _Dir+_Schema+"/"+_Table+".table",6);
        int Index_PK= get_Index(_PK);
        for (int i=0; i<_Values.size();i++){
            String tmp="";
            for(int j=0;j<_Values.get(0).size();j++){
                if(_Values.get(0).size()==j+1){
                    tmp=tmp+_Values.get(i).get(j);
                }
                else{
                    tmp=tmp+_Values.get(i).get(j)+",";
                }
            }
            
            
            myTree.Set(String.valueOf(_Values.get(i).get(Index_PK)), tmp);
            
        }
        myTree.Commit();
        myTree.Shutdown();
        charge_Table();
    }

    
    
    
////////////////////////////////////////////////////////////////////////////////
    
    
    
    
    
/////////////////////FUNCIONES DE VIZUALIZACION/////////////////////////////////
    
    /**
     *
     */
        
    
    public void printTable(){
       for(int i=0; i<_Values.size(); i++){
           for(int j=0; j<_Values.get(0).size(); j++){
              System.out.print(_Values.get(i).get(j)+"  "); 
           }
           System.out.print("\n");
       }
   }
    
    
////////////////////////////////////////////////////////////////////////////////
    
    /**
     *
     * @param pList
     */
        
   public void add(List<String> pList) throws Exception{
       _Values.add(pList);
       
       
   } 
   
    /**
     *
     * @param pColumn
     * @param pValor
     */
    public void set_Value(String pColumn, String pValor){
       int Index=get_Index(pColumn);
       for (int i=0; i<_Values.size();i++){
           _Values.get(i).remove(Index);
           _Values.get(i).add(Index, pValor);
       }
   }
   
    /**
     *
     * @param pColumn
     */
    public void table_get_colums(List<String> pColumn){
       
       List<List> tmp1=new ArrayList<List>();
    
              
             
           
        for (int j=0;j<_Values.size();j++){
            List<String> tmp2=new ArrayList<String>();
            for(int i=0; i<pColumn.size();i++){
                int Column_Number= get_Index(pColumn.get(i));
                String tmp3=(String) _Values.get(j).get(Column_Number);
                tmp2.add(tmp3);
            }
            tmp1.add(tmp2);
        
       }
       set_Values(tmp1);
       
       
   }
   
    /**
     *
     * @param pTabla
     */
    public void join_Table(Table pTabla){
       List<List> tmp1= pTabla.get_Values();
       List<List> tmp2= get_Values();
       List<List> tmp3= new ArrayList<List>();
        for(int i=0; i<_Values.size();i++){
            for(int j=0;j<tmp1.size();j++){
                List<String> tmp4= new ArrayList<String>(tmp2.get(i));
                tmp4.addAll(tmp1.get(j));
                tmp3.add(tmp4);
            }
        }
        this.set_Values(tmp3);
    }
   
   private boolean ver_Conds(List<Condition> Conds, List<String> tmp2){
       boolean R= false;
       for(int x=0;x<Conds.size();x++){
               Condition tcond= Conds.get(x);
               int IndxA=get_Index(tcond.get_ColA());
               int IndxB=get_Index(tcond.get_ColB());
           if (tcond.get_ColB()!=null){
               switch (tcond.get_Comp()){
                    case ">":
                       if(Integer.parseInt(tmp2.get(IndxA))>Integer.parseInt(tmp2.get(IndxB))){
                           R=true;
                       }
                       else{
                          R=false; 
                       }
                    case "<":
                        if(Integer.parseInt(tmp2.get(IndxA))<Integer.parseInt(tmp2.get(IndxB))){
                           R=true;
                       }
                    else{
                         R=false; 
                       }
                    case "=":
                        if(tmp2.get(IndxA).equals(tmp2.get(IndxB))){
                          R=true;
                       }
                    else{
                          R=false; 
                       }
                }
           }
           else{
               switch (tcond.get_Comp()){
                   
                    case "is null":
                       if(tmp2.get(IndxA).equals("NULL")){
                      R=true;
                       } 
                    else{
                         R=false;
                       }
                    case "is not null":
                        if(!(tmp2.get(IndxA).equals("NULL"))){
                         R=true;
                       } 
                    else{
                          R=false;
                       }
                    
                    case ">":
                       if(Integer.parseInt(tmp2.get(IndxA))>Integer.parseInt(tcond.get_Val())){
                          R=true;
                       }
                    else{
                          R=false;
                       }
                    case "<":
                        if(Integer.parseInt(tmp2.get(IndxA))<Integer.parseInt(tcond.get_Val())){
                         R=true;
                       }
                    else{
                          R=false; 
                       }
                    case "=":
                        
                        if(tmp2.get(IndxA).equals(tcond.get_Val())){
                       R=true;
                       }
                    else{
                           R=false;
                       }
                
               }
           }
                
             
       }
       return R;        
   }

    /**
     *
     * @param Conds
     * @param Col
     * @param Val
     */
    public void act_fila(List<Condition> Conds,String Col , String Val){
       
       List<List> tmp=new ArrayList<List>();
       for(int i=0;i<_Values.size();i++){
           
           List<String> tmp2 = new ArrayList<String>();
           tmp2.addAll(_Values.get(i));
           if (ver_Conds(Conds, tmp2)){
              tmp2.remove(get_Index(Col));
              tmp2.add(get_Index(Col), Val);
              tmp.add(tmp2);
           }
           else{
               tmp.add(tmp2);
           }
       }
       set_Values(tmp);
   }
   
    /**
     *
     * @param pColumA
     * @param pColumB
     * @param pValor
     * @param pComp
     */
    public void elim_fila(String pColumA, String pColumB, String pValor, String pComp){
       int IndxA=get_Index(pColumA);
       int IndxB=get_Index(pColumB);
       List<List> tmp=new ArrayList<List>();
       for(int i=0;i<_Values.size();i++){
           
           List<String> tmp2 = new ArrayList<String>(_Values.get(i));
           if (pColumB!=null){
                
             switch (pComp){
                    case ">":
                       if(Integer.parseInt(tmp2.get(IndxA))>Integer.parseInt(tmp2.get(IndxB))){
                           tmp.add(tmp2);
                       }
                    case "<":
                        if(Integer.parseInt(tmp2.get(IndxA))<Integer.parseInt(tmp2.get(IndxB))){
                           tmp.add(tmp2);
                       }
                    case "=":
                        if(tmp2.get(IndxA).equals(tmp2.get(IndxB))){
                           tmp.add(tmp2);
                       }
                }
            }
           else{
               
               switch (pComp){
                   
                    case "is null":
                       if(tmp2.get(IndxA).equals("NULL")){
                           tmp.add(tmp2);
                       } 
                    case "is not null":
                        if(!(tmp2.get(IndxA).equals("NULL"))){
                           tmp.add(tmp2);
                       } 
                    
                    case ">":
                       if(Integer.parseInt(tmp2.get(IndxA))>Integer.parseInt(pValor)){
                           tmp.add(tmp2);
                       }
                    case "<":
                        if(Integer.parseInt(tmp2.get(IndxA))<Integer.parseInt(pValor)){
                           tmp.add(tmp2);
                       }
                    case "=":
                        if(tmp2.get(IndxA).equals(pValor)){
                           
                           tmp.add(tmp2);
                       }
                
               }
           }
       }
       set_Values(tmp);
   }

    /**
     *
     * @param Conds
     * @throws Exception
     */
    public void borrar_fila(List<Condition> Conds) throws Exception{
       
       List<List> tmp=new ArrayList<List>();
       for(int i=0;i<_Values.size();i++){
           
           List<String> tmp2 = new ArrayList<String>();
           tmp2.addAll(_Values.get(i));
           if (!ver_Conds(Conds, tmp2)){
              tmp.add(tmp2); 
           }
           
       }
       set_Values(tmp);
       
   }
   
    /**
     *
     * @param pColumn
     * @param pDato
     * @return
     */
    public boolean exists(String pColumn, String pDato){
       int Index=get_Index(pColumn);
       boolean bool=false;
       for(int i=0;i<_Values.size();i++){
           if(_Values.get(i).get(Index).equals(pDato)){
               bool=true;
               break;
           }
       }
       return bool;
   }
   
   /**public String get_Dato(String pColumnA, String pColumnB,String pData){
       
       for(int i=0; i<_Values.size();i++){
           List<String> tmp= new ArrayList(_Values.get(i));
           String tmp1= tmp.get(get_Index(pColumn));
       }
   }**/
   
   private int get_Index(String pColumn){
       int Index=0;
       for(int i=0;i<_Columns.size();i++){
           if (_Columns.get(i).equals(pColumn)){
               Index=i;
               break;
           }
       }
       return Index;
   }
   
    /**
     *
     * @param pValues
     */
    public void set_Values(List<List> pValues){
       this._Values = pValues;
   }

    /**
     *
     * @return
     */
    public List<List> get_Values(){
       return _Values;
   }

    /**
     *
     * @param pColumns
     */
    public void setColumns(List<String> pColumns){
       
       this._Columns= pColumns;
   }
    
    public List<String> getColumns(){
        return _Columns;
    }

    /**
     *
     * @param pPK
     */
    public void set_PK(String pPK){
       _PK=pPK;
   }
    
    public String getPK(){
        return _PK;
    }
}
