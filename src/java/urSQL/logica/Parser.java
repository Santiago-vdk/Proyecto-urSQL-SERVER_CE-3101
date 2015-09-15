/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.logica;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import urSQL.parser.*;


/**
 *
 * @author Shagy
 */
public class Parser extends SQLiteBaseListener{
    CharStream _Input;
    SQLiteLexer _Lexer;
    CommonTokenStream _Tokens;
    SQLiteParser _Parser;
    
   
    
    public boolean processQuery(String pSource){
        CharStream in = new ANTLRInputStream(pSource);
        SQLiteLexer lexer = new SQLiteLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SQLiteParser parser = new SQLiteParser(tokens);
        if (parser.parse().isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }
    
    
    
    public static void main(String [] args)
	{
        String source = "CREATE TABLE table_name" +
"(" +
"column_name1 int(4)," +
"column_name2 int()"+
                ");";
        
        Parser p= new Parser();
        System.out.println(p.processQuery(source));
    }
    
}
