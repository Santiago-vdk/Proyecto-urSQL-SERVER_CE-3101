/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.Objects;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David
 */
public class Table {
    private List<List> _Values= new ArrayList<List>();
    private List<String> _Columns=new ArrayList<String>();
    
    /**
     *
     * @param pList
     */
    public void add(List<String> pList){
       _Values.add(pList);
       
   } 
   
    /**
     *
     */
    public void printTable(){
       for(int i=0; i>_Values.size(); i++){
           for(int j=0; j>_Values.get(0).size(); j++){
              System.out.print(_Values.get(i).get(j)+"  "); 
           }
           System.out.print("\n");
       }
   }
}
