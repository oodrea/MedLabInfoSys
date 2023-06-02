import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;  
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.*;

//imports for creating pdf file
import java.net.MalformedURLException;
import com.itextpdf.text.Document;  
import com.itextpdf.text.DocumentException;  
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.pdf.PdfWriter;  
import com.itextpdf.text.Image;
import java.io.FileOutputStream;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;


public class patient {
    private static Scanner sc;
    private String firstName;
    private String lastName;
    private String middleName;
    private int birthday;
    private char gender;
    private String address;
    private long phoneNo;
    private int nationalIDno;
    private boolean inList;
    private String UID;
    private boolean deleted;
    private String reason;

    public patient(
        String firstName,
        String lastName,
        String middleName,
        int birthday,
        char gender,
        String address,
        long phoneNo,
        int nationalIDno,
        boolean inList,
        String UID,
        boolean deleted,
        String reason
    )
 
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthday = birthday;
        this.gender = gender;
        this.address = address;
        this.phoneNo = phoneNo;
        this.nationalIDno = nationalIDno;
        this.inList = inList;
        this.UID = UID;
        this.deleted = deleted;
        this.reason = reason;
    }

    //######### SETTERS #########

    public void setFirst(String firstName){
        this.firstName = firstName;
    }

    public void setLast(String lastName){
        this.lastName = lastName;
    }

    public void setMiddle(String middleName){
        this.middleName = middleName;
    }

    public void setBirthday(int birthday){
        this.birthday = birthday;
    }

    public void setGender(char gender){
        this.gender = gender;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setPhone(long phoneNo){
        this.phoneNo = phoneNo;
    }

    public void setNationalID(int nationalIDno){
        this.nationalIDno = nationalIDno;
    }

    public void setinList(boolean inList){
        this.inList = inList;
    }

    public void setUID(String UID){
        this.UID = UID;
    }

    public void setDeleted(boolean deleted){
        this.deleted = deleted;
    }

    public void setReason(String reason){
        this.reason = reason;
    }

    //#################################

    //######### GETTERS #########

    public String getFirst(){
        return firstName;
    }

    public String getLast(){
        return lastName;
    }

    public String getMiddle(){
        return middleName;
    }

    public int getBirthday(){
        return birthday;
    }

    public char getGender(){
        return gender;
    }

    public String getAddress(){
        return address;
    }

    public long getPhone(){
        return phoneNo;
    }

    public int getNationalID(){
        return nationalIDno;
    }

    public boolean getInList(){
        return inList;
    }

    public String getUID(){
        return UID;
    }

    public boolean getDeleted(){
        return deleted;
    }

    public String getReason(){
        return reason;
    }

    //#################################

public void addPatientToFile(patient myPatient, String UID) throws IOException{ //append patient info to file function
        File file = new File("Patients.txt");
        if(!file.exists()){
            System.out.println("\nPatients.txt does not exist in path. Creating File...");

            try{Thread.sleep(2000);}
            catch(InterruptedException ex){
            ex.printStackTrace();
        }
        System.out.println("File creation success.\n");
        }
        else{
            System.out.println("");
        }
        System.out.println("Adding to Database...");
        try{Thread.sleep(2000);}
        catch(InterruptedException ex){
            ex.printStackTrace();
        }
        
        FileWriter patientsFW = new FileWriter("Patients.txt", true); // opens or creates(if doesn't exist) file for Patients.txt
        PrintWriter patientsOut = new PrintWriter(patientsFW);
        patientsOut.println(UID + ";" + myPatient.lastName + ";" + myPatient.firstName + ";" + myPatient.middleName + ";" + myPatient.birthday + ";" + myPatient.gender + ";" + myPatient.address + ";" + myPatient.phoneNo + ";" + myPatient.nationalIDno + ";");
        patientsOut.close();

        System.out.println("Patient info successfully added. \n\nReturning to Main Menu...\n");
        myPatient.setinList(true);
        
        
    }

public void readPatientFile(ArrayList<patient> patients) throws IOException{ //reads Patients.txt file and updates "patients" arraylist
    BufferedReader reader;
    String[] parts;
		try {
			reader = new BufferedReader(new FileReader("Patients.txt"));
			String line = reader.readLine();
			while (line != null) {
				parts = line.split(";"); //separates elements from each line

                //adds each indiv patient to "patients" arraylist
                if(parts.length <11){
                    patients.add(new patient(parts[2], parts[1], parts[3], Integer.parseInt(parts[4]), parts[5].charAt(0), parts[6], Long.parseLong(parts[7]), Integer.parseInt(parts[8]), true, parts[0], false, "")); //adds patient read from file to arraylist
                }
                else{
                    patients.add(new patient(parts[2], parts[1], parts[3], Integer.parseInt(parts[4]), parts[5].charAt(0), parts[6], Long.parseLong(parts[7]), Integer.parseInt(parts[8]), false, parts[0], true, parts[10])); //adds patient read from file to arraylist  
                }
                
				line = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
            System.out.println("ERROR: Patients File Not Found.");
                System.out.println("Creating File...");
                File file = new File("Patients.txt");
                file.createNewFile();
                System.out.println("File Created.\n");
		}catch(IOException e){
            e.printStackTrace();
        }
}

public int searchPatients(ArrayList<patient> patients, patient myPatient, int searchoption){
    patients = patient.removeDuplicates(patients);
    int count=0;
    char choice;
    sc = new Scanner(System.in);
    List<Integer> matchedIndexes=new ArrayList<Integer>(); 
    //search options: 1 = using UID, 2: using National ID no., 3: using last name, first name, birthday combination


    if(searchoption ==1){
        //search using UID
        for(int i=0; i<patients.size(); i++){
            if( myPatient.getUID().equals((patients.get(i)).getUID()) && patients.get(i).getDeleted()!=true)
                {
                count++;
                matchedIndexes.add(i);
                }
        }
    }
    if(searchoption==2){
        //search using nat id no
        for(int i=0; i<patients.size(); i++){
            if( myPatient.getNationalID()== ((patients.get(i)).getNationalID()) )
                {
                count++;
                matchedIndexes.add(i);
                }
        }
    }
    if(searchoption==3){
        //search using last name, first name, birthday
        for(int i=0; i<patients.size(); i++){
            if( myPatient.getFirst().equals((patients.get(i)).getFirst()) 
                && myPatient.getLast().equals((patients.get(i)).getLast()) 
                && myPatient.getBirthday() == (patients.get(i)).getBirthday())
                {
                count++;
                matchedIndexes.add(i);
                }
        }
    }


    System.out.println("\nSearching Database...\n");
        try{Thread.sleep(2000);}
        catch(InterruptedException ex){
            ex.printStackTrace();
        }
    if(count>=2){
        String head1 = "Patient's UID",
               head2 = "Last Name",
               head3 = "First Name",
               head4 = "Middle Name",
               head5 = "Birthday",
               head6 = "Gender",
               head7 = "Address",
               head8 = "Phone Number",
               head9 = "National ID no.";
        System.out.printf("%-16s %-12s %-12s %-12s %-12s %-12s %-20s %-13s %-12s%n", head1, head2, head3, head4, head5, head6, head7, head8, head9);
        for(int y=0; y<matchedIndexes.size(); y++){
            // System.out.println("index "+matchedIndexes.get(y));
            if(patients.get(matchedIndexes.get(y)).getDeleted()!=true){
                System.out.printf("%-16s %-12s %-12s %-12s %-12s %-12s %-20s %-13s %-12s%n", patients.get(matchedIndexes.get(y)).getUID(), 
                patients.get(matchedIndexes.get(y)).getLast(), 
                patients.get(matchedIndexes.get(y)).getFirst(), 
                patients.get(matchedIndexes.get(y)).getMiddle(), 
                Integer.toString(patients.get(matchedIndexes.get(y)).getBirthday()), 
                patients.get(matchedIndexes.get(y)).getGender(), 
                patients.get(matchedIndexes.get(y)).getAddress(), 
                Long.toString(patients.get(matchedIndexes.get(y)).getPhone()), 
                Integer.toString(patients.get(matchedIndexes.get(y)).getNationalID()));
            }
            
            
        }
        return 1;
    }
    else if(count==1 | count==2){
    myPatient.setUID(patients.get(matchedIndexes.get(0)).getUID());
    myPatient.setFirst(patients.get(matchedIndexes.get(0)).getFirst());
    myPatient.setLast(patients.get(matchedIndexes.get(0)).getLast());
    myPatient.setMiddle(patients.get(matchedIndexes.get(0)).getMiddle());
    myPatient.setBirthday(patients.get(matchedIndexes.get(0)).getBirthday());
    myPatient.setAddress(patients.get(matchedIndexes.get(0)).getAddress());
    myPatient.setPhone(patients.get(matchedIndexes.get(0)).getPhone());
    myPatient.setNationalID(patients.get(matchedIndexes.get(0)).getNationalID());

        return 2;
    }

    else{
        System.out.println("\nERROR: No record found.");
        System.out.print("\nSearch Again [Y/N] : ");
        choice = (sc.next().charAt(0));
        if(choice == 'y' | choice =='Y'){
            return 4;
        }
        sc.nextLine();
        return 3;
    }
    
}

public void displayInfo(patient myPatient, int year, int month, int day, int action, ArrayList<labreq> labreqsListMaster, String PUID) throws DocumentException, MalformedURLException, IOException, Exception{
    char labYN;
    String reqUID;
    System.out.println();
    System.out.printf("%s: %s\n", "Patient's UID", myPatient.getUID());
    System.out.printf("%s: %s, %s %s\n", "Name", myPatient.getLast(), myPatient.getFirst(), myPatient.getMiddle());
    System.out.printf("%s: %d\n", "Birthday", myPatient.getBirthday());
    System.out.printf("%s: %c\n", "Gender", myPatient.gender);
    System.out.printf("%s: %s\n", "Address", myPatient.getAddress());
    System.out.printf("%s: %d\n", "Phone Number", myPatient.getPhone());
    System.out.printf("%s: %d\n", "National ID no.", myPatient.getNationalID());

    switch(action){
    
    case 1: // delete patient
    // System.out.println("delete");
    break;

    case 2: //search patient -- print request churva
    // System.out.printf("\n%-16s %-16s %-16s %-16s\n", "Request's UID", "Lab Test Type", "Request Date", "Result");
    // System.out.println("print everything else");
    myPatient.setUID(PUID);
    labreq PatientLabreq = new labreq("", false, "", "", myPatient.getUID(), "", "", "", "");
    
    if(labreq.searchLabReqsforPatient(PUID, 1, labreqsListMaster)==true){;

    System.out.print("\nDo you want to print a laboratory test result? [Y/N]: "); // ask for user input ***
        labYN = (sc.next().charAt(0));
        sc.nextLine();
        while(labYN=='y' | labYN=='Y'){

            System.out.print("Enter request's UID: ");
            reqUID = sc.nextLine();
            PatientLabreq.setRUID(reqUID);
            int index=labreq.getIndex(labreqsListMaster, PatientLabreq, 1);
            PatientLabreq.setType(labreqsListMaster.get(index).getType());
            PatientLabreq.setResult(labreqsListMaster.get(index).getResult());
            //temp/placeholder vars
            String filename;  //lastName_requestUID_requestDate.pdf
            filename = myPatient.getLast()+"_";
            filename = filename + reqUID + "_";
            String date = Integer.toString(year);
            if(month<10){
                date = date + "0";
            }
            date = date + Integer.toString(month);
            if(day<10){
                date = date + "0";
            }
            date = date + Integer.toString(day);
            filename = filename + date;
            // System.out.println(filename);
            
            //add something if magka-error

            Document doc = new Document();  
            try  
            {  
            //generate a PDF at the specified location  
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(filename+".pdf"));   
            //opens the PDF  
            doc.open();  
            //adds paragraph to the PDF file  

            //adds company logo
            Image logo = Image.getInstance("images\\complogo.png");
            logo.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            doc.add(logo);

            Paragraph p1 = new Paragraph("629 Ave. Sa Puso Mo");
            p1.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            Paragraph p2 = new Paragraph("#8-7000");
            p2.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            doc.add(p1);   
            doc.add(p2);

            doc.add(Chunk.NEWLINE);        //Blank line
            doc.add(new LineSeparator());      //Thick line
            
            String fullName="";
            fullName = myPatient.getLast();
            fullName = fullName + ", ";
            fullName = fullName + myPatient.getFirst();
            fullName = fullName + " ";
            fullName = fullName + myPatient.getMiddle();
            
            Paragraph p3 = new Paragraph();

            p3.setTabSettings(new TabSettings(325f));

            p3.add("Name: "+fullName);
            p3.add(Chunk.TABBING);
            p3.add("Specimen ID: "+ myPatient.getUID()+"\n");

            p3.add("Patient ID: "+myPatient.getUID());
            p3.add(Chunk.TABBING);
            p3.add("Collection Date: "+ date+"\n");

            p3.add("Age: "+"19");
            p3.add(Chunk.TABBING);
            p3.add("Birthday: "+ myPatient.getBirthday()+"\n");

            p3.add("Gender: "+myPatient.getGender());
            p3.add(Chunk.TABBING);
            p3.add("Phone Number: "+ myPatient.getPhone()+"\n");

            doc.add(p3);

            doc.add(Chunk.NEWLINE);        //Blank line
            doc.add(new LineSeparator());      //Thick line
            doc.add(Chunk.NEWLINE);        //Blank line

            //creation of table
            PdfPTable pdfPTable = new PdfPTable(2);
    
            //Create cells
            Font bold = FontFactory.getFont(FontFactory.HELVETICA_BOLD , 12, Font.BOLD);
            PdfPCell pdfPCell1 = new PdfPCell(new Paragraph("Test", bold));
            PdfPCell pdfPCell2 = new PdfPCell(new Paragraph("Result", bold));
            PdfPCell pdfPCell3 = new PdfPCell(new Paragraph(PatientLabreq.getType()));
            PdfPCell pdfPCell4 = new PdfPCell(new Paragraph(PatientLabreq.getResult()));
    
            //Add cells to table
            pdfPTable.addCell(pdfPCell1);
            pdfPTable.addCell(pdfPCell2);
            pdfPTable.addCell(pdfPCell3);
            pdfPTable.addCell(pdfPCell4);
            pdfPTable.addCell(new PdfPCell());
            pdfPTable.addCell(new PdfPCell());

            doc.add(pdfPTable);

            doc.add(Chunk.NEWLINE);        //Blank line
            doc.add(new LineSeparator());      //Thick line
            doc.add(Chunk.NEWLINE);        //Blank line
            
            Paragraph p4 = new Paragraph();

            p4.setTabSettings(new TabSettings(325f));

            p4.add("Jollibee Bubuyog");
            p4.add(Chunk.TABBING);
            p4.add("Ronald McDonald"+"\n");

            p4.add("Medical Technologist");
            p4.add(Chunk.TABBING);
            p4.add("Pathologist"+"\n");

            p4.add("Lic. # 123456");
            p4.add(Chunk.TABBING);
            p4.add("Lic. # 456789"+"\n");

            doc.add(p4);

            //close the PDF file  
            doc.close();  
            //closes the writer  
            writer.close();  
            }   
            catch (DocumentException e)  
            {  
            e.printStackTrace();  
            }   
            catch (FileNotFoundException e)  
            {  
            e.printStackTrace();    
            }

            //add ung pause thing 
            System.out.println("Request PDF successfully created."); 
            //https://www.javatpoint.com/java-create-pdf --> tutorial for generating pdf files
            System.out.println("");
            System.out.print("Do you want to print another laboratory test result? [Y/N]: ");
            sc.nextLine();
            labYN = (sc.next().charAt(0));
            
        }

        if(labYN=='n' | labYN=='N'){
            System.out.println("\nReturning to Main Menu...");
        }
    }
    else{
        System.out.println("No Laboratory Request of Patient Found. \n\nReturning to Main Menu...\n");
        break;
    }
        break;
    
    default:
    System.out.println("Invalid input.");
    }

}

public void deleteRecord(ArrayList<patient> patients, patient myPatient, String reason) throws IOException{  //copy and pasted lng from readpatients
    myPatient.setDeleted(true);
    myPatient.setReason(reason);
    System.out.println("\nPatient Info has been successfully deleted.\n");
}

public String UIDGen(int year, int month, int day, int patientsCount){ //generates patient's unique ID  
    char D1= 'A';
    char D2= 'A';
    char D3= 'A';
    String UID = "P";
    int EE = patientsCount % 100;
    UID = UID + String.valueOf(year); //appends year to UID

    if(month<10){ //appends 0 to single digit months
        UID = UID + "0";
    }

    UID = UID + String.valueOf(month); //appends month to UID

    //**BALIKAN TO NDE P NAIIMPLEMENT LOGIC!!
    if(EE > 99){
    D1 +=1;
    }
   if(D1>=90){ //umabot na sa Z
    D2+=D1/90;
    D1=65;
   }
   if(D2>=90){
    D3+=D2/90;
    D2=65;
    D1=65;
   }
    UID = UID + D1;
    UID = UID + D2;
    UID = UID + D3;
    

    //** PATI E2 TANGENA
    if(EE<10){
        UID = UID + "0";
    }
    UID = UID + String.valueOf(EE);

    return UID;

    }

public static ArrayList<patient> removeDuplicates(ArrayList<patient> patients)
    {
  
        // Create a new LinkedHashSet
        Set<patient> set = new LinkedHashSet<>();
  
        // Add the elements to set
        set.addAll(patients);
  
        // Clear the list
        patients.clear();
  
        // add the elements of set
        // with no duplicates to the list
        patients.addAll(set);
  
        // return the list
        return patients;
    }

public static int getIndex(ArrayList<patient> patients, patient myPatient){
    sc = new Scanner(System.in);
    List<Integer> matchedIndexes=new ArrayList<Integer>(); 

    int count;
    for(count=0; count<patients.size(); count++){
        if(myPatient.UID.contains(patients.get(count).getUID()) && patients.get(count).getDeleted()!=true){
            return count;
        }
    }
    return count;

}

public static void updatePatientsFile(ArrayList<patient> patients) throws IOException{ //writes to Services file

    FileWriter patientFW = new FileWriter("Patients.txt"); // opens or creates(if doesn't exist) file for Patients.txt
    PrintWriter patientOut = new PrintWriter(patientFW);

    for(patient d : patients){
        if(d.deleted!=true){
            patientOut.println(d.UID + ";"+ d.lastName + ";"+ d.firstName + ";" + d.middleName + ";" + d.birthday + ";" + d.gender + ";" + d.address + ";" + d.phoneNo + ";" + d.nationalIDno +";");
        }
        else{
            patientOut.println(d.UID + ";"+ d.lastName + ";"+ d.firstName + ";" + d.middleName + ";" + d.birthday + ";" + d.gender + ";" + d.address + ";" + d.phoneNo + ";" + d.nationalIDno +";" + "D;" + d.reason+";");
        }
        
        }
    patientOut.close();
}

public static void editPatient(ArrayList<patient> patients, patient myPatient, String newadd, long newnum, int index){
    patients.get(index).setAddress(newadd);
    patients.get(index).setPhone(newnum);
}
}