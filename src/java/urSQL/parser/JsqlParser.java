/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.parser;
 
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import urSQL.logica.ExecutionPlan;
/**
 *
 * @author RafaelAngel
 */
public class JsqlParser {
   
    ExecutionPlan executionPlan = new ExecutionPlan();
   
    /**
     *
     * @param pQuerry
     * @return
     */
    public boolean Parse(String pQuerry){
    try{
            net.sf.jsqlparser.statement.Statement parse = CCJSqlParserUtil.parse(pQuerry);
            //net.sf.jsqlparser.statement.Statement parse = CCJSqlParserUtil.parse("select *");
            //System.out.println(parse.toString());
           
            executionPlan.createPlan(parse.toString());//crea el plan de ejecucion
            return true;
        }
        catch (JSQLParserException ex) {
            SecondTry(pQuerry);
            String msj = ex.getCause().toString();
            //System.out.println(msj);
            System.out.println(msj.substring(30, msj.indexOf("Was expecting")));
            return false;
           
           
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
   
    private void SecondTry(String pQuery){
        boolean valido=false;
        String tmp = elimSpaces(pQuery);
        if(tmp.substring(0,16).toUpperCase().compareTo("CREATE DATABASE ")==0){
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
        if(tmp.substring(0,17).toUpperCase().compareTo("DISPLAY DATABASE ")==0){
            valido=true;
        }
        if(valido){
            executionPlan.createPlan(tmp);
        }
    }
   
    /**
     *
     * @param args
     */
    public static void main(String [] args)
        {
           
           
            JsqlParser parser = new JsqlParser();
            parser.Parse("select");
       
        /*("ALTER TABLE Orders\n" +
            "ADD CONSTRAINT\n" +
            "FOREIGN KEY (P_Id)\n" +
            "REFERENCES Persons(P_Id)");
       
        ("DROP TABLE tabla");
       
       
        ("CREATE INDEX INDICE ON\n" +
            "\n" +
            "tabla (col1)");
       
       
       
        ("CREATE TABLE Persons\n" +
            "(\n" +
            "P_Id int NOT NULL,\n" +
            "LastName varchar(255) NOT NULL,\n" +
            "FirstName varchar,\n" +
            "Address DATETIME,\n" +
            "City varchar(255),\n" +
            "PRIMARY KEY (P_Id)\n" +
            ")");
       
        ("INSERT INTO tabla1 (col2,col1, col5)\n" +
            "\n" +
            "VALUES(1,2,th)");
       
       
        ("UPDATE tabla1\n" +
            "SET colum = val , col2 = val2\n" +
            "\n" +
            "WHERE colum='Germany';\n");
       
        ("CREATE DATABASE adname");
 
        ("SELECT column_name  ,column_name \n FROM table_name;");
                   
                    ("DEleTE\n" +
                "\n" +
                "FROM t \n" +
                "WHERE                  Country='Germany'\n" +
                "AND (City='Berlin' or City='MÃ¼nchen');");*/
        }
}