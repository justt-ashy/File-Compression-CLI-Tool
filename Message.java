import java.util.*;

//Encapsulates a message, its characters, frequencies and binary representations
public class Message {
    private final String text;
    static final int CHAR_SIZE = 8;
    private final int ALPHABET_SIZE = 256;
    private final char[] characters;
    private final int[] frequencyOfChars;

    //constructs a messsage with given text; throws exception if empty
    Message(final String text){
        if(text.length() <= 0 ){
            throw new RuntimeException("Error: Message size <= 0");
        }
        this.text = text;
        characters = getChars();
        frequencyOfChars = buildFrequencyTable();
    }

    //returns the original message text
    public String getMessage(){ return text;}

    //returns total number of characters in message
    public int getTotalFrequency(){ return text.length();}

    //returns the array of unique characters in message
    public char[] getCharacters(){ return characters ;}

    //returns frequency table for all characters
    public int[] getFrequencies(){ return frequencyOfChars;}
    
    //checks if message is empty
    public boolean isEmpty(){ return getTotalFrequency()==0;}

    //returns total size of message in bits
    public int getSize(){
        return CHAR_SIZE * getTotalFrequency();
    }

    //returns the array of unique characters in message
    private char[] getChars(){
        List<Character> list = new ArrayList<>();
        list.add(text.charAt(0));
        for(int i=0;i< getTotalFrequency(); i++){
            if( !list.contains(text.charAt(i))){
                list.add(text.charAt(i));
            }
        }
        char[] myChars = new char[list.size()];
        for(int i=0; i< list.size(); i++){
            myChars[i] = (Character) list.get(i);
        }
        return myChars;
    }

    //calculates the frequency of specific character in the message
    public int calcFrequencyOfChar(final char ch){
        int charFreq = 0;
        for(int i=0; i< getTotalFrequency();i++){
            if(text.charAt(i)==ch){
                charFreq++;
            }
        }
        return charFreq;
    }

    //builds frequency table for all characters in the message
        private int[] buildFrequencyTable(){
            int[] frequencies = new int[ALPHABET_SIZE];
            for(final char ch : text.toCharArray()){
                frequencies[ch]++;
            }
            return frequencies;
        }

        //returns the binary representation of entire message 
        public String binaryCode(){
            String binarySequence = new String();
            for(int i=0; i< getTotalFrequency();i++){
                binarySequence += convertBinary(getMessage().charAt(i));
            }
            return binarySequence;
        }

        //returns array of binary strings for each unique character
        public String[] binaryOfChars(){
            String[] binarySequence = new String[characters.length];
            for(int i=0;i<characters.length; i++){
                binarySequence[i] = convertBinary(characters[i]);
            }
            return binarySequence;
        }

        //converts a decimal value to an 8-bit binary string
        public String convertBinary(int decimal){
            String binary = "";
            while(decimal > 0){
                binary = (decimal%2)+binary;
                decimal = decimal/2;
            }

            String str = new String(), zeros = "0";
            if(binary.length()!=8){
                while(str.length() != 8){
                    str = zeros + binary;
                    zeros += "0";
                }
            }
            return String.format("%8s", Integer.toBinaryString(decimal)).replace(' ', '0');
        }
    }

