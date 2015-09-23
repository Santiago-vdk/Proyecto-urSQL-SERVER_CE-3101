/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.logica;

import NET.sourceforge.BplusJ.BplusJ.BplusTree;
import NET.sourceforge.BplusJ.BplusJ.hBplusTree;

/**
 *
 * @author David
 */
public class Commands {
    
    /**
     *
     * @param name
     * @param schema
     * @throws Exception
     */
    public void create_Table(String name,String schema) throws Exception{
        BplusTree myTree = hBplusTree.Initialize("c:\\tmp/DataBases/"+schema+"/Tables/"+name+".tree",
                "c:\\tmp/DataBases/"+schema+"/Tables/"+name+".table", 6);
        
    }

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String [] args) throws Exception
	{
            Commands C= new Commands();
            C.create_Table("Tabla_Prueba","Esquema_Prueba");
        }
}
