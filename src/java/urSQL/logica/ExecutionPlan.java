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
   
    private void writeBlockOfQuerry(int pi,int pj){
        _Plan+="~";
        int i = pi;
        while(i<=pj){
            _Plan+=_Querry[i];
            i++;
        }
        
    }
   
   
    private String adjustQuerry(String pQuerry){
        String tmp =pQuerry;
        boolean fin=false;
        int i=0;
       
        while(!fin){// agrega espacios en las comas
            int coma = tmp.indexOf(",",i);
            if(coma!=-1){
                    i=coma+2;
                    tmp = tmp.substring(0, coma)+" ,"+tmp.substring(coma+1);
                }
            else{
                fin=true;
            }
        }
        i=0;
        fin=false;
        
        while(!fin){//agrega espacios luego de parentesis izquierdo
            int parentesisIzq = tmp.indexOf("(",i);
            if(parentesisIzq!=-1){
                    i=parentesisIzq+2;
                    tmp = tmp.substring(0, parentesisIzq)+"( "+tmp.substring(parentesisIzq+1);
 
            }
            else{
                fin=true;
            }
        }
        i=0;
        fin=false;
        
        while(!fin){//agrega espacios entes de parentesis derecho
            int parentesisDer = tmp.indexOf(")",i);
            if(parentesisDer!=-1){
                     i=parentesisDer+2;
                    tmp = tmp.substring(0, parentesisDer)+" "+tmp.substring(parentesisDer);
            }
            else{
                fin=true;
            }
        }
        
        int comilla=0;
        i=0;
        while(comilla!=-1){//elimina comillas innecesarias en los valores
            comilla = tmp.indexOf("'",i);
            if(comilla!=-1){
                tmp = tmp.substring(0, comilla)+tmp.substring(comilla+1);
                i=comilla;
            }
           
        }
       
       
        return tmp;
    }
   
    private void writeColumnDefinition(int pi,int pj,String ptabla){
        String columna = _Querry[pi];
        
        
    }
    
    private void checkSetStatement(int pi,int pj,String pTabla){
        int i=pi;
        String columna;
        String value;
        while(i<pj){
            columna=_Querry[i];
            //validar que existe la columna
            i+=2;
            value=_Querry[i];
            //validar el tipo
            i+=2;//para saltar la coma
            _Plan+="SET~"+columna+"~"+value+"\n";
        }
    }
   
    private void checkWhereStatement(int pi,int pj,String pTabla){
        int i=pi;
        int estado=0;
        String columna="";
        String operador="";
        _Plan+="WHERE~";
        while(i<pj){
            if(estado==0){//estado inicial,inicio de where statement
                String token = _Querry[i];
                _Plan+=token;
                if(token.compareTo("(")!=0 && token.compareTo(")")!=0
                        && token.toUpperCase().compareTo("AND")!=0
                        && token.toUpperCase().compareTo("OR")!=0){
                    //ignora los parantesis y los operadores and y or
                    columna=token;
                    //validar que exista la columna en pTabla
                    estado=1;
                }
                
            }
            else if(estado==1){//estado para el compare operator   >, <, =, like, not, is null, is not null
                String token = _Querry[i];
                operador+=token;
               
                if(token.toLowerCase().compareTo("is")==0){
                    i++;
                    token = _Querry[i];
                    operador+=" "+token;
                    if(token.toLowerCase().compareTo("not")==0){
                        i++;
                        token = _Querry[i];
                        operador+=" "+token;
                    }
                }
                estado=2;
            }
            else{//estado para value
                if(operador.toLowerCase().compareTo("is null")!=0 &&
                        operador.toLowerCase().compareTo("is not null")!=0){//para esos casos no hay value
                    //validar el tipo de la columna
                    String token = _Querry[i];//value
                    _Plan+="~"+token;
                }
                else{
                    i--;//para compensar la falta del token de value
                }
                estado=0;
            }
            i++;
        }
        _Plan+="~"+operador+"\n";
        
    }
    //**************************************************************************
   
   
   
    //CLP Commands
    //**************************************************************************
    private void createPlan_Create_DB(){
        int indice = 2;
        String nombre = _Querry[indice];
        _Plan += "CREATE_DB~"+nombre;
    }
   
    private void createPlan_Drop_DB(){
        int indice = 2;
        String nombre = _Querry[indice];
        //validar que existe la tabla
        _Plan += "DELETE_DB~"+nombre;
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
        _Plan += "DISPLAY_DB~"+nombre;
    }
   
    //**************************************************************************
   
   
    //DDL Commands
    //**************************************************************************
    private void createPlan_Set_DB(){
        int indice = 2;
        String nombre = _Querry[indice];
        _Plan += "Set_DB~"+nombre;
        //asignarla a una variable?
    }
    private void createPlan_Create_Table(){
        int indice = 2;
        String tabla = _Querry[indice];
        _Plan += "CREATE_TABLE~"+tabla+"\n";
       
        indice=4;//se salta el parentesis
        int estado =0;
        String tipo="";
        boolean fin=false;
        while(!fin){
            if(estado==0){//nombre columna
                String columna = _Querry[indice];
                if(columna.toUpperCase().compareTo("PRIMARY")==0){//declaracion de primary key
                    String pk = _Querry[indice+3];//
                    _Plan+="PRIMARY_KEY~"+tabla+"~"+pk+"\n";
                    fin=true;
                }
                else{
                    _Plan+= "NEW_COLUMN~"+tabla+"~"+columna;
                    estado=1;
                    tipo="";
                }
            }
            else if(estado==1){//obteniendo tipo
                String tmp = _Querry[indice];
                if(tmp.compareTo(",")==0){//fin de declaracion
                    _Plan+="~"+tipo+"~NULL\n";
                    estado=0;
                }
                else if(tmp.toUpperCase().compareTo("NOT")==0){
                    _Plan+="~"+tipo+"~NOT_NULL\n";
                    estado=0;
                    indice+=2;
                }
                else if(tmp.toUpperCase().compareTo("NULL")==0){
                    _Plan+="~"+tipo+"~NULL\n";
                    estado=0;
                    indice++;
                }
                tipo+=tmp;
            }
            indice++;
            
        }
        
        
        
        
        
        
        
        
    }
   
    private void createPlan_Alter_Table(){
        int indice = 2;
        String nombre = _Querry[indice];
        //validar que existe la tabla
        _Plan += "OPEN_TABLE~"+nombre+" WRITE\n";
    }
   
    private void createPlan_Drop_Table(){
        int indice = 2;
        String nombre = _Querry[indice];
        //validar que existe la tabla
        
        _Plan += "DELETE_TABLE~"+nombre;
    }
   
    private void createPlan_Create_Index(){
        String nombre = _Querry[2];
        String tabla = _Querry[4];
        //validar que existe la tabla
        //validacion nombre indice?
        String columna = _Querry[6];
        _Plan += "OPEN_TABLE~"+tabla+"\n";
        _Plan += "CREATE_INDEX~"+nombre+"~"+columna;
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
            if(findInQuerry("WHERE")!=-1){//select con where
                int indice2;
                if(findInQuerry("GROUP")!=-1){
                    indice2=findInQuerry("GROUP");
                }
                else if(findInQuerry("FOR")!=-1){
                    indice2=findInQuerry("FOR");
                }
                else{
                    indice2=_Querry.length;
                }
                checkWhereStatement(indice,indice2,tabla);
            }
            if(findInQuerry("GROUP")!=-1){//select con group by
                //validar que existan las columnas
            }
            if(findInQuerry("FOR")!=-1){//select con group by
                //?
            }
           
           
        }
       
    }
   
    private void createPlan_Update(){
        int indice = 1;
        String tabla = _Querry[indice];
        //validar que existe la tabla
        _Plan += "OPEN_TABLE~"+tabla+"\n";
        indice++;
       
        if(findInQuerry("WHERE")!=-1){//select con where
                int indice2=_Querry.length;
                checkWhereStatement(findInQuerry("WHERE")+1,indice2,tabla);
                checkSetStatement(findInQuerry("SET")+1,findInQuerry("WHERE"),tabla);
            }
        else{//select sin where
            checkSetStatement(findInQuerry("SET")+1,_Querry.length,tabla);
        }
    }  
   
    private void createPlan_Delete(){
        int indice = 2;
        String tabla = _Querry[indice];
        //validar que exista la tabla
        _Plan +="OPEN_TABLE~"+tabla+"\n";
       
        if(findInQuerry("WHERE")!=-1){//select con where
                int indice2=_Querry.length;
                checkWhereStatement(findInQuerry("WHERE")+1,indice2,tabla);
                _Plan+="DELETE";
            }
        else{
            _Plan +="DELETE~ALL\n";
        }
    }  
   
    private void createPlan_Insert(){
        int indice = 2;//se salta las posiciones de insert into
        String tabla = _Querry[indice];
        //validar q exista la tabla
        
        _Plan += "OPEN_TABLE~"+tabla+"\n";
        indice+=2;//se posiciona en la primera columna
        int indice2 = findInQuerry("VALUES")+2;// se posiciona en el primer valor
        _Plan+="INSERT";
        
        if((indice2-4-indice)==(_Querry.length-2-indice2)){
            
            //VALIDAR QUE INCLUYA VALOR UNICO PARA LLAVE PRIMARIA
            //VALIDAR QUE COLUMNAS FALTANTES AGUANTEN NULL
            writeBlockOfQuerry(indice,indice2-4);//toma hasta el cierre del parentesis de las columnas
            writeBlockOfQuerry(indice2,_Querry.length-2);//el -2 es para excluir el parentesis derecho

        }
        else{
            System.out.println("error cant de columnas != cant de valores");
        }
    }
    //**************************************************************************
   
 
   
    //Funcion de entrada
   
    /**
     *
     * @param pQuerry
     */
       
    public void createPlan(String pQuerry){
        String tmp = adjustQuerry(pQuerry);
        _Querry = tmp.split(" ");
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
       System.out.println(_Plan);
        //guardar la variable _Plan en un archivo
       
    
    }
   
   
   
}