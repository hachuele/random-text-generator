// Name: Eric J. Hachuel
// CS 455 PA4
// Fall 2016

//IMPORT STATEMENTS
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;


/**
 * A Prefix Class.
 * Generates a Prefix object, modeled as a Queue implemented by a LinkedList.
 */
public class Prefix {
    
    //INSTANCE VARIABLES(S)
    private Queue<String> prefixQueue;
    
    //CONSTANTS
    private static final int PRIME_HASH_PREFIX = 79;
    private static final int HASH_OFFSET_PREFIX = 5;
    
   
    /**
     * Constructs a Prefix object
     * @param prefixQueueInput the Queue input, modeling the prefix object.
     */
    public Prefix(Queue prefixQueueInput){
        
        prefixQueue = new LinkedList<String>();
        
        Iterator<String> iter = prefixQueueInput.iterator();
        
        while(iter.hasNext()){
            prefixQueue.add(iter.next());
        }
    }
    
    /**
     * Generates an new Prefix object - shifts to the next word in the text.
     * @param nextWord the word following the prefix
     * @return a new prefix to which the next word has been added and the last word has been removed.
     */
    public Prefix shiftIn(String nextWord){
        
        Prefix nextPrefix = new Prefix(prefixQueue);
        
        nextPrefix.prefixQueue.add(nextWord);
        nextPrefix.prefixQueue.remove();
        
        return nextPrefix;
    }
            
       
    //HASHCODE, EQUALS, AND TOSTRING OVERRIDE
    @Override
    public int hashCode() {
        int hash = HASH_OFFSET_PREFIX;
        hash = PRIME_HASH_PREFIX * hash + Objects.hashCode(this.prefixQueue);
        return hash;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Prefix other = (Prefix) obj;
        if (!Objects.equals(this.prefixQueue, other.prefixQueue)) {
            return false;
        }
        return true;
    }
    
    
    /**
     * Overrides toString method to return Prefixes.
     * @return the string of prefixes.
     */
    @Override
    public String toString(){
        
        String stringResult ="";
        Iterator<String> prefixIter = prefixQueue.iterator();
        
        //Ensure no space at the beggining
        if(prefixIter.hasNext()){
            stringResult = prefixIter.next();
        }
        
        //Add remaining prefix values with a space
        while(prefixIter.hasNext()){
            stringResult = stringResult + " " + prefixIter.next();
        }        
        return stringResult;
    }
}
