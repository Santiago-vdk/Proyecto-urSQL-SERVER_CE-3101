/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urSQL.CLI;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import jline.console.ConsoleReader;
import jline.console.completer.Completer;

public class urSQLCLI {

    public static void usage() {
        System.out.println("urSQL commands:\n");
        System.out.println("CREATE DATABASE <database-name>           Crea un nuevo esquema.");
        System.out.println("DROP DATABASE <database-name>             Elimina el esquema indicado.");
        System.out.println("LIST DATABASES                            Genera un listado de todos los esquemas existentes.");
        System.out.println("DISPLAY DATABASE <database-name>          Reporte con todos los objetos del esquema indicado.");
        System.out.println("START                                     Inicia todos los componentes.");
        System.out.println("STOP                                      Detiene todos los procesos de urSQL.");
        System.out.println("GET STATUS                                Reporte del estado de los componentes de urSQL.");
        System.out.println("\n");

    }

    public static void welcome() {
        System.out.println("\n");
        System.out.println("                      .M\"\"\"bgd   .g8\"\"8q. `7MMF'     \n"
                + "                     ,MI    \"Y .dP'    `YM. MM       \n"
                + "`7MM  `7MM  `7Mb,od8 `MMb.     dM'      `MM MM       \n"
                + "  MM    MM    MM' \"'   `YMMNq. MM        MM MM       \n"
                + "  MM    MM    MM     .     `MM MM.      ,MP MM      ,\n"
                + "  MM    MM    MM     Mb     dM `Mb.    ,dP' MM     ,M\n"
                + "  `Mbod\"YML..JMML.   P\"Ybmmd\"    `\"bmmd\"' .JMMmmmmMMM\n"
                + "                                     MMb             \n"
                + "                                      `bood'         ");
        System.out.println("\n");
        System.out.println("urSQL-CLI, Command Line Interface running...");
        System.out.println("Type" + "'" + "help;" + "'" + " or " + "'" + "\\h" + "'" + "for help. \n");

    }

    public static void main(String[] args) throws IOException {
        try {
            ConsoleReader reader = new ConsoleReader();

            welcome();
            reader.setPrompt("urSQL> ");

            if ((args == null) || (args.length == 0)) {
                System.out.println("ERROR 1045: Access denied for user, use '-u root'.");
                return;
            }

            List<Completer> completors = new LinkedList<Completer>();

            if (args.length > 0) {
                if (args[0].equals("-u") && args[1].equals("root") && args.length == 2) {

                } 
            }

            for (Completer c : completors) {
                reader.addCompleter(c);
            }

            String line;
            PrintWriter out = new PrintWriter(reader.getOutput());

            while ((line = reader.readLine()) != null) {
                if (line.length() == 0) {

                } else if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
                    System.out.println("Bye bye!");
                    break;
                } else if (line.equalsIgnoreCase("\\h") || line.equalsIgnoreCase("help")) {
                    usage();
                } else if (line.equalsIgnoreCase("clear")) {
                    reader.clearScreen();
                } else {
                    //La linea es comando?
                    /*if(checkLine(line)){
                     out.println(line);
                     out.flush();
                     }
                     else{*/
                    if (line.contains(";")) {
                        //Ejecuto sentencia, pasar a parser
                        
                    } else {
                        ConsoleReader readerSentence = new ConsoleReader();
                        String lineSentence;
                        PrintWriter outSentence = new PrintWriter(readerSentence.getOutput());
                        readerSentence.setPrompt("    -> ");
                        String result = line + " ";
                        while ((lineSentence = readerSentence.readLine()) != null) {
                            if (lineSentence.contains(";")) {
                                break;
                            } else {
                                result = result + lineSentence;
                            }
                        }
                        outSentence.println(result);
                        outSentence.flush();
                        //Ya tengo la sentencia formada.
                    }
                    //}

                }
            }
        } catch (Throwable t) {
        }
    }

    
    private void sendQuery(String pQuery){
        
    }
    
    
    
    
    private static boolean checkLine(String pLine) {
        boolean isCommand = false;
        String line = pLine.toLowerCase();
        if (line.compareTo("start") == 0 || line.compareTo("start;") == 0) {
            isCommand = true;
        } else if (line.compareTo("stop") == 0 || line.compareTo("stop;") == 0) {
            isCommand = true;
        } else if (line.compareTo("get status") == 0 || line.compareTo("get status;") == 0) {
            isCommand = true;
        }
        return isCommand;
    }
}
