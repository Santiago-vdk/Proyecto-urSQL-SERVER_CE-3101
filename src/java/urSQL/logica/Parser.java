/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.logica;
import java.util.List;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
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

    public static void main(String[] args) {
        String source = "SELECT EmployeeID, FirstName, LastName, HireDate, City "
                + "FROM Employees\n" 
                + "WHERE City <> 'London'";

        Parser p = new Parser();
        
        System.out.println(p.processQuery(source));
    }

}
