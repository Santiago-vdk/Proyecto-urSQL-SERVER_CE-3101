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
            String msj = ex.getCause().toString();
            System.out.println(msj);
            //System.out.println(msj.substring(30, msj.indexOf("Was expecting")));
            return false;
           
            /*
            Error: 1149 SQLSTATE: 42000 (ER_SYNTAX_ERROR)
 
Message: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use
           
           
           
           
           
            "DEleTE\n" +
                "\n" +
                "FROM\n" +
                "WHERE                  Country='Germany'\n" +
                "AND (     City='Berlin' or City='München');"
            */
        }
    }
   
    /**
     *
     * @param args
     */
    public static void main(String [] args)
        {
            JsqlParser parser = new JsqlParser();
            parser.Parse("CREATE TABLE Persons\n" +
            "(\n" +
            "P_Id int NOT NULL,\n" +
            "LastName varchar(255) NOT NULL,\n" +
            "FirstName varchar,\n" +
            "Address DATETIME,\n" +
            "City varchar(255),\n" +
            "PRIMARY KEY (P_Id)\n" +
            ")");
        
        /*("ALTER TABLE Orders\n" +
            "ADD CONSTRAINT fk_PerOrders\n" +
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
                "AND (City='Berlin' or City='München');");*/
        }
}