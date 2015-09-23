/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.logica;

/**
 *
 * @author RafaelAngel
 */
public class Facade {
    boolean _On = false;
    
    public void processQuerry(String pQuerry){
        if(_On){
            
            if(pQuerry.toUpperCase().compareTo("STOP")==0){
                _On = false;
                 //finaliza los threads
            }
            
            //process querry
        }
        else if(pQuerry.toUpperCase().compareTo("START")==0){
            _On = true;
            //inicializa los threads
        }
    }
    
}
