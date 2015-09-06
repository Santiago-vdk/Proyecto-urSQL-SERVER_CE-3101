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
    
    public static void main(String [] args)
	{
        String source = "select * from estudiantes";
        
        CharStream in = new ANTLRInputStream(source);
        SQLiteLexer lexer = new SQLiteLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SQLiteParser parser = new SQLiteParser(tokens);
        parser.parse();
    }
    
}
