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
    
    //funciones auxiliares
    //**************************************************************************
    private int findInQuerry(String pString){
        for(int i=0;i<_Querry.length;i++){
            if(_Querry[i].toUpperCase().compareTo(pString)==0){
                return i;
            }
        }
        return -1;
    }
    //**************************************************************************
    
    
    
    //CLP Commands
    //**************************************************************************
    private void createPlan_Create_DB(){
        int indice = 2;
        String nombre = _Querry[indice];
        _Plan += "CREATE_DB "+nombre;
    }
    
    private void createPlan_Drop_DB(){
        int indice = 2;
        String nombre = _Querry[indice];
        //validar que existe la tabla
        _Plan += "DELETE_DB "+nombre;
    }
    
    private void createPlan_List_DB(){
        _Plan += "List_DB";
    }
    
    private void createPlan_Start(){
        //?
    }
    private void createPlan_Stop(){
        //?
    }
    private void createPlan_Get_Status(){
        //?
    }
    private void createPlan_Display_DB(){
        int indice = 2;
        String nombre = _Querry[indice];
        //validar que existe la tabla
        _Plan += "DISPLAY_DB "+nombre;
    }
    
    //**************************************************************************
    
    
    //DDL Commands
    //**************************************************************************
    private void createPlan_Set_DB(){
        int indice = 2;
        String nombre = _Querry[indice];
        _Plan += "Set_DB "+nombre;
        //asignarla a una variable?
    }
    private void createPlan_Create_Table(){
        int indice = 2;
        String nombre = _Querry[indice];
        _Plan += "CREATE_TABLE "+nombre;
        
        //columnas al system catalog
    }
    
    private void createPlan_Alter_Table(){
        int indice = 2;
        String nombre = _Querry[indice];
        //validar que existe la tabla
        _Plan += "OPEN_TABLE "+nombre+" WRITE\n";
    }
    
    private void createPlan_Drop_Table(){
        int indice = 2;
        String nombre = _Querry[indice];
        //validar que existe la tabla
        _Plan += "DELETE_TABLE "+nombre+"\n";
    }
    
    private void createPlan_Create_Index(){
        int indice = 2;
        String tabla = _Querry[indice];
        //validar que existe la tabla
        String columna = _Querry[indice+2];
        _Plan += "CREATE_INDEX "+columna+" IN "+tabla+ "\n";
    }
    
    //**************************************************************************
    
    
    //DML Commands
    //**************************************************************************
    private void createPlan_Select(){
        int indice = findInQuerry("FROM")+1;
        String tabla = _Querry[indice];
        if(tabla.toUpperCase().compareTo("JOIN")==0){
            //validaciones para el join
        }
        else{
            //validar que existe la tabla en el catalogo
            _Plan += "OPEN_TABLE "+tabla+" READ\n";
            indice++;
            
        } 
        
    }
    
    private void createPlan_Update(){
        int indice = 1;
        String tabla = _Querry[indice];
        //validar que existe la tabla
        _Plan += "OPEN_TABLE "+tabla+" WRITE\n";
        
        //VALIDAR SET
        
        //VALIDAR WHERE
    }  
    
    private void createPlan_Delete(){
        int indice = 2;
        String tabla = _Querry[indice];
        //validar que exista la tabla
        _Plan +="OPEN_TABLE "+tabla+" WRITE\n";
        
        //VALIDAR WHERE
    }   
    
    private void createPlan_Insert(){
        int indice = 2;//se salta las posiciones de insert into
        String tabla = _Querry[indice];
        //validar q exista la tabla
        _Plan += "OPEN_TABLE "+tabla+" WRITE\n";
        indice+=2;//se posiciona en la primera columna
        int indice2 = findInQuerry("VALUES")+2;// se salta el caracter "(" 
        String columna;
        String valor;
        while(indice2 < _Querry.length -1){//se sale al llegar el contador 2 al caracter ")"
            columna = _Querry[indice];
            valor = _Querry[indice2];
            if(0==columna.compareTo(")")){//compara el caracter )
                //error mas valores que columnas
            }
            //validar que existe columna
            //HAY QUE ORDENAR LA TUPLA
        }
    }
    //**************************************************************************
    

    
    //Funcion de entrada
    
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
        if(comando.compareTo("LIST")==0){//list database
            createPlan_List_DB();
        }
        if(comando.compareTo("START")==0){
            createPlan_Start();
        }
        if(comando.compareTo("STOP")==0){
            createPlan_Stop();
        }
        if(comando.compareTo("GET")==0){//get status
            createPlan_Get_Status();
        }
        if(comando.compareTo("DISPLAY")==0){//display database
            createPlan_Display_DB();
        }
        if(comando.compareTo("SET")==0){//set database
            createPlan_Set_DB();
        }
        if(comando.compareTo("ALTER")==0){//alter table
            createPlan_Alter_Table();
        }
        
        if(comando.compareTo("CREATE")==0){
            
            if(comando2.compareTo("DATABASE")==0){
                createPlan_Create_DB();
            }
            if(comando2.compareTo("TABLE")==0){
            createPlan_Create_Table();
            }
            if(comando2.compareTo("INDEX")==0){
                createPlan_Create_Index();
            }
        }
        
        if(comando.compareTo("DROP")==0){
            if(comando2.compareTo("DATABASE")==0){
                createPlan_Drop_DB();
            }
            if(comando2.compareTo("TABLE")==0){
                createPlan_Drop_Table();
            }
        }
        
    
    }
    
    
    
}
