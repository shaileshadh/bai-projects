
/*  This utility creates a windows executable from a list of commands to
    run. The list of commands would be in a file, similar to a batch file
    and the resulting exe would execute the commands, one by one.

    All commands are executed from the current directory. Therefore it would
    be impossible to use commands like cd.. like you could in a batch script.
    Instead all commands are pretty much independent. You won't be able to
    set environment variables that aren't already set.

    This program creates a C program that runs the commands. Then GCC is called
    to compile the program into an executable.

    There is nothing fancy here. If you need to create a utility that accepts
    input, this is inadequate. This is meant for one line commands such as
    'java MyGame' and not a scripting language.
*/

import java.io.*;
import java.util.*;

public class Main {

    // The command to compile a program. The first argument is the name of the
    // c file and the second argument is the name of the generated
    // executable file.
    private static final String compileOptions =
        "gcc -O2 %s -o %s";

    // Body of the program. The argument is the placeholder for all the system
    // calls that will go in.
    private static final String cProgram =
        "int main(){%sreturn 0;}";

    // Format of a line.
    private static final String cLine =
        "system(\"%s\");";

    public static void main(String[] args) throws Exception {

        // Accept command line arguments. There should only be one argument
        // which is the name of the file containing the commands.

        // If there are no arguments or more than one argument, it's improper
        // usage and the program should exit.
        if(args.length != 1)
            return;

        // This is the name of the input file.
        String inFileName = args[0];

        // Try to open the input file. If it doesn't exist, then it will throw
        // a FileNotFoundException and this program will crash.
        BufferedReader in = new BufferedReader(new FileReader(inFileName));

        // Now we read the contents of the file into a List of Strings. Each
        // entry of the list is one line in the input file.

        // List of the lines
        List<String> lines = new ArrayList<String>();

        while(true){
            // If the end of the file has been reached, readLine() will simply
            // return null. 

            // readLine() may also throw an IOException, but that is unlikely
            // to occur so we will not attempt to handle it.
            String line = in.readLine();

            if(line == null)
                break;

            // Trim the line to remove leading and trailing whitespace before
            // further processing. This must be done after the null check
            // and not before.
            line = line.trim();

            // Filter empty lines and comment lines. A comment line starts with
            // '#' and is ignored by this program.

            // Matches lines containing only whitespace.
            if(line.equals(""))
                continue;

            // Match lines starting with '#'.
            if(line.startsWith("#"))
                continue;

            lines.add(line);
        }

        // Generate the working program.
        String program = makeProgram(lines);
        
        // We need to write the program to a c file. The name of this c file
        // would be <infile>.c which should be fine unless there's already
        // a file with that exact name. Assume that there's not. If there is
        // then the c file will be overwritten.
        String cProgName = inFileName + ".c";

        // Calling the IO routines to write it to a file.
        File cFile = new File(cProgName);
        FileOutputStream outStream = new FileOutputStream(cFile);
        PrintWriter outWriter = new PrintWriter(outStream);
        outWriter.println(program);
        outWriter.flush();
        outWriter.close();

        // The name of the executable (which is the infile plus .exe)
        String exeName = inFileName + ".exe";

        // The command that we run to compile the C program.
        String compile = String.format(compileOptions, cProgName, exeName);

        // Run the compile command, assuming it doesn't give any errors.
        // We need to wait for the compiler to finish before cleaning up and
        // exiting.
        Process compileThread = Runtime.getRuntime().exec(compile);
        compileThread.waitFor();

        // Clean up and exit
        cFile.delete();
        System.exit(0);
    }

    // Quotes a string according to C standards. This means escaping certain
    // escape characters, namely " and \ which may occur. Newlines may not
    // occur so they will not be handled.
    private static String cQuote(String str){

        // Build the return string using a StringBuilder.
        StringBuilder ret = new StringBuilder();

        for(char c : str.toCharArray()){
            // Looping through the characters in the string, inserting the
            // escape sequences whenever necessary.

            // A quote or a backslash is preceded by a backslash in c,
            // while any other character is just there.
            switch(c){
                case '\"':
                case '\\':
                    ret.append('\\');
                default:
                    ret.append(c);
            }
        }

        return ret.toString();
    }

    // Generate a complete C program from a set of lines.
    private static String makeProgram(List<String> lines){

        // Build the list of system calls as a string.
        StringBuilder syms = new StringBuilder();

        for(String line : lines){
            
            // Looping through the list of lines, add a new system call to
            // the string with each line.

            // Need to quote the string before appending into the c program.
            syms.append(String.format(cLine, cQuote(line)));
        }

        // Put what we have into the main body of the c program, and return
        // it as a string.
        return String.format(cProgram, syms.toString());
    }

}

