/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.logica;

import urSQL.BPlusTree.BplusTree;
import urSQL.BPlusTree.hBplusTree;

/**
 *
 * @author David
 */
public class Commands {
    
    public void create_Table(String name,String schema) throws Exception{
        BplusTree myTree = hBplusTree.Initialize("c:\\tmp/DataBases/"+schema+"/Tables/"+name+".tree",
                "c:\\tmp/DataBases/"+schema+"/Tables/"+name+".table", 6);
        
    }
    public static void main(String [] args) throws Exception
	{
            Commands C= new Commands();
            C.create_Table("Tabla_Prueba","Esquema_Prueba");
        }
}
