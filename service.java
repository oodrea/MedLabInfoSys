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
import com.itextpdf.text.DocumentException;  


public class service {
    private static Scanner sc;
    private String serviceCode;
    private String description;
    private int price;
    private boolean deleted;
    private String reason;

    public service(
        String serviceCode,
        String description,
        int price,
        boolean deleted,
        String reason
    )
 
    {
        this.serviceCode = serviceCode;
        this.description = description;
        this.price = price;
        this.deleted = deleted;
        this.reason = reason;
    }

    //######### SETTERS #########

    public void setSC(String serviceCode){
        this.serviceCode = serviceCode;
    }

    public void setDesc(String description){
        this.description = description;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public void setDeleted(boolean deleted){
        this.deleted = deleted;
    }
    
    public void setReason(String reason){
        this.reason = reason;
    }


    //#################################

    //######### GETTERS #########

    public String getSC(){
        return serviceCode;
    }

    public String getDesc(){
        return description;
    }

    public int getPrice(){
        return price;
    }

    public boolean getDeleted(){
        return deleted;
    }

    public String getReason(){
        return reason;
    }

    //#################################

public void addService(service myService, ArrayList<service> servicesList) throws IOException{ 
        File file = new File("Services.txt");
        if(!file.exists()){
            System.out.println("\nServices.txt does not exist in path. Creating File...");

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
        
        FileWriter servicesFW = new FileWriter("Services.txt", true); // opens or creates(if doesn't exist) file for Patients.txt
        PrintWriter servicesOut = new PrintWriter(servicesFW);
        servicesOut.println(myService.serviceCode + ";"+ myService.description + ";"+ myService.price+";" );
        servicesOut.close();

        System.out.println(myService.serviceCode +" "+ myService.description + " has successfully been added.");
        
}

public void readServicesFile(ArrayList<service> servicesList) throws IOException{ //reads Services.txt file and updates "servicesList" arraylist
    BufferedReader reader;
    boolean delete=false;
    String rason="";
    String[] parts;
		try {
			reader = new BufferedReader(new FileReader("Services.txt"));
			String line = reader.readLine();
			while (line != null) {
				parts = line.split(";"); //separates elements from each line

                if(parts.length >3){ //more than 3 means it has deleted indicator and reason for deletion
                    delete = true;
                    rason = parts[4];
                    servicesList.add(new service(parts[0], parts[1], Integer.parseInt(parts[2]), delete, rason)); //adds service read from file to arraylist  
                }
                // System.out.println(parts.length);
                //adds each indiv patient to "patients" arraylist
                else{
                servicesList.add(new service(parts[0], parts[1], Integer.parseInt(parts[2]), delete, rason));
                }    
				line = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
            System.out.println("ERROR: Services File Not Found.");
                System.out.println("Creating File...");
                File file = new File("Services.txt");
                file.createNewFile();
                System.out.println("File Created.\n");
		}catch(IOException e){
            e.printStackTrace();
        }
}

public int searchServices(ArrayList<service> servicesList, service myService, int searchoption, String keyword){
    int count=0;
    sc = new Scanner(System.in);
    List<Integer> matchedIndexes=new ArrayList<Integer>(); 
    //search options: 1 = using UID, 2: using National ID no., 3: using last name, first name, birthday combination


    if(searchoption ==1){
        //search using UID
        for(int i=0; i<servicesList.size(); i++){
            if( myService.getSC().equals((servicesList.get(i)).getSC()) )
                {
                count++;
                matchedIndexes.add(i);
                }
        }
    }
    if(searchoption==2){
        //search keyword
        for(int i=0; i<servicesList.size(); i++){
            if( servicesList.get(i).getDesc().contains(keyword) )
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
        String head1 = "Service Code",
               head2 = "Description",
               head3 = "Price";
        System.out.printf("%-16s %-25s %-12s%n", head1, head2, head3);
        for(int y=0; y<matchedIndexes.size(); y++){
            // System.out.println("index "+matchedIndexes.get(y));
            if(servicesList.get(matchedIndexes.get(y)).getDeleted()!=true){
            System.out.printf("%-16s %-25s %-12s%n", servicesList.get(matchedIndexes.get(y)).getSC(), 
                                        servicesList.get(matchedIndexes.get(y)).getDesc(), 
                                        Integer.toString(servicesList.get(matchedIndexes.get(y)).getPrice())); 
            }
            
        }
        return 1;
    }
    else if(count==1){
        
    myService.setSC(servicesList.get(matchedIndexes.get(0)).getSC());
    myService.setDesc(servicesList.get(matchedIndexes.get(0)).getDesc());
    myService.setPrice(servicesList.get(matchedIndexes.get(0)).getPrice());

        return 2;
    }

    else{
        System.out.println("\nERROR: No record found.");
        return 0;
    }
    
}

public static int getIndex(ArrayList<service> servicesList, service myService, int searchoption, String keyword){
    int count=0;
    int index;
    sc = new Scanner(System.in);
    List<Integer> matchedIndexes=new ArrayList<Integer>(); 

    if(searchoption ==1){
        //search using service code
        for(int i=0; i<servicesList.size(); i++){
            if( myService.getSC().equals((servicesList.get(i)).getSC()) && servicesList.get(i).getDeleted()!=true)
                {
                count++;
                matchedIndexes.add(i);
                }
        }
    }
    if(searchoption==2){
        //search keyword
        for(int i=0; i<servicesList.size(); i++){
            if( servicesList.get(i).getDesc().contains(keyword) )
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

public void displayInfoServices(service myService) throws DocumentException, MalformedURLException, IOException, Exception{
    System.out.println();
    System.out.printf("%s: %s\n", "Service Code", myService.getSC());
    System.out.printf("%s: %s\n", "Description", myService.getDesc());
    System.out.printf("%s: %d\n", "Price", myService.getPrice());
    System.out.println();
    
}

public void updateServicesFile(ArrayList<service> servicesList) throws IOException{ //writes to Services file

    FileWriter servicesFW = new FileWriter("Services.txt"); // opens or creates(if doesn't exist) file for Patients.txt
    PrintWriter servicesOut = new PrintWriter(servicesFW);

    for(service d : servicesList){
        if(d.deleted==false){
            servicesOut.println(d.serviceCode + ";"+ d.description + ";"+ d.price);
        }
        else{
            servicesOut.println(d.serviceCode + ";"+ d.description + ";"+ d.price+";"+"D"+";"+d.reason);
        }
        
        }
    servicesOut.close();
}

public void deleteRecord(ArrayList<service> servicesList, service myService, String reason, int index) throws IOException{  //copy and pasted lng from readpatients
    servicesList.get(index).setDeleted(true);
    servicesList.get(index).setReason(reason);



    System.out.println("\nDeleting from Database...\n");
        try{Thread.sleep(2000);}
        catch(InterruptedException ex){
            ex.printStackTrace();
        }
        System.out.println("Service info successfully deleted.");
}

public static ArrayList<service> removeDuplicates(ArrayList<service> servicesList)
    {
  
        // Create a new LinkedHashSet
        Set<service> set = new LinkedHashSet<>();
  
        // Add the elements to set
        set.addAll(servicesList);
  
        // Clear the list
        servicesList.clear();
  
        // add the elements of set
        // with no duplicates to the list
        servicesList.addAll(set);
  
        // return the list
        return servicesList;
    }

public static void AddServiceEdit(ArrayList<service> servicesList) throws IOException{
    service myService = new service("", "", 0, false, "");
    String scode;
    boolean cont = true;
    boolean dupeflag=false;
    System.out.println("Adding New Service");
        System.out.println("-------------");

        System.out.println("\nExisting Services:");
        String head1 = "Service Code",
        head2 = "Description",
        head3 = "Price";
        System.out.printf("%-16s %-25s %-12s%n", head1, head2, head3);
        for(int i=0;i<servicesList.size(); i++){
            if(servicesList.get(i).getDeleted()!=true){
            System.out.printf("%-16s %-25s %-12s%n", servicesList.get(i).getSC(), 
            servicesList.get(i).getDesc(), 
            servicesList.get(i).getPrice()); 
            }
        }

        System.out.println();
        System.out.println("Please provide the following:");

        while(cont == true){
            System.out.print("Service Code : ");
            scode = sc.nextLine();
            for(service d : servicesList){
                if(d.getSC() != null && d.getSC().equals(scode) && d.getDeleted()!=true){
                    dupeflag=true;
                    }
                }

            while(dupeflag==true){
                System.out.println("\nService Code already exists in file. Please Reinput.");
                System.out.print("Service Code : ");
                scode = sc.nextLine();
                for(service d : servicesList){
                if(d.getSC() != null && d.getSC().contains(scode)){
                    dupeflag=true;
                    }
                    else{
                        dupeflag=false;
                    }
            }
        }
            if(dupeflag==false){
                myService.setSC(scode);
                System.out.print("Description: ");
                myService.setDesc(sc.nextLine());
                System.out.print("Price: ");
                myService.setPrice(sc.nextInt());
            
                myService.addService(myService, servicesList);
                servicesList.add(myService);
            
               break;
            }
}
}

public static String getTypeForRequest(ArrayList<service> servicesList, String sCode){
    String type;
    for(service s : servicesList){
        if(s.getSC() != null && s.getSC().equals(sCode)){
        type = s.getDesc();
        return type;
        }
    }
return "";
}

}

