/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.BPlusTree;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author David
 */
public class main {
    public static void main(String [] args)
	{
        try {
            
            BplusTree myTree = hBplusTree.Initialize("c:\\tmp/prueba.dat", "c:\\tmp/data.dat", 6);
            myTree.Set("a", "Hola");
            myTree.Set("b", "heloo");
            myTree.Set("c", "heeellooo");
            myTree.Commit();
            myTree.Shutdown();
            BplusTree otherTree = hBplusTree.ReadOnly("c:\\tmp/prueba.dat", "c:\\tmp/data.dat");
            //String a= (String) otherTree.Get("1234567", otherTree);
            //System.out.println(a);
        } catch (Exception ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
}
