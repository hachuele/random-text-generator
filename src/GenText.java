// Name: Eric J. Hachuel
// CS 455 PA4
// Fall 2016

//IMPORT STATEMENTS
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


/**
 * A GenText Class. Contains the main method.
 * Validates and reads inputs, generates output and prints to file
 */
public class GenText {
    
    //CONSTANTS
    private static final int POS_PREFIX_LENGTH = 0;
    private static final int POS_NUM_WORDS = 1;
    private static final int POS_SOURCE_TEXT = 2;
    private static final int POS_OUT_FILE = 3;
    private static final int NUM_ARGS_DEBUG_OFF = 4;
    private static final int MIN_NUM_WORDS_LENGTH = 0;
    private static final int MIN_PREFIX_LENGTH = 1;
    
    
    /**
     * Validates Command-Line arguments
     * @param args the arguments from the Command-Line interface
     * @return returns true iff the input data into the command line is valid.
     */
    public static boolean validateData(String[] args){
            
        //Offset modifier to take into account change in args length when debugger mode is on
        int offsetModifier = 0;

        if(args.length == 0){
            System.out.print("ERROR MESSAGE: No Input detected in command line.");
            return false;
        }

        if(args[0].equals("-d")){
            offsetModifier = 1;
        }

        if(args.length != NUM_ARGS_DEBUG_OFF + offsetModifier){
            System.out.print("ERROR MESSAGE: Missing Command Lines.");
            return false;
        }

        if(Integer.parseInt(args[POS_NUM_WORDS + offsetModifier]) < MIN_NUM_WORDS_LENGTH){
            System.out.print("ERROR MESSAGE: NumWords entered is < 0.");
            return false;
        }

        if(Integer.parseInt(args[POS_PREFIX_LENGTH + offsetModifier]) < MIN_PREFIX_LENGTH){
            System.out.print("ERROR MESSAGE: prefixLength is < 1.");
            return false;
        }
        
    return true;
    }
    
    /**
     * Prints the randomly generated string to the output file
     * @param fileName the name of the file to write to
     * @param fileContent the randomly generated text to write to the output file
     * @throws IOException
     */
    public static void printToFile(String fileName, String fileContent) throws IOException {
            FileWriter outputWriter = new FileWriter(fileName);
            outputWriter.write(fileContent);
            outputWriter.close();
    }
    
  
    
    public static void main(String[] args) {
        
        if(validateData(args)){
            
            //Try-Catch to catch FileNotFound, NumberFormat, and IO exceptions.
            try{
                boolean debugOn = false;
                int offsetModifier = 0;
                
                if(args[0].equals("-d")){
                    debugOn = true;
                    offsetModifier = 1;
                }
                
                //Command Line Arguments
                int prefixLength = Integer.parseInt(args[POS_PREFIX_LENGTH + offsetModifier]);
                int numWords = Integer.parseInt(args[POS_NUM_WORDS + offsetModifier]);
                String sourceFile = args[POS_SOURCE_TEXT + offsetModifier];
                String outFile = args[POS_OUT_FILE + offsetModifier];
                
                //Generate new file
                File inputFile = new File(sourceFile);
                
                //Pass file into scanner
                Scanner in = new Scanner(inputFile);
                
                //Construct RandomTextGenerator object
                RandomTextGenerator textGenerator = new RandomTextGenerator(prefixLength, in, debugOn);
                
                //Generate final random text with given amount of words
                String resultText = textGenerator.generateFinalText(numWords);

                //Write result string to output file
                printToFile(outFile,resultText);
            }
            
            
            catch(FileNotFoundException e){
                System.out.print("ERROR MESSAGE: "+ e.getMessage());
            }
            
            catch(NumberFormatException | IOException  e){
                
                System.out.print("ERROR MESSAGE: "+ e.getMessage());
            }
        }
    }
}


