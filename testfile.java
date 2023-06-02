import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File; 

public class testfile {
    static void filetest() throws IOException{
        File file = new File("services.txt");
        
        FileWriter fw = new FileWriter(file, true); // opens or creates(if doesn't exist) file for Patients.txt
        PrintWriter pw = new PrintWriter(fw);
        pw.println("Service Code");
        pw.close();

        
    }
}
