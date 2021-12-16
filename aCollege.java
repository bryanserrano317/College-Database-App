/*
 Project 3
 Bryan Serrano, Michael Tsang, Jonathan Cole Collins, Natasha Bodner

*/

import java.util.*;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.lang.Exception;

public class aCollege {
  public static void main(String[] args) {
        System.out.println("\n\t\tWelcome to my Personal Management Program\n\n");

        //PUT VARIABLES AND ARRAYS HERE
        ArrayList<Person> people = new ArrayList<Person>();

        String menuChoice;
        Scanner scnr = new Scanner(System.in);

        Person faculty = new Faculty();
        Person student = new Student();
        
        //END OF VARIABLES AND ARRAYS

        do {
            menuChoice = Integer.toString(menu());

            switch (menuChoice) {
                case "1" : ((Faculty)faculty).facultyInfo(people); //METHOD FOR FACULTY
                    break;
                case "2" : ((Student)student).addStudent(people); //METHOD FOR STUDENTS
                    break;
                case "3" : ((Student)student).invoiceFee(student, people); //METHOD FOR PRINTING INVOICE
                    break;
                case "4" : ((Faculty)faculty).searchFacultyID(faculty, people); //METHOD FOR PRINTING FACULTY INFO
                    break;
                case "5" : caseFiveOption(faculty, student, people);
                  menuChoice = "5";
                    break;
                default: System.out.println("Invalid entry- please try again");
            }
        } while (menuChoice != "5");
  }

  public static int menu (){
        String choiceString;
        int choice;
        
        //MENU PRINT STATEMENTS
        System.out.println("Choose one of the options:\n");
        System.out.println(
                "1- Add a new Faculty member\n" +
                "2- Add a new Student\n" +
                "3- Print tuition invoice for a student\n" +
                "4- Print information of a faculty\n" +
                "5- Exit Program\n\n");

        //USER SELECTION
        System.out.print("Enter your selection: ");
        Scanner choiceScanner = new Scanner (System.in);
        choiceString = choiceScanner.nextLine();
        System.out.println("");

        //USER INPUT ALGORITHIM
        if(choiceString.length() == 1)
          {char choice1 = choiceString.charAt(0);
          choice = (int)choice1 - 48;
            if (choice < 1 || choice > 5){
              return 0;
            }
            else {
              return choice;}
          }
        else{return 0;}
  }

  public static void caseFiveOption(Person faculty,Person student,ArrayList<Person> people)
  {
    System.out.print("Would you like to print a report? (Y/N):  "); //METHOD FOR EXITING PROGRAM  
      Scanner option = new Scanner(System.in);
      String choice = option.nextLine();
      char respond = choice.charAt(0);
      //System.out.println(choice); debug
       if(respond == 'Y')
       {
          System.out.println("Your file has been created");
          System.out.println();
          System.out.println("Goodbye!");
                          
          printReport(faculty, student, people);
          return;
       }
       else if(respond == 'N')
       {
          System.out.println("Goodbye!");
          //System.exit(0);
          return;
       }
       else
       {
        System.out.println("Please enter 'Y' or 'N'");
      }
  }

  public static void printReport(Person faculty, Person student, ArrayList<Person> people){

    int r = 1; 
    int s = 1;

    DateTimeFormatter dtf= DateTimeFormatter.ofPattern("MM/dd/yyyy");

    LocalDateTime now = LocalDateTime.now();

    try{
      File file = new File("Report.dat");

      if(!file.exists()){
        file.createNewFile();
      }

      PrintWriter pw = new PrintWriter(file);
      pw.println("Report Created on: " + dtf.format(now));
      pw.println("**************");

     pw.println("Faculty Members (Sorted by Department)");
     pw.println("--------------------------------------");

     for(int i = 0; i < people.size(); i++){
      if(people.get(i) instanceof Faculty)
      {
      pw.println(r + ". " + ((Faculty)(people.get(i))).getName());
      pw.println("ID: " + ((Faculty)(people.get(i))).getId());
      pw.println(((Faculty)(people.get(i))).getRank() + "," + (((Faculty)(people.get(i))).getDepartment()));
      pw.println();
      r++;
      }
    }

     pw.println("Students");
     pw.println("--------");

     for(int j = 0; j < people.size(); j++){
       if(people.get(j) instanceof Student)
       {
       pw.println(s + ". "+((Student)(people.get(j))).getName());
       pw.println("ID: " + ((Student)(people.get(j))).getId());
       pw.println("Gpa:" + ((Student)(people.get(j))).getGpa());
       pw.println("Credit hours: " + ((Student)(people.get(j))).getCreditHours());
       pw.println();
       s++;
       }
     }
      pw.close();
    } catch(IOException e){
      System.out.println("File not created");
      return;
    }
  }
}

class Faculty extends Person{
  
  private String rank = null;
  private String department = null; 
  

  public String getRank(){
    return rank;
  }

  public String getDepartment(){
    return department;
  }

  public void setRank(String rank){
    this.rank = rank;
  }

  public void setDepartment(String department){
    this.department = department;
  }

  public void setFaculty(String name, String id, String rank, String department){
    this.name = name;
    this.id = id;
    this.rank = rank;
    this.department = department;
  }

 
  public void facultyInfo(ArrayList<Person> people){ //case 1

    Person fac = new Faculty();
    String facultyName = "";
    String facultyID = "";
    String facultyRank = "";
    String facultyDepartment = "";
    String idCharacters = "";
    String store = "";
    String spCharacters = "!@#$%^&*()_+{}|:?><?-=[];',./";


    Scanner scnr = new Scanner(System.in);

    System.out.println("Enter the faculty's info:\n");

    System.out.print( "\t\tName of the faculty: "); 
    facultyName = scnr.nextLine(); 
    System.out.println("");

    System.out.print("\t\tID: ");

    try
    {  
      idCharacters = scnr.nextLine();
    	  
    	int k = idCharacters.length();
    	  
    	char check[] = idCharacters.toCharArray();
    	  
      try{
        for(int z = 0; z < k; z++)
        {
          if(spCharacters.contains(Character.toString(idCharacters.charAt(z))) && k != 6) 
          {
            throw new InputMismatchException("\n\tEnter an ID of length 6 with no special characters");
          } 
        }
      }
      catch(InputMismatchException exp)
      {
          System.out.println(exp.getMessage());
          System.out.println("\n\tEnter two characters and four integers in the following order yyxxxx\n");
          return;
      }
      
        if(k != 6)
          {
    		    throw new IDException("\n\tID is not the correct length");
          }
    	  
        for(int w = 0; w < k; w++)
        {
          if(spCharacters.contains(Character.toString(idCharacters.charAt(w))))
          {
            throw new InputMismatchException("\n\tPlease do not enter decimals or special characters");
          }
        }

    	  for(int i = 0; i < 2; i++)
    	  {
    		  Boolean flag = Character.isDigit(idCharacters.charAt(i));
    		  if(flag)
    		  {
    			  throw new IDException("\n\tIncorrect ID Format");
    		  }
          else
          {
            check[i] = Character.toUpperCase(idCharacters.charAt(i));
          }
    	  }
    	  
    	  for(int j = 2; j < k; j++)
    	  {
    		  Boolean flag = Character.isLetter(idCharacters.charAt(j));
    		  {
    			  if(flag)
    			  {
    				  throw new IDException("\n\tIncorrect ID Format");
    			  }
    		  }
    	  }
        store = new String(check);
    }
    catch(IDException ex){
        System.out.println(ex.getMessage());
        System.out.println("\n\tPlease enter two characters and four integers in the following order yyxxxx\n");
        return;
    }
    catch(InputMismatchException e){
      System.out.println(e.getMessage());
       System.out.println("\n\tEnter two characters and four integers in the following order yyxxxx\n");
       return;
    }

    //System.out.println(store);
    facultyID = store;
    //facultyID = idCharacters;
    //facultyID = scnr.nextLine();
    System.out.println("");

    do
          {
              System.out.print("\t\tRank: ");
              rank = scnr.nextLine();
              System.out.println("");

              if(rank.equalsIgnoreCase("Professor") || rank.equalsIgnoreCase("Adjunct"))
              {
                  facultyRank = rank;
                  break;
              }
              else
              {
                  System.out.println("\t\tSorry entered rank (" + rank + ") is invalid");
              }
          }
          while(!(rank.equalsIgnoreCase("Professor") || rank.equalsIgnoreCase("Adjunct")));


    do
          {
              System.out.print("\t\tDepartment: ");
              department = scnr.nextLine();
              System.out.println("");

              if((department.equalsIgnoreCase("Mathematics") || department.equalsIgnoreCase("Physics") || department.equals("Engineering")))
              {
                facultyDepartment = department;
                  break;
              }
              else
              {
                  System.out.println("\t\tSorry entered rank (" + department + ") is invalid\n");
              }
          }
          while(!(department.equalsIgnoreCase("Mathematics") || department.equalsIgnoreCase("Physics") || department.equals("Engineering")));

   
    ((Faculty)fac).setFaculty(facultyName, facultyID, facultyRank, facultyDepartment);

    people.add(fac);
  }

  private boolean isLetter(char c) 
  {
	return false;
  }

  private boolean isDigit(char charAt) 
  {
	return false;
  }

  
  public void searchFacultyID(Person faculty, ArrayList<Person> people){
    String temp = null;
    String id = null;
    Scanner scnr = new Scanner(System.in);

    if(people.size() == 0)
    {
      System.out.println("No Faculty entered yet\n");
      return;
    }

    System.out.println("Enter the faculty’s id: ");
    id = scnr.nextLine();
    
    boolean test = false;

    for(int i = 0; i< people.size(); i++)
    {
      if(people.get(i) instanceof Faculty)
      {
       temp = ((Faculty)(people.get(i))).getId();
       

      if(temp.equalsIgnoreCase(id))
      {
    	  test = true;
        System.out.println("Faculty found:");
        ((Faculty)faculty).printFacultyInfo(i, faculty, people);
        break;
      }
      }
    } 
    if(test == false)
    {
      System.out.println("Sorry " + id + " doesn’t exist");
    }
  }

  public void printFacultyInfo(int i, Person faculty, ArrayList<Person> people){

      if (faculty instanceof Faculty)
      { //((Faculty)(people.get(i))).getName();
          //PRINT FACULTY INFOMATION
          System.out.println("\n\n");
        	System.out.println("---------------------------------------------------------\n");
          System.out.println("" + ((Faculty)(people.get(i))).getName() + "\n");
          System.out.println("" + ((Faculty)(people.get(i))).getDepartment() + " Department , " + ((Faculty)(people.get(i))).getRank() + "\n");
          System.out.println("---------------------------------------------------------\n");
          System.out.println("\n");
    }
  }
}//end of class

//Start of abstract class
abstract class Person {
  public String name = null;
  public String id = null;
  //make setters getters for id

  public String getName(){
    return name;
  }

  public void setName(String name){
    this.name = name;
  }

  public String getId(){
    return id;
  }
  public void setId(String id){
    this.id = id;
  }

  public Person(){
    this.name = "Not Available";
    this.id = "Not Available";
  }

  public Person(String name){
    this.name = name;
    this.id = "Not Available";
  }

  public Person(String name, String id){
    this.name = name;
    this.id = id;
  }

  @Override
	public String toString() {
		return name + " " + id;
	}
	
	public void printInfo() {
		System.out.print(name + " " + id);
	}

}

//Extension of abstract class
class Student extends Person
{
  private double gpa;
  private int creditHours;


  public String getId(){
    String id = super.getId();
    return id;
  }

  public double getGpa () {
    return gpa;
  }
  public void setGpa (double gpa) {
    this.gpa = gpa;
  }
  
  public int getCreditHours() {
    return creditHours;
  }

  public void setCreditHours(int creditHours) {
    this.creditHours = creditHours;
  }

  public void setStudent (String name, String id, double gpa, int creditHours){
    this.name = name;
    this.id = id;
    this.gpa = gpa;
    this.creditHours = creditHours;
  }

  @Override
	public String toString() {
	return getGpa() + " " + getCreditHours();
	}


  public void addStudent(ArrayList<Person> people){ //case 2
    Person stu = new Student();
    Scanner scnr = new Scanner(System.in);
    String studentName = ""; 
    String studentID = "";
    double studentGPA = 0.0;
    int studentCreditHour = 0;
    String spCharacters = "!@#$%^&*()_+{}|:?><?-=[];',./";
    String store = "";

      //INPUT STUDENT INFO
      System.out.println("Enter the student's info:\n");

      System.out.print( "\t\tName of Student: "); 
      studentName = scnr.nextLine(); 
      System.out.println("");

      System.out.print("\t\tID: ");

      try{  
      studentID = scnr.nextLine();
    	  
    	  int k = studentID.length();

    	  char check[] = studentID.toCharArray();
    	  try{
        for(int z = 0; z < k; z++)
        {
          if(spCharacters.contains(Character.toString(studentID.charAt(z))) && k != 6) 
          {
            throw new InputMismatchException("\n\tEnter an ID of length 6 with no special characters");
          } 
        }
        }
        catch(InputMismatchException exp)
        {
          System.out.println(exp.getMessage());
          System.out.println("\n\tEnter two characters and four integers in the following order yyxxxx\n");
          return;
        }
      

    	  if(k != 6)
    	  {
    		  throw new IDException("\n\tID is not the correct length");
        }


        for(int w = 0; w < k; w++)
        {
          if(spCharacters.contains(Character.toString(studentID.charAt(w))))
          {
            throw new InputMismatchException("\n\tPlease do not enter decimals or special characters");
          }
        }


    	  for(int i = 0; i < 2; i++)
    	  {
    		  Boolean flag = Character.isDigit(studentID.charAt(i));
    		  if(flag)
    		  {
    			  throw new IDException("\n\tIncorrect ID Format");
    		  }
          else
          {
            check[i] = Character.toUpperCase(studentID.charAt(i));
          }
    	  }
    	  
    	  for(int j = 2; j < k; j++)
    	  {
    		  Boolean flag = Character.isLetter(studentID.charAt(j));
    		  {
    			  if(flag)
    			  {
    				  throw new IDException("\n\tIncorrect ID Format");
    			  }
    		  }
    	  }
        store = new String(check);
      }

    catch(IDException ex){
        System.out.println(ex.getMessage());
        System.out.println("\n\tPlease enter two characters and four integers in the following order yyxxxx\n");
        return;
    }
    catch(InputMismatchException e){
       System.out.println(e.getMessage());
       System.out.println("\n\tEnter two characters and four integers in the following order yyxxxx\n");
       return;
    }
      
      System.out.println("");
      
      System.out.print("\t\tGpa: ");
      studentGPA = scnr.nextDouble();
      System.out.println("");

      if(studentGPA > 4.0 || studentGPA < 0){
        System.out.println("The GPA entered is invalid, please try again and enter GPA between 0 and 4\n");
        System.out.println("\n");
        return;
      }

      System.out.print("\t\tCredit Hours: ");
      studentCreditHour = scnr.nextInt();
      System.out.println("");

      //this part creates a new student object and fills it with the information inputted and adds it to the ArrayList
  
      ((Student)stu).setStudent(studentName, studentID, studentGPA, studentCreditHour);
    
      people.add(stu);
      
  }

  public void invoiceFee(Person student, ArrayList<Person> people)
  {
    String temp = null;
    Scanner input = new Scanner(System.in); 
    String n = null;
    
    if(people.size() == 0)
    {
      System.out.println("No Student entered yet\n");
      return;
    }


    System.out.println("Enter the Student's ID: ");
    n = input.nextLine();

    //System.out.println(people.size());
    //((Student)(people.get(i))).getName();
    // if(student instanceof Student)
    
      boolean test = false;
      for(int i = 0; i < people.size(); i++)
      { 
        if(people.get(i) instanceof Student)
        {
        temp = ((Student)(people.get(i))).getId();
      
        if(temp.equalsIgnoreCase(n))
        {
          test = true;
                
          ((Student)student).printInvoice(i, student, people);
          break;
        }
        
        }
      }
      if(test == false)
      {
        System.out.println("Sorry-Student not found");
      }
  }

  public void printInfo(){
    super.printInfo();
    System.out.println(gpa + "" + creditHours);
  }//this method was just to check and make sure that the Object was storing info correctly

  //DO THIS METHOD @MIKE
  public void printInvoice(int i, Person student, ArrayList<Person> people)
  {
    //Alright. I think I've managed to put my part in the code. Although, it felt too easy. I still need to put in a condtion where the code prints "No Student entered", if nothing was inputed yet. Is there anything else I need to do. I get off of work at 2:00pm tomorrow. 
    //((Student)(people.get(i))).getName();
      double pay = 0;
		  double check = 3.85;
		  double discount = 0;
		  double dis = 0;
      if(student instanceof Student)
      {
		    if(((Student)(people.get(i))).getGpa() >= check)
		    {
			    dis = 0.25;
			    discount = (((Student)(people.get(i))).getCreditHours()*236.45 + 52)*dis;
			    pay = ((((Student)(people.get(i))).getCreditHours()*236.45 +52) - (((Student)(people.get(i))).getCreditHours()*236.45 + 52)*dis);
		    }
		  
		    else
		    {
			    pay = ((Student)(people.get(i))).getCreditHours()*236.45 + 52;
		    }

      
		    System.out.println("Here is the tuition invoice for " + ((Student)(people.get(i))).getName());
		    System.out.println("---------------------------------------------------------");
		    System.out.println(((Student)(people.get(i))).getName() + "           " + ((Student)(people.get(i))).getId() + "\n");
		    System.out.println("Credit Hours: " + ((Student)(people.get(i))).getCreditHours() + " ($236.45/credit hour)\n");
		    System.out.println("Fees: $52\n\n");
		    System.out.printf("Total payment: $%.2f" + "           " + "($%.2f" + " discount applied)\n", pay, discount);
        System.out.println("---------------------------------------------------------");
    }

  }
}

class IDException extends Exception 
{ 
    public IDException(String s) 
    { 
        // Call constructor of parent Exception 
        super(s); 
    } 
    public IDException() 
    { 
    }
}