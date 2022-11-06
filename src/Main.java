import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class Main
{
    public static void main(String[] args) throws Exception
    {
        readFile();
    }
    public static void readFile() throws FileNotFoundException {
        // pass the path to the file as a parameter
        File file = new File("C:\\Users\\fuatw\\IdeaProjects\\NfaToDfa\\src\\NFA1.txt");
        Scanner sc = new Scanner(file);
        ArrayList<String> nfaFile=new ArrayList<>();
        while (sc.hasNextLine()){
            nfaFile.add(sc.nextLine());
        }
        int statePos= nfaFile.indexOf("STATES");
        int startPos= nfaFile.indexOf("START");
        int finalPos= nfaFile.indexOf("FINAL");
        int transPos= nfaFile.indexOf("TRANSITIONS");
        int endPos  = nfaFile.indexOf("END");
        ArrayList<String> alphabet = new ArrayList<>(nfaFile.subList(1,statePos));
        System.out.println("Alphabet: "+alphabet);
        ArrayList<String> states = new ArrayList<>(nfaFile.subList(statePos+1,startPos));
        System.out.println("States: "+states);
        StartState startState=new StartState(nfaFile.get(startPos+1));
        startState.print();
        FinalState finalState=new FinalState(nfaFile.get(finalPos+1));
        finalState.print();
        ArrayList<String> transitions=new ArrayList<>(nfaFile.subList(transPos+1,endPos));
        System.out.println("Transitions: "+transitions);
    }
}