/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.threads;
 
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import org.json.JSONException;
import urSQL.logica.logHandler;
 
 
/**
 *
 * @author Shagy
 */
public class RuntimeDBProcessor implements Callable {
    
    String Query ="";
    RuntimeDBProcessor() throws Exception{
        
    }
 
    public void set_Query(String pQuery){
        Query = pQuery;
    }
    
    @Override
    public String call() throws Exception {
        
        boolean tmp = Parse(Query);
        System.out.println(tmp);
        return Boolean.toString(tmp);
    }
   
 
    
 
    /**
     *
     */
    
   
   
   
   
   
   
   // PARSER
   
    /**
     *
     * @param pQuery
     * @throws java.lang.InterruptedException
     * @throws java.util.concurrent.ExecutionException
     * @throws java.io.IOException
     * @throws org.json.JSONException
     */
       
    
    public boolean Parse(String pQuery) throws InterruptedException, ExecutionException, IOException, JSONException{
    try{
            net.sf.jsqlparser.statement.Statement parse = CCJSqlParserUtil.parse(pQuery);
            //net.sf.jsqlparser.statement.Statement parse = CCJSqlParserUtil.parse("select *");
            //System.out.println(parse.toString());
            
            return createPlan(parse.toString());//crea el plan de ejecucion
        }
        catch (JSQLParserException ex) {
            if(SecondTry(pQuery)){
                return createPlan(elimSpaces(pQuery));
            }
            else{
                String msj = ex.getCause().toString();
                //System.out.println(msj.substring(30, msj.indexOf("Was expecting")));
                return false;
            }
            
            //ver si es un clp o alter
           
           
            /*
            Error: 1149 SQLSTATE: 42000 (ER_SYNTAX_ERROR)
 
Message: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use
           
            */
        }
    }
   
    private String elimSpaces(String pQuery){
        String tmp = "";
        boolean espacio=true;
        for(int i=0;i<pQuery.length();i++){
            if((pQuery.substring(i,i+1).compareTo(" ")==0 && espacio)
                    || pQuery.substring(i,i+1).compareTo(";")==0
                    || pQuery.substring(i,i+1).compareTo("\n")==0){
                //pass
            }
            else{
                if(pQuery.substring(i,i+1).compareTo(" ")==0){
                    tmp+=pQuery.substring(i,i+1);
                    espacio = true;
                }
                else{
                    tmp+=pQuery.substring(i,i+1);
                    espacio = false;
                }
            }
        }
        return tmp;
    }
   
    private boolean SecondTry(String pQuery){
        
        boolean valido=false;
        String tmp = elimSpaces(pQuery);
        if(tmp.length()>16 && tmp.substring(0,16).toUpperCase().compareTo("CREATE DATABASE ")==0){
            valido=true;
        }
        if(tmp.toUpperCase().compareTo("LIST DATABASES")==0){
            valido=true;
        }
        if(tmp.toUpperCase().compareTo("START")==0){
            valido=true;
        }
        if(tmp.toUpperCase().compareTo("GET STATUS")==0){
            valido=true;
        }
        if(tmp.toUpperCase().compareTo("STOP")==0){
            valido=true;
        }
        if(tmp.length()>17 && tmp.substring(0,17).toUpperCase().compareTo("DISPLAY DATABASE ")==0){
            valido=true;
        }
        if (tmp.length()>13 && tmp.substring(0,13).toUpperCase().compareTo("SET DATABASE ")==0){
            valido=true;
        }
        
        return valido;
    }
    
    //**************************************************************************
    
    
    
    
    //CREACION DEL PLAN DE EJECUCION
   
    private String[] _Query;
    private String _Plan="";
    
    //funciones auxiliares
    //**************************************************************************
    private int findInQuery(String pString){
        for(int i=0;i<_Query.length;i++){
            if(_Query[i].toUpperCase().compareTo(pString)==0){
                return i;
            }
        }
        return -1;
    }
   
    private void writeBlockOfQuery(int pi,int pj){
        _Plan+="~";
        int i = pi;
        while(i<=pj){
            _Plan+=_Query[i];
            i++;
        }
       
    }
   
   
    private String adjustQuery(String pQuery){
        String tmp =pQuery;
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
   
   
private boolean checkJoinStatement(int pi,int pj) throws InterruptedException, ExecutionException{
        int i =pi;
        String tmp="JOIN";
        while(i<=pj){
            String tabla = _Query[i];
            ThreadManager._SYCT.set_Plan(null, tabla, null, null, null, null, null, "V_T");
            ThreadManager._Pool.execute(ThreadManager.futureSystem);
            ThreadManager.waitSC();
            String Resp= (String) ThreadManager.futureSystem.get();
 
            if(Resp.compareTo("true")==0 && i==pi){
                _Plan+="OPEN_TABLE~"+tabla+"\n";
               // tmp+="~"+tabla;
                i+=2;
            }
            else if(i==pi+2){
                tmp+="~"+tabla;
            }
            else{
                //error no existe tabla
                return false;
            }
        }
        
        _Plan+=tmp+"\n";
        return true;
    }
   
    private boolean checkSetStatement(int pi,int pj,String pTabla) throws InterruptedException, ExecutionException{
        int i=pi;
        String columna;
        String value;
        while(i<pj){
            columna=_Query[i];
           
            ThreadManager._SYCT.set_Plan(ThreadManager.Current_Schema, pTabla, columna, null, null, null, null, "V_C");
            ThreadManager._Pool.execute(ThreadManager.futureSystem);
            ThreadManager.waitSC();
            String Resp= (String) ThreadManager.futureSystem.get();
 
            if(Resp.compareTo("true")==0){
                i+=2;
                value=_Query[i];
               
                //validar el tipo
               
                i+=2;//para saltar la coma
                _Plan+="SET~"+columna+"~"+value+"\n";
            }
            else{
                //error no existe columna
                return false;
            }
        }
        return true;
    }
   
    private boolean checkWhereStatement(int pi,int pj,String pTabla) throws InterruptedException, ExecutionException{
        int i=pi;
        int estado=0;
        String columna="";
        String operador="";
        _Plan+="WHERE~";
        while(i<pj){
            if(estado==0){//estado inicial,inicio de where statement
                String token = _Query[i];
                _Plan+=token;
                if(token.compareTo("(")!=0 && token.compareTo(")")!=0
                        && token.toUpperCase().compareTo("AND")!=0
                        && token.toUpperCase().compareTo("OR")!=0){
                    //ignora los parantesis y los operadores and y or
                    columna=token;
                    //validar que exista la columna en pTabla
                    ThreadManager._SYCT.set_Plan(ThreadManager.Current_Schema, pTabla, columna, null, null, null, null, "V_C");
                    ThreadManager._Pool.execute(ThreadManager.futureSystem);
                    ThreadManager.waitSC();
                    String Resp= (String) ThreadManager.futureSystem.get();
 
                    if(Resp.compareTo("true")==0){
                        estado=1;
                    }
                    else{
                        //error no existe columna
                        return false;
                    }
                }
               
            }
            else if(estado==1){//estado para el compare operator   >, <, =, like, not, is null, is not null
                String token = _Query[i];
                operador+=token;
               
                if(token.toLowerCase().compareTo("is")==0){
                    i++;
                    token = _Query[i];
                    operador+=" "+token;
                    if(token.toLowerCase().compareTo("not")==0){
                        i++;
                        token = _Query[i];
                        operador+=" "+token;
                    }
                }
                estado=2;
            }
            else{//estado para value
                if(operador.toLowerCase().compareTo("is null")!=0 &&
                        operador.toLowerCase().compareTo("is not null")!=0){//para esos casos no hay value
                    //validar el tipo de la columna
                    String token = _Query[i];//value
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
        return true;
       
    }
    //**************************************************************************
   
   
   
    //CLP Commands
    //**************************************************************************
    private boolean createPlan_Create_DB(){
        int indice = 2;
        String nombre = _Query[indice];
        _Plan += "CREATE_DB~"+nombre;
        return true;
    }
   
    private boolean createPlan_Drop_DB() throws InterruptedException, ExecutionException{
        int indice = 2;
        String nombre = _Query[indice];
        
        ThreadManager._SYCT.set_Plan(nombre, null, null, null, null, null, null, "V_S");
        ThreadManager._Pool.execute(ThreadManager.futureSystem);
        ThreadManager.waitSC();
        String Resp= (String) ThreadManager.futureSystem.get();
        
        if(Resp.compareTo("true")==0){
            _Plan += "DELETE_DB~"+nombre;
            return true;
        }
        else{
            return false;
        }
    }
   
    private boolean createPlan_List_DB(){
        _Plan += "LIST_DB";
        return true;
    }
   
    private boolean createPlan_Start(){
        //?
        return true;
    }
    private boolean createPlan_Stop(){
        //?
        return true;
    }
    private boolean createPlan_Get_Status(){
        //?
        return true;
    }
    private boolean createPlan_Display_DB() throws InterruptedException, ExecutionException{
        int indice = 2;
        String nombre = _Query[indice];
        ThreadManager._SYCT.set_Plan(nombre, null, null, null, null, null, null, "V_S");
        ThreadManager._Pool.execute(ThreadManager.futureSystem);
        ThreadManager.waitSC();
        String Resp= (String) ThreadManager.futureSystem.get();
       
        if(Resp.compareTo("true")==0){
            _Plan += "DISPLAY_DB~"+nombre;
            return true;
        }
        else{
            //no existe esquema
            return false;
        }
    }
   
    //**************************************************************************
   
   
    //DDL Commands
    //**************************************************************************
    private boolean createPlan_Set_DB() throws InterruptedException, ExecutionException{
        
        int indice = 2;
        String nombre = _Query[indice];
        
        ThreadManager._SYCT.set_Plan(nombre, null, null, null, null, null, null,"V_S");
        ThreadManager._Pool.execute(ThreadManager.futureSystem);
        ThreadManager.waitSC();
        String Resp= (String) ThreadManager.futureSystem.get();
       
        if(Resp.compareTo("true")==0){
        _Plan += "SET_DB~"+nombre;
        return true;
        }
        else{
            return false;
        }
    }
    private boolean createPlan_Create_Table(){
        int indice = 2;
        String tabla = _Query[indice];
        _Plan += "CREATE_TABLE~"+tabla+"\n";
       
        indice=4;//se salta el parentesis
        int estado =0;
        String tipo="";
        boolean fin=false;
        while(!fin){
            if(estado==0){//nombre columna
                String columna = _Query[indice];
                if(columna.toUpperCase().compareTo("PRIMARY")==0){//declaracion de primary key
                    String pk = _Query[indice+3];//
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
                String tmp = _Query[indice];
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
        return true; //tabla duplicada?
       
       
       
       
       
       
       
       
    }
   
    private boolean createPlan_Alter_Table() throws InterruptedException, ExecutionException{
        int indice = 2;
        String tabla = _Query[indice];
       
        ThreadManager._SYCT.set_Plan(null, tabla, null, null, null, null, null, "V_T");
        ThreadManager._Pool.execute(ThreadManager.futureSystem);
        ThreadManager.waitSC();
        String Resp= (String) ThreadManager.futureSystem.get();
       
        if(Resp.compareTo("true")==0){
            _Plan += "OPEN_TABLE~"+tabla+" WRITE\n";
            return true;
        }
        else{
            //error no existe tabla
            return false;
        }
    }
   
    private boolean createPlan_Drop_Table() throws InterruptedException, ExecutionException{
        int indice = 2;
        String tabla = _Query[indice];
       
        ThreadManager._SYCT.set_Plan(ThreadManager.Current_Schema, tabla, null, null, null, null, null, "V_T");
        ThreadManager._Pool.execute(ThreadManager.futureSystem);
        ThreadManager.waitSC();
        String Resp= (String) ThreadManager.futureSystem.get();
       
        if(Resp.compareTo("true")==0){
       
            _Plan += "DELETE_TABLE~"+tabla;
            return true;
        }
        else{
            //error no existe tabla
            return false;
        }
    }
   
    private boolean createPlan_Create_Index() throws InterruptedException, ExecutionException{
        String nombre = _Query[2];
        String tabla = _Query[4];
       
        ThreadManager._SYCT.set_Plan(ThreadManager.Current_Schema, tabla, null, null, null, null, null, "V_T");
        ThreadManager._Pool.execute(ThreadManager.futureSystem);
        ThreadManager.waitSC();
        String Resp= (String) ThreadManager.futureSystem.get();
       
            if(Resp.compareTo("true")==0){
                //validacion nombre indice?
                String columna = _Query[6];
                _Plan += "OPEN_TABLE~"+tabla+"\n";
                _Plan += "CREATE_INDEX~"+nombre+"~"+columna;
                return true;
            }
            else{
                //no existe tabla
                return false;
            }
    }
   
    //**************************************************************************
   
   
    //DML Commands
    //**************************************************************************
private boolean createPlan_Select() throws InterruptedException, ExecutionException{
        int indice = findInQuery("FROM")+1;
        String tabla = _Query[indice];
       
        int indiceWhere=findInQuery("WHERE");
        int indiceGroup=findInQuery("GROUP");
        int indiceFor=findInQuery("FOR");
        int indiceJoin =findInQuery("JOIN");
       boolean flag=true;
        if(indiceJoin!=-1){//select con join
            if(indiceWhere!=-1){
               flag=checkJoinStatement(indiceJoin-1,indiceWhere-1);
            }  
            else if(indiceGroup!=-1){
                flag=checkJoinStatement(indiceJoin-1,indiceGroup-1);
            }
            else if(indiceFor!=-1){
                flag=checkJoinStatement(indiceJoin-1,indiceFor-1);
            }
            else{
                flag=checkJoinStatement(indiceJoin-1,_Query.length-1);
            }
           
        }
        if(!flag){
            return false;
        }
        if(indiceJoin==-1){//select con una tabla
           
            ThreadManager._SYCT.set_Plan(ThreadManager.Current_Schema, tabla, null, null, null, null, null, "V_T");
            ThreadManager._Pool.execute(ThreadManager.futureSystem);
            ThreadManager.waitSC();
            String Resp= (String) ThreadManager.futureSystem.get();
 
            if(Resp.compareTo("true")==0){
                flag=true;
                _Plan += "OPEN_TABLE~"+tabla+"\n";
                indice++;
            }
            else{
                return false;
            }
        }
        if(indiceWhere!=-1 && flag){//select con where
            int indice2;
            if(indiceGroup!=-1){
                indice2=indiceGroup;
                if(!checkWhereStatement(indiceWhere+1,indice2,tabla)){
                    return false;
                }
            }
            else if(indiceFor!=-1){
                indice2=indiceFor;
                if(!checkWhereStatement(indiceWhere+1,indice2,tabla)){
                    return false;
                }
            }
            else{
                indice2=_Query.length;
                if(!checkWhereStatement(indiceWhere+1,indice2,tabla)){
                    return false;
                }
            }

        }
        if(indiceGroup!=-1 && flag){//select con group by
            _Plan += "GROUP_BY";
            int fin;
            if(indiceFor!=-1){
                fin=indiceFor;
            }
            else{
                fin=_Query.length-1;
            }
            indiceGroup+=2;
            while(indiceGroup<=fin){
                String columna = _Query[indiceGroup];
                ThreadManager._SYCT.set_Plan(ThreadManager.Current_Schema, tabla, columna, null, null, null, null, "V_C");
                ThreadManager._Pool.execute(ThreadManager.futureSystem);
                ThreadManager.waitSC();
                String Resp= (String) ThreadManager.futureSystem.get();

                if(Resp.compareTo("true")==0){
                        _Plan +="~"+columna;
                        indiceGroup+=2;//se salta la coma
                }
                else{
                    return false;
                }
            
            }
            _Plan += "\n";

        }
        if(indiceFor!=-1 && flag){//select con for json/xml
            if(findInQuery("JSON")!=-1){
                _Plan += "FOR_JSON\n";
            }
            else{
                _Plan += "FOR_XML\n";
            }
        }

        String columna;
        String tmp="";
        for(int i=1;i<indice-1;i+=1){
            columna = _Query[i];
            if(i==1 && columna.compareTo("*")==0){
                break;
            }
            //validar q exista columna
            ThreadManager._SYCT.set_Plan(ThreadManager.Current_Schema, tabla, columna, null, null, null, null, "V_C");
            ThreadManager._Pool.execute(ThreadManager.futureSystem);
            ThreadManager.waitSC();
            String Resp= (String) ThreadManager.futureSystem.get();

            if(Resp.compareTo("true")==0){
                tmp +=columna;
            }
            else if(columna.compareTo(",")==0){
                tmp +=columna;
            }
        
            else{
                return false;
            }
            
        }
        if(tmp.compareTo("")!=0){
            _Plan+="SELECT~"+tmp;
        }

        return true;    
    }
   
    private boolean createPlan_Update() throws InterruptedException, ExecutionException{
        int indice = 1;
        String tabla = _Query[indice];
       
        ThreadManager._SYCT.set_Plan(ThreadManager.Current_Schema, tabla, null, null, null, null, null, "V_T");
        ThreadManager._Pool.execute(ThreadManager.futureSystem);
        ThreadManager.waitSC();
        String Resp= (String) ThreadManager.futureSystem.get();
       
        if(Resp.compareTo("true")==0){
            _Plan += "OPEN_TABLE~"+tabla+"\n";
            indice++;
 
            if(findInQuery("WHERE")!=-1){//select con where
                    int indice2=_Query.length;
                    if(checkWhereStatement(findInQuery("WHERE")+1,indice2,tabla) 
                            && checkSetStatement(findInQuery("SET")+1,findInQuery("WHERE"),tabla)){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            else{//select sin where
                if(checkSetStatement(findInQuery("SET")+1,_Query.length,tabla)){
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        else{
            //error no existe la tabla
            return false;
        }
    }  
   
    private boolean createPlan_Delete() throws InterruptedException, ExecutionException{
        int indice = 2;
        String tabla = _Query[indice];
       
        ThreadManager._SYCT.set_Plan(ThreadManager.Current_Schema, tabla, null, null, null, null, null, "V_T");
        ThreadManager._Pool.execute(ThreadManager.futureSystem);
        ThreadManager.waitSC();
        String Resp= (String) ThreadManager.futureSystem.get();
       
        if(Resp.compareTo("true")==0){
            _Plan +="OPEN_TABLE~"+tabla+"\n";
 
            if(findInQuery("WHERE")!=-1){//select con where
                    int indice2=_Query.length;
                    if(checkWhereStatement(findInQuery("WHERE")+1,indice2,tabla)){
                        _Plan+="DELETE";
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            else{
                _Plan +="DELETE~ALL\n";
                return true;
            }
        }
        else{
            //no existe la tabla
            return false;
        }
    }  
   
    private boolean createPlan_Insert() throws InterruptedException, ExecutionException{
        int indice = 2;//se salta las posiciones de insert into
        String tabla = _Query[indice];
       
        ThreadManager._SYCT.set_Plan(ThreadManager.Current_Schema, tabla, null, null, null, null, null, "V_T");
        ThreadManager._Pool.execute(ThreadManager.futureSystem);
        ThreadManager.waitSC();
        String Resp= (String) ThreadManager.futureSystem.get();
       
        if(Resp.compareTo("true")==0){
           
            _Plan += "OPEN_TABLE~"+tabla+"\n";
            indice+=2;//se posiciona en la primera columna
            int indice2 = findInQuery("VALUES")+2;// se posiciona en el primer valor
            _Plan+="INSERT";
 
            if((indice2-4-indice)==(_Query.length-2-indice2)){
                //VALIDAR QUE INCLUYA VALOR UNICO PARA LLAVE PRIMARIA
                //VALIDAR QUE COLUMNAS FALTANTES AGUANTEN NULL
                writeBlockOfQuery(indice,indice2-4);//toma hasta el cierre del parentesis de las columnas
                writeBlockOfQuery(indice2,_Query.length-2);//el -2 es para excluir el parentesis derecho
                return true;
 
            }
            else{
                System.out.println("error cant de columnas != cant de valores");
                return false;
            }
        }
        else{
            return false;
            //tabla no existe
        }
    }
    //**************************************************************************
   
 
   
    //Funcion de entrada
   
    /**
     *
     * @param pQuery
     * @throws java.lang.InterruptedException
     * @throws java.util.concurrent.ExecutionException
     */
       
    public boolean createPlan(String pQuery) throws InterruptedException, ExecutionException, IOException, JSONException{
        String tmp = adjustQuery(pQuery);
        _Query = tmp.split(" ");
        String comando = _Query[0].toUpperCase();
        String comando2 = _Query[1].toUpperCase();
        boolean valido = false;
       
        if(comando.compareTo("SELECT")==0){
            valido = createPlan_Select();
        }
        if(comando.compareTo("UPDATE")==0){
            valido = createPlan_Update();
        }
        if(comando.compareTo("DELETE")==0){
            valido = createPlan_Delete();
        }
        if(comando.compareTo("INSERT")==0){
            valido = createPlan_Insert();
        }
        if(comando.compareTo("LIST")==0){//list database
            valido = createPlan_List_DB();
        }
        if(comando.compareTo("START")==0){
            valido = createPlan_Start();
        }
        if(comando.compareTo("STOP")==0){
            valido = createPlan_Stop();
        }
        if(comando.compareTo("GET")==0){//get status
            valido = createPlan_Get_Status();
        }
        if(comando.compareTo("DISPLAY")==0){//display database
            valido = createPlan_Display_DB();
        }
        if(comando.compareTo("SET")==0){//set database
            valido = createPlan_Set_DB();
        }
        if(comando.compareTo("ALTER")==0){//alter table
            valido = createPlan_Alter_Table();
        }
       
        if(comando.compareTo("CREATE")==0){
           
            if(comando2.compareTo("DATABASE")==0){
                valido = createPlan_Create_DB();
            }
            if(comando2.compareTo("TABLE")==0){
                valido = createPlan_Create_Table();
            }
            if(comando2.compareTo("INDEX")==0){
                valido = createPlan_Create_Index();
            }
        }
       
        if(comando.compareTo("DROP")==0){
            if(comando2.compareTo("DATABASE")==0){
                valido = createPlan_Drop_DB();
            }
            if(comando2.compareTo("TABLE")==0){
                valido = createPlan_Drop_Table();
            }
        }
        if(valido){
            
            logHandler.getInstance().logExecution_Plan(_Plan);
             //guardar la variable _Plan en un archivo
            return true;
        }
        else{
            return false;
        }
       
   
    }
   
   
}