import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class Main
{

    public static void main(String[] args) throws Exception
    {
        Table table=new Table();
        ReadFile readFile=new ReadFile();
        readFile.readFile();
        table.constructTable(readFile.getTransition(),readFile.getAlphabet(),readFile.getState());

    }

}