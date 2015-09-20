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
public class ExecutionPlan {
    
    private String[] _Querry;
    private String _Plan=""; 
    
    
    private int findInQuerry(String pString){
        for(int i=0;i<_Querry.length;i++){
            if(_Querry[i].toUpperCase().compareTo(pString)==0){
                return i;
            }
        }
        return -1;
    }
    
    private void createPlan_Select(){
        int indice = findInQuerry("FROM")+1;
        String tabla = _Querry[indice];
        if(tabla.toUpperCase().compareTo("JOIN")==0){
            //validaciones para el join
        }
        else{
            //validar que existe la tabla en el catalogo
            indice++;
            
        }
        
        
        
    }
    private void createPlan_Update(){
        
    }  
    private void createPlan_Delete(){
        int indice = 2;
        String tabla = _Querry[indice];
        //validar que exista la tabla
        _Plan +="OPEN_TABLE "+tabla+" DELETE\n";
        
        //VALIDAR WHERE
    }  
    
    
    private void createPlan_Insert(){
        int indice = 2;//se salta las posiciones de insert into
        String tabla = _Querry[indice];
        //validar q exista la tabla
        _Plan += "OPEN_TABLE "+tabla+" INSERT\n";
        indice+=2;//se posiciona en la primera columna
        int indice2 = findInQuerry("VALUES")+2;// se salta el caracter "(" 
        String columna = "";
        String valor = "";
        while(indice2 < _Querry.length -1){//se sale al llegar el contador 2 al caracter ")"
            columna = _Querry[indice];
            valor = _Querry[indice2];
            if(columna.compareTo("\\)")==0){
                //error mas valores que columnas
            }
            //validad que existe columna
            //HAY QUE ORDENAR LA TUPLA
        }
    }

    public void createPlan(String pQuerry){
        
        String[] _Querry = pQuerry.split(" ");
        String comando = _Querry[0].toUpperCase();
        String comando2 = _Querry[1].toUpperCase();
        
        if(comando.compareTo("SELECT")==0){
            createPlan_Select();
        }
        if(comando.compareTo("UPDATE")==0){
            createPlan_Update();
        }
        if(comando.compareTo("DELETE")==0){
            createPlan_Delete();
        }
        if(comando.compareTo("INSERT")==0){
            createPlan_Insert();
        }
        
        if(comando.compareTo("SET")==0){//set database
            
        }
        if(comando.compareTo("LIST")==0){//list database
            
        }
        if(comando.compareTo("START")==0){
            
        }
        if(comando.compareTo("STOP")==0){
            
        }
        if(comando.compareTo("GET")==0){//get status
            
        }
        if(comando.compareTo("DISPLAY")==0){//display database
            
        }
        if(comando.compareTo("SET")==0){//set database
            
        }
        if(comando.compareTo("ALTER")==0){//alter table
            
        }
        
        if(comando.compareTo("CREATE")==0){
            
            if(comando2.compareTo("DATABASE")==0){
            
            }
            if(comando2.compareTo("TABLE")==0){
            
            }
            if(comando2.compareTo("INDEX")==0){
            
            }
            
        }
        
        if(comando.compareTo("DROP")==0){
            if(comando2.compareTo("DATABASE")==0){
            
            }
            if(comando2.compareTo("TABLE")==0){
            
            }
        }
        
    
    }
    
    
    
}
