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
            System.out.println(msj.substring(30, msj.indexOf("Was expecting one of:")));
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
            parser.Parse("SELECT column_name  ,column_name \n FROM table_name;");
                   
                    /*("DEleTE\n" +
                "\n" +
                "FROM t \n" +
                "WHERE                  Country='Germany'\n" +
                "AND (City='Berlin' or City='München');");*/
        }
}