/*

Submitted By: Audrea Arjaemi T. Tabadero
4 July 2022

*/

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;  
import java.util.Date;

public class App {
    private static Scanner sc;
    public static void main(String[] args) throws Exception {
        int transaction;
        char subtransaction;
        char choice;
        int patientsCount=0;
        boolean stopProg;

        patient myPatient = new patient("", "", "", 0, 'o', "", 0, 0, false, "", false, "");
        ArrayList<patient> patients;
        patients = new ArrayList<patient>();

        ArrayList<service> servicesList;
        servicesList = new ArrayList<service>();
        service myService = new service("", "", 0, false,"");

        ArrayList<labreq> labreqsList;
        labreqsList = new ArrayList<labreq>();
        labreq myLabreq = new labreq("Master", false, "", "", "", "", "", "", "");

        ArrayList<labreq> labreqsListMaster;
        labreqsListMaster = new ArrayList<labreq>();

        sc = new Scanner(System.in);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
        String date = sdf.format(new Date()); 
        

        //used for UID of patient; getting year and month
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);  
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);
        month++;

        System.out.println("\nSession Login: " + date+ "\n");

        stopProg=false;
        while(stopProg!=true){
        
        myPatient.readPatientFile(patients);
        patients = patient.removeDuplicates(patients);

        myService.readServicesFile(servicesList);
        servicesList = service.removeDuplicates(servicesList);

        labreq.updateReqsList(labreqsList, myLabreq, servicesList, labreqsListMaster);
        labreqsList = labreq.removeDuplicates(labreqsList);
        labreqsListMaster = labreq.removeDuplicates(labreqsListMaster);

        //### FOR CHECKING ELEMENTS OF patients ARRAYLIST ###
        // for(int i=0;i<labreqsList.size(); i++){
        //     System.out.println((i+1)+"."+labreqsList.get(i).getReason());
        // }

        //make main menu a function!!
         System.out.println("Medical Laboratory Information System\n[1] Manage Patient Records\n[2] Manage Services\n[3] Manage Laboratory Results\n[4] Exit Program\n");
         System.out.print("Select a transaction: ");
        transaction = sc.nextInt();
        System.out.println();

        switch(transaction){
            case 1:
            System.out.println("You have selected: [1] Manage Patient Records");
            break;
            case 2:
            System.out.println("You have selected: [2] Manage Services");
            break;
            case 3:
            System.out.println("You have selected: [3] Manage Laboratory Results");
            break;
            case 4:
            System.out.println("You have selected: [4] Exit Program");
            break;
            default:
            System.out.println("Sorry but that is an invalid input.");
        }
        
        if(transaction>0 && transaction<5){
        System.out.print("Is that correct? [Y/N]: ");
        choice = (sc.next().charAt(0));
        System.out.println("");
        if(choice == 'y' | choice =='Y'){
            
            if(transaction!=4){
                ConsoleProgressBar.Loading();
            }
            
            int searchChoice=0;
            String uidChoice;
            char UIDyn;
            char NatYN;
            int searchoption;
            int action; //1 - delete patient; 2 - search patient

            switch(transaction){
            //######### MANAGE PATIENT RECORDS #########
                case 1: 
                patients = patient.removeDuplicates(patients); //remove duplicates in the patients arraylist
                System.out.println("Manage Patient Records\n[1] Add New Patient\n[2] Edit Patient Record\n[3] Delete Patient Record\n[4] Search Patient Record\n[X] Return to Main Menu");
                System.out.println("");
                System.out.print("Select a transaction: ");
                subtransaction = (sc.next().charAt(0));
                System.out.println();
            
                switch(subtransaction){
                    //######### ADD NEW PATIENT #########
                    case '1': //add new patient
                    System.out.println("You have selected: [1] Add New Patient");
                    System.out.println("-------------");
                    System.out.println("Please provide the following:");
                    sc.nextLine();

                    System.out.print("First Name : ");
                    myPatient.setFirst(sc.nextLine());

                    System.out.print("Last Name : ");
                    myPatient.setLast(sc.nextLine());

                    System.out.print("Middle Name : ");
                    myPatient.setMiddle(sc.nextLine());

                    System.out.print("Birthday(YYYYMMDD) : ");
                    myPatient.setBirthday(sc.nextInt());

                    System.out.print("Gender : ");
                    myPatient.setGender(sc.next().charAt(0));
                    sc.nextLine();

                    System.out.print("Address : ");
                    myPatient.setAddress(sc.nextLine());

                    System.out.print("Phone No. : ");
                    myPatient.setPhone(sc.nextLong());

                    System.out.print("National ID No. : ");
                    myPatient.setNationalID(sc.nextInt());

                    System.out.print("Save Patient Record [Y/N] : ");
                    char savepatient;
                    savepatient = sc.next().charAt(0);
                    patientsCount = patients.size();

                    // System.out.println("Patients count ausin to nde p tapos:" +patientsCount); **BALIKAN TOOOOO

                    String UID = myPatient.UIDGen(year, month, day, patientsCount);
                    myPatient.setUID(UID); //sets respective UID to patient

                    if(savepatient=='y' | savepatient=='Y'){
                        //**IMPORTANT** add smth that READS contents of Patients.txt and stores them into array
                        myPatient.addPatientToFile(myPatient, UID); //BALIKAN TO
                        patients.add(myPatient);
                    }
                    else if(savepatient==('N'|'n')){
                        System.out.println("Returning to Main Menu...\n"); //BALIKAN TO; MAKE IT GO BACK TO MAIN MENU
                    }
                    break;

                    //######### EDIT PATIENT RECORD #########
                    case '2': // ** edit patient record 
                    System.out.println("You have selected: [2] Edit Patient Record");
                    System.out.println("-------------");
                    System.out.println("Take Note: Only Patient Address and Phone Number can be edited.\n");
                    
                    while(searchChoice!=3 | searchChoice!=1 | searchChoice!=2){
                        System.out.print("Do you know the UID [Y/N]: ");
                        UIDyn = (sc.next().charAt(0));
                        sc.nextLine();
                        if(UIDyn == 'y' | UIDyn == 'Y'){
                            searchoption =1;
                            System.out.print("Please Enter Patient's UID: ");
                            myPatient.setUID(sc.nextLine());
                            searchChoice = myPatient.searchPatients(patients, myPatient, searchoption);
                        }
                        else if(UIDyn=='n' | UIDyn=='N'){
                            System.out.print("Do you know the National ID no. [Y/N]: ");
                            NatYN = (sc.next().charAt(0));
                            sc.nextLine();
                            if(NatYN=='y' | NatYN=='Y'){
                                searchoption =2;
                                System.out.print("Please Enter Patient's National ID no.: ");
                                myPatient.setNationalID(sc.nextInt());
                                sc.nextLine();
                                searchChoice = myPatient.searchPatients(patients, myPatient, searchoption);
                            }
                            else if(NatYN=='n'| NatYN=='N'){
                                searchoption =3;
                                System.out.println("\nPlease Enter the Following: ");
                                System.out.print("Patient's Last Name: ");
                                myPatient.setLast(sc.nextLine());
                                

                                System.out.print("Patient's First Name: ");
                                myPatient.setFirst(sc.nextLine());
                                

                                System.out.print("Patient's Birthday(YYYYMMDD): ");
                                myPatient.setBirthday(sc.nextInt());
                                sc.nextLine();
                                searchChoice = myPatient.searchPatients(patients, myPatient, searchoption);
                            }
                        }
                        if(searchChoice ==3){ //3 == return to main menu
                            System.out.println("Returning to Main Menu...");
                            break;
                        }
                        if(searchChoice==1){ //enter the UID of the patient
                            String newadd;
                            long newNum;
                            // myPatient.displayInfo(myPatient, year, month, day, 1, labreqsListMaster, myPatient.getUID());
                            System.out.print("\nEnter the Patient's UID you want to edit: ");
                            uidChoice = sc.nextLine();
                            myPatient.setUID(uidChoice);

                            int index1 =patient.getIndex(patients, myPatient);
                            System.out.print("Please Enter New Address: ");
                            
                            newadd = sc.nextLine();
                            System.out.print("Please Enter New Phone Number: ");
                            newNum = sc.nextLong();

                            patient.editPatient(patients, myPatient, newadd, newNum, index1);
                            patient.updatePatientsFile(patients);

                            System.out.println("Info has successfully been updated.\n\n");
                            
                            break;
                        }
                        if(searchChoice==2){
                            // System.out.println("iisa lng choice");

                            int index1 =patient.getIndex(patients, myPatient);

                            myPatient.setGender(patients.get(index1).getGender());
                            myPatient.displayInfo(myPatient, year, month, day, 1, labreqsListMaster, myPatient.getUID());

                            System.out.print("\nPlease Enter New Address: ");
                            patients.get(index1).setAddress(sc.nextLine());
                            System.out.print("Please Enter New Phone Number: ");
                            patients.get(index1).setPhone(sc.nextLong());
                            sc.nextLine();

                            patient.updatePatientsFile(patients);
                            System.out.println("Info has successfully been updated.\n\nReturning to Main Menu...\n");
                            break;
                        }
                    }
                    patients = patient.removeDuplicates(patients);
;                    break;

    //######### DELETE PATIENT RECORD #########
                    case '3': // ** delete patient record
                    action =1;
                    System.out.println("You have selected: [3] Delete Patient Record");
                    System.out.println("-------------");
                    while(searchChoice!=3 | searchChoice!=1 | searchChoice!=2){
                        System.out.print("Do you know the UID [Y/N]: ");
                        UIDyn = (sc.next().charAt(0));
                        sc.nextLine();
                        if(UIDyn == 'y' | UIDyn == 'Y'){
                            searchoption =1;
                            System.out.print("Please Enter Patient's UID: ");
                            myPatient.setUID(sc.nextLine());
                            searchChoice = myPatient.searchPatients(patients, myPatient, searchoption);
                        }
                        else if(UIDyn=='n' | UIDyn=='N'){
                            System.out.print("Do you know the National ID no. [Y/N]: ");
                            NatYN = (sc.next().charAt(0));
                            sc.nextLine();
                            if(NatYN=='y' | NatYN=='Y'){
                                searchoption =2;
                                System.out.print("Please Enter Patient's National ID no.: ");
                                myPatient.setNationalID(sc.nextInt());
                                sc.nextLine();
                                searchChoice = myPatient.searchPatients(patients, myPatient, searchoption);
                            }
                            else if(NatYN=='n'| NatYN=='N'){
                                searchoption =3;
                                System.out.println("\nPlease Enter the Following: ");
                                System.out.print("Patient's Last Name: ");
                                myPatient.setLast(sc.nextLine());
                                
                                System.out.print("Patient's First Name: ");
                                myPatient.setFirst(sc.nextLine());
                                
                                System.out.print("Patient's Birthday(YYYYMMDD): ");
                                myPatient.setBirthday(sc.nextInt());
                                sc.nextLine();
                                searchChoice = myPatient.searchPatients(patients, myPatient, searchoption);
                            }
                        }
                        if(searchChoice ==3){ //3 == return to main menu
                            System.out.println("Returning to Main Menu...");
                            break;
                        }
                        if(searchChoice==1){ //enter the UID of the patient
                            // myPatient.displayInfo(myPatient, year, month, day, action, labreqsListMaster, myPatient.getUID());
                            System.out.print("\nEnter the patient's UID you want to delete: ");
                            uidChoice = sc.nextLine();
                            myPatient.setUID(uidChoice);
                        }
                        if(searchChoice==2){
                            // System.out.println("iisa lng choice");
                            myPatient.displayInfo(myPatient, year, month, day, action, labreqsListMaster, myPatient.getUID());
                        }
                        System.out.print("\nDelete? [Y/N]: ");

                        char DELyn;
                        String reasonDel;
                       
                        DELyn = (sc.next().charAt(0));
                        sc.nextLine();
                        
                        if(DELyn=='Y' | DELyn=='y'){
                            System.out.print("Please Enter for Deletion: ");
                            reasonDel = sc.nextLine();

                            int a = patient.getIndex(patients, myPatient);
                            patients.get(a).setReason(reasonDel);
                            patients.get(a).setDeleted(true);
                            System.out.println("\nPatient Info has been successfully deleted.\n");

                            patient.updatePatientsFile(patients);
                            patients =patient.removeDuplicates(patients);
                            break;
                        }
                        else if(DELyn=='n' | DELyn=='N'){
                            System.out.println("Returning to Main Menu...");
                        }
                    }
    
                        break;

                    //######### SEARCH PATIENT RECORD #########
                    case '4': // search patient record                    
                    int index;
                    patients = patient.removeDuplicates(patients);
                    action =2; 
                    System.out.println("You have selected: [4] Search Patient Record");
                    System.out.println("-------------");
                    //search options: 1 = using UID, 2: using National ID no., 3: using last name, first name, birthday combination

                    while(searchChoice!=3 | searchChoice!=1 | searchChoice!=2){
                    System.out.print("Do you know the UID [Y/N]: ");
                    UIDyn = (sc.next().charAt(0));
                    sc.nextLine();
                    if(UIDyn == 'y' | UIDyn == 'Y'){
                        searchoption =1;
                        System.out.print("Please Enter Patient's UID: ");
                        myPatient.setUID(sc.nextLine());
                        searchChoice = myPatient.searchPatients(patients, myPatient, searchoption);
                    }
                    else if(UIDyn=='n' | UIDyn=='N'){
                        System.out.print("Do you know the National ID no. [Y/N]: ");
                        NatYN = (sc.next().charAt(0));
                        sc.nextLine();
                        if(NatYN=='y' | NatYN=='Y'){
                            searchoption =2;
                            System.out.print("Please Enter Patient's National ID no.: ");
                            myPatient.setNationalID(sc.nextInt());
                            searchChoice = myPatient.searchPatients(patients, myPatient, searchoption);
                        }
                        else if(NatYN=='n'| NatYN=='N'){
                            searchoption =3;
                            System.out.println("\nPlease Enter the Following: ");
                            System.out.print("Patient's Last Name: ");
                            myPatient.setLast(sc.nextLine());
    
                            System.out.print("Patient's First Name: ");
                            myPatient.setFirst(sc.nextLine());
                            
                            System.out.print("Patient's Birthday(YYYYMMDD): ");
                            myPatient.setBirthday(sc.nextInt());
                            searchChoice = myPatient.searchPatients(patients, myPatient, searchoption);
                        }
                    }

                    if(searchChoice ==3){ //3 == return to main menu
                        System.out.println("Returning to Main Menu...");
                        break;
                    }
// ** SEARCH AND DISPLAY FOR MULTIPLE RESULTS NOT WROKING PUTANIGNA BALIKAN TO 
                    else if(searchChoice==1){ //enter the UID of the patient //multiple results
                        System.out.print("\nPlease enter the Patient's UID: ");
                        sc.nextLine();
                        uidChoice = sc.nextLine();
                        myPatient.setUID(uidChoice);
                        int index2 = patient.getIndex(patients, myPatient);

                        myPatient.setFirst(patients.get(index2).getFirst());
                        myPatient.setLast(patients.get(index2).getLast());
                        myPatient.setMiddle(patients.get(index2).getMiddle());
                        myPatient.setBirthday(patients.get(index2).getBirthday());
                        myPatient.setGender(patients.get(index2).getGender());
                        myPatient.setAddress(patients.get(index2).getAddress());
                        myPatient.setPhone(patients.get(index2).getPhone());
                        myPatient.setNationalID(patients.get(index2).getNationalID());
                        myPatient.setinList(true);
                        myPatient.setDeleted(false);


                        myPatient.displayInfo(myPatient, year, month, day, action, labreqsListMaster, myPatient.getUID());
                    }
                    else if(searchChoice==2){
                        // System.out.println("iisa lng choice");
                        index = patient.getIndex(patients, myPatient);
                        myPatient.setUID(patients.get(index).getUID());
                        myPatient.setFirst(patients.get(index).getFirst());
                        myPatient.setLast(patients.get(index).getLast());
                        myPatient.setMiddle(patients.get(index).getMiddle());
                        myPatient.setBirthday(patients.get(index).getBirthday());
                        myPatient.setGender(patients.get(index).getGender());
                        myPatient.setAddress(patients.get(index).getAddress());
                        myPatient.setPhone(patients.get(index).getPhone());
                        myPatient.setNationalID(patients.get(index).getNationalID());
                        myPatient.setinList(true);
                        myPatient.setDeleted(false);
                        myPatient.displayInfo(myPatient, year, month, day, action, labreqsListMaster, myPatient.getUID());
                        break;
                    }
                }

                    break;

                    //######### RETURN TO MAIN MENU #########
                    case 'x': //return to main menu
                    System.out.println("You have selected: [X] Return to Main Menu");
                    break;
                    default: // invalid input
                    System.out.println("That is in invalid input.");
                    stopProg=true;
                }
                patients = patient.removeDuplicates(patients);
            break;

            case 2: //manage services
            servicesList = listTingz.removeDuplicates(servicesList);
            System.out.println("Manage Services\n[1] Add New Service\n[2] Search Service\n[3] Delete Service\n[4] Edit Service\n[X] Return to Main Menu");
            System.out.println("");
            System.out.print("Select a transaction: ");
            subtransaction = (sc.next().charAt(0));
            System.out.println();
   
            switch(subtransaction){

                case '1': // ** add new service
                String scode;
                boolean cont = true;
                boolean dupeflag=false;
                char addanother='y';
                System.out.println("You have selected: [1] Add New Service");
                    System.out.println("-------------");

                    System.out.println("\nExisting Services:");
                    String head1 = "Service Code",
                    head2 = "Description",
                    head3 = "Price";
                    System.out.printf("%-16s %-25s %-12s%n", head1, head2, head3);
                    for(int i=0;i<servicesList.size(); i++){
                        System.out.printf("%-16s %-25s %-12s%n", servicesList.get(i).getSC(), 
                        servicesList.get(i).getDesc(), 
                        servicesList.get(i).getPrice()); 
                    }

                    while(addanother=='y'){
                    System.out.println();
                    System.out.println("Please provide the following:");
                    sc.nextLine();

                    while(cont == true){
                        System.out.print("Service Code : ");
                        scode = sc.nextLine();
                        for(service d : servicesList){
                            if(d.getSC() != null && d.getSC().contains(scode)){
                                dupeflag=true;
                                }
                            }

                        while(dupeflag==true){
                            System.out.println("\nService Code already exists in file. Please Reinput.");
                            System.out.print("Service Code : ");
                            scode = sc.nextLine();
                            for(service d : servicesList){
                            if(d.getSC() != null && d.getSC().contains(scode)){
                                System.out.println("duplciate");
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
                        
                            System.out.print("Add Another [Y/N]: ");
                            addanother = sc.next().charAt(0);
                            sc.nextLine();

                            if(addanother==('N'|'n')){
                                System.out.println("Returning to Main Menu...\n"); //BALIKAN TO; MAKE IT GO BACK TO MAIN MENU
                                cont=false;
                            }
                        }
    }
}
                break;
//####################################################################################################

                case '2': // ** search service
                char scYN;
                String keyword;
                char ul8='y';
                System.out.println("You have selected: [2] Search Service");
                    System.out.println("-------------");
                    //search options: 1 = service code, 2: keyword that matches any of the words in description

                    while(searchChoice!=3 | searchChoice!=1 | searchChoice!=2){
                    System.out.print("Do you know the Service Code [Y/N]: ");
                    scYN = (sc.next().charAt(0));
                    sc.nextLine();
                    if(scYN == 'y' | scYN == 'Y'){
                        searchoption =1;
                        System.out.print("Please Enter the Service Code: ");
                        myService.setSC(sc.nextLine());
                        searchChoice = myService.searchServices(servicesList, myService, searchoption, "");
                        myService.displayInfoServices(myService);
                        System.out.print("Search Again? [Y/N]: ");
                        ul8 = (sc.next().charAt(0));
                        if(ul8=='n' | ul8=='N'){
                            System.out.println("\nReturning to Main Menu...\n");
                            break;
                        }
                    }
                    else if(scYN=='n' | scYN=='N'){
                        searchoption=2;
                        System.out.print("Please Enter Keyword from Description: ");
                        keyword = sc.nextLine();
                        searchChoice = myService.searchServices(servicesList, myService, searchoption, keyword);
                        if(searchChoice==2){
                            myService.displayInfoServices(myService);
                        }
                        System.out.print("Search Again? [Y/N]: ");
                        ul8 = (sc.next().charAt(0));
                        if(ul8=='n' | ul8=='N'){
                            System.out.println("\nReturning to Main Menu...\n");
                            break;
                        }

                    }

                    if(searchChoice ==3){ //3 == return to main menu
                        System.out.println("Returning to Main Menu...");
                        break;
                    }
                }

                break;

                case '3': // ** delete service
                String reason;
                String scChoice;
                int index;
                
                System.out.println("You have selected: [3] Delete Service");
                System.out.println("-------------");
                while(searchChoice!=3 | searchChoice!=1 | searchChoice!=2){
                    myService.updateServicesFile(servicesList);
                    servicesList = service.removeDuplicates(servicesList);

                    System.out.print("Do you know the Service Code [Y/N]: ");
                    scYN = (sc.next().charAt(0));
                    sc.nextLine();
                    if(scYN == 'y' | scYN == 'Y'){
                        searchoption =1;
                        System.out.print("Please Enter the Service Code: ");
                        myService.setSC(sc.nextLine());
                        searchChoice = myService.searchServices(servicesList, myService, searchoption, "");

                        myService.displayInfoServices(myService);
                        System.out.print("Please Enter Reason for Deletion: ");
                        reason = sc.nextLine();
                        index= service.getIndex(servicesList, myService, 1, "");
                        myService.deleteRecord(servicesList, myService, reason, index);

                        System.out.print("Delete Again? [Y/N]: ");
                        ul8 = (sc.next().charAt(0));
                        if(ul8=='n' | ul8=='N'){
                            System.out.println("\nReturning to Main Menu...\n");
                            break;
                        }
                    }
                    else if(scYN=='n' | scYN=='N'){
                        searchoption=2;
                        System.out.print("Please Enter Keyword from Description: ");
                        keyword = sc.nextLine();
                        searchChoice = myService.searchServices(servicesList, myService, searchoption, keyword);
                        if(searchChoice==2){
                            myService.displayInfoServices(myService);

                            index= service.getIndex(servicesList, myService, 1, "");
                            System.out.print("Please Enter Reason for Deletion: ");
                            reason = sc.nextLine();
                            myService.deleteRecord(servicesList, myService, reason, index);
                        }
                        else if(searchChoice==1){ //enter the service code //multiple results
                            System.out.print("\nPlease enter the Service Code: ");
                            scChoice = sc.nextLine();
                            myService.setSC(scChoice);
                            index= service.getIndex(servicesList, myService, 1, "");
                            // System.out.println(index);

                            myService.searchServices(servicesList, myService, 1, "");

                            System.out.print("Please Enter Reason for Deletion: ");
                            reason = sc.nextLine();
                            myService.deleteRecord(servicesList, myService, reason, index);
                            
                        }

                        System.out.print("Delete Again? [Y/N]: ");
                        ul8 = (sc.next().charAt(0));
                        if(ul8=='n' | ul8=='N'){
                            System.out.println("\nReturning to Main Menu...\n");
                            break;
                        }

                    }

                    if(searchChoice ==3){ //3 == return to main menu
                        System.out.println("Returning to Main Menu...");
                        break;
                    }
                }
                myService.updateServicesFile(servicesList);
                servicesList = service.removeDuplicates(servicesList);
                break;

                case '4': // ** edit service
                char proceed;
                System.out.println("You have selected: [4] Edit Service");
                System.out.println("-------------");
                System.out.println("The Services CANNOT be edited. If you would like to edit an Existing Service, the Service will be first DELETED, and a new Service will be created.\n");
                System.out.print("Proceed? [Y/N]: ");
                proceed = sc.next().charAt(0);

                if(proceed=='y' | proceed=='Y'){
                    while(searchChoice!=3 | searchChoice!=1 | searchChoice!=2){
                        myService.updateServicesFile(servicesList);
                        servicesList = service.removeDuplicates(servicesList);
    
                        System.out.print("Do you know the Service Code [Y/N]: ");
                        scYN = (sc.next().charAt(0));
                        sc.nextLine();
                        if(scYN == 'y' | scYN == 'Y'){
                            searchoption =1;
                            System.out.print("Please Enter the Service Code: ");
                            myService.setSC(sc.nextLine());
                            searchChoice = myService.searchServices(servicesList, myService, searchoption, "");
    
                            myService.displayInfoServices(myService);
                            System.out.print("Please Enter Reason for Deletion (EDIT): ");
                            reason = sc.nextLine();
                            index= service.getIndex(servicesList, myService, 1, "");
                            myService.deleteRecord(servicesList, myService, reason, index);

                            service.AddServiceEdit(servicesList);
                            myService.updateServicesFile(servicesList);
                            break;

                        }
                        else if(scYN=='n' | scYN=='N'){
                            searchoption=2;
                            System.out.print("Please Enter Keyword from Description: ");
                            keyword = sc.nextLine();
                            searchChoice = myService.searchServices(servicesList, myService, searchoption, keyword);
                            if(searchChoice==2){
                                myService.displayInfoServices(myService);
                                
                                System.out.print("Please Enter Reason for Deletion (EDIT): ");
                                reason = sc.nextLine();
                                index= service.getIndex(servicesList, myService, 1, "");
                                myService.deleteRecord(servicesList, myService, reason, index);
                                service.AddServiceEdit(servicesList);
                                myService.updateServicesFile(servicesList);
                                break;
                            }
                            else if(searchChoice==1){ //enter the service code //multiple results
                                System.out.print("\nPlease enter the Service Code: ");
                                scChoice = sc.nextLine();
                                myService.setSC(scChoice);
                                index= service.getIndex(servicesList, myService, 1, "");
                                // System.out.println(index);
    
                                myService.searchServices(servicesList, myService, 1, "");
    
                                System.out.print("Please Enter Reason for Deletion: ");
                                reason = sc.nextLine();
                                myService.deleteRecord(servicesList, myService, reason, index);
                                service.AddServiceEdit(servicesList);
                                break;
                            }
                        }
                    }    
                }
                System.out.print("Edit Again? [Y/N]: ");
                ul8 = (sc.next().charAt(0));
                if(ul8=='n' | ul8=='N'){
                    System.out.println("\nReturning to Main Menu...\n");
                    break;
                }
                break;

                case 'x': //return to main menu
                System.out.println("You have selected: [X] Return to Main Menu");
                //insert function for main menu
                break;
                default: // invalid input
                System.out.println("That is in invalid input.");
                stopProg=true;
            }
            break;

            case 3: //manage laboratory results
            System.out.println("Manage Laboratory Results\n[1] Add New Laboratory Request\n[2] Search Laboratory Request\n[3] Delete Laboratory Request\n[4] Edit Laboratory Request\n[X] Return to Main Menu");
            System.out.println("");
            System.out.print("Select a transaction: ");
            subtransaction = (sc.next().charAt(0));
            System.out.println();
            String PUID;
            String sCode;

            switch(subtransaction){
                case '1': //add laboratory request
                char searchagain='y';
                boolean havePUID=false;
                boolean haveSC=false;
                System.out.println("You have selected: [1] Add New Laboratory Request");
                System.out.println("-------------");

                while(searchagain!='n' | searchagain!='N'){
                System.out.println("Please Enter the Following: ");
                sc.nextLine();
                System.out.print("Patient's UID: ");
                PUID = sc.nextLine();
                System.out.print("Service Code: ");
                sCode = sc.nextLine();
                System.out.println("");

                for(patient p : patients){
                    if(p.getUID() != null && p.getUID().contains(PUID)){
                        havePUID = true;
                        myLabreq.setPUID(PUID);
                    }
                }
                for(service s : servicesList){
                    if(s.getSC() != null && s.getSC().contains(sCode)){
                    haveSC = true;
                    myLabreq.setSC(sCode);
                    }
                }
                if(havePUID == true && haveSC == true){
                    myService.getDesc();
                    labreq.updateReqsList(labreqsList, myLabreq, servicesList, labreqsListMaster);
                    // System.out.println(labreqsList.size());

                    myLabreq.setRUID(labreq.RUIDGen(year, month, day, labreqsList.size(), myLabreq));
                    
                    String rdate = Integer.toString(year);
                    if(month<10){
                        rdate = rdate + "0";
                    }
                    rdate = rdate + Integer.toString(month);
                    if(day<10){
                        rdate = rdate + "0";
                    }
                    rdate = rdate + Integer.toString(day);
                    myLabreq.setRDate(rdate);

                    String rtime = new SimpleDateFormat("HHmm").format(new Date());
                    // System.out.println(rtime);
                    myLabreq.setRTime(rtime);

                    System.out.print("Please Enter Result for Laboratory Result: ");
                    myLabreq.setResult(sc.nextLine());

                    myLabreq.setType(service.getTypeForRequest(servicesList, sCode));

                    labreq.addLabreq(myLabreq, labreqsList);
                    labreq.updateReqsList(labreqsList, myLabreq, servicesList, labreqsListMaster);

                    System.out.print("Add Another Laboratory Request? [Y/N]: ");
                    searchagain = (sc.next().charAt(0));

                    if(searchagain=='n' | searchagain == 'N'){
                        System.out.println();
                        System.out.println("Returning to Main Menu...");
                        break;
                    }
                }
                else{
                    System.out.println("ERROR: No Match Found.");
                    System.out.print("Search Again? [Y/N]: ");
                    searchagain = (sc.next().charAt(0));
                    System.out.println();
                    if(searchagain=='n' | searchagain == 'N'){
                        System.out.println("Returning to Main Menu...");
                        break;
                    }
                }
            }
                break;
                case '2': //search laboratory requests
                char ruidyn;
                char again;
                String typefromservice;
                System.out.println("You have selected: [2] Search Laboratory Request");
                System.out.println("-------------");
                while(searchChoice!=3 | searchChoice!=1 | searchChoice!=2){
                    System.out.print("Do you know the Request UID [Y/N]: ");
                    ruidyn = (sc.next().charAt(0));
                    sc.nextLine();
                    if(ruidyn == 'y' | ruidyn == 'Y'){
                        System.out.print("Please Enter the Request UID: ");
                        myLabreq.setRUID(sc.nextLine());
                        
                        String scode = "";
                        scode = scode + myLabreq.getRUID().charAt(0);
                        scode = scode + myLabreq.getRUID().charAt(1);
                        scode = scode + myLabreq.getRUID().charAt(2);

                        typefromservice = service.getTypeForRequest(servicesList, scode);
                        
                        myLabreq.setType(typefromservice);
                        myLabreq.setSC(scode);

                        searchChoice = labreq.searchLabReqs(myLabreq, 1, labreqsListMaster);
                        myLabreq.displayInfoLabReqs(myLabreq);
                        System.out.print("Search Again? [Y/N]: ");
                        again = (sc.next().charAt(0));
                        if(again=='n' | again=='N'){
                            System.out.println("\nReturning to Main Menu...\n");
                            break;
                        }
                    }

                    else if(ruidyn=='n' | ruidyn=='N'){
                        System.out.print("Please Enter Patient's UID: ");
                        myLabreq.setPUID(sc.nextLine());
                        searchChoice = labreq.searchLabReqs(myLabreq, 2, labreqsListMaster);
                        if(searchChoice==2){ //isa lng
                            myLabreq.displayInfoLabReqs(myLabreq);
                        }

                        System.out.print("\nSearch Again? [Y/N]: ");
                        again = (sc.next().charAt(0));
                        if(again=='n' | again=='N'){
                            System.out.println("\nReturning to Main Menu...\n");
                            break;
                        }

                    }

                    if(searchChoice ==3){ //3 == return to main menu
                        System.out.println("Returning to Main Menu...");
                        break;
                    }
                } 
                break;

                case '3': //delete laboratory request
                System.out.println("You have selected: [3] Delete Laboratory Request");
                System.out.println("-------------");
                
                String reason;
                int index;

                //search choice1 = ruid, 2=puid
                while(searchChoice!=3 | searchChoice!=1 | searchChoice!=2){
                    
                    System.out.print("Do you know the Request UID [Y/N]: ");
                    ruidyn = (sc.next().charAt(0));
                    sc.nextLine();
                    if(ruidyn == 'y' | ruidyn == 'Y'){
                        searchoption =1;
                        System.out.print("Please Enter the Request UID: ");
                        myLabreq.setRUID(sc.nextLine());

                        String scode = "";
                        scode = scode + myLabreq.getRUID().charAt(0);
                        scode = scode + myLabreq.getRUID().charAt(1);
                        scode = scode + myLabreq.getRUID().charAt(2);

                        typefromservice = service.getTypeForRequest(servicesList, scode);
                        
                        myLabreq.setType(typefromservice);
                        myLabreq.setSC(scode);

                        searchChoice = labreq.searchLabReqs(myLabreq, 1, labreqsListMaster);

                        myLabreq.displayInfoLabReqs(myLabreq);
                        System.out.print("Please Enter Reason for Deletion: ");
                        reason = sc.nextLine();

                        index= labreq.getIndex(labreqsListMaster, myLabreq, 1);
                        int indexotherlist = labreq.getIndex(labreqsList, myLabreq, 1);

                        myLabreq.deleteRecord(labreqsList, myLabreq, reason, indexotherlist, false);
                        myLabreq.deleteRecord(labreqsListMaster, myLabreq, reason, index, true);
                        labreq.updateRequestFiles(labreqsList, myLabreq, labreqsListMaster);
                        
                        System.out.print("Delete Again? [Y/N]: ");
                        again = (sc.next().charAt(0));
                        if(again=='n' | again=='N'){
                            System.out.println("\nReturning to Main Menu...\n");
                            break;
                        }
                    }
                    else if(ruidyn=='n' | ruidyn=='N'){
                        searchoption=2;
                        System.out.print("Please Enter Patient's UID: ");
                        myLabreq.setPUID(sc.nextLine());
                        searchChoice = labreq.searchLabReqs(myLabreq, 2, labreqsListMaster);
                        if(searchChoice==2){
                            myLabreq.displayInfoLabReqs(myLabreq);
                        }
                        else{
                            System.out.print("\nPlease Enter Request's UID for Deletion: ");
                            myLabreq.setRUID(sc.nextLine());

                            index= labreq.getIndex(labreqsListMaster, myLabreq, 1);
                            int index6 = labreq.getIndex(labreqsList, myLabreq, 1);
                            System.out.print("Please Enter Reason for Deletion: ");
                            reason = sc.nextLine();
                            myLabreq.deleteRecord(labreqsList, myLabreq, reason, index6,false);
                            myLabreq.deleteRecord(labreqsListMaster, myLabreq, reason, index, true);
                        }

                        System.out.print("Delete Again? [Y/N]: ");
                        again = (sc.next().charAt(0));
                        if(again=='n' | again=='N'){
                            System.out.println("\nReturning to Main Menu...\n");
                            break;
                        }

                    }

                    if(searchChoice ==3){ //3 == return to main menu
                        System.out.println("Returning to Main Menu...");
                        break;
                    }
                }
                // labreq.updateReqsList(labreqsList, myLabreq, servicesList, labreqsListMaster);
                // labreqsList = labreq.removeDuplicates(labreqsList);
                // labreqsListMaster = labreq.removeDuplicates(labreqsListMaster);
                labreq.updateRequestFiles(labreqsList, myLabreq, labreqsListMaster);
                break;

                case '4': //edit laboratory request
                System.out.println("You have selected: [4] Edit Laboratory Request");
                System.out.println("-------------");
                while(searchChoice!=3 | searchChoice!=1 | searchChoice!=2){
                    System.out.print("Do you know the Request UID [Y/N]: ");
                    ruidyn = (sc.next().charAt(0));
                    sc.nextLine();
                    if(ruidyn == 'y' | ruidyn == 'Y'){
                        System.out.print("Please Enter the Request UID to Edit: ");
                        myLabreq.setRUID(sc.nextLine());
                        
                        String scode = "";
                        scode = scode + myLabreq.getRUID().charAt(0);
                        scode = scode + myLabreq.getRUID().charAt(1);
                        scode = scode + myLabreq.getRUID().charAt(2);

                        typefromservice = service.getTypeForRequest(servicesList, scode);
                        
                        myLabreq.setType(typefromservice);
                        myLabreq.setSC(scode);

                        searchChoice = labreq.searchLabReqs(myLabreq, 1, labreqsListMaster);
                        int index4=labreq.getIndex(labreqsList, myLabreq, 1);
                        myLabreq.displayInfoLabReqs(myLabreq);
                        if(myLabreq.getResult()==""){
                            System.out.print("Please Enter Edited Entry: ");
                            labreqsList.get(index4).setResult(sc.nextLine());
                            System.out.println("The Laboratory Request " + myLabreq.getRUID() + " has successfully been updated.\n");
                        }
                        else{
                            System.out.println("ERROR: Request already has existing results. Please Retry.");
                        }
                        System.out.print("Edit another Laboratory Request? [Y/N]: ");
                        again = (sc.next().charAt(0));
                        if(again=='n' | again=='N'){
                            System.out.println("\nReturning to Main Menu...\n");
                            break;
                        }
                    }

                    else if(ruidyn=='n' | ruidyn=='N'){
                        searchoption=2;
                        System.out.print("Please Enter Patient's UID: ");
                        myLabreq.setPUID(sc.nextLine());
                        searchChoice = labreq.searchLabReqs(myLabreq, 2, labreqsListMaster);
                        
                        if(searchChoice==2){
                            myLabreq.displayInfoLabReqs(myLabreq);
                        }
                        else{
                            System.out.print("\nPlease Enter Request's UID to Edit: ");
                            myLabreq.setRUID(sc.nextLine());

                            if(myLabreq.getResult()==""){
                                index= labreq.getIndex(labreqsListMaster, myLabreq, 1);
                                System.out.print("Please Enter Edited Entry: ");
                                myLabreq.setResult(sc.nextLine());
                                System.out.println("The Laboratory Request " + myLabreq.getRUID() + " has successfully been updated.\n");
                            }
                            else{
                                System.out.println("ERROR: Request already has existing results. Please Retry.");
                            }
                        }
                      System.out.print("Edit another Laboratory Request? [Y/N]: ");
                        again = (sc.next().charAt(0));
                        if(again=='n' | again=='N'){
                            System.out.println("\nReturning to Main Menu...\n");
                            break;
                        }

                    }

                    if(searchChoice ==3){ //3 == return to main menu
                        System.out.println("Returning to Main Menu...");
                        break;
                    }
                } 
                labreq.updateReqsList(labreqsList, myLabreq, servicesList, labreqsListMaster);
                labreqsList = labreq.removeDuplicates(labreqsList);
                labreqsListMaster = labreq.removeDuplicates(labreqsListMaster);
                labreq.updateRequestFiles(labreqsList, myLabreq, labreqsListMaster);
                break;
                case 'x':
                System.out.println("You have selected: [X] Return to Main Menu");
                //insert function for main menu
                break;
                default:
                System.out.println("That is in invalid input.");
                stopProg=true;
            }
            break;

            case 4: //exit program
            System.out.println("Program Terminating...");
            try{
            Thread.sleep(2000);
            }
            catch(InterruptedException ex){
            ex.printStackTrace();
            }
            System.out.println("\nGoodbye!");
            stopProg=true;

            }

        }
        else if(choice==('N'|'n')){
            System.out.println("Program Terminating...");
            stopProg =true;
        }
    }

    }
}
}
    
