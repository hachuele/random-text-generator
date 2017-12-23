
// Name: Eric J. Hachuel
// CS 455 PA4
// Fall 2016

//IMPORT STATEMENTS
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

/**
 * A Random Text Generator Class.
 * Generates a random text given a specified prefix length and number of words.
 */
public class RandomTextGenerator {
    
    //INSTANCE VARIABLES
    private int prefixLength;
    private Scanner in;
    private boolean debugOn;
    private Random randNum;
    
    //CONSTANTS
    private static final int SPACE_CHAR_LENGTH = 1;
    private static final int MAX_NUM_CHARS = 80;
   
    
    /**
     * Constructs a RandomTextGenerator object with a given prefix length.
     * @param prefixLength the Prefix length
     * @param in the Scanner that reads the input file
     * @param debugOn boolean parameter to turn on/off the debugger mode to print debug messages
     */
    public RandomTextGenerator(int prefixLength, Scanner in, boolean debugOn) {
        this.prefixLength = prefixLength;
        this.in = in;
        this.debugOn = debugOn;
        
        if(this.debugOn){
            randNum = new Random(1); //For debug mode, repeat sequence of random numbers
        }
        
        else{
            randNum = new Random();
        }
    }
   

    /**
     * Generates a String containing a text of length numWords that will be printed to the output file.
     * @param numWords the number of words to be written to the output file
     * @return the final text string containing the randomly generated version of the input text
     * @throws IOException
     */
    public String generateFinalText(int numWords) throws IOException {
        
        //Initialize final text string
        String finalTextString = "";
        
        //Character count to put line breaks at/before 80 characters
        int characterCounter = 0;
        
        //Generate Map of Prefixes and associated ArrayLists of words
        HashMap<Prefix, ArrayList<String>> myMap = generateMapFromFile();
       
        //Generate a random Prefix from the input Map
        Prefix currentPrefix = generateRandomPrefix(myMap);
        
        //For loop to generate final text string
        for(int i = 0; i < numWords; i++){
            
            //Check if map contains prefix, otherwise, generate new random prefix
            if(!myMap.containsKey(currentPrefix)){
                
                //Print message for debug mode
                debuggerModePrint("<END OF FILE>");
                
                currentPrefix = generateRandomPrefix(myMap);
            }
            
            //Print message for debug mode
            debuggerModePrint("prefix: "+currentPrefix);
            
            //generate random word with current prefix
            String currentWord = randomWordGenerator(myMap.get(currentPrefix));
            
            //Print message for debug mode
            debuggerModePrint("successors: " + myMap.get(currentPrefix));
            debuggerModePrint("word generated: " + currentWord);
            
            //Check if the character count, added to the current word, is greater than 80 characters
            if((characterCounter)+(currentWord.length()+SPACE_CHAR_LENGTH) > MAX_NUM_CHARS){
                
                //If greater than 80 characters, add word to new line
                finalTextString += "\n" + currentWord+ " ";
                
                //Re-initalize character count to the length of the word and following space
                characterCounter = currentWord.length()+SPACE_CHAR_LENGTH;
            }
            
            //If length is < 80 characters, add the prefix and the word.
            else{
                finalTextString +=currentWord + " ";
                characterCounter += currentWord.length()+SPACE_CHAR_LENGTH;
            }
            
            //Update prefix by Shifting to subsequent Prefix
            currentPrefix = currentPrefix.shiftIn(currentWord);
        }
        
        return finalTextString;
    }
    
    /**
     * Creates HashMap of all Prefixes and associated ArrayLists of subsequent words from source file
     * @return HashMap of (Prefix - ArrayList) value pairs.
     */
    private HashMap<Prefix, ArrayList<String>> generateMapFromFile () throws IOException {

         HashMap<Prefix, ArrayList<String>> myMap = new HashMap<>();
         Queue<String> inputQueue = new LinkedList<>();

        //Create Queue of length prefixLength
        for(int i = 0; i < prefixLength; i++){
            if(in.hasNext()){
                inputQueue.add(in.next());
            }

            else{
                throw new IOException("prefixLength >= number of words in sourceFile");
            }
        }

        //Create first Prefix with previously created Queue as input
        Prefix thisPrefix = new Prefix(inputQueue);

        //Generate HashMap with subsequent words in source file through Scanner object
        while(in.hasNext()){

            String nextWord = in.next();
            
            //If the map contains the specified Prefix, add the word to its ArrayList of words
            if(myMap.containsKey(thisPrefix)){
                
                //Store ArrayList into temporary ArrayList while removing current ArrayList from Map
                ArrayList<String> tempArrayList = myMap.remove(thisPrefix);

                //Add next word to temporary ArrayList
                tempArrayList.add(nextWord);

                //Add temporary ArrayList and Prefix to map
                myMap.put(thisPrefix,tempArrayList);
            }

            //If prefix not already in map, add it and add current word to ArrayList
            else{
                
                ArrayList<String> newArrayList = new ArrayList<>();

                //Add next word to new ArrayList
                newArrayList.add(nextWord);

                //Add Prefix and ArrayList to map
                myMap.put(thisPrefix, newArrayList);
            }

            //Update prefix - shift with nextWord as parameter
            thisPrefix = thisPrefix.shiftIn(nextWord);
        }

    //Return the Map of Prefixes and associated Arraylists of words
    return myMap;

    }

    /**
     * Selects a random Prefix from the inputed Map
     * @param inputMap the Map to randomly select a Prefix from
     * @return the randomly selected Prefix
     */    
    private Prefix generateRandomPrefix(HashMap inputMap){

        //Create ArrayList to add all Possible Prefix values
        ArrayList<Prefix> randomNumArray = new ArrayList<>();

        //Add all keys from Map into the ArrayList
        randomNumArray.addAll(inputMap.keySet());

        //Generate a random integer within the size of the keySet
        int arrayIndex = randNum.nextInt(inputMap.keySet().size());

        //Select the Prefix located at the randomly selected index
        Prefix nextPrefix = randomNumArray.get(arrayIndex);

        //Print message for debug mode
        debuggerModePrint("chose a new initial prefix: "+nextPrefix);

        //return random prefix from arrayList
        return randomNumArray.get(arrayIndex);

    }

    /**
     * Generates/selects a random word from the input ArrayList (will select based on word occurrence) 
     * @param inputArrayList the ArrayList of words for a specific Prefix
     * @return the randomly selected word from the ArrayList
     */ 
    private String randomWordGenerator(ArrayList<String> inputArrayList){
        
        //Randomly select the index in the ArrayList to select a word
        int arrayIndex = randNum.nextInt(inputArrayList.size());

        return inputArrayList.get(arrayIndex);
    }
    
    /**
     * Prints the debug message when required.
     * @param debugString the string to print as a debug message
     */
    private void debuggerModePrint(String debugString){
        
        //If "-d" contained in command-line, print debug messages
        if(debugOn){
            System.out.println("DEBUG: " + debugString);
        }
    }
}
    
    
    
    
    
    
    

