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


public class labreq {
    private static Scanner sc;
    private String serviceCode;
    private boolean deleted;
    private String reason;
    private String RUID;
    private String PUID;
    private String RDate;
    private String RTime;
    private String result;
    private String type;

    public labreq(
        String serviceCode,
        boolean deleted,
        String reason,
        String RUID,
        String PUID,
        String RDate,
        String RTime,
        String result,
        String type
    )
 
    {
        this.serviceCode = serviceCode;
        this.deleted = deleted;
        this.reason = reason; 
        this.RUID = RUID;
        this.PUID = PUID;
        this.RDate = RDate;
        this.RTime = RTime;
        this.result = result;
        this.type = type;
    }

    //######### SETTERS #########

    public void setSC(String serviceCode){
        this.serviceCode = serviceCode;
    }

    public void setRUID(String RUID){
        this.RUID = RUID;
    }

    public void setPUID(String PUID){
        this.PUID = PUID;
    }

    public void setRDate(String RDate){
        this.RDate = RDate;
    }

    public void setRTime(String RTime){
        this.RTime = RTime;
    }

    public void setResult(String result){
        this.result = result;
    }

    public void setDeleted(boolean deleted){
        this.deleted = deleted;
    }
    
    public void setReason(String reason){
        this.reason = reason;
    }

    public void setType(String type){
        this.type = type;
    }


    //#################################

    //######### GETTERS #########

    public String getSC(){
        return serviceCode;
    }

    public String getRUID(){
        return RUID;
    }

    public String getPUID(){
        return PUID;
    }

    public String getRDate(){
        return RDate;
    }

    public String getRTime(){
        return RTime;
    }

    public String getResult(){
        return result;
    }

    public boolean getDeleted(){
        return deleted;
    }
    
    public String getReason(){
        return reason;
    }

    public String getType(){
        return type;
    }

    //#################################


public static void addLabreq(labreq myLabreq, ArrayList<labreq> labreqsList) throws IOException{ 
    //<Service_Code>_Requests.txt
    String filename="";
    filename = filename + myLabreq.serviceCode;
    filename = filename + "_Requests.txt";
    File file = new File(filename);
    if(!file.exists()){
        System.out.println("\n"+ filename + " does not exist in path. Creating File...");

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
    
    FileWriter labreqFW = new FileWriter(filename, true); // opens or creates(if doesn't exist) file for Patients.txt
    PrintWriter labreqOut = new PrintWriter(labreqFW);

    labreqOut.println(myLabreq.RUID + ";"+ myLabreq.PUID + ";"+ myLabreq.RDate+";"+ myLabreq.RTime + ";"+ myLabreq.result + ";" );
    labreqOut.close();

    System.out.println("Laboratory Request " + myLabreq.RUID + " has been successfully added to the file "+ filename);
    
    //adds info to master requests file
    FileWriter masterlabreqFW = new FileWriter("Master_Requests.txt", true); // opens or creates(if doesn't exist) file for Patients.txt
    PrintWriter masterlabreqOut = new PrintWriter(masterlabreqFW);

    masterlabreqOut.println(myLabreq.RUID + ";"+ myLabreq.PUID + ";"+ myLabreq.RDate+";"+ myLabreq.RTime + ";"+ myLabreq.result + ";" + myLabreq.type+";" );
    masterlabreqOut.close();
}

public static String RUIDGen(int year, int month, int day, int requestsCount, labreq myLabreq){ //generates patient's unique ID  
    String RUID="";
    String Z123 = myLabreq.serviceCode;
    String YYYY = String.valueOf(year);
    String MM = "";
    String DD = "";
    int BB = requestsCount;
    char Aright = 65;
    char Aleft = 65;
    

    if(month<10){ //appends 0 to single digit months
        MM = MM + "0";
    }
    MM = MM + String.valueOf(month);
    
    if(day<10){
        DD = DD + "0";
    }
    DD = DD + String.valueOf(day);

   RUID = RUID + Z123 + YYYY + MM + DD;

   if(BB > 99){
    Aright +=1;
   }
   if(Aright>=90){ //umabot na sa Z
    Aleft+=Aright/90;
   }

   RUID = RUID + Aleft + Aright;
   
   if(BB<10){
    RUID = RUID + "0";
   }

   RUID = RUID + String.valueOf(BB);

    // System.out.println(RUID);
    return RUID;

    }

public static ArrayList<labreq> removeDuplicates(ArrayList<labreq> labreqsList)
    {
  
        // Create a new LinkedHashSet
        Set<labreq> set = new LinkedHashSet<>();
  
        // Add the elements to set
        set.addAll(labreqsList);
  
        // Clear the list
        labreqsList.clear();
  
        // add the elements of set
        // with no duplicates to the list
        labreqsList.addAll(set);
  
        // return the list
        return labreqsList;
    }

public static void updateReqsList(ArrayList<labreq> labreqsList, labreq myLabreq, ArrayList<service> servicesList, ArrayList<labreq> labreqsListMaster) throws IOException, FileNotFoundException{ //reads Services.txt file and updates "servicesList" arraylist
    BufferedReader reader;
    String[] parts;
    boolean delete=false;
    String rason="";

    String filename="";
    filename = filename + myLabreq.serviceCode;
    filename = filename + "_Requests.txt";
		try {
			reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			while (line != null) {
				parts = line.split(";"); //separates elements from each line
                String typefromservice = service.getTypeForRequest(servicesList, myLabreq.serviceCode);
                if(parts.length >5){ //more than 3 means it has deleted indicator and reason for deletion
                    delete = true;
                    rason = parts[4];
                    labreqsList.add(new labreq(myLabreq.serviceCode, delete, rason, parts[0], parts[1], parts[2], parts[3], parts[4], typefromservice));
                }
                else{
                labreqsList.add(new labreq(myLabreq.serviceCode, false, "", parts[0], parts[1], parts[2], parts[3], parts[4], typefromservice));
                }    
				line = reader.readLine();

                
                
				line = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
            System.out.println("ERROR: Laboratory Requests File Not Found.");
                System.out.println("Creating File...");
                File file = new File(filename);
                file.createNewFile();
                System.out.println("File Created.\n");
		}catch(IOException e){
            e.printStackTrace();
        }
        labreq.removeDuplicates(labreqsList);

		try {
			reader = new BufferedReader(new FileReader("Master_Requests.txt"));
			String line = reader.readLine();
			while (line != null) {
				parts = line.split(";"); //separates elements from each line
                labreqsListMaster.add(new labreq(myLabreq.serviceCode, false, "", parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]));
				line = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        labreq.removeDuplicates(labreqsListMaster);
}

public static int searchLabReqs(labreq myLabreq, int searchoption, ArrayList<labreq> labreqsListMaster){
    int count=0;
    sc = new Scanner(System.in);
    List<Integer> matchedIndexes=new ArrayList<Integer>(); 
    labreq.removeDuplicates(labreqsListMaster);
    //search options: 1 = using RUID, 2: PUID.


    if(searchoption ==1){
        //search using RUID
        for(int i=0; i<labreqsListMaster.size(); i++){
            if( labreqsListMaster.get(i).getRUID().equals(myLabreq.getRUID()) )
                {
                count++;
                matchedIndexes.add(i);
                }
        }
    }
    if(searchoption==2){
        //search PUID
        for(int i=0; i<labreqsListMaster.size(); i++){
            if( labreqsListMaster.get(i).getPUID().equals(myLabreq.PUID) )
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
        String head1 = "Request's UID",
               head2 = "Lab Test Type",
               head3 = "Request Date",
               head4 = "Result";
        System.out.printf("%-16s %-25s %-12s %-12s%n", head1, head2, head3, head4);
        for(int y=0; y<matchedIndexes.size(); y++){
            // System.out.println("index "+matchedIndexes.get(y));
            if(labreqsListMaster.get(matchedIndexes.get(y)).getDeleted()!=true){
            System.out.printf("%-16s %-25s %-12s %-12s%n", labreqsListMaster.get(matchedIndexes.get(y)).getRUID(), 
                                        labreqsListMaster.get(matchedIndexes.get(y)).getType(), 
                                        labreqsListMaster.get(matchedIndexes.get(y)).getRDate(),
                                        labreqsListMaster.get(matchedIndexes.get(y)).getResult()); 
            }
            
        }
        return 1;
    }
    else if(count==1){
        
    myLabreq.setRUID(labreqsListMaster.get(matchedIndexes.get(0)).getRUID());
    // myLabreq.setType(labreqsList.get(matchedIndexes.get(0)).getType());
    myLabreq.setRDate(labreqsListMaster.get(matchedIndexes.get(0)).getRDate());
    myLabreq.setPUID(labreqsListMaster.get(matchedIndexes.get(0)).getPUID());
    myLabreq.setRTime(labreqsListMaster.get(matchedIndexes.get(0)).getRTime());
    myLabreq.setResult(labreqsListMaster.get(matchedIndexes.get(0)).getResult());
    myLabreq.setSC(labreqsListMaster.get(matchedIndexes.get(0)).getSC());
    myLabreq.setDeleted(labreqsListMaster.get(matchedIndexes.get(0)).getDeleted());

        return 2;
    }

    else{
        System.out.println("\nERROR: No record found.");
        return 5;
    }
    
}

public static int getIndex(ArrayList<labreq> labreqsList, labreq myLabreq, int searchoption){
    int count=0;
    sc = new Scanner(System.in);
    List<Integer> matchedIndexes=new ArrayList<Integer>(); 

    if(searchoption ==1){
        //search using ruid
        for(int i=0; i<labreqsList.size(); i++){
            if( myLabreq.getRUID().equals((labreqsList.get(i)).getRUID()) && labreqsList.get(i).getDeleted()!=true )
                {
                count++;
                matchedIndexes.add(i);
                }
        }
    }
    if(searchoption==2){
        //search keyword
        for(int i=0; i<labreqsList.size(); i++){
            if( labreqsList.get(i).getRUID().equals((labreqsList.get(i)).getRUID()) && labreqsList.get(i).getDeleted()!=true )
                {
                count++;
                matchedIndexes.add(i);
                }
        }
    }
    if(count==1){

        return matchedIndexes.get(0);
    }
    return 0;

}

public void displayInfoLabReqs(labreq myLabreq){
    System.out.println();
    System.out.printf("%s: %s\n", "Request's UID", myLabreq.getRUID());
    System.out.printf("%s: %s\n", "Lab Test Type", myLabreq.getType());
    System.out.printf("%s: %s\n", "Request Date", myLabreq.getRDate());
    System.out.printf("%s: %s\n", "Result", myLabreq.getResult());
    System.out.println();
    
}

public void deleteRecord(ArrayList<labreq> labreqsList, labreq myLabreq, String reason, int index, boolean display) throws IOException{  //copy and pasted lng from readpatients
    labreqsList.get(index).setDeleted(true);
    labreqsList.get(index).setReason(reason);
    if(display==true){
        System.out.println("\nDeleting from Database...\n");
        try{Thread.sleep(2000);}
        catch(InterruptedException ex){
            ex.printStackTrace();
        }
        System.out.println(myLabreq.RUID + " has successfully been deleted.");
}
    }

public static void updateRequestFiles(ArrayList<labreq> labreqsList, labreq myLabreq, ArrayList<labreq> labreqslistMaster) throws IOException{ //writes to Services file

        String filename = myLabreq.serviceCode;
        filename= filename + "_Requests.txt";
        FileWriter labreqslistFW = new FileWriter(filename); // opens or creates(if doesn't exist) file for Patients.txt
        PrintWriter labreqslistOut = new PrintWriter(labreqslistFW);
    
        for(labreq d : labreqsList){
            if(d.deleted==false){
                labreqslistOut.println(d.RUID + ";"+ d.PUID + ";"+ d.RDate + ";" + d.RTime + ";" + d.result + ";");
            }
            else{
                labreqslistOut.println(d.RUID + ";"+ d.PUID + ";"+ d.RDate + ";" + d.RTime + ";" + d.result + ";" + "D"+ ";" + d.reason);
            }
            
            }
        labreqslistOut.close();

        FileWriter masterFW = new FileWriter("Master_Requests.txt"); // opens or creates(if doesn't exist) file for Patients.txt
        PrintWriter masterOut = new PrintWriter(masterFW);
    
        for(labreq d : labreqslistMaster){
            if(d.deleted==false){
                masterOut.println(d.RUID + ";"+ d.PUID + ";"+ d.RDate + ";" + d.RTime + ";" + d.result + ";" + d.type);
            }
            else{
                masterOut.println(d.RUID + ";"+ d.PUID + ";"+ d.RDate + ";" + d.RTime + ";" + d.result + ";" + "D"+ ";" + d.reason +";" + d.type);
            }
            
            }
        masterOut.close();
    }

public static boolean searchLabReqsforPatient(String PUID, int searchoption, ArrayList<labreq> labreqsListMaster){
    int count=0;
    sc = new Scanner(System.in);
    List<Integer> matchedIndexes=new ArrayList<Integer>(); 
    labreqsListMaster = labreq.removeDuplicates(labreqsListMaster);
    boolean check;
    //search options: 1 = using RUID, 2: PUID.

    if(searchoption ==1){
        //search using PUID
        for(int i=0; i<labreqsListMaster.size(); i++){
            if( labreqsListMaster.get(i).getPUID().contains(PUID) )
                {
                count++;
                matchedIndexes.add(i);
                }
        }
    }
        System.out.println("");
        String head1 = "Request's UID",
               head2 = "Lab Test Type",
               head3 = "Request Date",
               head4 = "Result";
        System.out.printf("%-16s %-25s %-12s %-12s%n", head1, head2, head3, head4);
        for(int y=0; y<matchedIndexes.size(); y++){
            // System.out.println("index "+matchedIndexes.get(y));
            if(labreqsListMaster.get(matchedIndexes.get(0)).getDeleted()!=true){
            System.out.printf("%-16s %-25s %-12s %-12s%n", labreqsListMaster.get(matchedIndexes.get(y)).getRUID(), 
                                        labreqsListMaster.get(matchedIndexes.get(y)).getType(), 
                                        labreqsListMaster.get(matchedIndexes.get(y)).getRDate(),
                                        labreqsListMaster.get(matchedIndexes.get(y)).getResult()); 
                                        return true;
            }
            
        }
        if(matchedIndexes.size()==0){
            return false;
        }
    return false;
}
}

